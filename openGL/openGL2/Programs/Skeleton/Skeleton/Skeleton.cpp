//=============================================================================================
// Mintaprogram: Zöld háromszög. Ervenyes 2019. osztol.
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
// Nev    : Nagy Bozsó Zsombor
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
    union { float x, r; }; // x és r néven is lehessen hivatkozni erre a tagra.
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
Color Screen::image[width * height]; // A statikus adattagat out-of-line példányosítani kell (kivéve az inteket és enumokat).

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

// Idáig egy általános raytracert definiáltam. Innentõl jönnek a konkrétumok.

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

    // Az óra járásával ellentétes (CCW) körüljárási irányt feltételez ez a kód.
    // A köröljárási irányból döntjük el a normálvektor "elõjelét".
    Triangle(Material* mat, const Vector& a, const Vector& b, const Vector& c)
        : Object(mat), a(a), b(b), c(c) {
        Vector ab = b - a;
        Vector ac = c - a;
        normal = cross(ab.normalize(), ac.normalize()).normalize();
    }

    // Ennek a függvénynek a megértéséhez rajzolj magadnak egyszerû ábrákat!
    Intersection intersectRay(Ray r) {
        // Elõször számoljuk ki, hogy melyen mekkora távot 
        // tesz meg a sugár, míg eléri a háromszög síkját
        // A számoláshoz tudnuk kell hogy ha egy 'v' vektort 
        // skaliráisan szorzunk egy egységvektorral, akkor
        // az eredmény a 'v'-nek az egységvektorra vetített 
        // hossza lesz. Ezt felhasználva, ha a sugár kiindulási 
        // pontjából a sík egy pontjba mutató vektort levetítjük 
        // a sík normál vektorára, akkor megkapjuk, hogy milyen 
        // távol van a sugár kiindulási pontja a síktól. Továbbá,
        // ha az a sugár irányát vetítjük a normálvektorra, akkor meg
        // megtudjuk, hogy az milyen gyorsan halad a sík fele.
        // Innen a már csak a t = s / v képletet kell csak használnunk.
        float ray_travel_dist = dot(a - r.origin, normal) / dot(r.direction, normal);

        // Ha a háromszög az ellenkezõ irányba van, mint 
        // amerre a sugár megy, vagy az elõzõ képletben 
        // nullával osztottunk, akkor nincs metszéspontjuk
        if (ray_travel_dist < 0 || isnan(ray_travel_dist))
            return Intersection();

        // Számoljuk ki, hogy a sugár hol metszi a sugár síkját.
        Vector plane_intersection = r.origin + ray_travel_dist * r.direction;

        /* Most már csak el kell döntenünk, hogy ez a pont a háromszög
           belsejében van-e. Erre két lehetõség van:

           - A háromszög összes élére megnézzük, hogy a pontot a hároszög
           egy megfelelõ pontjával összekötve a kapott szakasz, és a háromszög
           élének a vektoriális szorzata a normál irányába mutat-e.
           Pl:

                     a
                   / |
                  /  |
                 /   |
                /  x |  y
               /     |
              b------c

           Nézzük meg az x és y pontra ezt az algoritmust.
           A cross(ab, ax), a cross(bc, bx), és a cross(ca, cx) és kifele mutat a
           képernyõbõl, ugyan abba az irányba mint a normál vektor. Ezt amúgy a
           dot(cross(ab, ax), normal) >= 0 összefüggéssel egyszerû ellenõrizni.
           Az algoritmus alapján az x a háromszög belsejében van.

           Míg az y esetében a cross(ca, cy) befele mutat, a normállal ellenkezõ irányba,
           tehát a dot(cross(ca, cy), normal) < 0 ami az algoritmus szerint azt jelenti,
           hogy az y pont a háromszögön kívül van.

           - A mádik lehetõség a barycentrikus koordinátáknak azt a tulajdonságát használja
           ki, hogy azok a háromszög belsejében lévõ pontokra kivétel nélkül nem negatívak,
           míg a háromszögön kívül lévõ pontokra legalább egy koordináta negatív.
           Ennek a megoldásnak a használatához ki kell jelölnünk két tetszõleges, de egymásra
           merõleges vektort a síkon, ezekre le kell vetíteninünk a háromszög pontjait, és
           kérdéses pontot, és az így kapott koordinátákra alakzmanunk kell egy a wikipediáról
           egyszerûen kimásolható képletet:
           http://en.wikipedia.org/wiki/Barycentric_coordinate_system#Converting_to_barycentric_coordinates

           Én az elsõ lehetõséget implementálom. */

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

