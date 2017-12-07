package com.heu.cs.poet.matrix;

/**
 * AbstractMatrix
 *
 * @author MengQi
 * @create 2017-12-05 13:30
 */
public abstract class AbstractMatrix<E> implements Matrix {

    abstract public E[] getRow(int index);

    abstract public E[] getColumn(int index);

    abstract public E get(int m, int n);

    abstract public void set(int m, int n, E e);

    abstract public void replaceRow(int index, E[] data);

    abstract public void replaceColumn(int index, E[] data);


    protected void rangeCheck(int m, int n) {
        rowCheck(m);
        columnCheck(n);
    }


    protected void rowCheck(int m) {
        if (m >= getRowSize() || m < 0) {

            throw new IndexOutOfBoundsException("out of row length:" + m);
        }
    }


    protected void columnCheck(int n) {
        if (n >= getColumnSize() || n < 0) {

            throw new IndexOutOfBoundsException("out of column length:" + n);

        }
    }


}
