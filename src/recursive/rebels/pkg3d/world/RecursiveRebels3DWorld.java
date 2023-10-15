/***************************************************************
* file: RecursiveRebels3DWorld.java
* author: Nhat Tran
* class: CS 4450 - Computer Graphic
*
* assignment: Final Program
* date last modified: 10/14/2023
*
* purpose: Create an original scene in Minecraft fashion
*
****************************************************************/
package recursive.rebels.pkg3d.world;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;


public class RecursiveRebels3DWorld {

    
    public static void main(String[] args) {
        RecursiveRebels3DWorld  app = new RecursiveRebels3DWorld();
        app.start();
    }

    public void start(){
        try{
            createWindow();
            initL();
            render();
        } catch(Exception e){
            System.out.print(e);
        }
    }
    
    private void createWindow() throws Exception{
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640,480));
        Display.setTitle("3D World");
        Display.create();
    }

    private void initL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(-32, 32, -24, 24 , 100, -100);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        // glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
    }

    private void render() {
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glPointSize(1);
                Cube c1 = new Cube(4);
                
                glRotatef(45,0,0,1);
                glRotatef(45,0,1,0);
                glRotatef(45,1,0,0);
                c1.drawFaces();
                c1.drawLines();
                  
                Display.update();
                Display.sync(60);
            }catch(Exception e){
                System.out.print(e);
            }
        }
        Display.destroy();
    }
}
