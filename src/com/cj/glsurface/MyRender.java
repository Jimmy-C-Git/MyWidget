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
	public float eyeX=0,eyeY=0,eyeZ=50;
	public float theta=(float) (Math.PI/4),alfa=(float) (Math.PI/4);
	public volatile float mAngle=1;
	
	public MyRender(Context main) {
		this.context = main;
	}
	private Mesh coordinates =new Mesh();
	private Sphere sun,moon,earth;
	private int angle=0;
	float[] pos = { 0.0f, 0.0f,10.0f, 1.0f, };
	public void initLight(GL10 gl) {
		float[] amb = { 1.0f, 1.0f, 1.0f, 1.0f, };
		float[] diff = { 1.0f, 1.0f, 1.0f, 1.0f, };
		float[] spec = { 1.0f, 1.0f, 1.0f, 1.0f, };
		

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

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffBuf);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specBuf);
		//gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, posBuf);
		//gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 0.0f);
		//gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_CUTOFF, 45.0f);

		/*float sun_mat_ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		float sun_mat_diffuse[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		float sun_mat_specular[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		float sun_mat_emission[] = { 1f, 1.0f, 1.0f, 1.0f };
		float sun_mat_shininess = 0.0f;
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT,
				getBuffer(sun_mat_ambient, sun_mat_ambient.length));
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE,
				getBuffer(sun_mat_diffuse, sun_mat_diffuse.length));
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR,
				getBuffer(sun_mat_specular, sun_mat_specular.length));
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_EMISSION,
				getBuffer(sun_mat_emission, sun_mat_emission.length));
		gl.glMaterialf(GL10.GL_FRONT, GL10.GL_SHININESS, sun_mat_shininess);
*/
	}
	FloatBuffer getBuffer(float[] f,int length){
		ByteBuffer spbb= ByteBuffer.allocateDirect(length*4);
		spbb.order(ByteOrder.nativeOrder());
		FloatBuffer spot_dirBuf = spbb.asFloatBuffer();
		spot_dirBuf.put(f);
		spot_dirBuf.position(0);
		return spot_dirBuf;
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
		
		
		mSquare=new Square();
		sun=new Sphere(2);
		earth=new Sphere(1);
		moon=new Sphere(0.5f);
		
		/*coordinates.setColor(1, 1, 1, 1);
		coordinates.setVertices(new float[]{0,0,0, 10,0,0, 0,10,0, 0,0,10,2,0,0, 0,2,0, 0,0,2});
		coordinates.setIndices(new short[]{4,1,5,2,6,3});*/
		
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
		initLight(gl);
		
		
		/*gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, pos,0);getBuffer(pos, pos.length)*/
	}

	private void drawCoordinates(GL10 gl){
		coordinates.setColor(1, 0, 0, 1);
		float ver[]=new float[]{0,0,0, 10,0,0};
		for(int i=0;i<=10;i++)
			for(int j=0;j<=10;j++)
				for(int k=0;k<=10;k++){
			ver[3]=i;
			ver[4]=j;
			ver[5]=k;
			if(i==10||j==10||k==10){
				coordinates.setVertices(ver);
				coordinates.setIndices(new short[]{0,1});
				coordinates.draw(gl,GL10.GL_LINES);
			}
			
		}
		/*coordinates.setColor(1, 0, 0, 1);
		coordinates.setVertices(new float[]{0,0,0, 10,0,0});
		coordinates.setIndices(new short[]{0,1});
		coordinates.draw(gl,GL10.GL_LINES);
		coordinates.setColor(0, 1, 0, 1);
		coordinates.setVertices(new float[]{0,0,0, 0,10,0});
		coordinates.setIndices(new short[]{0,1});
		coordinates.draw(gl,GL10.GL_LINES);
		coordinates.setColor(0, 0, 1, 1);
		coordinates.setVertices(new float[]{0,0,0, 0,0,10});
		coordinates.setIndices(new short[]{0,1});
		coordinates.draw(gl,GL10.GL_LINES);*/
	}
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
		
		eyeX=(float) (50*Math.cos(alfa)*Math.sin(theta));
		eyeY= (float) (50*Math.sin(alfa));
		eyeZ=(float) (50*Math.cos(alfa)*Math.cos(theta));
		GLU.gluLookAt(gl,eyeX,eyeY,eyeZ , 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, getBuffer(pos, pos.length));
		
		gl.glPushMatrix();
		gl.glTranslatef(pos[0], pos[1], pos[2]);
		gl.glRotatef(180f, 1, 1, (float) (1-Math.sqrt(3)));
		drawCoordinates(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glColor4f(1, 0, 0, 1);
		gl.glRotatef(angle, 0, 0, 1);
		sun.draw(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glRotatef(angle, 0, 0, 1);
		gl.glTranslatef(8, 0, 0);
		gl.glColor4f(0, 0, 1, 1);
		earth.draw(gl);
		
		gl.glRotatef(angle*1.5f, 0, 0, 1);
		gl.glTranslatef(2, 0, 0);
		gl.glColor4f(1,1,1,1);
		moon.draw(gl);
		gl.glPopMatrix();
		
		angle++;

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
