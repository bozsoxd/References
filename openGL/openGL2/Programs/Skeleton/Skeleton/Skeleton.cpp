#include "framework.h"



struct Hit {
	float t;
	vec3 position, normal;
	Hit() { t = -1; }
};

struct Ray {
	vec3 start, dir;
	Ray(vec3 _start, vec3 _dir) { start = _start; dir = normalize(_dir); }
};

class Intersectable {
protected:
public:
	virtual Hit intersect(const Ray& ray) = 0;
};
const float epsilon = 0.0001f;
const float epsilonBig = 0.01f;




struct Cube : public Intersectable {
	Cube() {}

	std::vector<vec3> vertexes{
		vec3(0.0f, 0.0f, 0.0f),
		vec3(0.0f, 0.0f, 1.0f),
		vec3(0.0f, 1.0f, 0.0f),
		vec3(0.0f, 1.0f, 1.0f),
		vec3(1.0f, 0.0f, 0.0f),
		vec3(1.0f, 0.0f, 1.0f),
		vec3(1.f, 1.0f, 0.0f),
		vec3(1.0f, 1.0f, 1.0f)
	};



	std::vector<vec3> faxceIndexes{
		vec3(1,7,5),
		vec3(1,3,7),
		vec3(1,4,3),
		vec3(1,2,4),
		vec3(3,8,7),
		vec3(3,4,8),
		vec3(5,7,8),
		vec3(5,8,6),
		vec3(1,5,6),
		vec3(1,6,2),
		vec3(2,6,8),
		vec3(2,8,4)
	};


	Hit intersect(const Ray& ray) {
		Hit hit;
		
		for (int i = 0; i < 12; i++) {
			vec3 r1 = vertexes[faxceIndexes[i].x - 1];
			vec3 r2 = vertexes[faxceIndexes[i].y - 1];
			vec3 r3 = vertexes[faxceIndexes[i].z - 1];
			
			vec3 n = cross(r2 - r1, r3 - r1);
			
			float t = dot((r1 - ray.start), n) / dot(ray.dir, n);
			if (t < 0.01f) continue;

			vec3 hitPoint = ray.start + (ray.dir * t);

			float intersect1 = dot(cross(r2 - r1, hitPoint - r1), n);
			float intersect2 = dot(cross(r3 - r2, hitPoint - r2), n);
			float intersect3 = dot(cross(r1 - r3, hitPoint - r3), n);
			if (intersect1 > 0 && intersect2 > 0 && intersect3 > 0 && dot(ray.dir, normalize(n)) > 0.0f) {
				hit.normal = normalize(n);
				hit.position = hitPoint;
				hit.t = t;
			}

		}
		return hit;
	}



};



struct Icosahedron : public Intersectable {
	Icosahedron() {}
	float div = 5.0f;
	std::vector<vec3> vertexes{
		vec3(0.0f, -0.525731f, 0.850651f),
		vec3(0.850651f, 0.0f, 0.525731f),
		vec3(0.850651f, 0.0f, -0.525731f),
		vec3(-0.850651f, 0.0f, -0.525731f),
		vec3(-0.850651f, 0.0f, 0.525731f),
		vec3(-0.525731f, 0.850651f, 0.0f),
		vec3(0.525731f, 0.850651f, 0.0f),
		vec3(0.525731f, -0.850651f, 0.0f),
		vec3(-0.525731f, -0.850651f, 0.0f),
		vec3(0.0f, -0.525731f, -0.850651f),
		vec3(0.0f, 0.525731f, -0.850651f),
		vec3(0.0f, 0.525731f, 0.850651f)
	};



	std::vector<vec3> faxceIndexes{
		vec3(2,3,7),
		vec3(2,8,3),
		vec3(4,5,6),
		vec3(5,4,9),
		vec3(7,6,12),
		vec3(6,7,11),
		vec3(10,11,3),
		vec3(11,10,4),
		vec3(8,9,10),
		vec3(9,8,1),
		vec3(12,1,2),
		vec3(1,12,5),
		vec3(7,3,11),
		vec3(2,7,12),
		vec3(4,6,11),
		vec3(6,5,12),
		vec3(3,8,10),
		vec3(8,2,1),
		vec3(4,10,9),
		vec3(5,9,1)
	};


