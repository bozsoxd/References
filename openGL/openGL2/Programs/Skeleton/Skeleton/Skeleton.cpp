//=============================================================================================
// Mintaprogram: Z�ld h�romsz�g. Ervenyes 2019. osztol.
//
// A beadott program csak ebben a fajlban lehet, a fajl 1 byte-os ASCII karaktereket tartalmazhat, BOM kihuzando.
// Tilos:
// - mast "beincludolni", illetve mas konyvtarat hasznalni
// - faljmuveleteket vegezni a printf-et kiveve
// - Mashonnan atvett programresszleteket forrasmegjeloles nelkul felhasznalni es
// - felesleges programsorokat a beadott programban hagyni!!!!!!! 
// - felesleges kommenteket a beadott programba irni a forrasmegjelolest kommentjeit kiveve
// ---------------------------------------------------------------------------------------------
// A feladatot ANSI C++ nyelvu forditoprogrammal ellenorizzuk, a Visual Studio-hoz kepesti elteresekrol
// es a leggyakoribb hibakrol (pl. ideiglenes objektumot nem lehet referencia tipusnak ertekul adni)
// a hazibeado portal ad egy osszefoglalot.
// ---------------------------------------------------------------------------------------------
// A feladatmegoldasokban csak olyan OpenGL fuggvenyek hasznalhatok, amelyek az oran a feladatkiadasig elhangzottak 
// A keretben nem szereplo GLUT fuggvenyek tiltottak.
//
// NYILATKOZAT
// ---------------------------------------------------------------------------------------------
// Nev    : Nagy Bozs� Zsombor
// Neptun : RIPKBY
// ---------------------------------------------------------------------------------------------
// ezennel kijelentem, hogy a feladatot magam keszitettem, es ha barmilyen segitseget igenybe vettem vagy
// mas szellemi termeket felhasznaltam, akkor a forrast es az atvett reszt kommentekben egyertelmuen jeloltem.
// A forrasmegjeloles kotelme vonatkozik az eloadas foliakat es a targy oktatoi, illetve a
// grafhazi doktor tanacsait kiveve barmilyen csatornan (szoban, irasban, Interneten, stb.) erkezo minden egyeb
// informaciora (keplet, program, algoritmus, stb.). Kijelentem, hogy a forrasmegjelolessel atvett reszeket is ertem,
// azok helyessegere matematikai bizonyitast tudok adni. Tisztaban vagyok azzal, hogy az atvett reszek nem szamitanak
// a sajat kontribucioba, igy a feladat elfogadasarol a tobbi resz mennyisege es minosege alapjan szuletik dontes.
// Tudomasul veszem, hogy a forrasmegjeloles kotelmenek megsertese eseten a hazifeladatra adhato pontokat
// negativ elojellel szamoljak el es ezzel parhuzamosan eljaras is indul velem szemben.
//=============================================================================================
#include "framework.h"

#include <math.h>
#include <stdlib.h>

#if defined(__APPLE__)
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>
#else
#if defined(WIN32) || defined(_WIN32) || defined(__WIN32__)
#include <windows.h>
#endif
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#endif

#ifndef M_PI
#define M_PI 3.14159265359
#endif



struct Vector {
    union { float x, r; }; // x �s r n�ven is lehessen hivatkozni erre a tagra.
    union { float y, g; };
    union { float z, b; };

    Vector(float v = 0) : x(v), y(v), z(v) { }
    Vector(float x, float y, float z) : x(x), y(y), z(z) { }
    Vector operator+(const Vector& v) const { return Vector(x + v.x, y + v.y, z + v.z); }
    Vector operator-(const Vector& v) const { return Vector(x - v.x, y - v.y, z - v.z); }
    Vector operator*(const Vector& v) const { return Vector(x * v.x, y * v.y, z * v.z); }
    Vector operator/(const Vector& v) const { return Vector(x / v.x, y / v.y, z / v.z); }
    friend Vector operator+(float f, const Vector& v) { return v + f; }
    friend Vector operator-(float f, const Vector& v) { return Vector(f) - v; }
    friend Vector operator*(float f, const Vector& v) { return v * f; }
    friend Vector operator/(float f, const Vector& v) { return Vector(f) / v; }
    Vector& operator+=(const Vector& v) { x += v.x, y += v.y, z += v.z; return *this; }
    Vector& operator-=(const Vector& v) { x -= v.x, y -= v.y, z -= v.z; return *this; }
    Vector& operator*=(const Vector& v) { x *= v.x, y *= v.y, z *= v.z; return *this; }
    Vector& operator/=(const Vector& v) { x /= v.x, y /= v.y, z /= v.z; return *this; }
    Vector operator-() const { return Vector(-x, -y, -z); }
    float dot(const Vector& v) const { return x * v.x + y * v.y + z * v.z; }
    friend float dot(const Vector& lhs, const Vector& rhs) { return lhs.dot(rhs); }
    Vector cross(const Vector& v) const { return Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x); }
    friend Vector cross(const Vector& lhs, const Vector& rhs) { return lhs.cross(rhs); }
    float length() const { return sqrt(x * x + y * y + z * z); }
    Vector normalize() const { float l = length(); if (l > 1e-3) { return (*this / l); } else { return Vector(); } }
    bool isNull() const { return length() < 1e-3; }
    Vector saturate() const { return Vector(max(min(x, 1.0f), 0.0f), max(min(y, 1.0f), 0.0f), max(min(z, 1.0f), 0.0f)); }
};

