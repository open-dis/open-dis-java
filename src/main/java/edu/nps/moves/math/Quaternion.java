/*
Copyright (c) 1995-2009 held by the author(s).  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer
      in the documentation and/or other materials provided with the
      distribution.
    * Neither the names of the Naval Postgraduate School (NPS)
      Modeling Virtual Environments and Simulation (MOVES) Institute
      (http://www.nps.edu and http://www.movesinstitute.org)
      nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific
      prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package edu.nps.moves.math;

/**
 * EXECUTIVE SUMMARY
 * Module Name: Quaternion.java
 * Description: Definition of the Quaternion class
 * @author Kent A. Watsen, http://www.mbay.net/~watsen
 */
public class Quaternion {

    private float q[];

    public Quaternion() {
        q = new float[4];
        makeIdent();
    }

    public Quaternion(float axis[], float angle) // radians
    {
        q = new float[4];
        setAxisAngle(axis, angle);
    }

    public Quaternion(Vec3f axis, float angle) // radians
    {
        q = new float[4];
        setAxisAngle(axis, angle);
    }

    public Quaternion(Matrix3f mat) {
        q = new float[4];
        setMat3(mat);
    }

    public Quaternion(Matrix4f mat) {
        q = new float[4];
        setMat4(mat);
    }

    public Quaternion(Quaternion quat) {
        q = new float[4];
        setQuat(quat);
    }

    public Quaternion(float vec1[], float vec2[]) {
        q = new float[4];
        makeFromVecs(vec1, vec2);
    }

    public Quaternion(Vec3f vec1, Vec3f vec2) {
        q = new float[4];
        makeFromVecs(vec1, vec2);
    }

    public void print() {
        System.out.println("q = " + q[0] + ", " + q[1] + ", " + q[2] + ", " + q[3]);
    }

    public void setVec(float i, float j, float k) {
        q[0] = i;
        q[1] = j;
        q[2] = k;
        q[3] = 0.0f;
    }

    public void getVec(float i[], float j[], float k[]) {
        i[0] = q[0];
        j[0] = q[1];
        k[0] = q[2];
    }

    public void setVec(float vec[]) {
        q[0] = vec[0];
        q[1] = vec[1];
        q[2] = vec[2];
        q[3] = 0.0f;
    }

    public void getVec(float vec[]) {
        vec[0] = q[0];
        vec[1] = q[1];
        vec[2] = q[2];
    }

    public void setVec(Vec3f vec) {
        q[0] = vec.get(0);
        q[1] = vec.get(1);
        q[2] = vec.get(2);
        q[3] = 0.0f;
    }

    public void getVec(Vec3f vec) {
        vec.set(q[0], q[1], q[2]);
    }

    public void setAxisAngle(float axis_angle[]) // radians - tested ok
    {
        float sin;
        float length_sqr;
        float one_over_length;

        if (axis_angle[3] == 0.0f) {
            makeIdent();
        }

        length_sqr = axis_angle[0] * axis_angle[0] + axis_angle[1] * axis_angle[1] + axis_angle[2] * axis_angle[2];
        if (length_sqr < 0.0001) {
            makeIdent();
        }

        one_over_length = 1.0f / (float) Math.sqrt(length_sqr);
        axis_angle[0] = axis_angle[0] * one_over_length;
        axis_angle[1] = axis_angle[1] * one_over_length;
        axis_angle[2] = axis_angle[2] * one_over_length;

        sin = (float) Math.sin(axis_angle[3] * 0.5f);
        q[0] = axis_angle[0] * sin;
        q[1] = axis_angle[1] * sin;
        q[2] = axis_angle[2] * sin;
        q[3] = (float) Math.cos(axis_angle[3] * 0.5f);
    }

    public void getAxisAngle(float axis_angle[]) // radians - tested ok
    {
        float one_over_sin;

        axis_angle[3] = 2.0f * (float) Math.acos(q[3]);
        if (Math.abs(axis_angle[3]) < 0.0001f) {
            axis_angle[0] = 0.0f; // Doesn't this lose
            axis_angle[1] = 1.0f; // axis info?
            axis_angle[2] = 0.0f;
        } else {
            one_over_sin = 1.0f / (float) Math.sin(axis_angle[3] * 0.5f);
            axis_angle[0] = q[0] * one_over_sin;
            axis_angle[1] = q[1] * one_over_sin;
            axis_angle[2] = q[2] * one_over_sin;
        }
    }