	Hit intersect(const Ray& ray) {
		Hit hit;
		float back = 0;

		for (int i = 0; i < 20; i++) {

			vec3 r1 = vertexes[faxceIndexes[i].x - 1] * 0.2f + vec3(0.8f, 0.2f, 0.5f);
			vec3 r2 = vertexes[faxceIndexes[i].y - 1] * 0.2f + vec3(0.8f, 0.2f, 0.5f);
			vec3 r3 = vertexes[faxceIndexes[i].z - 1] * 0.2f + vec3(0.8f, 0.2f, 0.5f);


			vec3 n = cross(r2 - r1, r3 - r1);
			float t = dot((r1 - ray.start), n) / dot(ray.dir, n);
			if (t < 0.01f) continue;



			vec3 hitPoint = ray.start + (ray.dir * t);

			float intersect1 = dot(cross(r2 - r1, hitPoint - r1), n);
			float intersect2 = dot(cross(r3 - r2, hitPoint - r2), n);
			float intersect3 = dot(cross(r1 - r3, hitPoint - r3), n);

			if (t > back && intersect1 > 0 && intersect2 > 0 && intersect3 > 0) {
				back = t;
				hit.t = t;
				hit.normal = normalize(n);
				hit.position = hitPoint;
			}

		}

		return hit;
	}



};


struct Diamond : public Intersectable {
	Diamond() {}
	float div = 5.0f;
	std::vector<vec3> vertexes{
		vec3(1, 0 ,0 ),
		vec3(0,-1 ,0 ),
		vec3(-1, 0 ,0 ),
		vec3(0, 1 ,0 ),
		vec3(0, 0 ,1 ),
		vec3(0, 0 ,-1 )

	};


	std::vector<vec3> faxceIndexes{
		vec3(2,1,5),
		vec3(3,2,5),
		vec3(4,3,5),
		vec3(1,4,5),
		vec3(1,2,6),
		vec3(2,3,6),
		vec3(3,4,6),
		vec3(4,1,6)
	};


	
		Hit intersect(const Ray& ray) {
			Hit hit;
			float back = 0;

			for (int i = 0; i < 8; i++) {

				vec3 r1 = vertexes[faxceIndexes[i].x - 1] * 0.2f + vec3(0.6f, 0.2f, 0.9f);
				vec3 r2 = vertexes[faxceIndexes[i].y - 1] * 0.2f + vec3(0.6f, 0.2f, 0.9f);
				vec3 r3 = vertexes[faxceIndexes[i].z - 1] * 0.2f + vec3(0.6f, 0.2f, 0.9f);



				vec3 n = cross(r2 - r1, r3 - r1);
				float t = dot((r1 - ray.start), n) / dot(ray.dir, n);
				if (t < 0.01f) continue;



				vec3 hitPoint = ray.start + (ray.dir * t);

				float intersect1 = dot(cross(r2 - r1, hitPoint - r1), n);
				float intersect2 = dot(cross(r3 - r2, hitPoint - r2), n);
				float intersect3 = dot(cross(r1 - r3, hitPoint - r3), n);

				if (t > back && intersect1 > 0 && intersect2 > 0 && intersect3 > 0) {
					back = t;
					hit.t = t;
					if (dot(n, ray.dir) > 0) n = n * -1;
					hit.normal = normalize(n);
					hit.position = hitPoint;
				}

			}

			return hit;
		}

};




struct Cone : public Intersectable {
	float alpha;	
	float h;	
	vec3 cent;		
	vec3 v;		
	vec3 color;

	Cone(vec3 center, float _alpha, vec3 irany, float height, vec3 col) {
		alpha = _alpha;
		h = height;
		cent = center;
		v = normalize(irany);
		color = col;
	}

	Hit intersect(const Ray& ray) {
		Hit hit;
		vec3 co = ray.start - cent;

		float a = dot(ray.dir, v) * dot(ray.dir, v) - cosf(alpha) * cosf(alpha);
		float b = 2. * (dot(ray.dir, v) * dot(co, v) - dot(ray.dir, co) * cosf(alpha) * cosf(alpha));
		float c = dot(co, v) * dot(co, v) - dot(co, co) * cosf(alpha) * cosf(alpha);


		float det = b * b - 4. * a * c;
		if (det < 0.) {
			hit.t = -1;
			return hit;
		}
		det = sqrt(det);
		float t1 = (-b + det) / (2. * a);
		float t2 = (-b - det) / (2. * a);
		if (t1 <= 0) {
			hit.t = -1;
			return hit;
		}
		float t;
		vec3 p1 = ray.start + ray.dir * t1;
		vec3 p2 = ray.start + ray.dir * t2;
		float x1 = dot(p1 - cent, v);
		float x2 = dot(p2 - cent, v);
		if (x1 >= 0 && x1 <= h) {
			if (x2 < 0 || x2 > h) {
				t = t1;
			}
			else {
				t = (t2 < t1) ? t2 : t1;
			}
		}
		else if (x2 >= 0 && x2 <= h) {
			t = t2;
		}
		else {
			hit.t = -1;
			return hit;
		}
		vec3 hitPoint = ray.start +  (ray.dir *t);
		
		vec3 n = normalize(2*dot(hit.position-cent, v) * v - 2*(hit.position - cent)* cosf(alpha)*cosf(alpha));
		hit.normal = n;
		hit.t = t;
		hit.position = hitPoint;
		return hit;
	}

	