typedef Vector Color;

struct Screen {
    static const int width = 600;
    static const int height = 600;
    static Color image[width * height];
    static void Draw() {
        glDrawPixels(width, height, GL_RGB, GL_FLOAT, image);
    }
    static Color& Pixel(size_t x, size_t y) {
        return image[y * width + x];
    }
};
Color Screen::image[width * height]; // A statikus adattagat out-of-line p�ld�nyos�tani kell (kiv�ve az inteket �s enumokat).

struct Ray {
    Vector origin, direction;
};

struct Intersection {
    Vector pos, normal;
    bool is_valid;
    Intersection(Vector pos = Vector(), Vector normal = Vector(), bool is_valid = false)
        : pos(pos), normal(normal), is_valid(is_valid) { }
};

struct Light {
    enum LightType { Ambient, Directional } type;
    Vector pos, dir;
    Color color;
};

struct Material {
    virtual ~Material() { }
    virtual Color getColor(Intersection, const Light[], size_t) = 0;
};

struct Object {
    Material* mat;
    Object(Material* m) : mat(m) { }
    virtual ~Object() { }
    virtual Intersection intersectRay(Ray) = 0;
};

struct Scene {
    static const size_t max_obj_num = 100;
    size_t obj_num;
    Object* objs[max_obj_num];

    void AddObject(Object* o) {
        objs[obj_num++] = o;
    }

    ~Scene() {
        for (int i = 0; i != obj_num; ++i) {
            delete objs[i];
        }
    }

    static const size_t max_lgt_num = 10;
    size_t lgt_num;
    Light lgts[max_obj_num];

    void AddLight(const Light& l) {
        lgts[lgt_num++] = l;
    }

    static const Vector env_color;

    Scene() : obj_num(0) { }

    Intersection getClosestIntersection(Ray r, int* index = NULL) const {
        Intersection closest_intersection;
        float closest_intersection_dist;
        int closest_index = -1;

        for (int i = 0; i < obj_num; ++i) {
            Intersection inter = objs[i]->intersectRay(r);
            if (!inter.is_valid)
                continue;
            float dist = (inter.pos - r.origin).length();
            if (closest_index == -1 || dist < closest_intersection_dist) {
                closest_intersection = inter;
                closest_intersection_dist = dist;
                closest_index = i;
            }
        }

        if (index) {
            *index = closest_index;
        }
        return closest_intersection;
    }

    Color shootRay(Ray r) const {
        int index;
        Intersection inter = getClosestIntersection(r, &index);

        if (index != -1) {
            return objs[index]->mat->getColor(inter, lgts, lgt_num);
        }
        else {
            return env_color;
        }
    }
} scene;
const Vector Scene::env_color = Vector();

struct Camera {
    Vector pos, plane_pos, right, up;

    Camera(float fov, const Vector& eye, const Vector& target, const Vector& plane_up)
        : pos(eye), plane_pos(eye + (target - eye).normalize())
    {
        Vector fwd = (plane_pos - pos).normalize();
        float plane_half_size = tan((fov * M_PI / 180) / 2);

        right = plane_half_size * cross(fwd, plane_up).normalize();
        up = plane_half_size * cross(right, fwd).normalize();
    }

    void takePicture() {
        for (int x = 0; x < Screen::height; ++x)
            for (int y = 0; y < Screen::width; ++y)
                capturePixel(x, y);
    }

