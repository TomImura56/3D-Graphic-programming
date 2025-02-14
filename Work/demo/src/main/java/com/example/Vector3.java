package com.example;

public class Vector3 {
    public float x, y, z;

    // Constructor to initialize vector components
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Adds this vector to another vector and returns the result
    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    // Subtracts another vector from this vector and returns the result
    public Vector3 subtract(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    // Multiplies this vector by a scalar and returns the result
    public Vector3 multiply(float scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    // Computes the dot product of this vector and another vector
    public float dot(Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    // Computes the cross product of this vector and another vector
    public Vector3 cross(Vector3 v) {
        return new Vector3(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    // Computes the magnitude (length) of this vector
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    // Normalizes this vector (makes it unit length)
    public Vector3 normalize() {
        float mag = magnitude();
        return (mag == 0) ? new Vector3(0, 0, 0) : multiply(1 / mag);
    }

    // Computes the distance between this vector and another vector
    public float distance(Vector3 v) {
        return (float) Math.sqrt(
            (x - v.x) * (x - v.x) +
            (y - v.y) * (y - v.y) +
            (z - v.z) * (z - v.z)
        );
    }

    // Converts this vector to a string representation
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
