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

const char * const vertexSource = R"(
	#version 330				// Shader 3.3
	precision highp float;		// normal floats, makes no difference on desktop computers

	uniform mat4 MVP;			// uniform variable, the Model-View-Projection transformation matrix
	layout(location = 0) in vec2 vp;	// Varying input: vp = vertex position is expected in attrib array 0

	void main() {
		gl_Position = vec4(vp.x, vp.y, 0, 1) * MVP;		// transform vp from modeling space to normalized device space
	}
)";


const char * const fragmentSource = R"(
	#version 330			// Shader 3.3
	precision highp float;	// normal floats, makes no difference on desktop computers
	
	uniform vec3 color;		// uniform variable, the color of the primitive
	out vec4 outColor;		// computed color of the current pixel

	void main() {
		outColor = vec4(color, 1);	// computed color is the color of the primitive
	}
)";

GPUProgram gpuProgram; 

vec3 normal(vec3 v);
float lorentz(vec3 v1, vec3 v2);
vec3 meroleges(vec3 vektor, vec3 pont);
float tav(vec3 p1, vec3 p2);
vec3 irany(vec3 p1, vec3 p2);
vec3 ujPont(vec3 start, vec3 irany, float t);
vec3 ujSebesseg(vec3 start, vec3 irany, float t, vec3 CurrentPos);
vec3 forgatas(vec3 p, vec3 v, float phi);
vec2 convertToScreen(vec3 v);
vec3 visszavetitPont(vec3 p);
vec3 visszavetitVektor(vec3 v, vec3 p);
void drawAll(int type, std::vector<vec2> vertices, vec4 color);
bool compare_float(float x, float y);

bool compare_float(float x, float y) {
	float epsilon = 0.01f;
	if (fabs(x - y) < epsilon)
		return true; 
	return false; 
}

void drawAll(int type, std::vector<vec2> vertices, vec4 color) {
	int location = glGetUniformLocation(gpuProgram.getId(), "color");
	glUniform3f(location, color.x, color.y, color.z); 
	glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(vec2), &vertices[0], GL_DYNAMIC_DRAW);
	glDrawArrays(type, 0, vertices.size());


	glEnableVertexAttribArray(0);  
	glVertexAttribPointer(0,       
		2, GL_FLOAT, GL_FALSE, 
		0, NULL); 		    

}

vec3 normal(vec3 v) {
	float a = sqrtf(lorentz(v, v));
	v.x /= a;
	v.y /= a;
	v.z /= a;
	return v;
}

float lorentz(vec3 v1, vec3 v2) {
	return v1.x * v2.x + v1.y * v2.y - v1.z * v2.z;
}


vec3 meroleges(vec3 vektor, vec3 pont) {
	visszavetitPont(pont);
	vektor.z *= -1;
	pont.z *= -1;
	vec3 ret = cross(vektor, pont);
	return ret;
}

float tav(vec3 p1, vec3 p2) {
	float tav = acoshf(lorentz(-p1, p2));
	return tav;

}

vec3 irany(vec3 p1, vec3 p2) {
	float dist = tav(p1, p2);
	p1 = visszavetitPont(p1 *coshf(dist));
	vec3 orient = normal((p2 - p1) / sinhf(dist));
	return orient;
}

vec3 ujPont(vec3 start, vec3 irany, float t) {
	start = visszavetitPont(start);
	vec3 ret = start * coshf(t) + irany * sinhf(t);
	return ret;
}


vec3 ujSebesseg(vec3 start, vec3 irany, float t, vec3 CurrentPos) {
	vec3 ret = start * sinhf(t) + irany * coshf(t);
	return ret;
}

vec3 forgatas(vec3 p, vec3 v, float phi) {
	p = visszavetitPont(p);
	vec3 ret = v * cosf(phi*M_PI/180) + meroleges(v, p) * sinf(phi * M_PI / 180);
	ret = normal(ret);
	return ret;
	
}

vec2 convertToScreen(vec3 v) {
	v = visszavetitPont(v);
	vec2 ret(v.x / (v.z + 1), v.y / (v.z + 1));
	return ret;
}

vec3 visszavetitPont(vec3 p) {
	p.z = sqrt(p.x * p.x + p.y * p.y + 1);
	return p;
}

vec3 visszavetitVektor(vec3 v, vec3 p) {
	float lambda = lorentz(v, p);
	v = (v + lambda * p);
	return v;

}

unsigned int vao;


class Disc {
	 
	vec4 color;
	float radius;
	vec3 center;
	std::vector<vec2> vtx;
	
	

public:
	Disc(vec4 c) {
		vec3 cent = vec3(0.0f, 0.0f, 0.0f);

		color = c;
		radius = 1;


			for (int i = 0; i < 100; i++) {
				float phi = i * 2.0f * M_PI / 100;
				vtx.push_back(vec2(cosf(phi), sinf(phi)));
			}
			
	}
	void draw() {

		drawAll(GL_TRIANGLE_FAN, vtx, color);
	}
};