    public void setAxisAngle(Vec4f axis_angle) // radians
    {
        float aa[] = new float[4];
        axis_angle.get(aa);
        setAxisAngle(aa);
    }

    public void getAxisAngle(Vec4f axis_angle) // radians
    {
        float aa[] = new float[4];
        getAxisAngle(aa);
        axis_angle.set(aa);
    }

    public void setAxisAngle(float axis[], float angle) // radians
    {
        float aa[] = new float[4];
        aa[0] = axis[0];
        aa[1] = axis[1];
        aa[2] = axis[2];
        aa[3] = angle;
        setAxisAngle(aa);
    }

    public void getAxisAngle(float axis[], float angle[]) // radians
    {
        float aa[] = new float[4];
        getAxisAngle(aa);
        axis[0] = aa[0];
        axis[1] = aa[1];
        axis[2] = aa[2];
        angle[0] = aa[3];
    }

    public void setAxisAngle(Vec3f axis, float angle) // radians
    {
        float aa[] = new float[4];
        aa[0] = axis.get(0);
        aa[1] = axis.get(1);
        aa[2] = axis.get(2);
        aa[3] = angle;
        setAxisAngle(aa);
    }

    public void getAxisAngle(Vec3f axis, float angle[]) // radians
    {
        float aa[] = new float[4];
        getAxisAngle(aa);
        axis.set(aa[0], aa[1], aa[2]);
        angle[0] = aa[3];
    }

    public void setAxisAngle(float i, float j, float k, float angle) // radians
    {
        float aa[] = new float[4];
        aa[0] = i;
        aa[1] = j;
        aa[2] = k;
        aa[3] = angle;
        setAxisAngle(aa);
    }

    public void getAxisAngle(float i[], float j[], float k[], float angle[]) // radians
    {
        float aa[] = new float[4];
        getAxisAngle(aa);
        i[0] = aa[0];
        j[0] = aa[1];
        k[0] = aa[2];
        angle[0] = aa[3];
    }

    public void setEulers(float hpr[]) // radians - tested ok
    {
        float sin_h = (float) Math.sin(hpr[0] * 0.5f);
        float cos_h = (float) Math.cos(hpr[0] * 0.5f);
        float sin_p = (float) Math.sin(hpr[1] * 0.5f);
        float cos_p = (float) Math.cos(hpr[1] * 0.5f);
        float sin_r = (float) Math.sin(hpr[2] * 0.5f);
        float cos_r = (float) Math.cos(hpr[2] * 0.5f);

        q[0] = cos_h * sin_p * cos_r + sin_h * cos_p * sin_r;
        q[1] = sin_h * cos_p * cos_r - cos_h * sin_p * sin_r;
        q[2] = cos_h * cos_p * sin_r - sin_h * sin_p * cos_r;
        q[3] = cos_h * cos_p * cos_r + sin_h * sin_p * sin_r;
    }

    public void getEulers(float hpr[]) // radians - tested ok
    {
        Matrix3f m = new Matrix3f();

        getMat3(m);
        m.getEulers(hpr);
    }

    public void setEulers(float h, float p, float r) // radians; Vince, pg 201
    {
        float hpr[] = new float[3];
        hpr[0] = h;
        hpr[1] = p;
        hpr[2] = r;
        setEulers(hpr);
    }

    public void getEulers(float h[], float p[], float r[]) {
        float hpr[] = new float[3];
        getEulers(hpr);
        h[0] = hpr[0];
        p[0] = hpr[1];
        r[0] = hpr[2];
    }

