package com.example;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Sphere {
    private long window;

    public void run() {
        init(); // Initialize the window and OpenGL settings
        loop(); // Start the rendering loop
        
        // Cleanup and terminate GLFW
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Create a window
        window = glfwCreateWindow(800, 600, "3D Sphere Rendering", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create window");
        }
        
        // Set the current OpenGL context to the window
        glfwMakeContextCurrent(window);
        
        // Initialize OpenGL capabilities
        GL.createCapabilities();
        
        // Enable depth testing for proper 3D rendering
        glEnable(GL_DEPTH_TEST);
        
        // Enable lighting and set up a light source
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
        
        // Set up perspective projection
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspectRatio = 800f / 600f;
        gluPerspective(45.0f, aspectRatio, 0.1f, 100.0f);
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        // Main rendering loop
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            
            // Set light position
            float[] lightPos = {4.0f, 2.0f, 3.0f, 1.0f}; // Point light
            glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
            
            // Render spheres
            renderSpheres();
            
            // Swap buffers and poll events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void renderSpheres() {
        // Render a red sphere on the left
        glPushMatrix();
        glTranslatef(-1.0f, 0.0f, -3.0f);
        glColor3f(1.0f, 0.0f, 0.0f); // Red
        drawSphere(0.3f, 16, 16);
        glPopMatrix();
        
        // Render a green sphere in the middle
        glPushMatrix();
        glTranslatef(0.0f, 0.0f, -3.0f);
        glColor3f(0.0f, 1.0f, 0.0f); // Green
        drawSphere(0.4f, 16, 16);
        glPopMatrix();
        
        // Render a blue sphere on the right
        glPushMatrix();
        glTranslatef(1.0f, 0.0f, -3.0f);
        glColor3f(0.0f, 0.0f, 1.0f); // Blue
        drawSphere(0.5f, 16, 16);
        glPopMatrix();
    }

    private void drawSphere(float radius, int slices, int stacks) {
        // Render a sphere using triangle strips
        for (int i = 0; i < stacks; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i) / stacks);
            double z0 = Math.sin(lat0);
            double zr0 = Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) (i + 1) / stacks);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            glBegin(GL_TRIANGLE_STRIP);
            for (int j = 0; j <= slices; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / slices;
                double x = Math.cos(lng);
                double y = Math.sin(lng);
                
                // Set normal and vertex for the first point
                glNormal3d(x * zr0, y * zr0, z0);
                glVertex3d(radius * x * zr0, radius * y * zr0, radius * z0);
                
                // Set normal and vertex for the second point
                glNormal3d(x * zr1, y * zr1, z1);
                glVertex3d(radius * x * zr1, radius * y * zr1, radius * z1);
            }
            glEnd();
        }
    }

    private void gluPerspective(float fov, float aspect, float near, float far) {
        // Custom implementation of gluPerspective
        float fH = (float) Math.tan(fov / 360 * Math.PI) * near;
        float fW = fH * aspect;
        glFrustum(-fW, fW, -fH, fH, near, far);
    }

    public static void main(String[] args) {
        new Sphere().run(); // Start the application
    }
}
