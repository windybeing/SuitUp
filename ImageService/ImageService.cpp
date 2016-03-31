#define _CRT_SECURE_NO_WARNINGS
#define EXPORTING_DLL

#define WIN_WIDTH 1000
#define WIN_HEIGHT 1000
#include "ImageService.h"
#define EXPORT_WRITECOMMONHEADER
using namespace std;
#define STEP1 20
#define STEP2 40
#define STEP3 60
#define STEP4 80
#define STEP5 81
#define STEP6 82
#define STEP7 83




//ImageUtil
//包含了一些对图像的处理的辅助函数。由于要编译成.dll，所以写在了一个.cpp里面。
//都是传入一个黑白的屏幕的信息，再进行相应处理

//获取周长
vector<double> getCircumference(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{
	vector<double> result;
	double cir = 0;
	int up, down, preUp, preDown;

	for (int i = 0;i < WIN_WIDTH;i++)
	{
		for (int j = 0;j < WIN_HEIGHT;j++)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				cir = 0;
				int flag = 0;
				up = down = j;
				preUp = up;
				preDown = down;
				for (;i < WIN_WIDTH;i++)
				{
					flag++;
					for (int k = 15;k != -16;k--)
					{
						if (screen[(i + (up + k)*WIN_WIDTH) * 3] != 0)
						{
							flag = 0;
							up = up + k;
							if (k == 15)
							{
								for (;;up++)
								{
									if (screen[(i + (up)*WIN_WIDTH) * 3] == 0)
									{
										up--;
										break;
									}
								}
							}
							break;
						}
					}

					for (int k = -15;k != 16;k++)
					{
						if (screen[(i + (down + k)*WIN_WIDTH) * 3] != 0)
						{
							flag = 0;
							down = down + k;
							if (k == -15)
							{
								for (;;down--)
								{
									if (screen[(i + (down)*WIN_WIDTH) * 3] == 0)
									{
										down++;
										break;
									}
								}
							}
							break;
						}
					}



					cir += sqrt((up - preUp)*(up - preUp) + 1);
					cir += sqrt((down - preDown)*(down - preDown) + 1);
					if (flag >= 2)
					{
						if ((up - down) < 8)
						{
							cir -= 4;
							result.push_back(cir);
							break;
						}
					}
					if (flag > 20)
					{
						result.push_back(cir - 20 * 2);
						break;
					}
					preUp = up;
					preDown = down;
				}
				break;
			}
		}
	}
	return result;
}
//获取宽度
double getLength(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{
	int left, right;
	int flag = 0;
	for (int i = 0;i != WIN_WIDTH;i++)
	{
		for (int j = 0;j != WIN_HEIGHT;j++)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				left = i;
				flag = 1;
				break;
			}
		}
		if (flag)
			break;
	}
	flag = 0;
	for (int i = WIN_WIDTH - 1;i >= 0;i--)
	{
		for (int j = WIN_HEIGHT - 1;j >= 0;j--)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				right = i;
				flag = 1;
				break;
			}
		}
		if (flag)
			break;
	}
	return right - left;
}
//获取下方长度
double getDown(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{
	int down, preDown;
	int flag = 0;
	double cir = 0.0;
	for (int i = 0;i < WIN_WIDTH;i++)
	{
		for (int j = 0;j < WIN_HEIGHT;j++)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				down = j;
				preDown = down;
				for (;i < WIN_WIDTH;i++)
				{
					flag = 1;
					for (int k = -2;k != 3;k++)
					{
						if (screen[(i + (down + k)*WIN_WIDTH) * 3] != 0)
						{
							flag = 0;
							down = down + k;
							if (k == -2)
							{
								for (;;down--)
								{
									if (screen[(i + (down)*WIN_WIDTH) * 3] == 0)
									{
										down++;
										break;
									}
								}
							}
							break;
						}
					}
					if (flag)
						break;
					else
					{
						cir += sqrt((down - preDown)*(down - preDown) + 1);
						preDown = down;
					}
				}
				break;
			}
		}
		if (flag)
			break;
	}
	return cir;
}
//获取上方长度
double getUp(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{
	int up, preUp;
	int flag = 0;
	double cir = 0.0;
	for (int i = 0;i < WIN_WIDTH;i++)
	{
		for (int j = WIN_HEIGHT - 1;j >= 0;j--)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				up = j;
				preUp = up;
				for (;i < WIN_WIDTH;i++)
				{
					flag = 1;
					for (int k = 2;k != -3;k--)
					{
						if (screen[(i + (up + k)*WIN_WIDTH) * 3] != 0)
						{
							flag = 0;
							up = up + k;
							if (k == 2)
							{
								for (;;up++)
								{
									if (screen[(i + (up)*WIN_WIDTH) * 3] == 0)
									{
										up--;
										break;
									}
								}
							}
							break;
						}
					}
					if (flag)
						break;
					else
					{
						cir += sqrt((up - preUp)*(up - preUp) + 1);
						preUp = up;
					}
				}
				break;
			}
		}
		if (flag)
			break;
	}
	return cir;
}
//获取左侧长度
double getLeft(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{
	int left, preLeft;
	int flag = 0;
	double cir = 0.0;
	for (int i = WIN_HEIGHT - 1;i >= 0;i--)
	{
		for (int j = 0;j < WIN_WIDTH;j++)
		{
			if (screen[(j + i*WIN_WIDTH) * 3] != 0)
			{
				left = j;
				preLeft = left;

				for (;i >= 0;i--)
				{
					flag = 1;
					for (int k = -2;k != 3;k++)
					{
						if (screen[(left + k + i*WIN_WIDTH) * 3] != 0)
						{
							flag = 0;
							left = left + k;
							if (k == -2)
							{
								for (;;left--)
								{
									if (screen[(left + i*WIN_WIDTH) * 3] == 0)
									{
										left++;
										break;
									}
								}
							}
							break;
						}
					}
					if (flag)
						break;
					else
					{
						cir += sqrt((left - preLeft)*(left - preLeft) + 1);
						preLeft = left;
					}
				}
				break;
			}
		}
		if (flag)
			break;
	}
	return cir;

}

//获取较为平滑的一段的长度
double getSmoothLength(GLubyte screen[WIN_HEIGHT*WIN_WIDTH * 3])
{

	int left;
	int flag = 0;
	int height = -1;
	int preHeight = -1;
	for (int i = 0;i != WIN_WIDTH;i++)
	{
		for (int j = 0;j != WIN_HEIGHT;j++)
		{
			if (screen[(i + j*WIN_WIDTH) * 3] != 0)
			{
				left = i;
				for (;i != WIN_WIDTH;i++)
				{
					height = 0;
					for (int k = 0;k != WIN_HEIGHT;k++)
					{
						if (screen[(i + k*WIN_WIDTH) * 3] != 0)
						{
							height++;
						}
					}
					if (preHeight != -1)
					{
						if (height - preHeight > 6)
						{
							return i - left;
						}
					}
					if (height == 0)
					{
						flag = 1;
						break;
					}
					preHeight = height;
				}
				break;
			}
		}
		if (flag)
			break;
	}
	return -1;
}



//下面定义了一些类，方便在解析文件的时候使用。
class vertex
{
public:
	double x, y, z;
};
class normal
{
public:
	double x, y, z;
};

class face
{
public:
	int v1, v2, v3, vn1, vn2, vn3;
};

vector<vertex> v;
vector<normal> vn;
vector<face> f;
float dis = 0;
GLubyte screen[WIN_HEIGHT*WIN_WIDTH*3];
int step = 0;
double waist =0;
double shoulder = 0;

void Background(void)
{
	glClearColor(0.0, 0.0, 0.0, 0.0);//设置背景颜色为黑色
}

//绘制函数，是opengl中关键的一个函数
void RenderScene(void) {

	//注意我们这里清除了深度缓冲区。
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	//保存当前模型视图矩阵。
	glPushMatrix();
	
	glLineWidth(2);
	if (step <STEP4)
	{
		GLdouble eqn[4] = { 0,0,1,dis };
		glClipPlane(GL_CLIP_PLANE0, eqn);
		glEnable(GL_CLIP_PLANE0);

		GLdouble eqn2[4] = { 0,0,-1,-dis + 0.04 };
		glClipPlane(GL_CLIP_PLANE1, eqn2);
		glEnable(GL_CLIP_PLANE1);
	}
	else if (step <STEP5)
	{
		glRotatef(-90, 0.0, 1.0, 0.0);
		glTranslatef(-0.2, 0, 0);

		GLdouble eqn[4] = { 1,0,0,0.4};
		glClipPlane(GL_CLIP_PLANE0, eqn);
		glEnable(GL_CLIP_PLANE0);

		GLdouble eqn2[4] = { 0,0,1,waist};
		glClipPlane(GL_CLIP_PLANE1, eqn2);
		glEnable(GL_CLIP_PLANE1);

		GLdouble eqn3[4] = { 0,0,-1,-shoulder };
		glClipPlane(GL_CLIP_PLANE2, eqn3);
		glEnable(GL_CLIP_PLANE2);

	}
	else if (step <STEP6)
	{
		glDisable(GL_CLIP_PLANE2);
		glRotatef(-90, 0.0, 1.0, 0.0);
		glTranslatef(-0.2, 0, 0);

		GLdouble eqn[4] = { 1,0,0,0.4 };
		glClipPlane(GL_CLIP_PLANE0, eqn);
		glEnable(GL_CLIP_PLANE0);

		GLdouble eqn2[4] = { 0,0,-1,-waist };
		glClipPlane(GL_CLIP_PLANE1, eqn2);
		glEnable(GL_CLIP_PLANE1);

	}
	else if(step >0)
	{
		glDisable(GL_CLIP_PLANE0);
		glDisable(GL_CLIP_PLANE1);
		glRotatef(-90, 1.0, 0.0, 0.0);
		glTranslatef(0, -0.3, 0);

		GLdouble eqn2[4] = { 0,0,-1,-shoulder};
		glClipPlane(GL_CLIP_PLANE0, eqn2);
		glEnable(GL_CLIP_PLANE0);
		
	}
	for (int i = 0;i != f.size();i++)
	{
		glBegin(GL_TRIANGLES);
		//cout << f[i].v1 << endl;
		glVertex3f(v[f[i].v1].x, v[f[i].v1].y, v[f[i].v1].z);
		glVertex3f(v[f[i].v2].x, v[f[i].v2].y, v[f[i].v2].z);
		glVertex3f(v[f[i].v3].x, v[f[i].v3].y, v[f[i].v3].z);
		glEnd();

	}
	// 弹出堆栈
	glPopMatrix();

	// 交换缓冲区
	glutSwapBuffers();


	// 让dis自动增加。
	//dis+=0.0005;
}

//读取文件函数。这里面针对realsense建模出来的.obj文件及其特性
//而写的一个读取文件的函数。读取出来的结果保存在数组里以便之后再用。
void ReadFile(string fileName)
{
	v.push_back(vertex());
	vn.push_back(normal());
	char temp[100];
	//ifstream fs(fileName);
	FILE* inFile;
	inFile = fopen(fileName.c_str(), "rb+");
	for (;;)
	{
		fscanf(inFile, "%s", temp);
		if (strcmp(temp, "file") == 0)
		{
			break;
		}
		if (strcmp(temp,"v")==0)
		{
			vertex tmpv;
			fscanf(inFile,"%lf %lf %lf",&tmpv.x,&tmpv.y,&tmpv.z);
			v.push_back(tmpv);
			fscanf(inFile, "%lf %lf %lf", &tmpv.x, &tmpv.y, &tmpv.z);

		}
		else if (strcmp(temp, "vn") == 0)
		{
			normal tmpvn;
			fscanf(inFile, "%lf %lf %lf", &tmpvn.x, &tmpvn.y, &tmpvn.z);
			vn.push_back(tmpvn);
		}
		else if (strcmp(temp, "f") == 0)
		{
			face tmpf;
			fscanf(inFile, "%d//%d %d//%d %d//%d", &tmpf.v1, 
				&tmpf.vn1, &tmpf.v2, &tmpf.vn2, &tmpf.v3, &tmpf.vn3);
			f.push_back(tmpf);

		}
	}
}

//整个过程将是，先读取文件，提取出信息之后，在进行建模
//对于不同部位建模出的图像也会有所不同。分多步进行。
char* ImageService(char* fileName)
{
	double* result = new double[8];
	for (int i = 0;i != 8;i++)
		result[i] = -1;
	ReadFile(fileName);
	int t = 0;
	char* tt = "";
	glutInit(&t,&tt);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(WIN_WIDTH, WIN_HEIGHT);
	glutInitWindowPosition(2000, 2000);

	glutCreateWindow("Tridis");
	
	Background();
	glutDisplayFunc(RenderScene);
	glutIdleFunc(RenderScene);
	glEnable(GL_DEPTH_TEST);
	
	
	dis = 0.309;
	double tmpLen = 0;

	while (true)
	{
		//cout << dis << endl;
		glutMainLoopEvent();
		glutPostRedisplay();
		glReadPixels(0, 0, WIN_WIDTH, WIN_HEIGHT, GL_RGB, GL_UNSIGNED_BYTE, screen);

		vector<double> cirs;
		
		if (step < STEP1)
		{
			cirs = getCircumference(screen);
			if (cirs.size() == 3)
			{
				result[0] = 0.9390 * cirs[1] * 1000 / WIN_WIDTH / WIN_HEIGHT * 3;
				step = STEP1-1;
				dis = 0.408;
				//cout << "RESULT!: " << cirs[1]-10 << endl;
				tmpLen = WIN_WIDTH;
			}
			dis += 0.01;
		}
		
		else if (step < STEP2)
		{
			cirs = getCircumference(screen);
			if (cirs.size() == 3)
			{
				if (cirs[1] < tmpLen)
				{
					waist = dis;
					tmpLen = cirs[1];
				}
			}
			dis += 0.01;
			if (step == STEP2-1)
			{
				result[1] = 0.9265 * tmpLen * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
				tmpLen = 0;
				dis = 0.51;
			}
		}
		
		else if(step < STEP3)
		{
			cirs = getCircumference(screen);
			if (cirs.size() == 3)
			{
				if (cirs[1] > tmpLen)
				{
					tmpLen = cirs[1];
				}
			}
			dis += 0.01;
			if (step == STEP3-1)
			{
				result[2] = 0.976 * tmpLen * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
				tmpLen = 0;
				dis = 0.1;
			}
		}
		else if (step < STEP4)
		{
			cirs = getCircumference(screen);
			if (cirs.size() == 1)
			{
				if (cirs[0] - tmpLen < 7)
				{
					step = STEP4-2;
					dis -= 0.05;
				}
				tmpLen = cirs[0];
			}
			if (step = STEP4 - 1)
			{

				tmpLen = getLength(screen);
				result[3] = tmpLen * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
				shoulder = dis;
				dis = 0.00;
			}
			dis += 0.01;
		}
		else if(step <STEP5)
		{
			result[4] = 1.117*getUp(screen)*1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
			result[5] = 1.117*getDown(screen) * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
		}
		else if(step <STEP6)
		{
			result[6] = getSmoothLength(screen) * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
		}
		else
		{
			result[7] = 1.18*getLeft(screen) * 1000 / WIN_HEIGHT / WIN_HEIGHT * 3;
			
			break;
		}
		step++;

	}
	char* trans = new char[1000];
	for (int i = 0;i != 1000;i++)
		trans[i] = 0;
	sprintf(trans, "%lf %lf %lf %lf %lf %lf %lf %lf", result[0], result[1],
		result[2], result[3], result[4], result[5], result[6], result[7]);
	glutExit();
	return trans;
}