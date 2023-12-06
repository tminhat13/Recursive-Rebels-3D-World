/***************************************************************
* file: FPCameraController.java
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

import java.nio.FloatBuffer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

public class FPCameraController {
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition= null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
//    private Vector3Float me;
    boolean dayTime = true;
    int mode = 0; // mode = 0 spring
    
    public FPCameraController(float x, float y, float z)
    {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition= new Vector3f(x,y,z);
        lPosition.x= 30f;
        lPosition.y= 5f;
        lPosition.z= 30f;
        
    }
    //increment the camera's current yaw rotation
    public void yaw(float amount)
    {
        //increment the yaw by the amount param
        yaw += amount;
    }
        //increment the camera's current yaw rotation
        public void pitch(float amount)
    {
        //increment the pitch by the amount param
        pitch -= amount;
    }
        
    //moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw));
        position.x-= xOffset;
        position.z+= zOffset;
//        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
//        lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
//        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    //moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance)
    {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw));
        position.x+= xOffset;
        position.z-= zOffset;
//        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
//        lightPosition.put(lPosition.x+=xOffset).put(lPosition.y).put(lPosition.z-=zOffset).put(1.0f).flip();
//        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    //strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x-= xOffset;
        position.z+= zOffset;
//        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
//        lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
//        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    //strafes the camera right relative to its current rotation (yaw)
    public void strafeRight(float distance)
    {
        float xOffset= distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset= distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x-= xOffset;
        position.z+= zOffset;
//        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
//        lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
//        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }   
    
    //moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance)
    {
        position.y-= distance;
    }
    //moves the camera down
    public void moveDown(float distance)
    {
        position.y+= distance;
    }
    
    //translates and rotate the matrix so that it looks through the camera
    //this does basically what gluLookAt() does
    public void lookThrough()
    {
        //roatatethe pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatatethe yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
//        System.out.println(lPosition.x +"|"+ lPosition.y +"|"+ lPosition.z);
    }
    
    // move the light source to create day and night feature
    public void moveLight(){
        float xOffset= 1.0f;
        float zOffset= 1.0f;
        if(dayTime== true){
            lPosition.x-= xOffset;
            lPosition.z+= zOffset;
            FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
            lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(-1.0f).flip();
            glLight(GL_LIGHT0, GL_POSITION, lightPosition);
//            System.out.println(lPosition.x +"|"+ lPosition.y +"|"+ lPosition.z +"|"+ xOffset);
            if(lPosition.x<1 || lPosition.z>=60){
                dayTime = false;
            }
        }
        if(dayTime== false){
            lPosition.x+= xOffset;
            lPosition.z-= zOffset;
            FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
            lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(-1.0f).flip();
            glLight(GL_LIGHT0, GL_POSITION, lightPosition);
//            System.out.println(lPosition.x +"|"+ lPosition.y +"|"+ lPosition.z +"|"+ xOffset);
            if(lPosition.x>=60 || lPosition.z<1){
                dayTime = true;
            }
        }
        
        
    }
    public void gameLoop()
    {
        FPCameraController camera = new FPCameraController(-30, -30, -30);
        float dx = 0.0f;
        float dy= 0.0f;
        float dt= 0.0f; //length of frame
        float lastTime= 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity= 0.09f;
        float movementSpeed= .35f;
        float startTime = Sys.getTime();
        
        //hide the mouse
        Mouse.setGrabbed(true);
        // keep looping till the display window is closed the ESC key is down
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime= time;
            //distance in mouse movement //from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement //from the last getDY() call.
            dy= Mouse.getDY();
            //controllcamera yaw from x movement fromtthe mouse
            camera.yaw(dx * mouseSensitivity);
            //controllcamera pitch from y movement fromtthe mouse
            camera.pitch(dy* mouseSensitivity);
            
            // move the light source every 0.5 second to create day and night
            float elapsedTimeSeconds = (time - startTime) / 1000;
            if(elapsedTimeSeconds >= 0.5) {
                camera.moveLight();
                startTime = time;
            }
            
            //when passing in the distance to move
            //we times the movementSpeedwith dtthis is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
            {
                camera.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left 
            {
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right 
            {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up 
            {
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                camera.moveDown(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_O))// toggle terran to winter / spring
            {
                mode = 1-mode;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FPCameraController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //set the modelviewmatrix back to the identity
            glLoadIdentity();
            //look through the camera before you draw anything
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //you would draw your scene here.
//            render();
            
            Chunk chunk = new Chunk(0, 0, 0, mode);
            chunk.render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
//    private void render() {
//        try{
//            Cube c1 = new Cube(10);
//            c1.drawFaces();
//            c1.drawLines();
//        }
//        catch(Exception e){
//        }
//    }
}
