//ImageService用c++写，之后编译为.dll，以便c#程序调用

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
//包含一个函数，在c#中将会变为 IntPtr ImageService(byte[] fileName)的函数
#ifdef EXPORTING_DLL
extern "C" __declspec(dllexport) char* ImageService(char* fileName);
#else
extern "C" __declspec(dllimport) char* ImageService(char* fileName);
#endif


#endif
