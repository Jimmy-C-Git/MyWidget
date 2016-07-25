package com.cj.glsurface;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.cj.mywidget.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;

public class MyRender implements Renderer {

    private Square   mSquare;
	private Context context;
	private final float[] mProjectionMatrix = new float[16];
	public float transX=0,transY=0,transZ=0;
	
	public volatile float mAngle=1;
	
	public MyRender(Context main) {
		this.context = main;
	}

	public void initScene(GL10 gl) {
		float[] amb = { 1.0f, 1.0f, 1.0f, 1.0f, };
		float[] diff = { 1.0f, 1.0f, 1.0f, 1.0f, };
		float[] spec = { 1.0f, 1.0f, 1.0f, 1.0f, };
		float[] pos = { 0.0f, 0.0f, 5.0f, 0.0f, };
		float[] spot_dir = { 0.0f, -1.0f, 0.0f, };
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_CULL_FACE);

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		ByteBuffer abb = ByteBuffer.allocateDirect(amb.length * 4);
		abb.order(ByteOrder.nativeOrder());
		FloatBuffer ambBuf = abb.asFloatBuffer();
		ambBuf.put(amb);
		ambBuf.position(0);

		ByteBuffer dbb = ByteBuffer.allocateDirect(diff.length * 4);
		dbb.order(ByteOrder.nativeOrder());
		FloatBuffer diffBuf = dbb.asFloatBuffer();
		diffBuf.put(diff);
		diffBuf.position(0);

		ByteBuffer sbb = ByteBuffer.allocateDirect(spec.length * 4);
		sbb.order(ByteOrder.nativeOrder());
		FloatBuffer specBuf = sbb.asFloatBuffer();
		specBuf.put(spec);
		specBuf.position(0);

		ByteBuffer pbb = ByteBuffer.allocateDirect(pos.length * 4);
		pbb.order(ByteOrder.nativeOrder());
		FloatBuffer posBuf = pbb.asFloatBuffer();
		posBuf.put(pos);
		posBuf.position(0);

		ByteBuffer spbb = ByteBuffer.allocateDirect(spot_dir.length * 4);
		spbb.order(ByteOrder.nativeOrder());
		FloatBuffer spot_dirBuf = spbb.asFloatBuffer();
		spot_dirBuf.put(spot_dir);
		spot_dirBuf.position(0);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, posBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPOT_DIRECTION, spot_dirBuf);
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 0.0f);
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 45.0f);

		gl.glLoadIdentity();
		

	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
				GL10.GL_NICEST);
		
		initScene(gl);
		mSquare=new Square();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f,
				(float) width / (float) height,
				0.1f, 100f);
		
		//GLU.gluLookAt(gl, 0.0f, 0f, 15.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -10);
		
	}

	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);

		gl.glRotatef(mAngle,1, 1, 0);
		//gl.glTranslatef(transX, transY, transZ);
		//gl.glTranslatef(0, 0, -1);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		mSquare.draw(gl,GL10.GL_TRIANGLES);
		
	//	GLU.gluLookAt(gl, 0.0f, 0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		//angle++;

	}
	
	public static int loadShader(int type, String shaderCode){

	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);

	    return shader;
	}
	public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}
