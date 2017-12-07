package com.heu.cs.poet.matrix;

import com.heu.cs.poet.exception.IndexIllegalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用矩阵
 *
 * @author MengQi
 * @create 2017-12-07 09:46
 */
@SuppressWarnings("unchecked")
public class GenericMatrix<E> extends AbstractMatrix implements Matrix {


    protected Object[][] matrix;

    protected GenericMatrix() {

    }

    public GenericMatrix(int m, int n) {
        this.matrix = new Object[m][n];
    }


    public GenericMatrix(E[][] objectsMatrix) {
        this.matrix = objectsMatrix;
    }

    public Object[][] getMatrix() {
        return matrix;
    }

    public int getRowSize() {
        return matrix.length;
    }


    public int getColumnSize() {
        return matrix[0].length;
    }


    public void toArray(E[][] destArray) throws IllegalAccessException, InstantiationException {
        rangeCheck(destArray.length - 1, destArray[0].length - 1);
        for (int i = 0; i < destArray.length; i++) {
            System.arraycopy(matrix[i], 0, destArray[i], 0, getColumnSize() - 1);
        }
    }

    public GenericMatrix getSubMatrix(int x1, int y1, int x2, int y2) {
        rangeCheck(x1, y1);
        rangeCheck(x2, y2);
        int rowCount = x2 - x1;
        int columnCount = y2 - y1;
        Object[][] subMatrix = new Object[rowCount][columnCount];
        for (int i = x1; i < x2; i++) {
            System.arraycopy(matrix[x1], y1, subMatrix[i - x1], 0, columnCount);
        }
        return new GenericMatrix(subMatrix);
    }


