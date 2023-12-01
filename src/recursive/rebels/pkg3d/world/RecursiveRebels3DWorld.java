/***************************************************************
* file: RecursiveRebels3DWorld.java
* author: Nhat Tran
* class: CS 4450 - Computer Graphic
*
* assignment: Final Program
* date last modified: 11/30/2023
*
* purpose: Create an original scene in Minecraft fashion
*
****************************************************************/
package recursive.rebels.pkg3d.world;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;


public class RecursiveRebels3DWorld {

    private FPCameraController fp;
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;

    public void start(){
        try{
            createWindow();
            initL();
            fp = new FPCameraController(0f,0f,0f);
            fp.gameLoop();//render();
        } catch(Exception e){
            System.out.print(e);
        }
    }
    
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (DisplayMode d1 : d) {
            if (d1.getWidth() == 640 && d1.getHeight() == 480 && d1.getBitsPerPixel() == 32) {
                displayMode = d1;
                break;
            }
        }
        Display.setDisplayMode(displayMode); Display.setTitle("Recursive Rebels");
        Display.create();
    }

    private void initL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)
            displayMode.getHeight(), 0.1f, 300.0f);
        //glOrtho(-320, 320, -240, 240 , 1000, -1000);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        // glEnable(GL_CULL_FACE);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0    
    }
    
    private void initLightArrays() {
        lightPosition= BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight= BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }

//    private void render() {
//        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
//            try{
//                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//                glLoadIdentity();
//                glPointSize(1);
//                Cube c1 = new Cube(200);
//                
//                glRotatef(45,0,0,1);
//                glRotatef(45,0,1,0);
//                glRotatef(45,1,0,0);
//                c1.drawFaces();
//                c1.drawLines();
//                  
//                Display.update();
//                Display.sync(60);
//            }catch(Exception e){
//                System.out.print(e);
//            }
//        }
//        Display.destroy();
//    }
    
    public static void main(String[] args) {
        RecursiveRebels3DWorld  app = new RecursiveRebels3DWorld();
        app.start();
    }
}