    public void setMat3(float mat[][]) // Chrenshaw, pg 16 (he transposes m!)
    {
        float tr, s;

        tr = mat[0][0] + mat[1][1] + mat[2][2] + 1;
        if (tr > 0.0625f) {
            s = (float) Math.sqrt(tr);
            q[3] = s * 0.5f;
            s = 0.5f / s;

            q[0] = (mat[1][2] - mat[2][1]) * s;
            q[1] = (mat[2][0] - mat[0][2]) * s;
            q[2] = (mat[0][1] - mat[1][0]) * s;
            return;
        }

        tr = -mat[0][0] - mat[1][1] + mat[2][2] + 1;
        if (tr > 0.0625f) {
            s = (float) Math.sqrt(tr);
            q[2] = s * 0.5f;
            s = 0.5f / s;

            q[0] = (mat[2][0] - mat[0][2]) * s;
            q[1] = (mat[2][1] + mat[1][2]) * s;
            q[3] = (mat[0][1] - mat[1][0]) * s;
            return;
        }

        tr = -mat[0][0] + mat[1][1] - mat[2][2] + 1.0f;
        if (tr > 0.0625f) {
            s = (float) Math.sqrt(tr);
            q[1] = s * 0.5f;
            s = 0.5f / s;

            q[0] = (mat[1][0] + mat[0][1]) * s;
            q[2] = (mat[2][1] + mat[1][2]) * s;
            q[3] = (mat[2][0] - mat[0][2]) * s;
            return;
        }

        tr = mat[0][0] - mat[1][1] - mat[2][2] + 1.0f;
        if (tr > 0.0625f) {
            s = (float) Math.sqrt(tr);
            q[0] = s * 0.5f;
            s = 0.5f / s;

            q[1] = (mat[1][0] + mat[0][1]) * s;
            q[2] = (mat[2][0] - mat[0][2]) * s;
            q[3] = (mat[1][2] - mat[2][1]) * s;
            return;
        }
    }

    public void getMat3(float mat[][]) // Vince, pg 202; WATT, pg 362 - tested ok
    {
        float two_over_length_sqr;
        float xs, ys, zs;
        float wx, wy, wz;
        float xx, xy, xz;
        float yy, yz, zz;

        two_over_length_sqr = 2.0f / length_sqr();
        xs = q[0] * two_over_length_sqr;
        ys = q[1] * two_over_length_sqr;
        zs = q[2] * two_over_length_sqr;

        wx = q[3] * xs;
        wy = q[3] * ys;
        wz = q[3] * zs;
        xx = q[0] * xs;
        xy = q[0] * ys;
        xz = q[0] * zs;
        yy = q[1] * ys;
        yz = q[1] * zs;
        zz = q[2] * zs;

        mat[0][0] = 1.0f - yy - zz;
        mat[0][1] = xy - wz;
        mat[0][2] = xz + wy;
        mat[1][0] = xy + wz;
        mat[1][1] = 1.0f - xx - zz;
        mat[1][2] = yz - wx;
        mat[2][0] = xz - wy;
        mat[2][1] = yz + wx;
        mat[2][2] = 1.0f - xx - yy;
    }

    public void setMat3(Matrix3f mat) {
        float m[][] = new float[3][3];
        mat.getMat(m);
        setMat3(m);
    }

    public void getMat3(Matrix3f mat) {
        float m[][] = new float[3][3];
        getMat3(m);
        mat.setMat(m);
    }

    public void setMat4(float mat[][]) {
        setMat3(mat);  // It doesn't know the difference
    }

    public void getMat4(float mat[][]) {
        getMat3(mat);  // It doesn't know the difference
        mat[0][3] = 0.0f;
        mat[1][3] = 0.0f;
        mat[2][3] = 0.0f;
        mat[3][0] = 0.0f;
        mat[3][1] = 0.0f;
        mat[3][2] = 0.0f;
        mat[3][3] = 1.0f;
    }

    public void setMat4(Matrix4f mat) {
        float m[][] = new float[4][4];
        mat.getMat(m);
        setMat4(m);
    }

    public void getMat4(Matrix4f mat) {
        float m[][] = new float[4][4];
        getMat4(m);
        mat.setMat(m);
    }

    public void setQuat(float quat[]) {
        q[0] = quat[0];
        q[1] = quat[1];
        q[2] = quat[2];
        q[3] = quat[3];
    }

    public void getQuat(float quat[]) {
        quat[0] = q[0];
        quat[1] = q[1];
        quat[2] = q[2];
        quat[3] = q[3];
    }

    public void setQuat(Quaternion quat) {
        float quat2[] = new float[4];
        quat.getQuat(quat2);
        setQuat(quat2);
    }