vec4 blue(0, 0, 1);
vec4 green(0, 1, 0);
vec4 black(0.0f, 0.0f, 0.0f);
vec4 white(1, 1, 1, 1);
vec4 red(1, 0, 0, 1);

class Mouth {
	vec4 color;
	float radius;
	vec3 hypCenter;
	std::vector<vec2> vtx;
	std::vector<vec3> vtxhyp;


public:
	Mouth(vec4 c, vec3 hcent, float r) {
		hypCenter = hcent;
		color = c;
		radius = r;

		

		vec3 mer = visszavetitPont(vec3(1, 0, 0));

		vtx.clear();
		vtxhyp.clear();


		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0)))), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, mer)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));

		}



	
	}
	void draw() {

		drawAll(GL_TRIANGLE_FAN, vtx, color);
	}

	
};

class EyeBlack {
	vec4 color;
	float radius;
	vec3 hypCenter;
	std::vector<vec2> vtx;
	std::vector<vec3> vtxhyp;




public:
	EyeBlack(vec4 c, vec3 hcent, float r) {
		hypCenter = hcent;
		color = c;
		radius = r;




		vec3 mer = visszavetitPont(vec3(1, 0, 0));

		vtx.clear();
		vtxhyp.clear();


		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0)))), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, mer)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));

		}




	}
	void draw() {

		drawAll(GL_TRIANGLE_FAN, vtx, color);

	}


};


class EyeWhite {
	vec4 color;
	float radius;
	vec3 hypCenter;
	std::vector<vec2> vtx;
	std::vector<vec3> vtxhyp;
	vec3 masikhamiCent;

public:
	EyeWhite(vec4 c, vec3 hcent, float r) {
		hypCenter = hcent;
		color = c;
		radius = r;



		vec3 mer = visszavetitPont(vec3(1, 0, 0));

		vtx.clear();
		vtxhyp.clear();


		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0)))), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(meroleges(hypCenter, mer)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));

		}




	}
	void draw(vec3 masikHam) {

		drawAll(GL_TRIANGLE_FAN, vtx, color);
		vec3 ebOrient = irany(hypCenter, masikHam);
		EyeBlack* eb = new EyeBlack(black, ujPont(hypCenter, normal(visszavetitVektor(ebOrient, hypCenter)), radius - 0.03f), 0.03f);
		eb->draw();
		delete eb;
	}


};




std::vector<vec2> greenHamiPoints, redHamiPoints;

class Hami {

	vec4 color;
	float radius;
	vec3 hypCenter;
	
	std::vector<vec2> vtx;
	std::vector<vec3> vtxhyp;
	
	vec3 orient;
	vec3 mer;
	std::vector<vec2> thisHamiPoints;
	float mouthRadius;
	bool szajno;
	
public:
	Hami(vec4 c, vec3 hcent, float r, std::vector<vec2> hamiPoints) {
		hypCenter = hcent;
		color = c;
		radius = r;
		std::vector<vec2> thisHamiPoints = hamiPoints;
		thisHamiPoints.push_back(convertToScreen(hypCenter));
		mouthRadius = 0.2f;
		szajno = false;
		mer = visszavetitPont(vec3(1, 0, 0));
		
		
		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0))), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);

			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, mer), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));
		}
		orient = irany(hypCenter, vtxhyp[0]);



		
	}
	void draw(vec3 masikCent) {
		drawAll(GL_LINE_STRIP,thisHamiPoints, white);
		drawAll(GL_TRIANGLE_FAN, vtx, color);
		 
		



			if (szajno == false && mouthRadius > 0.01f) {
				mouthRadius -= 0.01f;
			}
			else if (szajno == false && compare_float(mouthRadius, 0.01f)) {
				mouthRadius += 0.01f;
				szajno = true;
			}
			else if (szajno == true && mouthRadius < 0.2f) {
				mouthRadius += 0.01f;
			}
			else if (szajno == true && compare_float(mouthRadius, 0.2f)) {
				mouthRadius -= 0.01f;
				szajno = false;
			}

		

		Mouth* m = new Mouth(black, ujPont(hypCenter, normal(visszavetitVektor(orient, hypCenter)), radius), mouthRadius);
		 
		 EyeWhite* e1 = new EyeWhite(white, ujPont(hypCenter, normal(visszavetitVektor(forgatas(hypCenter, orient, -30), hypCenter)), radius), 0.1f);
		 EyeWhite* e2 = new EyeWhite(white, ujPont(hypCenter, normal(visszavetitVektor(forgatas(hypCenter, orient, 30), hypCenter)), radius), 0.1f);
		 

		m->draw();
		e1->draw(masikCent);
		e2->draw(masikCent);
		delete m;
		delete e1;
		delete e2;
	}


	void move(vec3 masikHam) {
		hypCenter =visszavetitPont(ujPont(hypCenter, orient, 0.001f));
		vtx.clear();
		vtxhyp.clear();
		thisHamiPoints.push_back(convertToScreen(hypCenter));


		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0))), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, mer), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));
		}
		draw(masikHam);
	}
	void rotateright() {
		
		orient = normal(visszavetitVektor(forgatas(hypCenter, orient, -0.5), hypCenter));
	}
	void rotateleft() {

		orient = normal(visszavetitVektor(forgatas(hypCenter, orient, 0.5), hypCenter));

	}


	void moveAndRotate(vec3 masikham) {
		orient = normal(visszavetitVektor(forgatas(hypCenter, orient, 10), hypCenter));
		hypCenter = visszavetitPont(ujPont(hypCenter, orient, 0.1f));
		vtx.clear();
		vtxhyp.clear();
		thisHamiPoints.push_back(convertToScreen(hypCenter));


		for (int i = 0; i < 60; i++) {
			vec3 temp;
			if (mer.x == hypCenter.x && mer.y == hypCenter.y && mer.z == hypCenter.z) {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, visszavetitPont(vec3(0, 0, 0))), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			else {
				temp = visszavetitPont(ujPont(hypCenter, forgatas(hypCenter, normal(visszavetitVektor(meroleges(hypCenter, mer), hypCenter)), i * 6), radius));
				vtxhyp.push_back(temp);
			}
			vtx.push_back(convertToScreen(temp));
		}
		draw(masikham);
	}

	vec3 getCenter() {
		return hypCenter;
	}

};






