/***************************************************************
* file: Point.java
* author: Nhat Tran
* class: CS 4450 - Computer Graphic
*
* assignment: Final Program
* date last modified: 10/14/2023
*
* purpose: this is a Point class
*
****************************************************************/
package recursive.rebels.pkg3d.world;

public class Point{
    private float x, y, z;

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    
}