    void capturePixel(float x, float y) {
        Vector pos_on_plane = Vector(
            (x + 0.5f - Screen::width / 2) / (Screen::width / 2),
            (y + 0.5f - Screen::height / 2) / (Screen::height / 2),
            0
        );

        Vector plane_intersection = plane_pos + pos_on_plane.x * right + pos_on_plane.y * up;

        Ray r = { pos, (plane_intersection - pos).normalize() };
        Screen::Pixel(x, y) = scene.shootRay(r);
    }
} camera(60, Vector(-3, 2, -2), Vector(), Vector(0, 1, 0));

// Id�ig egy �ltal�nos raytracert defini�ltam. Innent�l j�nnek a konkr�tumok.

struct DiffuseMaterial : public Material {
    Color own_color;

    DiffuseMaterial(const Color& color) : own_color(color) { }

    Color getColor(Intersection inter, const Light* lgts, size_t lgt_num) {
        Color accum_color;

        for (int i = 0; i < lgt_num; ++i) {
            const Light& light = lgts[i];
            switch (light.type) {
            case Light::Ambient: {
                accum_color += light.color * own_color;
            } break;
            case Light::Directional: {
                float intensity = max(dot(inter.normal, light.dir.normalize()), 0.0f);
                accum_color += intensity * light.color * own_color;
            } break;
            }
        }

        return accum_color.saturate();
    }
};

DiffuseMaterial blue(Color(0.0f, 0.4f, 1.0f));


struct Triangle : public Object {
    Vector a, b, c, normal;

    // Az �ra j�r�s�val ellent�tes (CCW) k�r�lj�r�si ir�nyt felt�telez ez a k�d.
    // A k�r�lj�r�si ir�nyb�l d�ntj�k el a norm�lvektor "el�jel�t".
    Triangle(Material* mat, const Vector& a, const Vector& b, const Vector& c)
        : Object(mat), a(a), b(b), c(c) {
        Vector ab = b - a;
        Vector ac = c - a;
        normal = cross(ab.normalize(), ac.normalize()).normalize();
    }

    // Ennek a f�ggv�nynek a meg�rt�s�hez rajzolj magadnak egyszer� �br�kat!
    Intersection intersectRay(Ray r) {
        // El�sz�r sz�moljuk ki, hogy melyen mekkora t�vot 
        // tesz meg a sug�r, m�g el�ri a h�romsz�g s�kj�t
        // A sz�mol�shoz tudnuk kell hogy ha egy 'v' vektort 
        // skalir�isan szorzunk egy egys�gvektorral, akkor
        // az eredm�ny a 'v'-nek az egys�gvektorra vet�tett 
        // hossza lesz. Ezt felhaszn�lva, ha a sug�r kiindul�si 
        // pontj�b�l a s�k egy pontjba mutat� vektort levet�tj�k 
        // a s�k norm�l vektor�ra, akkor megkapjuk, hogy milyen 
        // t�vol van a sug�r kiindul�si pontja a s�kt�l. Tov�bb�,
        // ha az a sug�r ir�ny�t vet�tj�k a norm�lvektorra, akkor meg
        // megtudjuk, hogy az milyen gyorsan halad a s�k fele.
        // Innen a m�r csak a t = s / v k�pletet kell csak haszn�lnunk.
        float ray_travel_dist = dot(a - r.origin, normal) / dot(r.direction, normal);

        // Ha a h�romsz�g az ellenkez� ir�nyba van, mint 
        // amerre a sug�r megy, vagy az el�z� k�pletben 
        // null�val osztottunk, akkor nincs metsz�spontjuk
        if (ray_travel_dist < 0 || isnan(ray_travel_dist))
            return Intersection();

        // Sz�moljuk ki, hogy a sug�r hol metszi a sug�r s�kj�t.
        Vector plane_intersection = r.origin + ray_travel_dist * r.direction;

        /* Most m�r csak el kell d�nten�nk, hogy ez a pont a h�romsz�g
           belsej�ben van-e. Erre k�t lehet�s�g van:

           - A h�romsz�g �sszes �l�re megn�zz�k, hogy a pontot a h�rosz�g
           egy megfelel� pontj�val �sszek�tve a kapott szakasz, �s a h�romsz�g
           �l�nek a vektori�lis szorzata a norm�l ir�ny�ba mutat-e.
           Pl:

                     a
                   / |
                  /  |
                 /   |
                /  x |  y
               /     |
              b------c

           N�zz�k meg az x �s y pontra ezt az algoritmust.
           A cross(ab, ax), a cross(bc, bx), �s a cross(ca, cx) �s kifele mutat a
           k�perny�b�l, ugyan abba az ir�nyba mint a norm�l vektor. Ezt am�gy a
           dot(cross(ab, ax), normal) >= 0 �sszef�gg�ssel egyszer� ellen�rizni.
           Az algoritmus alapj�n az x a h�romsz�g belsej�ben van.

           M�g az y eset�ben a cross(ca, cy) befele mutat, a norm�llal ellenkez� ir�nyba,
           teh�t a dot(cross(ca, cy), normal) < 0 ami az algoritmus szerint azt jelenti,
           hogy az y pont a h�romsz�g�n k�v�l van.

           - A m�dik lehet�s�g a barycentrikus koordin�t�knak azt a tulajdons�g�t haszn�lja
           ki, hogy azok a h�romsz�g belsej�ben l�v� pontokra kiv�tel n�lk�l nem negat�vak,
           m�g a h�romsz�g�n k�v�l l�v� pontokra legal�bb egy koordin�ta negat�v.
           Ennek a megold�snak a haszn�lat�hoz ki kell jel�ln�nk k�t tetsz�leges, de egym�sra
           mer�leges vektort a s�kon, ezekre le kell vet�tenin�nk a h�romsz�g pontjait, �s
           k�rd�ses pontot, �s az �gy kapott koordin�t�kra alakzmanunk kell egy a wikipedi�r�l
           egyszer�en kim�solhat� k�pletet:
           http://en.wikipedia.org/wiki/Barycentric_coordinate_system#Converting_to_barycentric_coordinates

           �n az els� lehet�s�get implement�lom. */

        const Vector& x = plane_intersection;

        Vector ab = b - a;
        Vector ax = x - a;

        Vector bc = c - b;
        Vector bx = x - b;

        Vector ca = a - c;
        Vector cx = x - c;

        if (dot(cross(ab, ax), normal) >= 0)
            if (dot(cross(bc, bx), normal) >= 0)
                if (dot(cross(ca, cx), normal) >= 0)
                    return Intersection(x, normal, true);

        return Intersection();
    }
};

