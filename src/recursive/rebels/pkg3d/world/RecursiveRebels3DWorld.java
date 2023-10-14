/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package recursive.rebels.pkg3d.world;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author prapi
 */
public class RecursiveRebels3DWorld {

    /**
     * @param args the command line arguments
     */
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
            e.printStackTrace();
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
        
        glOrtho(-320, 320, -240, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }

    private void render() {
        while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            try{
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                glPointSize(2);
                
//               testing case
//                for(int i =0; i< polygons.size();i++){
//                    glPushMatrix();
//                    polygons.get(i).transform();
//                    glBegin(GL_POLYGON);
//                    polygons.get(i).getPoints().forEach(p ->glVertex2f (p.getX(), p.getY()));
//                    glEnd();
//                    glPopMatrix();
//                }
//                for(int i =0; i< polygons.size();i++){
//                    glPushMatrix();
//                    polygons.get(i).transform();
                    glBegin(GL_POINTS);
//                    polygons.get(i).fill();
                    glEnd();
//                    glPopMatrix();
//                }
                
                Display.update();
                Display.sync(60);
            }catch(Exception e){
                
            }
        }
        Display.destroy();
    }
}
