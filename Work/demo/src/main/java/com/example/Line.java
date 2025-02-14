package com.example;

import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Line {
    private long window;

    public void run() {
        init(); // Initialize the window and OpenGL settings
        loop(); // Start the rendering loop
        
        // Cleanup and terminate GLFW
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Create the GLFW window
        window = glfwCreateWindow(800, 600, "3D Graphics Demo", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Enable depth testing for proper 3D rendering
        glEnable(GL_DEPTH_TEST);
        
        // Enable smooth line rendering
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        
        // Enable blending for smooth transparency effects
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // Clear the color and depth buffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            
            // Draw different elements
            drawLine(new Vector3(-0.5f, -0.5f, 0), new Vector3(0.5f, 0.5f, 0)); // Draw a simple line
            drawBezierCurve(); // Draw a smooth Bezier curve
            drawPolygon(); // Draw a polygon

            // Swap buffers and poll for user input events
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void drawLine(Vector3 p1, Vector3 p2) {
        glBegin(GL_LINES); // Begin drawing a line
        glVertex3f(p1.x, p1.y, p1.z); // First point
        glVertex3f(p2.x, p2.y, p2.z); // Second point
        glEnd(); // End drawing
    }

    private void drawBezierCurve() {
        // Define control points for the Bezier curve
        Vector3[] controlPoints = {
            new Vector3(-0.8f, 0, 0), new Vector3(-0.4f, 0.5f, 0),
            new Vector3(0.4f, -0.5f, 0), new Vector3(0.8f, 0, 0)
        };

        glBegin(GL_LINE_STRIP); // Start drawing a line strip
        for (float t = 0; t <= 1; t += 0.01f) {
            Vector3 p = bezier(t, controlPoints); // Calculate Bezier curve point
            glVertex3f(p.x, p.y, p.z);
        }
        glEnd(); // End drawing
    }

    private Vector3 bezier(float t, Vector3[] points) {
        int n = points.length - 1;
        Vector3 result = new Vector3(0, 0, 0);
        for (int i = 0; i <= n; i++) {
            float b = bernstein(n, i, t); // Calculate Bernstein polynomial
            result = result.add(points[i].multiply(b)); // Accumulate result
        }
        return result;
    }

    private float bernstein(int n, int i, float t) {
        return (float) (binomial(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i));
    }

    private int binomial(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomial(n - 1, k - 1) + binomial(n - 1, k); // Recursively calculate binomial coefficient
    }

    private void drawPolygon() { 
        // Define vertices for the polygon
        Vector3[] points = {
            new Vector3(-0.5f, -0.5f, 0), new Vector3(0.5f, -0.5f, 0),
            new Vector3(0.7f, 0.5f, 0), new Vector3(-0.7f, 0.5f, 0)
        };

        glBegin(GL_POLYGON); // Start drawing a polygon
        for (Vector3 p : points) {
            glVertex3f(p.x, p.y, p.z); // Define polygon vertices
        }
        glEnd(); // End drawing
    }

    public static void main(String[] args) {
        new Line().run(); // Start the application
    }
}
