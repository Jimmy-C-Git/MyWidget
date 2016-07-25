package com.cj.glsurface;


public class Square extends Mesh{


    
    public Square() {
    	float vertices[] = {0,0,0, // 0
    			1,0,0, // 1
    			0,1,0, // 2
    			0,0,1 // 3
    			
    	};
    	float colors[]={
    			0,0,0,0,
    			1,0,0,0,
    			0,1,0,0,
    			0,0,1,0
    			
    	};
    	short indices[] = { 0, 2, 1, 0, 3, 2, 0, 1, 3,3,1,2 };
    	setColors(colors);
    	setIndices(indices);
    	setVertices(vertices);
    }
}