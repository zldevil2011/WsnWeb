package com.zhaolong.wsn.util;  
import javax.script.*;

import org.python.util.PythonInterpreter;

import java.io.*;  
import static java.lang.System.*;  
public class TTest  
{  
	public static void main(String args[])  
	{  
		PythonInterpreter interpreter = new PythonInterpreter();  
		interpreter.exec("days=('mod','Tue','Wed','Thu','Fri','Sat','Sun'); ");  
		interpreter.exec("print days[1];");  
    }  
} 