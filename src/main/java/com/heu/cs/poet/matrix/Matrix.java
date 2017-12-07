package com.heu.cs.poet.matrix;

import com.heu.cs.poet.exception.IndexIllegalException;

/**
 * Matrix Interface
 *
 * @author MengQi
 * @create 2017-12-05 11:09
 */
public interface Matrix {


    int getRowSize();

    int getColumnSize();

    Matrix getSubMatrix(int x1, int y1, int x2, int y2);

    Matrix getSubMatrix(String rowFormat, String columnFormat) throws IndexIllegalException;

    Matrix getSubRowMatrix(String rowFormat) throws IndexIllegalException;

    Matrix getSubColumnMatrix(String columnFormat) throws IndexIllegalException;

    void transpose();


}