	void setCenter(vec3 newC, vec3 n) {
		cent = newC + n * epsilonBig;
		v = n;
	}
	
};


class Camera {
	vec3 eye, lookat, right, up;
public:
	void set(vec3 _eye, vec3 _lookat, vec3 vup, float fov) {
		eye = _eye;
		lookat = _lookat;
		vec3 w = eye - lookat;
		float focus = length(w);
		right = normalize(cross(vup, w)) * focus * tanf(fov / 2);
		up = normalize(cross(w, right)) * focus * tanf(fov / 2);
	}
	Ray getRay(int X, int Y) {
		vec3 dir = lookat + right * (2.0f * (X + 0.5f) / windowWidth - 1) + up * (2.0f * (Y + 0.5f) / windowHeight - 1) - eye;
		return Ray(eye, dir);
	}
};




Cone* cone1 = new Cone(vec3(0.6f, 0.8f, 0.5f), 20 * M_PI / 180, vec3(0, -1, 0), 0.2f, vec3(1., 0., 0.));
Cone* cone2 = new Cone(vec3(0.6f, 0.8f, 0.9f), 20 * M_PI / 180, vec3(0, -1, 0.0f), 0.2f, vec3(0., 1., 0.));
Cone* cone3 = new Cone(vec3(0.7f, 0.8f, 0.4f), 20 * M_PI / 180, vec3(0, -1, 0.0f), 0.2f, vec3(0., 0., 1.));

float rnd() { return (float)rand() / RAND_MAX; }


class Scene {
	std::vector<Intersectable*> objects;
	std::vector<Cone*> cones;
	Camera camera;
	vec3 La;
public:
	void build() {
		vec3 eye = vec3(3., 0.5f, 2.), vup = vec3(0, 1, 0), lookat = vec3(0, 0, 0);
		float fov = 45 * M_PI / 180;
		camera.set(eye, lookat, vup, fov);
		La = vec3(0., 0., 0.);
		
		

		
		objects.push_back(new Cube());
		objects.push_back(new Icosahedron());
		objects.push_back(new Diamond());
		objects.push_back(cone1);
		cones.push_back(cone1);
		objects.push_back(cone2);
		cones.push_back(cone2);
		objects.push_back(cone3);
		cones.push_back(cone3);
		 
		
	}

	void render(std::vector<vec4>& image) {
		for (int Y = 0; Y < windowHeight; Y++) {
#pragma omp parallel for
			for (int X = 0; X < windowWidth; X++) {
				vec3 color = trace(camera.getRay(X, Y));
				image[Y * windowWidth + X] = vec4(color.x, color.y, color.z, 1);
			}
		}
	}

	Hit firstIntersect(Ray ray) {
		Hit bestHit;
		for (Intersectable* object : objects) {
			Hit hit = object->intersect(ray); //  hit.t < 0 if no intersection
			if (hit.t > 0 && (bestHit.t < 0 || hit.t < bestHit.t))  bestHit = hit;
		}
		if (dot(ray.dir, bestHit.normal) > 0) bestHit.normal = bestHit.normal * (-1);
		return bestHit;
	}
	
	

	
	
	
	vec3 trace(Ray ray, int depth = 0) {
		Hit hit = firstIntersect(ray);
		float grey = 0.2 * (1 + dot(hit.normal, (- 1) * ray.dir));
		if (hit.t < 0) return vec3(0, 0, 0);
		vec3 outRadiance = vec3(grey, grey, grey);
		for (Cone* cone : cones) {
			float dist = length(cone->cent - hit.position);

			vec3 posl = hit.position - cone->cent + cone->v * epsilon*3;
			Ray sray(hit.position + hit.normal * epsilon*2, normalize(cone->cent - cone->v * epsilon * 2 - hit.position));
			Hit between = firstIntersect(sray);
			if (dot(normalize(posl), cone->v) > cosf(cone->alpha) && (between.t < 0 || between.t >= dist)) {
				outRadiance = outRadiance + cone->color - 0.5f * dist * cone->color;
			}
		}
		return outRadiance;
	}

