package com.cj.glsurface;


public class Square extends Mesh{


    
    public Square() {
    	float vertices[] = {0,0,0, // 0
    			1,0,0, // 1
    			0,1,0, // 2
    			1,1,1 // 3
    			
    	};
    	setVertices(vertices);
    }
}