package com.example;

import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window; // Handle for the GLFW window
    private float angle = 0; // Rotation angle for the cube

    public void run() {
        init(); // Initialize GLFW and OpenGL settings
        loop(); // Enter the render loop
        
        glfwDestroyWindow(window); // Cleanup and close the window
        glfwTerminate(); // Terminate GLFW
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create a window with size 800x600 and title "Rotating Cube"
        window = glfwCreateWindow(800, 600, "Rotating Cube", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities(); // Initialize OpenGL capabilities
        glEnable(GL_DEPTH_TEST); // Enable depth testing for 3D rendering
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) { // Run until the window should close
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Clear color and depth buffers

            glLoadIdentity(); // Reset transformations
            
            // Create a transformation matrix: rotate the cube around Y-axis and scale it
            Matrix4 transform = Matrix4.rotationY(angle).multiply(Matrix4.scaling(0.5f));
            
            drawWireframeCube(transform); // Render the cube

            angle += 0.01f; // Increment the rotation angle

            glfwSwapBuffers(window); // Swap the front and back buffers
            glfwPollEvents(); // Poll for user input events
        }
    }

    private void drawWireframeCube(Matrix4 transform) {
        // Define the 8 vertices of a cube
        float[][] vertices = {
            {-1, -1, -1}, {1, -1, -1}, {1, 1, -1}, {-1, 1, -1},
            {-1, -1,  1}, {1, -1,  1}, {1, 1,  1}, {-1, 1,  1}
        };

        // Define the edges connecting the vertices
        int[][] edges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0},
            {4, 5}, {5, 6}, {6, 7}, {7, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
        };
        
        glColor3f(1.0f, 0.1f, 0.6f); // Set cube color
        glBegin(GL_LINES); // Start drawing lines for edges
        for (int[] edge : edges) {
            // Transform each vertex using the given transformation matrix
            Vector3 v1 = transform.multiply(new Vector3(vertices[edge[0]][0], vertices[edge[0]][1], vertices[edge[0]][2]));
            Vector3 v2 = transform.multiply(new Vector3(vertices[edge[1]][0], vertices[edge[1]][1], vertices[edge[1]][2]));
            
            glVertex3f(v1.x, v1.y, v1.z); // Draw first vertex of the edge
            glVertex3f(v2.x, v2.y, v2.z); // Draw second vertex of the edge
        }
        glEnd(); // End drawing
    }

    public static void main(String[] args) {
        new Main().run(); // Start the application
    }
}