    public void getQuat(Quaternion quat) {
        float quat2[] = new float[4];
        getQuat(quat2);
        quat.setQuat(quat2);
    }

    public void setQuat(float i, float j, float k, float w) {
        q[0] = i;
        q[1] = j;
        q[2] = k;
        q[3] = w;
    }

    public void getQuat(float i[], float j[], float k[], float w[]) {
        i[0] = q[0];
        j[0] = q[1];
        k[0] = q[2];
        w[0] = q[3];
    }

    public void setQuatValue(int index, float value) {
        if (index < 0 || index > 3) {
            return;
        }
        q[index] = value;
    }

    public float getQuatValue(int index) {
        if (index < 0 || index > 3) {
            return 0.0f;
        }
        return q[index];
    }

    public void makeIdent() {
        q[0] = 0.0f;
        q[1] = 0.0f;
        q[2] = 0.0f;
        q[3] = 1.0f;
    }

    public float length() {
        return (float) Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
    }

    public float length_sqr() {
        return q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3];
    }

    public void normalize() // Crenshaw, pg 20
    {
        float one_over_length = 1.0f / length();
        q[0] = q[0] * one_over_length;
        q[1] = q[1] * one_over_length;
        q[2] = q[2] * one_over_length;
        q[3] = q[3] * one_over_length;
    }

    public void normalize(Quaternion quat) // Crenshaw, pg 20
    {
        float one_over_length = 1.0f / quat.length();
        q[0] = quat.getQuatValue(0) * one_over_length;
        q[1] = quat.getQuatValue(1) * one_over_length;
        q[2] = quat.getQuatValue(2) * one_over_length;
        q[3] = quat.getQuatValue(3) * one_over_length;
    }

    public void conjugate() // Gem1, pg 501
    {
        q[0] = -q[0];
        q[1] = -q[1];
        q[2] = -q[2];
        q[3] = q[3];
    }

    public void conjugate(Quaternion quat) // Gem1, pg 501
    {
        q[0] = -quat.getQuatValue(0);
        q[1] = -quat.getQuatValue(1);
        q[2] = -quat.getQuatValue(2);
        q[3] = quat.getQuatValue(3);
    }

    public void invert() // Vince 197; Gem1 pg 501 - tested ok
    {
        float one_over_length_sqr;
        one_over_length_sqr = 1.0f / length_sqr();
        q[0] = -q[0] * one_over_length_sqr;
        q[1] = -q[1] * one_over_length_sqr;
        q[2] = -q[2] * one_over_length_sqr;
        q[3] = q[3] * one_over_length_sqr;
    }

    public void invert(Quaternion quat) // Vince 197; Gem1 pg 501 - tested ok
    {
        float one_over_length_sqr;
        one_over_length_sqr = 1.0f / quat.length_sqr();
        q[0] = -quat.getQuatValue(0) * one_over_length_sqr;
        q[1] = -quat.getQuatValue(1) * one_over_length_sqr;
        q[2] = -quat.getQuatValue(2) * one_over_length_sqr;
        q[3] = quat.getQuatValue(3) * one_over_length_sqr;
    }

    public void add(Quaternion quat) // Vince 197
    {
        q[0] = q[0] + quat.getQuatValue(0);
        q[1] = q[1] + quat.getQuatValue(1);
        q[2] = q[2] + quat.getQuatValue(2);
        q[3] = q[3] + quat.getQuatValue(3);
    }

    public void add(Quaternion quat1, Quaternion quat2) // Vince 197
    {
        q[0] = quat1.getQuatValue(0) + quat2.getQuatValue(0);
        q[1] = quat1.getQuatValue(1) + quat2.getQuatValue(1);
        q[2] = quat1.getQuatValue(2) + quat2.getQuatValue(2);
        q[3] = quat1.getQuatValue(3) + quat2.getQuatValue(3);
    }

    public void sub(Quaternion quat) // Vince 197
    {
        q[0] = q[0] - quat.getQuatValue(0);
        q[1] = q[1] - quat.getQuatValue(1);
        q[2] = q[2] - quat.getQuatValue(2);
        q[3] = q[3] - quat.getQuatValue(3);
    }

    public void sub(Quaternion quat1, Quaternion quat2) // Vince 197
    {
        q[0] = quat1.getQuatValue(0) - quat2.getQuatValue(0);
        q[1] = quat1.getQuatValue(1) - quat2.getQuatValue(1);
        q[2] = quat1.getQuatValue(2) - quat2.getQuatValue(2);
        q[3] = quat1.getQuatValue(3) - quat2.getQuatValue(3);
    }

    public void preMult(Quaternion quat1) // NPS wrong?; Crenshaw pg 20
    {
        float tmp_q[] = new float[4];
        float q1[] = new float[4];

        quat1.getQuat(q1);
        tmp_q[0] = q1[3] * q[0] - q1[2] * q[1] + q1[1] * q[2] + q1[0] * q[3];
        tmp_q[1] = q1[2] * q[0] + q1[3] * q[1] - q1[0] * q[2] + q1[1] * q[3];
        tmp_q[2] = -q1[1] * q[0] + q1[0] * q[1] + q1[3] * q[2] + q1[2] * q[3];
        tmp_q[3] = -q1[0] * q[0] - q1[1] * q[1] - q1[2] * q[2] + q1[3] * q[3];
        setQuat(tmp_q);
    }

    public void postMult(Quaternion quat2) // NPS wrong?; Crenshaw pg 20
    {
        float tmp_q[] = new float[4];
        float q2[] = new float[4];

        quat2.getQuat(q2);
        tmp_q[0] = q[3] * q2[0] - q[2] * q2[1] + q[1] * q2[2] + q[0] * q2[3];
        tmp_q[1] = q[2] * q2[0] + q[3] * q2[1] - q[0] * q2[2] + q[1] * q2[3];
        tmp_q[2] = -q[1] * q2[0] + q[0] * q2[1] + q[3] * q2[2] + q[2] * q2[3];
        tmp_q[3] = -q[0] * q2[0] - q[1] * q2[1] - q[2] * q2[2] + q[3] * q2[3];
        setQuat(tmp_q);
    }

    public void mult(Quaternion quat1, Quaternion quat2) // NPS wrong?; Crenshaw pg 20 - tested ok
    {
        float q1[] = new float[4];
        float q2[] = new float[4];

        quat1.getQuat(q1);
        quat2.getQuat(q2);
        q[0] = q1[3] * q2[0] - q1[2] * q2[1] + q1[1] * q2[2] + q1[0] * q2[3];
        q[1] = q1[2] * q2[0] + q1[3] * q2[1] - q1[0] * q2[2] + q1[1] * q2[3];
        q[2] = -q1[1] * q2[0] + q1[0] * q2[1] + q1[3] * q2[2] + q1[2] * q2[3];
        q[3] = -q1[0] * q2[0] - q1[1] * q2[1] - q1[2] * q2[2] + q1[3] * q2[3];
    }

    public void makeFromVecs(float i1, float j1, float k1, float i2, float j2, float k2) {
        Vec3f tmp1 = new Vec3f(i1, j1, k1);
        Vec3f tmp2 = new Vec3f(i2, j2, k2);
        makeFromVecs(tmp1, tmp2);
    }

    public void makeFromVecs(float vec1[], float vec2[]) {
        Vec3f tmp1 = new Vec3f(vec1);
        Vec3f tmp2 = new Vec3f(vec2);
        makeFromVecs(tmp1, tmp2);
    }

    public void makeFromVecs(Vec3f vec1, Vec3f vec2) // modified Ken Shoemake - tested ok
    {
        Vec3f tmp1 = new Vec3f();
        Vec3f tmp2 = new Vec3f();
        Vec3f axis = new Vec3f();
        float dot;
        float angle;
        float abs_angle;

        tmp1.normalize(vec1);
        tmp2.normalize(vec2);

        dot = Vec3f.dot(tmp1, tmp2);
        angle = (float) Math.acos(dot);       // -PI < angle < +PI
        abs_angle = Math.abs(angle);          // 0 <= angle < PI

        if (abs_angle < 0.0001f) // vec's parallel
        {
            makeIdent();
            return;
        }

        if (abs_angle > ((float) Math.PI) - 0.0001f) // vec's oppose
        {
            // Can't use cross product for axis.  Find arbitrary axis.
            // First try cross with X-axis, else just use Z-axis.
            axis.set(1.0f, 0.0f, 0.0f);
            if (axis.dot(tmp1) < 0.1f) {
                axis.cross(tmp1);
                axis.normalize();
            } else {
                axis.set(0.0f, 0.0f, 1.0f);
            }
            setAxisAngle(axis, angle);
            return;
        }

        // normal case
        axis.cross(tmp1, tmp2);
        axis.normalize();
        setAxisAngle(axis, angle);
    }

    public void xform(Vec3f vec) // - tested ok
    {
        Quaternion vecQuat = new Quaternion();
        Quaternion tmpQuat = new Quaternion();

        vecQuat.setVec(vec);
        tmpQuat.invert(this);

        tmpQuat.mult(vecQuat, tmpQuat);
        tmpQuat.mult(this, tmpQuat);

        tmpQuat.getVec(vec);
    }

    public void xform(float v[]) // - tested ok
    {
        Quaternion vecQuat = new Quaternion();
        Quaternion tmpQuat = new Quaternion();

        vecQuat.setVec(v);
        tmpQuat.invert(this);

        tmpQuat.mult(vecQuat, tmpQuat);
        tmpQuat.mult(this, tmpQuat);

        tmpQuat.getVec(v);
    }

    public void slerp(Quaternion quat1, Quaternion quat2, float alpha, int spin) // - tested ok
    { // 0<=alpha<=1, spin = {..., -1, 0, 1, ...} 
        float q1[] = new float[4];
        float q2[] = new float[4];
        float beta;                 // complementary interp parameter
        float theta;                // angle between A and B
        float sin_t, cos_t;         // sine, cosine of theta
        float phi;                  // theta plus spins
        int bflip;                // use negation of B?

        quat1.getQuat(q1);
        quat2.getQuat(q2);


        // cosine theta = dot product of A and B
        cos_t = q1[0] * q2[0] + q1[1] * q2[1] + q1[2] * q2[2] + q1[3] * q2[3];

        // if B is on opposite hemisphere from A, use -B instead
        if (cos_t < 0.0f) {
            cos_t = -cos_t;
            bflip = 1; // true
        } else {
            bflip = 0; // false
        }

        // if B is (within precision limits) the same as A,
        // just linear interpolate between A and B.
        // Can't do spins, since we don't know what direction to spin.

        if (1.0f - cos_t < 0.0001f) {
            beta = 1.0f - alpha;
        } else // normal case
        {
            theta = (float) Math.acos(cos_t);
            phi = theta + spin * (float) Math.PI;
            sin_t = (float) Math.sin(theta);
            beta = (float) Math.sin(theta - alpha * phi) / sin_t;
            alpha = (float) Math.sin(alpha * phi) / sin_t;
        }

        if (bflip == 1) {
            alpha = -alpha;
        }

        // interpolate
        q[0] = beta * q1[0] + alpha * q2[0];
        q[1] = beta * q1[1] + alpha * q2[1];
        q[2] = beta * q1[2] + alpha * q2[2];
        q[3] = beta * q1[3] + alpha * q2[3];
    }
}
/* Alternative...may have less accuracy.
public void setMat(float mat[][]) // Watt, pg 363; Crenshaw, pg 15
{
float tr, s;
int i, j, k;

tr = m[0][0] + m[1][1] + m[2][2];
if (tr > 0.0f)
{
s = (float)Math.sqrt(tr + 1.0f);
r[3] = s*0.5f;
s = 0.5f/s;

r[0] = (m[1][2] - m[2][1])*s;
r[1] = (m[2][0] - m[0][2])*s;
r[2] = (m[0][1] - m[1][0])*s;
}
else
{
i = 0;
if (m[1][1] > m[i][i])
i = 1;
if (m[2][2] > m[i][i])
i = 2;
j = (i+1)%3;
k = (j+1)%3;

s = (float)Math.sqrt( (m[i][i] - (m[j][j]+m[k][k])) + 1.0f);

r[i] = s*0.5f;
s = 0.5f/s;
r[3] = (m[j][k] - m[k][j])*s;
r[j] = (m[i][j] + m[j][i])*s;
r[k] = (m[i][k] + m[k][i])*s;
}
}
 */