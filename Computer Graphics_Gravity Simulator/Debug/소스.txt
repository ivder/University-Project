
#include "stdafx.h"
#include <GL/glut.h>
#include <vector>

struct Particle {
	float x;
	float y;
	float r;  //diameter 지름
	float ax; //acceleration x 가속도 x 촤표
	float ay; //acceleration y 가속도 y 촤표
	float m;  //mass
	float color[3];
};

void timer(int = 0);
void display();
void mouse(int, int, int, int);
void addParticle(float, float, bool = true, float = 0, float = 0);
void removeParticles();
void keyboard(unsigned char, int, int);

int Mx, My, WIN;
bool PRESSED_LEFT = false, PRESSED_RIGHT = false,
PRESSED_MIDDLE = false, SPEED_PARTICLES = false,
earth=false, venus=false, mars=false,jupiter=false,uranus=false,saturn=false,neptune=false;

std::vector<Particle> particles;

int main(int argc, char **argv)
{
	glutInit(&argc, argv);  //initialize glut library
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);  //sets the initial display mode
	glutInitWindowSize(1000, 600);
	glutInitWindowPosition(50, 50);
	WIN = glutCreateWindow("Gravity Simulator");

	glClearColor(0, 0, 0, 1);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();  // replace the current matrix with the identity matrix
	glOrtho(-500.0, 500.0, 300.0, -300.0, 0, 1);  //multiply the current matrix with an orthographic matrix

	glutDisplayFunc(display);
	glutMouseFunc(mouse);
	glutKeyboardFunc(keyboard);
	timer();

	glutMainLoop();
	return 0;
}

void timer(int)
{
	display();
	if (PRESSED_LEFT && !SPEED_PARTICLES)
	{
		addParticle(10, 3); //입자 추가
		PRESSED_LEFT = false;
	}

	if (PRESSED_RIGHT)
	{
		if (earth)
		{
			addParticle(5970, 12.756, 0); //지구 추가
			earth = false;
		}
		if (venus)
		{
			addParticle(4870, 12.104, 0); //금성 추가
			venus = false;
		}
		if (mars)
		{
			addParticle(642, 6.792, 0); //화성 추가
			mars = false;
		}
		if (jupiter)
		{
			addParticle(1898000, 142.984, 0); //목성 추가
			jupiter = false;
		}
		if (saturn)
		{
			addParticle(568000, 120.536, 0); //토성 추가
			saturn = false;
		}
		if (uranus)
		{
			addParticle(86800, 51.118, 0); //차왕성 추가
			uranus = false;
		}
		if (neptune)
		{
			addParticle(102000, 49.528, 0); //해왕성 추가
			neptune = false;
		}

		PRESSED_RIGHT = false;
	}

	if (PRESSED_MIDDLE)
		removeParticles(); //모든 입자와 행성을 지운다

	for (int i = 0; i < particles.size(); i++)   //첫 입자/행성
	{
		Particle &p = particles[i];
		bool not_fall = true;
		for (int j = 0; j < particles.size(); j++)  //다음 입자/행성
		{
			if (j == i || p.m >= 100) // 행성이면 이동되지 않다
				continue;

			const Particle &p1 = particles[j];

			float d = sqrt((p1.x - p.x)*(p1.x - p.x) + (p1.y - p.y)*(p1.y - p.y));   //2object 사이의거리 계산=피타고라스

			if (d > p1.r)  //거리 > p1의 지름
			{   //Newton's Acceleration due to gravity theorem 만유인력의 법칙으로 계산
				//ax=G*m/D^2 * cosx , ay=G*m/D^2 * siny
				p.ax += 0.0667 * p1.m / (d*d) * (p1.x - p.x) / d;  //ax+ => acceleration keep increasing after moving (particle getting faster)
				p.ay += 0.0667 * p1.m / (d*d) * (p1.y - p.y) / d;          //이동한 후에 가속도 증가
				       //0.0667=gravitational constant, 원래값 =6.67*10^-11
			}
			else
				not_fall = false;   //fall=true, 입자를 지운다
		}

		if (not_fall)
		{
			p.x += p.ax;  //set particle coordinate + coordinate from ax and ay
			p.y += p.ay;  //입자 촤표계 + 가속도촤표계
		}
		else
			particles.erase(particles.begin() + i);  //delete when falls to the big particle 입자 지운다
	}

	glutTimerFunc(1, timer, 0);  //callback, 저장하는 촤표계로 display() 수정
}

