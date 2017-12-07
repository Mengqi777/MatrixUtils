package com.heu.cs.poet.matrix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.heu.cs.poet.exception.IndexIllegalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Random;

/**
 * double matrix
 *
 * @author MengQi
 * @create 2017-12-05 14:04
 */
public class DoubleMatrix extends GenericMatrix implements Matrix {
    public DoubleMatrix() {
        super();
    }

    public DoubleMatrix(int m, int n) {
        super(m, n);
    }

    public void random(int m, int n, int start, int end) {
        matrix = null;
        matrix = new Object[m][n];
        Random random = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextDouble() * end + start;
            }
        }
    }

    public boolean isZero() {
        int m = getRowSize();
        int n = getColumnSize();
        boolean flag = true;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("ceshi" + i);
                if (!equal(matrix[i][j], 0)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }


    public void zero() {
        int m = getRowSize();
        int n = getColumnSize();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = 0;
            }
        }
    }


    private boolean equal(Object o, int n) {

        return (n + "").equals(o + "");

    }


    /**
     * 两个矩阵加法
     *
     * @param m 第2个矩阵
     * @return 返回新的矩阵
     * @throws Exception 长度不一致异常
     */
    public void plus(DoubleMatrix m) throws Exception {
        int rowCount = getRowSize();
        int columnCount = getColumnSize();
        if (rowCount != m.getRowSize() || columnCount != m.getColumnSize()) throw new Exception("长度不一致");
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, (Double) get(i, j) + (Double) m.get(i, j));
            }
        }
    }


    /**
     * 两个矩阵减法
     *
     * @param m 第2个矩阵
     * @return 返回新的矩阵
     * @throws Exception 长度不一致异常
     */
    public void subtracte(DoubleMatrix m) throws Exception {
        int rowCount = getRowSize();
        int columnCount = getColumnSize();
        if (rowCount != m.getRowSize() || columnCount != m.getColumnSize()) throw new Exception("长度不一致");
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                set(i, j, (Double) get(i, j) - (Double) m.get(i, j));
            }
        }
    }


    /**
     * 两个矩阵内积
     *
     * @param m 第2个矩阵
     * @return 返回新的矩阵
     * @throws Exception 长度不一致异常
     */
    public DoubleMatrix multiplication(DoubleMatrix m) throws Exception {
        int rn1 = getRowSize();
        int cn1 = getColumnSize();
        int rn2 = m.getRowSize();
        int cn2 = m.getColumnSize();
        if (cn1 != rn2) throw new Exception("长度不一致");
        DoubleMatrix doubleMatrix = new DoubleMatrix(rn1, cn2);
        for (int i = 0; i < rn1; i++) {
            for (int j = 0; j < cn2; j++) {
                doubleMatrix.set(i, j, mutlipAdd((Double[]) getRow(i), (Double[]) m.getColumn(j)));
            }
        }
        return doubleMatrix;
    }


    /**
     * 两个数组内积
     *
     * @param a 第一个数组
     * @param b 第二个数组
     * @return 返回double数据
     */
    public double mutlipAdd(Double[] a, Double[] b) {
        double d = 0;
        for (int i = 0; i < a.length; i++) {
            d += a[i] * b[i];
        }
        return d;
    }
}