	Cone* getClosestCone(int X, int Y) {
		
		Ray RaytoCone = camera.getRay(X, windowHeight-Y);
		Hit hit = firstIntersect(RaytoCone);
		if (hit.t < 0) return NULL;
		float minLength = 1000000.;
		Cone* closest;
		for (Cone* cone : cones) {
			if (length(cone->cent - hit.position) < minLength) {
				closest = cone;
				minLength = length(cone->cent - hit.position);
			}
		}
		return closest;
	}

	void setConePos(Cone* cone, int X, int Y) {
		Ray toCone = camera.getRay(X, windowHeight-Y);
		Hit hit = firstIntersect(toCone);
		cone->setCenter(hit.position, hit.normal);
		
	}

};

GPUProgram gpuProgram; // vertex and fragment shaders
Scene scene;

// vertex shader in GLSL
const char* vertexSource = R"(
	#version 330
    precision highp float;

	layout(location = 0) in vec2 cVertexPosition;	// Attrib Array 0
	out vec2 texcoord;

	void main() {
		texcoord = (cVertexPosition + vec2(1, 1))/2;							// -1,1 to 0,1
		gl_Position = vec4(cVertexPosition.x, cVertexPosition.y, 0, 1); 		// transform to clipping space
	}
)";

// fragment shader in GLSL
const char* fragmentSource = R"(
	#version 330
    precision highp float;

	uniform sampler2D textureUnit;
	in  vec2 texcoord;			// interpolated texture coordinates
	out vec4 fragmentColor;		// output that goes to the raster memory as told by glBindFragDataLocation

	void main() {
		fragmentColor = texture(textureUnit, texcoord); 
	}
)";

class FullScreenTexturedQuad {
	unsigned int vao;	// vertex array object id and texture id
	Texture texture;
public:
	FullScreenTexturedQuad(int windowWidth, int windowHeight, std::vector<vec4>& image)
		: texture(windowWidth, windowHeight, image)
	{
		glGenVertexArrays(1, &vao);	// create 1 vertex array object
		glBindVertexArray(vao);		// make it active

		unsigned int vbo;		// vertex buffer objects
		glGenBuffers(1, &vbo);	// Generate 1 vertex buffer objects

		// vertex coordinates: vbo0 -> Attrib Array 0 -> vertexPosition of the vertex shader
		glBindBuffer(GL_ARRAY_BUFFER, vbo); // make it active, it is an array
		float vertexCoords[] = { -1, -1,  1, -1,  1, 1,  -1, 1 };	// two triangles forming a quad
		glBufferData(GL_ARRAY_BUFFER, sizeof(vertexCoords), vertexCoords, GL_STATIC_DRAW);	   // copy to that part of the memory which is not modified 
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, GL_FALSE, 0, NULL);     // stride and offset: it is tightly packed
	}

	void Draw() {
		glBindVertexArray(vao);	// make the vao and its vbos active playing the role of the data source
		gpuProgram.setUniform(texture, "textureUnit");
		glDrawArrays(GL_TRIANGLE_FAN, 0, 4);	// draw two triangles forming a quad
	}
};

FullScreenTexturedQuad* fullScreenTexturedQuad;
std::vector<vec4> image(windowWidth* windowHeight);

// Initialization, create an OpenGL context
void onInitialization() {
	glViewport(0, 0, windowWidth, windowHeight);
	scene.build();

	long timeStart = glutGet(GLUT_ELAPSED_TIME);
	scene.render(image);
	long timeEnd = glutGet(GLUT_ELAPSED_TIME);
	//printf("Rendering time: %d milliseconds\n", (timeEnd - timeStart));

	// copy image to GPU as a texture
	fullScreenTexturedQuad = new FullScreenTexturedQuad(windowWidth, windowHeight, image);

	// create program for the GPU
	gpuProgram.create(vertexSource, fragmentSource, "fragmentColor");
}

// Window has become invalid: Redraw
void onDisplay() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the screen
	scene.render(image);
	fullScreenTexturedQuad = new FullScreenTexturedQuad(windowWidth, windowHeight, image);

	fullScreenTexturedQuad->Draw();
	glutSwapBuffers();									// exchange the two buffers
	//printf("avg color: %f", sum / count);
}

// Key of ASCII code pressed
void onKeyboard(unsigned char key, int pX, int pY) {
}

// Key of ASCII code released
void onKeyboardUp(unsigned char key, int pX, int pY) {

}

// Mouse click event
void onMouse(int button, int state, int pX, int pY) {
	Cone* closest = scene.getClosestCone(pX, pY);
	if (closest != NULL) {
		scene.setConePos(closest, pX, pY);
		glutPostRedisplay();
	}
	
}

// Move mouse with key pressed
void onMouseMotion(int pX, int pY) {
}

// Idle event indicating that some time elapsed: do animation here
void onIdle() {
}