    public GenericMatrix getSubMatrix(String rowFormat, String columnFormat) throws IndexIllegalException {
        Integer[] indexRowIntegers = analysisComplexIndex(rowFormat, getRowSize());
        Integer[] indexColumnIntegers = analysisComplexIndex(columnFormat, getColumnSize());
        int m = indexRowIntegers.length;
        int n = indexColumnIntegers.length;
        Object[][] objects = new Object[m][n];
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                objects[i][j] = matrix[indexRowIntegers[i]][indexColumnIntegers[j]];
            }
        }
        return new GenericMatrix(objects);
    }


    public GenericMatrix getSubRowMatrix(String rowFormat) throws IndexIllegalException {
        Integer[] indexIntegers = analysisComplexIndex(rowFormat, getRowSize());
        Object[][] objects = new Object[indexIntegers.length][getColumnSize()];
        for (int i = 0; i < indexIntegers.length; i++) {
            System.arraycopy(matrix[indexIntegers[i]], 0, objects[i], 0, getColumnSize());
        }
        return new GenericMatrix(objects);
    }


    public GenericMatrix getSubColumnMatrix(String columnFormat) throws IndexIllegalException {

        Integer[] indexIntegers = analysisComplexIndex(columnFormat, getColumnSize());
        Object[][] objects = new Object[getRowSize()][indexIntegers.length];
        GenericMatrix<Object> matrix = new GenericMatrix<>(objects);
        for (int i = 0; i < indexIntegers.length; i++) {
            matrix.replaceColumn(i, this.getColumn(indexIntegers[i]));
        }
        return matrix;
    }


    public void save(String filePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(filePath));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.matrix.length; i++) {
                for (int j = 0; j < this.matrix[i].length; j++) {
                    sb.append(this.matrix[i][j]).append(",");
                }
                sb.append("\r\n");
            }
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public E[] getRow(int index) {
        rowCheck(index);
        return (E[]) matrix[0];
    }


    public E[] getColumn(int index) {
        columnCheck(index);
        Object[] objects = new Object[getRowSize()];
        for (int i = 0; i < getRowSize(); i++) {
            for (int j = 0; j < getColumnSize(); j++) {
                if (j == index) objects[i] = matrix[i][j];
            }
        }
        return (E[]) objects;
    }


    public E get(int m, int n) {
        rangeCheck(m, n);
        return (E) matrix[m][n];
    }


    public void set(int m, int n, Object o) {
        rangeCheck(m, n);
        matrix[m][n] = o;
    }


    /**
     * 替换某行数据
     *
     * @param index 索引
     * @param data  行数据
     */
    public void replaceRow(int index, Object[] data) {
        rowCheck(data.length);
        this.matrix[index] = data;
    }

    /**
     * 替换某列数据
     *
     * @param index 索引
     * @param data  列数据
     */
    public void replaceColumn(int index, Object[] data) {
        columnCheck(data.length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if (j == index) this.matrix[i][j] = data[i];
            }
        }
    }


    /**
     * 矩阵转置
     */
    public void transpose() {
        Object[][] result_arr = new Object[getColumnSize()][getRowSize()];
        //进行元素倒置
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                //转置核心
                result_arr[j][i] = matrix[i][j];
            }
        }
        matrix = null;
        matrix = result_arr;
    }


    /**
     * 分析复杂的子矩阵索引规则，返回所有行下标
     *
     * @param complexIndex 复制字符串
     * @return 整型下标数组
     * @throws IndexIllegalException 索引字符串不符合规则异常
     */
    private Integer[] analysisComplexIndex(String complexIndex, int num) throws IndexIllegalException {

        String[] complexIndexs = complexIndex.split(",");
        for (int i = 0; i < complexIndexs.length; i++) {
            if (!isLegal(complexIndexs[i])) throw new IndexIllegalException("索引字符串不符合规则");
        }
        List<Integer> list = new ArrayList<>();
        for (String s : complexIndexs) {
            if (s.length() == 1) list.add(Integer.parseInt(s));
            else {
                String[] subs = s.split("-");
                if (subs[0].equals("*")) {
                    for (int i = 0; i < Integer.parseInt(subs[1]); i++) {
                        list.add(i);
                    }
                } else if (subs[1].equals("*")) {
                    for (int i = Integer.parseInt(subs[0]); i < num; i++) {
                        list.add(i);
                    }
                } else {
                    for (int i = Integer.parseInt(subs[0]); i < Integer.parseInt(subs[1]); i++) {
                        list.add(i);
                    }
                }
            }
        }
        Integer[] integers = new Integer[list.size()];
        list.toArray(integers);
        return integers;
    }


    /**
     * 判断输入的复制的子矩阵匹配规则是否合法
     *
     * @param complex 复制字符串
     * @return 正则表达式是否匹配
     */
    private boolean isLegal(String complex) {
        String s = "^(\\d+-\\d+|\\d+-\\*|\\*-\\d+|\\d+)$";
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(complex);
        if (matcher.find()) return true;
        else return false;
    }

    /**
     * 打印
     */
    public void print(int length) {
        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%-" + length + "s ", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    private void setMatrix(Object[][] objects) {
        if (matrix != null) matrix = null;
        matrix = objects;
    }


    /**
     * 插入行
     *
     * @param index 索引
     * @param row   列数组数据
     * @throws Exception 长度不一致异常
     */
    public void insertRow(int index, E[] row) throws Exception {
        if (row.length != getColumnSize()) throw new Exception("长度不一致");
        Object[][] re = new Object[getRowSize() + 1][getColumnSize()];
        for (int i = 0; i < re.length; i++) {
            if (i < index) {
                re[i] = this.matrix[i];
            } else if (i == index) {
                re[i] = row;
            } else {
                re[i] = this.matrix[i - 1];
            }
        }
        setMatrix(re);
    }

    /**
     * 插入列
     *
     * @param index  索引
     * @param column 列数组数据
     * @throws Exception 长度不一致异常
     */
    public void insertColumn(int index, E[] column) throws Exception {
        if (column.length != getRowSize()) throw new Exception("长度不一致");
        Object[][] re = new Object[getRowSize()][getColumnSize() + 1];
        for (int i = 0; i < re.length; i++) {
            for (int j = 0; j < re[i].length; j++) {
                if (j < index) re[i][j] = this.matrix[i][j];
                else if (j == index) re[i][j] = column[i];
                else {
                    re[i][j] = this.matrix[i][j - 1];
                }
            }
        }
        setMatrix(re);
    }


    /**
     * 移除列
     *
     * @param index 索引
     */
    public void removeColumn(int index) {
        Object[][] re = new Object[getRowSize()][getColumnSize() - 1];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if (j == index) continue;
                if (j < index) re[i][j] = this.matrix[i][j];
                if (j > index) re[i][j - 1] = this.matrix[i][j];
            }
        }
        setMatrix(re);
    }


    /**
     * 移除行
     *
     * @param index 索引
     */
    public void removeRow(int index) {
        Object[][] re = new Object[getRowSize() - 1][getColumnSize()];
        int count = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            if (i == index) continue;
            re[count++] = this.getRow(i);
        }
        setMatrix(re);
    }


}
