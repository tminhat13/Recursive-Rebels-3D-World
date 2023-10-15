/***************************************************************
* file: Cube.java
* author: Nhat Tran
* class: CS 4450 - Computer Graphic
*
* assignment: Final Program
* date last modified: 10/14/2023
*
* purpose: this is a Cube class
*
****************************************************************/
package recursive.rebels.pkg3d.world;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;


public class Cube {

    private final float x;
    private final float y;
    private final float z;
    private final ArrayList<Point> positions = new ArrayList<>();
    private final int[][] indices;
    private final float[][] colors;
    
    Cube (float width){
        this.x = width/2;
        this.y = width/2;
        this.z = width/2;
        
        this.positions.add(new Point(x, y, z)); // V0
        this.positions.add(new Point(-x, y, z)); // V1
        this.positions.add(new Point(-x, -y, z)); // V2
        this.positions.add(new Point(x, -y, z)); // V3
        this.positions.add(new Point(-x, y, -z)); // V4
        this.positions.add(new Point(x, y, -z)); // V5
        this.positions.add(new Point(x, -y, -z)); // V6
        this.positions.add(new Point(-x, -y, -z)); // V7

        this.indices = new int[][] {
            // Front face
            {0, 1, 2, 3},
            // Top Face
            {5, 4, 1, 0},
            // Right face
            {5, 0, 3, 6},
            // Left face
            {1, 4, 7, 2},
            // Bottom face
            {3, 2, 7, 6},
            // Back face
            {4, 5, 6, 7}
        };
        this.colors = new float[][] {
            // Front face
            {1.0f, 0.0f, 0.0f},
            // Top Face
            {0.0f, 1.0f, 0.0f},
            // Right face
            {0.0f, 0.0f, 1.0f},
            // Left face
            {0.0f, 1.0f, 1.0f},
            // Bottom face
            {1.0f, 0.0f, 0.0f},
            // Back face
            {0.0f, 1.0f, 0.0f}
        };
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    ArrayList<Point> getPositions() {
        return this.positions;
    }

    public int[][] getIndices() {
        return indices;
    }
    
    void drawFaces(){
        glBegin(GL_QUADS);
        for (int i = 0; i < this.indices.length; i++) {
            glColor3f(this.colors[i][0],this.colors[i][1],this.colors[i][2]);
            for (int j = 0; j < this.indices[i].length; j++) {
                glVertex3f(this.getPositions().get(this.indices[i][j]).getX(),
                        this.getPositions().get(this.indices[i][j]).getY(),
                        this.getPositions().get(this.indices[i][j]).getZ());
            }
        }
        glEnd();
    }
    
    void drawLines(){
        glColor3f(1.0f,0.0f,0.0f);
        for (int[] indice : this.getIndices()) {
            glBegin(GL_LINE_LOOP);
            for (int j = 0; j < indice.length; j++) {
                glVertex3f(this.getPositions().get(indice[j]).getX(), 
                        this.getPositions().get(indice[j]).getY(), 
                        this.getPositions().get(indice[j]).getZ());
            }
            glEnd();
        }
    }
}
