
package edu.nps.moves.math;

// Standard classes
import java.lang.Math;


/**
 * EXECUTIVE SUMMARY
 * Module Name: Matrix3f.java
 * Description: Definition of the Matrix3f class
 * @author Kent A. Watsen, http://www.mbay.net/~watsen
 */
public class Matrix3f
  {
  private
    float m[][];

  public Matrix3f()
    {
    m = new float[3][3];
    makeNull();
    }

  public Matrix3f(float mat[][])
    {
    m = new float[3][3];
    setMat(mat);
    }

  public Matrix3f(Matrix3f mat)
    {
    m = new float[3][3];
    setMat(mat);
    }

  public Matrix3f(Quaternion quat)
    {
    setQuat(quat);
    }

  public Matrix3f(float hpr[])
    {
    m = new float[3][3];
    setEulers(hpr);
    }

  public Matrix3f(float heading, float pitch, float roll)
    {
    m = new float[3][3];
    setEulers(heading, pitch, roll);
    }

  public void print()
    {
    System.out.println("m = " + m[0][0] + ", " + m[0][1] + ", " + m[0][2]
                              + m[1][0] + ", " + m[1][1] + ", " + m[1][2]
                              + m[2][0] + ", " + m[2][1] + ", " + m[2][2]);
    }

  public void setMatValue(int row, int col, float val)
    {
    if (row < 0 || row > 3 || col < 0 || col > 3)
      return;
    m[row][col] = val;
    }

  public float getMatValue(int row, int col)
    {
    if (row < 0 || row > 3 || col < 0 || col > 3)
      return 0.0f;
    return m[row][col];
    }

  public void setMat(float mat[][])
    {
    m[0][0] = mat[0][0];
    m[0][1] = mat[0][1];
    m[0][2] = mat[0][2];
    m[1][0] = mat[1][0];
    m[1][1] = mat[1][1];
    m[1][2] = mat[1][2];
    m[2][0] = mat[2][0];
    m[2][1] = mat[2][1];
    m[2][2] = mat[2][2];
    }

  public void getMat(float mat[][])
    {
    mat[0][0] = m[0][0];
    mat[0][1] = m[0][1];
    mat[0][2] = m[0][2];
    mat[1][0] = m[1][0];
    mat[1][1] = m[1][1];
    mat[1][2] = m[1][2];
    mat[2][0] = m[2][0];
    mat[2][1] = m[2][1];
    mat[2][2] = m[2][2];
    }

  public void setMat(Matrix3f mat)
    {
    float mat2[][] = new float[3][3];
    mat.getMat(mat2);
    setMat(mat2);
    }

  public void getMat(Matrix3f mat)
    {
    float mat2[][] = new float[3][3];
    getMat(mat2);
    mat.setMat(mat2);
    }

  public void setQuat(Quaternion quat)
    {
    quat.getMat3(m);
    }

  public void getQuat(Quaternion quat)
    {
    quat.setMat3(m);
    }

  public void setEulers(float hpr[])
    {
    setEulers(hpr[0], hpr[1], hpr[2]);
    }

  public void getEulers(float hpr[]) // self factored - tested ok
    {
    float cosh, sinh, cosp, sinp, cosr, sinr;

    sinp = -m[1][2];
    cosp = (float)Math.sqrt(1.0f - sinp*sinp);

    if ( (float)Math.abs(cosp) > 0.0001f ) 
        {
        cosh = m[2][2] / cosp;
        sinh = m[0][2] / cosp;
        cosr = m[2][1] / cosp;
        sinr = m[1][0] / cosp;
        } 
    else 
        {
        cosh =  1.0f;
        sinh =  0.0f;
        cosr =  m[0][0];
        sinr = -m[0][1];
        }

    hpr[0] = (float)Math.atan2(sinh, cosh);
    hpr[1] = (float)Math.atan2(sinp, cosp);
    hpr[2] = (float)Math.atan2(sinr, cosr);
    }

  public void setEulers(float h, float p, float r) // Vince p.26 - tested ok
    {
    float cosh, sinh, cosp, sinp, cosr, sinr;

    cosh = (float)Math.cos(h);
    sinh = (float)Math.sin(h);
    cosp = (float)Math.cos(p);
    sinp = (float)Math.sin(p);
    cosr = (float)Math.cos(r);
    sinr = (float)Math.sin(r);

    m[0][0] = + cosh * cosr + sinh * sinp * sinr;
    m[0][1] = - cosh * sinr + sinh * sinp * cosr;
    m[0][2] = + sinh * cosp;
    m[1][0] = + cosp * sinr;
    m[1][1] = + sinh * sinr + cosh * sinp * cosr;
    m[1][2] = - sinp;
    m[2][0] = - sinh * cosr + cosh * sinp * sinr;
    m[2][1] = + cosp * cosr;
    m[2][2] = + cosh * cosp;
    }

  public void getEulers(float h[], float p[], float r[])
    {
    float hpr[] = new float[3];
    getEulers(hpr);
    h[0] = hpr[0];
    p[0] = hpr[1];
    r[0] = hpr[2];
    }

  public void makeNull()
    {
    m[0][0] = 0f;
    m[0][1] = 0f;
    m[0][2] = 0f;
    m[1][0] = 0f;
    m[1][1] = 0f;
    m[1][2] = 0f;
    m[2][0] = 0f;
    m[2][1] = 0f;
    m[2][2] = 0f;
    }

  public void makeIdent()
    {
    m[0][0] = 1f;
    m[0][1] = 0f;
    m[0][2] = 0f;
    m[1][0] = 0f;
    m[1][1] = 1f;
    m[1][2] = 0f;
    m[2][0] = 0f;
    m[2][1] = 0f;
    m[2][2] = 1f;
    }

  public void xform(Vec3f vec) // math_utils - tested ok
    {
    float v[] = new float[3];

    vec.get(v);
    vec.set(0, v[0]*m[0][0] + v[1]*m[0][1] + v[2]*m[0][2]);
    vec.set(1, v[0]*m[1][0] + v[1]*m[1][1] + v[2]*m[1][2]);
    vec.set(2, v[0]*m[2][0] + v[1]*m[2][1] + v[2]*m[2][2]);
    }

  public void xform(float v[]) // math_utils - tested ok
    {
    float tmp_v[] = new float[3];

    tmp_v[0] = v[0]*m[0][0] + v[1]*m[0][1] + v[2]*m[0][2];
    tmp_v[1] = v[0]*m[1][0] + v[1]*m[1][1] + v[2]*m[1][2];
    tmp_v[2] = v[0]*m[2][0] + v[1]*m[2][1] + v[2]*m[2][2];
    v[0] = tmp_v[0];
    v[1] = tmp_v[1];
    v[2] = tmp_v[2];
    }

  }
  
  /* Copyright (c) 1997-2003, Naval Postgraduate School
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the Naval Postgraduate School nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