void onInitialization() {
    Light amb = { Light::Ambient, Vector(), Vector(), Color(0.2f, 0.2f, 0.2f) };
    Light dir = { Light::Directional, Vector(), Vector(-2, 4, -1), Color(1.0f, 1.0f, 1.0f) };
    scene.AddLight(amb);
    scene.AddLight(dir);

    float s = 0.5f;

    // Front face
    scene.AddObject(new Triangle(&blue, Vector(+s, -s, -s), Vector(-s, -s, -s), Vector(-s, +s, -s)));
    scene.AddObject(new Triangle(&blue, Vector(-s, +s, -s), Vector(+s, +s, -s), Vector(+s, -s, -s)));

    // Back face
    scene.AddObject(new Triangle(&blue, Vector(+s, -s, +s), Vector(-s, -s, +s), Vector(-s, +s, +s)));
    scene.AddObject(new Triangle(&blue, Vector(-s, +s, +s), Vector(+s, +s, +s), Vector(+s, -s, +s)));

    // Right face
    scene.AddObject(new Triangle(&blue, Vector(+s, -s, -s), Vector(+s, -s, +s), Vector(+s, +s, +s)));
    scene.AddObject(new Triangle(&blue, Vector(+s, +s, +s), Vector(+s, +s, -s), Vector(+s, -s, -s)));

    // Left face
    scene.AddObject(new Triangle(&blue, Vector(-s, -s, -s), Vector(-s, -s, +s), Vector(-s, +s, +s)));
    scene.AddObject(new Triangle(&blue, Vector(-s, +s, +s), Vector(-s, +s, -s), Vector(-s, -s, -s)));

    // Upper face
    scene.AddObject(new Triangle(&blue, Vector(-s, +s, -s), Vector(-s, +s, +s), Vector(+s, +s, +s)));
    scene.AddObject(new Triangle(&blue, Vector(+s, +s, -s), Vector(-s, +s, -s), Vector(+s, +s, +s)));

    // Lower face
    scene.AddObject(new Triangle(&blue, Vector(-s, -s, +s), Vector(-s, -s, -s), Vector(+s, -s, +s)));
    scene.AddObject(new Triangle(&blue, Vector(+s, -s, -s), Vector(+s, -s, +s), Vector(-s, -s, -s)));

    camera.takePicture();
}

void onDisplay() {
    glClear(GL_COLOR_BUFFER_BIT);

    Screen::Draw();

    glutSwapBuffers();
}

void onIdle() {
    static bool first_call = true;
    if (first_call) {
        glutPostRedisplay();
        first_call = false;
    }
}

void onKeyboard(unsigned char key, int, int) {}

void onKeyboardUp(unsigned char key, int, int) {}

void onMouse(int, int, int, int) {}

void onMouseMotion(int, int) {}

