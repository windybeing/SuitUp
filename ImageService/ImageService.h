//ImageService��c++д��֮�����Ϊ.dll���Ա�c#�������

#ifndef INDLL_H
#define INDLL_H

#include "GL/freeglut.h"
#include<string>
#include<vector>
#include<fstream>
#include<iostream>
#include<cstdio>

#include<string>
using std::string;
//����һ����������c#�н����Ϊ IntPtr ImageService(byte[] fileName)�ĺ���
#ifdef EXPORTING_DLL
extern "C" __declspec(dllexport) char* ImageService(char* fileName);
#else
extern "C" __declspec(dllimport) char* ImageService(char* fileName);
#endif


#endif
