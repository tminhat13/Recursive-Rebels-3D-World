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

import static org.lwjgl.opengl.GL11.*;


public class Cube {

    private float x;
    private float y;
    private float z;
    //private ArrayList<Point> positions = new ArrayList<>();
    private float[][] positions;
    private int[][] indices;
    private float[][] colors;
    
    Cube (float width){
        this.x = width/2;
        this.y = width/2;
        this.z = width/2;
        
//        this.positions.add(new Point(x, y, z)); // V0
//        this.positions.add(new Point(-x, y, z)); // V1
//        this.positions.add(new Point(-x, -y, z)); // V2
//        this.positions.add(new Point(x, -y, z)); // V3
//        this.positions.add(new Point(-x, y, -z)); // V4
//        this.positions.add(new Point(x, y, -z)); // V5
//        this.positions.add(new Point(x, -y, -z)); // V6
//        this.positions.add(new Point(-x, -y, -z)); // V7

            this.positions = new float[][]{
                {x, y, z},  //v0
                {-x, y, z},  //v1
                {-x, -y, z}, //v2
                {x, -y, z}, //v3
                {-x, y, -z}, //v4
                {x, y, -z}, //v5
                {x, -y, -z}, //v6
                {-x, -y, -z} //v7
            };
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
            {1.0f, 1.0f, 0.0f},
            // Back face
            {1.0f, 0.0f, 1.0f}
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

    public float[][] getPositions() {
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
                glVertex3f(this.positions[this.indices[i][j]][0],
                        this.positions[this.indices[i][j]][1],
                        this.positions[this.indices[i][j]][2]);
            }
        }
        glEnd();
    }
    
    void drawLines(){
        glColor3f(1.0f,0.0f,0.0f);
        for (int[] indice : this.getIndices()) {
            glBegin(GL_LINE_LOOP);
            for (int j = 0; j < indice.length; j++) {
                glVertex3f(this.positions[indice[j]][0],
                        this.positions[indice[j]][1],
                        this.positions[indice[j]][2]);
            }
            glEnd();
        }
    }
}
