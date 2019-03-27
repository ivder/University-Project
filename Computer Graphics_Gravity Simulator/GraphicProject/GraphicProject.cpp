
#include "stdafx.h"
#include <GL/glut.h>
#include <vector>

struct Particle {
	float x;
	float y;
	float r;  //diameter ����
	float ax; //acceleration x ���ӵ� x ��ǥ
	float ay; //acceleration y ���ӵ� y ��ǥ
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
		addParticle(10, 3); //���� �߰�
		PRESSED_LEFT = false;
	}

	if (PRESSED_RIGHT)
	{
		if (earth)
		{
			addParticle(5970, 12.756, 0); //���� �߰�
			earth = false;
		}
		if (venus)
		{
			addParticle(4870, 12.104, 0); //�ݼ� �߰�
			venus = false;
		}
		if (mars)
		{
			addParticle(642, 6.792, 0); //ȭ�� �߰�
			mars = false;
		}
		if (jupiter)
		{
			addParticle(1898000, 142.984, 0); //�� �߰�
			jupiter = false;
		}
		if (saturn)
		{
			addParticle(568000, 120.536, 0); //�伺 �߰�
			saturn = false;
		}
		if (uranus)
		{
			addParticle(86800, 51.118, 0); //���ռ� �߰�
			uranus = false;
		}
		if (neptune)
		{
			addParticle(102000, 49.528, 0); //�ؿռ� �߰�
			neptune = false;
		}

		PRESSED_RIGHT = false;
	}

	if (PRESSED_MIDDLE)
		removeParticles(); //��� ���ڿ� �༺�� �����

	for (int i = 0; i < particles.size(); i++)   //ù ����/�༺
	{
		Particle &p = particles[i];
		bool not_fall = true;
		for (int j = 0; j < particles.size(); j++)  //���� ����/�༺
		{
			if (j == i || p.m >= 100) // �༺�̸� �̵����� �ʴ�
				continue;

			const Particle &p1 = particles[j];

			float d = sqrt((p1.x - p.x)*(p1.x - p.x) + (p1.y - p.y)*(p1.y - p.y));   //2object �����ǰŸ� ���=��Ÿ���

			if (d > p1.r)  //�Ÿ� > p1�� ����
			{   //Newton's Acceleration due to gravity theorem �����η��� ��Ģ���� ���
				//ax=G*m/D^2 * cosx , ay=G*m/D^2 * siny
				p.ax += 0.0667 * p1.m / (d*d) * (p1.x - p.x) / d;  //ax+ => acceleration keep increasing after moving (particle getting faster)
				p.ay += 0.0667 * p1.m / (d*d) * (p1.y - p.y) / d;          //�̵��� �Ŀ� ���ӵ� ����
				       //0.0667=gravitational constant, ������ =6.67*10^-11
			}
			else
				not_fall = false;   //fall=true, ���ڸ� �����
		}

		if (not_fall)
		{
			p.x += p.ax;  //set particle coordinate + coordinate from ax and ay
			p.y += p.ay;  //���� ��ǥ�� + ���ӵ���ǥ��
		}
		else
			particles.erase(particles.begin() + i);  //delete when falls to the big particle ���� �����
	}

	glutTimerFunc(1, timer, 0);  //callback, �����ϴ� ��ǥ��� display() ����
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
	//set the coordinates (x,y��ǥ ����)
	Mx = x - 500;   //position of x particle after clicked (make it center)
	My = y - 300;

	//check which button is pressed, ��� Ŭ���� ��ư�� Ȯ���Ѵ�
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
	p.x = Mx;       //���� ��ġ=mouse ��ġ
	p.y = My;
	p.ax = ax / 30; 
	p.ay = ay / 30; // slow down the speed a little
	p.m = m;
	p.r = r;
	if (randColor)  //����, ������
	{
		p.color[0] = rand() % 200 / 200.0;
		p.color[1] = rand() % 200 / 200.0;
		p.color[2] = rand() % 200 / 200.0;
	}
	else if (earth) //�༺�� ���� ����
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
		particles.pop_back();   //vector���� ����/�༺�� ����
}

void keyboard(unsigned char key, int x, int y)
{
	switch (key)  //�༺ �ĺ���, Ű����κ��� �Է�
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