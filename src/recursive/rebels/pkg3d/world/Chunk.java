/***************************************************************
* file: Chunk.java
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

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Chunk {
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    private Random r;
    private int VBOTextureHandle;
    private Texture texture;
    
    static final double PERSISTANCE = 0.3;
    static final int SEED = new Random().nextInt();
    private double[][] heightMap;
    
    public void render(){
        glPushMatrix();
            glBindBuffer(GL_ARRAY_BUFFER,VBOVertexHandle);
            glVertexPointer(3, GL_FLOAT, 0, 0L);
            glBindBuffer(GL_ARRAY_BUFFER,VBOColorHandle);
            glColorPointer(3,GL_FLOAT, 0, 0L);
            glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
            glBindTexture(GL_TEXTURE_2D, 1);
            glTexCoordPointer(2,GL_FLOAT,0,0L);
            glDrawArrays(GL_QUADS, 0, CHUNK_SIZE *CHUNK_SIZE*CHUNK_SIZE*24);
        glPopMatrix();
    }
    
    public void rebuildMesh(float startX, float startY, float startZ) {
//        SimplexNoise noise=new SimplexNoise(100,0.1,5000);
//        int i=(int)(xStart+x*((XEnd-xStart)/xResolution));
//        float height = (startY+ (int)(100*noise.getNoise(i,j,k)) * CUBE_LENGTH);
        VBOColorHandle= glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle= glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData= BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexTextureData=BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE *CHUNK_SIZE)* 6 * 12);
        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                for(float y = 0; y < CHUNK_SIZE; y++){
//                    VertexPositionData.put(createCube((float) (startX+ x * CUBE_LENGTH), (float)(y*CUBE_LENGTH+(int)(CHUNK_SIZE*.8)),(float) (startZ+ z * CUBE_LENGTH)));
                    if(Blocks[(int)x][(int)y][(int)z] == null)
                        continue;
                    VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH) + CUBE_LENGTH / 2, (float) (y * CUBE_LENGTH) + CUBE_LENGTH / 2, (float) (startZ + z * CUBE_LENGTH) + CUBE_LENGTH));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
                    VertexTextureData.put(createTexCube((float) 0, (float) 0,Blocks[(int)(x)][(int) (y)][(int) (z)]));
                }
            }
        }
        VertexTextureData.flip();
        VertexColorData.flip();
        VertexPositionData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors= new float[CubeColorArray.length* 4 * 6];
        for (int i= 0; i< cubeColors.length; i++) {
            cubeColors[i] = CubeColorArray[i%CubeColorArray.length];
        }
        return cubeColors;
    }
    
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[] {
            // TOP QUAD
            x + offset, y + offset, z,x -offset, y + offset, z,x -offset, y + offset, z -CUBE_LENGTH,x + offset, y + offset, z -CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y -offset, z -CUBE_LENGTH, x -offset, y -offset, z -CUBE_LENGTH,x -offset, y -offset, z,x + offset, y -offset, z,
            // FRONT QUAD
            x + offset, y + offset, z -CUBE_LENGTH, x -offset, y + offset, z -CUBE_LENGTH, x -offset, y -offset, z -CUBE_LENGTH,x + offset, y -offset, z -CUBE_LENGTH,
            // BACK QUAD
            x + offset, y -offset, z, x -offset, y -offset, z,x -offset, y + offset, z,x + offset, y + offset, z,
            // LEFT QUAD
            x -offset, y + offset, z -CUBE_LENGTH, x -offset, y + offset, z, x -offset, y -offset, z, x -offset, y -offset, z -CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z, x + offset, y + offset, z -CUBE_LENGTH, x + offset, y -offset, z -CUBE_LENGTH,x + offset, y -offset, z 
        };
    }
    
    private float[] getCubeColor(Block block) {
//        switch (block.GetID()) {
//            case 1:
//            return new float[] { 0, 1, 0 };
//            case 2:
//            return new float[] { 1, 0.5f, 0 };
//            case 3:
//            return new float[] { 0, 0f, 1f };
//        }
        return new float[] { 1, 1, 1 };
    }
    
    public Chunk(int startX, int startY, int startZ) {
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("terrain.png"));
        }
        catch(IOException e){
            System.out.print("ER-ROAR!");
        }
//        r= new Random();
//        generateHeightMap();
//        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
//        for (int x = 0; x < CHUNK_SIZE; x++) {
//            for (int y = 0; y < CHUNK_SIZE; y++) {
//                for (int z = 0; z < CHUNK_SIZE; z++) {
//                    if(r.nextFloat()>0.7f){
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
//                    }
//                    else if(r.nextFloat()>0.4f){
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);
//                    }
//                    else if(r.nextFloat()>0.2f){
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
//                    }
//                    else{
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Default);
//                    }
//                }
//            }
//        }
        VBOColorHandle= glGenBuffers();
        VBOVertexHandle= glGenBuffers();
        VBOTextureHandle= glGenBuffers();
        StartX= startX;
        StartY= startY;
        StartZ= startZ;
        
        // generate height map
        r = new Random(SEED);
        SimplexNoise noise = new SimplexNoise(CHUNK_SIZE, PERSISTANCE, SEED);
        heightMap = new double[CHUNK_SIZE][CHUNK_SIZE];
        for (int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                for (int k = 0; k < CHUNK_SIZE; k++) {
                    heightMap[i][j] = (noise.getNoise(StartX + i, StartY + j, StartZ + k) + 1) * 6;
                }
            }
        }

        
        // generate blocks based on height
        int lowest = CHUNK_SIZE;
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                int height = (int) heightMap[x][z];
                if (height < lowest){
                    lowest = height;
                }
                for (int y = 0; y < CHUNK_SIZE; y++) {
                    if (y > heightMap[x][z]) {
                        Blocks[x][y][z] = null;
                    }
                    else if (y == 0) {
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Bedrock);
                    } 
                    else if (y < CHUNK_SIZE / 8) {
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Stone);
                    } 
//                    else if (y < CHUNK_SIZE / 6) {
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Sand);
//                    }
//                    else if (y < CHUNK_SIZE / 4) {
//                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Water);
//                    }
                    else if (y == height) { 
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);           
                    }
                    else {
                        Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Dirt);             
                    }
                }
            }
        }
        
         // Fill sand      
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                if (Blocks[x][lowest+1][z] == null) {
                    continue;
                }
                if ((x + 1 < CHUNK_SIZE && Blocks[x + 1][lowest+1][z] == null) || (x - 1 >= 0 && Blocks[x - 1][lowest+1][z] == null)
                        || (z + 1 < CHUNK_SIZE && Blocks[x][lowest+1][z + 1] == null) || (z - 1 >= 0 && Blocks[x][lowest+1][z - 1] == null)) {
                    Blocks[x][lowest+1][z] = new Block(Block.BlockType.BlockType_Sand);
                }
            }
        }
        // Fill water
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                if (Blocks[x][lowest+1][z] == null) {
                    Blocks[x][lowest+1][z] = new Block(Block.BlockType.BlockType_Water);
                    if (lowest >= 1) {
                        Blocks[x][lowest][z] = new Block(Block.BlockType.BlockType_Sand);
                    }
                }
            }
        }
        
        rebuildMesh(startX, startY, startZ);
    }
    
    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        switch (block.GetID()) {
            case 0: //grass
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*10,
                x + offset*2, y + offset*10,
                x + offset*2, y + offset*9,
                x + offset*3, y + offset*9,
                // TOP!
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // FRONT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                // BACK QUAD
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                // LEFT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1,
                // RIGHT QUAD
                x + offset*3, y + offset*0,
                x + offset*4, y + offset*0,
                x + offset*4, y + offset*1,
                x + offset*3, y + offset*1};
            case 1: //SAND
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*1,
                x + offset*3, y + offset*2,
                x + offset*2, y + offset*2,
                x + offset*2, y + offset*1,
                // TOP!
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                // FRONT QUAD
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                // BACK QUAD
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                // LEFT QUAD
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                // RIGHT QUAD
                x + offset*3, y + offset*0,
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0};
            case 2: //WATER
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11,
                // TOP!
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11,
                // FRONT QUAD
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11,
                // BACK QUAD
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11,
                // LEFT QUAD
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11,
                // RIGHT QUAD
                x + offset*3, y + offset*11,
                x + offset*3, y + offset*12,
                x + offset*2, y + offset*12,
                x + offset*2, y + offset*11};
            case 3: //dirt
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // TOP!
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // FRONT QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // BACK QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // LEFT QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0,
                // RIGHT QUAD
                x + offset*3, y + offset*1,
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*0,
                x + offset*3, y + offset*0};
            case 4: //STONE
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                // TOP!
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                // FRONT QUAD
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                // BACK QUAD
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                // LEFT QUAD
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1,
                // RIGHT QUAD
                x + offset*1, y + offset*1,
                x + offset*1, y + offset*2,
                x + offset*0, y + offset*2,
                x + offset*0, y + offset*1};
            case 5: //BEDROCK
                return new float[] {
                // BOTTOM QUAD(DOWN=+Y)
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                // TOP!
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                // FRONT QUAD
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                // BACK QUAD
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                // LEFT QUAD
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1,
                // RIGHT QUAD
                x + offset*2, y + offset*1,
                x + offset*2, y + offset*2,
                x + offset*1, y + offset*2,
                x + offset*1, y + offset*1};
            default:
                return new float[]{};
        }
    }
}