void display()
{
	glClear(GL_COLOR_BUFFER_BIT);
	//draw particles
	for (int i = 0; i < particles.size(); i++)
	{
		Particle &p = particles[i];  //get particle data from vector
		glColor3f(p.color[0], p.color[1], p.color[2]);
		glBegin(GL_POLYGON);
		for (float c = 0; c < 2 * 3.14; c += 0.2)  //full circle degree, 2*pi
			glVertex2f(p.r*cos(c) + p.x, p.r*sin(c) + p.y); //x coordinate=r*cos a+saved x coordinate, so does y coordinate
		glEnd();
	}

	glFlush();
	glutSwapBuffers();
}

void mouse(int button, int state, int x, int y)
{
	//set the coordinates (x,y촤표 설정)
	Mx = x - 500;   //position of x particle after clicked (make it center)
	My = y - 300;

	//check which button is pressed, 어느 클릭돤 버튼을 확인한다
	if (button == GLUT_LEFT_BUTTON)
		PRESSED_LEFT = state == GLUT_DOWN;
	else if (button == GLUT_RIGHT_BUTTON)
		PRESSED_RIGHT = state == GLUT_DOWN;
	else if (button == GLUT_MIDDLE_BUTTON)
		PRESSED_MIDDLE = state == GLUT_DOWN;
}



void addParticle(float m, float r, bool randColor, float ax, float ay)
{
	Particle p;
	p.x = Mx;       //입자 위치=mouse 위치
	p.y = My;
	p.ax = ax / 30; 
	p.ay = ay / 30; // slow down the speed a little
	p.m = m;
	p.r = r;
	if (randColor)  //입자, 색상변하
	{
		p.color[0] = rand() % 200 / 200.0;
		p.color[1] = rand() % 200 / 200.0;
		p.color[2] = rand() % 200 / 200.0;
	}
	else if (earth) //행성의 색상 설정
	{
		p.color[0] = 0;
		p.color[1] = 0.5;
		p.color[2] = 1;
	}
	else if (venus)
	{
		p.color[0] = 1;
		p.color[1] = 0;
		p.color[2] = 0;
	}
	else if (mars)
	{
		p.color[0] = 0.8;
		p.color[1] = 0;
		p.color[2] = 0;
	}
	else if (jupiter)
	{
		p.color[0] = 1;
		p.color[1] = 0.5;
		p.color[2] = 0;
	}
	else if (saturn)
	{
		p.color[0] = 1;
		p.color[1] = 0.8;
		p.color[2] = 0;
	}
	else if (uranus)
	{
		p.color[0] = 0;
		p.color[1] = 0.5;
		p.color[2] = 1;
	}
	else if (neptune)
	{
		p.color[0] = 0;
		p.color[1] = 0;
		p.color[2] = 1;
	}
	
	particles.push_back(p);
}

void removeParticles()
{
	for (int i = 0; i < particles.size(); i++)
		particles.pop_back();   //vector에서 입자/행성을 제거
}

void keyboard(unsigned char key, int x, int y)
{
	switch (key)  //행성 식별자, 키보드로부터 입력
	{ 
	case 'e':
		earth = true;
		break;
	case 'v':
		venus = true;
		break;
	case'm':
		mars = true;
		break;
	case'j':
		jupiter = true;
		break;
	case's':
		saturn = true;
		break;
	case'u':
		uranus = true;
		break;
	case'n':
		neptune = true;
		break;
	case 27:  //esc
		removeParticles();
		glutDestroyWindow(WIN);
		exit(0);
		break;
	}
}