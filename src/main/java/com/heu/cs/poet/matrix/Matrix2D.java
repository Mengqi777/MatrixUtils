package com.heu.cs.poet.matrix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.heu.cs.poet.exception.IndexIllegalException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java 矩阵工具
 *
 * @author MengQi
 * @create 2017-10-11 13:01
 */
public class Matrix2D {


    private int rowNum;
    private int columnNum;

    private double[][] matrix;

    public int getRowNum() {
        return rowNum;
    }


    public int getColumnNum() {
        return columnNum;
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        if (matrix == null || matrix.length == 0) {
            this.rowNum = 0;
            this.columnNum = 0;
        } else {
            this.rowNum = matrix.length;
            this.columnNum = matrix[0].length;
        }
    }

    public Matrix2D(double[][] matrix) {
        this.matrix = matrix;
        if (matrix == null || matrix.length == 0) {
            this.rowNum = 0;
            this.columnNum = 0;
        } else {
            this.rowNum = matrix.length;
            this.columnNum = matrix[0].length;
        }
    }


    public Matrix2D(int rowNum, int columnNum) {
        double[][] m = new double[rowNum][columnNum];
        this.setMatrix(m);
        this.toZero();
    }


    public void toZero() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }


    public void init(int rowNum, int columnNum) {
        double[][] m = new double[rowNum][columnNum];
        this.setMatrix(m);
        this.toZero();
    }


    public void print() {
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void save(String filePath) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(new File(filePath));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.rowNum; i++) {
                for (int j = 0; j < this.columnNum; j++) {
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

    public double[] getRow(int rowIndex) {
        if (rowIndex < this.rowNum) {
            return this.matrix[rowIndex];
        } else return null;
    }

    public double[] getColumn(int columnIndex) {
        int rowCount = this.matrix.length;
        double[] column = new double[rowCount];
        for (int i = 0; i < rowCount; i++) {
            column[i] = this.matrix[i][columnIndex];
        }
        return column;
    }


    public boolean isZero1D(double[] doubles) {
        boolean flag = true;
        for (double d : doubles) {
            if (d != 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public boolean isZero2D(double[][] doubles) {
        boolean flag = true;
        for (double[] d : doubles) {
            if (!isZero1D(d)) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public JsonArray toJsonArray() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this.matrix), JsonArray.class);
    }


    /**
     * 根据左上角索引坐标和右下角索引坐标获取子矩阵
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public Matrix2D getSubMatrix(int x1, int y1, int x2, int y2) {
        int rowCount = x2 - x1;
        double[][] subMatrix = new double[rowCount][y2 - y1];
        for (int i = 0; i < rowCount; i++) {
            subMatrix[i] = this.getRow(x1++);
        }
        return new Matrix2D(subMatrix);
    }


    /**
     * 根据行索引获取子矩阵
     *
     * @param indexRowFormat 索引格式：不同部分用 , 分割
     *                       - 表示连续，3-7表示匹配 3，4，5，6行
     *                       * 匹配数字之前或之后的所有，*-3表示匹配 0，1，2行，3-*表示匹配 3，4，5，6.....直到最后一行
     *                       *-3,4-6,7,8-*表示匹配 0，1，2，4，5，7，8，9，10，11，12......到最后一行
     * @return
     */
    public Matrix2D getSubRowToMatrix(String indexRowFormat) throws IndexIllegalException {
        Integer[] indexIntegers = analysisComplexIndex(indexRowFormat);
        double[][] db = new double[indexIntegers.length][this.columnNum];
        for (int i = 0; i < indexIntegers.length; i++) {
            db[i] = this.getRow(indexIntegers[i]);
        }
        return new Matrix2D(db);
    }


    /**
     * 分析复杂的子矩阵索引规则，返回所有行下标
     *
     * @param complexIndex
     * @return
     * @throws IndexIllegalException
     */
    private Integer[] analysisComplexIndex(String complexIndex) throws IndexIllegalException {
        if (!isLegal(complexIndex)) throw new IndexIllegalException("索引字符串不符合规则");
        String[] complexIndexs = complexIndex.split(",");
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
                    for (int i = Integer.parseInt(subs[0]); i < this.rowNum; i++) {
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
     * @param complex
     * @return
     */
    private boolean isLegal(String complex) {
        String s = "(\\d+\\-\\d+|\\d+\\-\\*|\\*\\-\\d+|\\d+)+";
        Pattern pattern = Pattern.compile(s);
        Matcher matcher = pattern.matcher(complex);
        if (matcher.find()) return true;
        else return false;
    }

    /**
     * 根据列索引获取子矩阵
     *
     * @param indexColumnFormat
     * @return
     */
    public Matrix2D getSubColumnToMatrix(String indexColumnFormat) throws IndexIllegalException {
        Integer[] indexIntegers = analysisComplexIndex(indexColumnFormat);
        double[][] db = new double[this.rowNum][indexIntegers.length];
        Matrix2D matrix2D = new Matrix2D(db);
        for (int i = 0; i < indexIntegers.length; i++) {
            matrix2D.replaceColumn(i, this.getColumn(indexIntegers[i]));
        }
        return matrix2D;
    }


    public void replaceRow(int index, double[] data) {

        for (int i = 0; i < this.matrix[index].length; i++) {
            this.matrix[index][i] = data[i];
        }
    }

    public void replaceColumn(int index, double[] data) {
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {
                if (j == index) this.matrix[i][j] = data[i];
            }
        }
    }

    public double[][] removeRow(int index) {
        double[][] re = new double[this.rowNum - 1][this.columnNum];
        int count = 0;
        for (int i = 0; i < this.rowNum; i++) {
            if (i == index) continue;
            re[count++] = this.getRow(i);
        }
        return re;
    }

    public double[][] removeColumn(int index) {
        double[][] re = new double[this.rowNum][this.columnNum - 1];
        int count = 0;
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {

                if (j == index) continue;
                if (j < index) re[i][j] = this.matrix[i][j];
                if (j > index) re[i][j - 1] = this.matrix[i][j];
            }
        }
        return re;
    }


    /**
     * 矩阵转置
     *
     * @return
     */
    public double[][] trans() {
        double[][] result_arr = new double[this.columnNum][this.rowNum];
        /*******进行元素倒置******/
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                result_arr[j][i] = this.matrix[i][j]; //转置核心
            }
        }
        return result_arr;
    }


    public double[][] addOneRow(double[] row) {
        double[][] re = new double[this.rowNum + 1][this.columnNum];
        for (int i = 0; i < this.rowNum + 1; i++) {
            if (i == this.rowNum) {
                re[i] = row;
            } else if (i < this.rowNum) {
                re[i] = this.matrix[i];
            }
        }
        return re;
    }

    public double[][] addOneColumn(double[] column) {
        double[][] re = new double[this.rowNum][this.columnNum + 1];
        Matrix2D trancMatrix2D = new Matrix2D(this.trans());
        Matrix2D ma = new Matrix2D(trancMatrix2D.addOneRow(column));
        return ma.trans();
    }


    public void insertOneRowToSelf(int index, double[] row) {
        double[][] re = new double[this.rowNum + 1][this.columnNum];
        for (int i = 0; i < this.rowNum + 1; i++) {
            if (i < index) {
                re[i] = this.matrix[i];
            } else if (i == index) {
                re[i] = row;
            } else {
                re[i] = this.matrix[i - 1];
            }
        }
        this.setMatrix(re);
    }

    public void insertOneColumnToSelf(int index, double[] column) {
        double[][] re = new double[this.rowNum][this.columnNum + 1];
        Matrix2D trancMatrix2D = new Matrix2D(this.trans());
        trancMatrix2D.insertOneRowToSelf(index, column);
        trancMatrix2D.trans();
        this.setMatrix(trancMatrix2D.getMatrix());
    }


    public void addOneRowToSelf(double[] row) {
        double[][] re = new double[this.rowNum + 1][this.columnNum];
        for (int i = 0; i < this.rowNum + 1; i++) {
            if (i == this.rowNum) {
                re[i] = row;
            } else if (i < this.rowNum) {
                re[i] = this.matrix[i];
            }
        }
        this.setMatrix(re);
    }

    public void addOneColumnToSelf(double[] column) {
        double[][] re = new double[this.rowNum][this.columnNum + 1];
        Matrix2D trancMatrix2D = new Matrix2D(this.trans());
        Matrix2D ma = new Matrix2D(trancMatrix2D.addOneRow(column));
        this.setMatrix(ma.trans());
    }


    public void removeSelfColumn(int index) {
        double[][] re = new double[this.rowNum][this.columnNum - 1];
        int count = 0;
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {
                if (j == index) continue;
                if (j < index) re[i][j] = this.matrix[i][j];
                if (j > index) re[i][j - 1] = this.matrix[i][j];
            }
        }

        Matrix2D matrix2DTemp = new Matrix2D(re);
        this.matrix = null;
        this.matrix = matrix2DTemp.matrix;
        this.columnNum = matrix2DTemp.columnNum;
        this.rowNum = matrix2DTemp.rowNum;
    }


    public void removeSelfRow(int index) {
        double[][] re = new double[this.rowNum - 1][this.columnNum];
        int count = 0;
        for (int i = 0; i < this.rowNum; i++) {
            if (i == index) continue;
            re[count++] = this.getRow(i);
        }
        Matrix2D matrix2DTemp = new Matrix2D(re);
        this.matrix = null;
        this.matrix = matrix2DTemp.matrix;
        this.columnNum = matrix2DTemp.columnNum;
        this.rowNum = matrix2DTemp.rowNum;
    }


}