Disc *poincare;
Hami* redHam;
Hami* greenHam;





// Initialization, create an OpenGL context
void onInitialization() {
	glViewport(0, 0, windowWidth, windowHeight);
	glPointSize(2);
	glLineWidth(2);
	glEnable(GL_LINE_SMOOTH);
	glGenVertexArrays(1, &vao);
	glBindVertexArray(vao);		// make it active
	unsigned int vbo;
	glGenBuffers(1, &vbo);	// Generate 1 buffer
	glBindBuffer(GL_ARRAY_BUFFER, vbo);



	poincare = new Disc(black);
	redHam = new Hami(red, visszavetitPont(vec3(1, 2, 0)), 0.5f, redHamiPoints);
	greenHam = new Hami(green, visszavetitPont(vec3(-1, -2, 0)), 0.5f, greenHamiPoints);

	gpuProgram.create(vertexSource, fragmentSource, "outColor");
}

// Window has become invalid: Redraw
void onDisplay() {
	glClearColor(0.5f, 0.5f, 0.5f, 1);     // background color
	glClear(GL_COLOR_BUFFER_BIT); // clear frame buffer
	int location;
	float MVPtransf[4][4] = { 1, 0, 0, 0,    // mvp matrix, 
							  0, 1, 0, 0,    // row-major!
							  0, 0, 1, 0,
							  0, 0, 0, 1 };
	location = glGetUniformLocation(gpuProgram.getId(), "MVP");	// Get the GPU location of uniform variable MVP
	glUniformMatrix4fv(location, 1, GL_TRUE, &MVPtransf[0][0]);	// Load a 4x4 row-major float matrix to the specified location
	poincare->draw();
	redHam->draw(greenHam->getCenter());
	greenHam->draw(redHam->getCenter());

	glutSwapBuffers(); // exchange buffers for double buffering
}

bool pressed[256] = { false};


// Key of ASCII code pressed
void onKeyboard(unsigned char key, int pX, int pY) {
	pressed[key] = true;
}

// Key of ASCII code released
void onKeyboardUp(unsigned char key, int pX, int pY) {
	pressed[key] = false;
}


// Move mouse with key pressed
void onMouseMotion(int pX, int pY) {	// pX, pY are the pixel coordinates of the cursor in the coordinate system of the operation system
	
}

// Mouse click event
void onMouse(int button, int state, int pX, int pY) { // pX, pY are the pixel coordinates of the cursor in the coordinate system of the operation system
	

	
}

float oldTime = 0;
// Idle event indicating that some time elapsed: do animation here
void onIdle() {
	oldTime = 0;
	float time = glutGet(GLUT_ELAPSED_TIME)/100; // elapsed time since the start of the program
	float dif = time - oldTime;
	oldTime = time;
	for (int i = 0; i < dif; i++) {
		 greenHam->moveAndRotate(redHam->getCenter());
		 if (pressed['e']) {
			 redHam->move(greenHam->getCenter());

		 }
		 if (pressed['s']) {
			 redHam->rotateright();
		 }
		 if (pressed['f']) {
			 redHam->rotateleft();

		 }

		 glutPostRedisplay();
	 }
	

	/*if (time % 200 == 0)
	greenHam->moveAndRotate(redHam->getCenter());
	
	
	
	if (pressed['e']) {
		redHam->move(greenHam->getCenter());
		
	}
	if (pressed['s']) {
		redHam->rotateright();
		pressed['s'] = false;
	}
	if (pressed['f']) {
		redHam->rotateleft();
		pressed['f'] = false;
	}
	
	glutPostRedisplay();
	*/
}
