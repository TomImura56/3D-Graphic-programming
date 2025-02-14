package com.example;

public class Matrix4 {
    public float[][] m = new float[4][4];

    // Constructor initializes the matrix to an identity matrix
    public Matrix4() {
        identity();
    }

    // Sets this matrix to the identity matrix
    public void identity() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                m[i][j] = (i == j) ? 1 : 0;
    }

    // Multiplies this matrix by another matrix and returns the result
    public Matrix4 multiply(Matrix4 mat) {
        Matrix4 result = new Matrix4();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.m[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result.m[i][j] += this.m[i][k] * mat.m[k][j];
                }
            }
        }
        return result;
    }

    // Multiplies this matrix by a 3D vector and returns the transformed vector
    public Vector3 multiply(Vector3 v) {
        float x = m[0][0] * v.x + m[0][1] * v.y + m[0][2] * v.z + m[0][3];
        float y = m[1][0] * v.x + m[1][1] * v.y + m[1][2] * v.z + m[1][3];
        float z = m[2][0] * v.x + m[2][1] * v.y + m[2][2] * v.z + m[2][3];
        return new Vector3(x, y, z);
    }

    // Creates a translation matrix
    public static Matrix4 translation(float x, float y, float z) {
        Matrix4 mat = new Matrix4();
        mat.m[0][3] = x;
        mat.m[1][3] = y;
        mat.m[2][3] = z;
        return mat;
    }

    // Creates a uniform scaling matrix
    public static Matrix4 scaling(float scale) {
        Matrix4 mat = new Matrix4();
        mat.m[0][0] = scale;
        mat.m[1][1] = scale;
        mat.m[2][2] = scale;
        return mat;
    }

    // Creates a rotation matrix for rotation around the Y-axis
    public static Matrix4 rotationY(float angle) {
        Matrix4 mat = new Matrix4();
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        mat.m[0][0] = cos;
        mat.m[0][2] = -sin;
        mat.m[2][0] = sin;
        mat.m[2][2] = cos;
        return mat;
    }
}
