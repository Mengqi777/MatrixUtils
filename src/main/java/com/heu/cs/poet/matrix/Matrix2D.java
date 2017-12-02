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


    private double[][] matrix;

    public double[][] getMatrix() {
        return matrix;
    }


    public int getRowNum(){
        return this.matrix.length;
    }

    public int getColumnNum()
    {
        return this.matrix[0].length;
    }

    public void setMatrix(double[][] matrix) {
        if (this.matrix != null) this.matrix = null;
        this.matrix = matrix;
    }

    public Matrix2D(double[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * 构造零矩阵
     * @param rowNum 行数
     * @param columnNum 列数
     */
    public Matrix2D(int rowNum, int columnNum) {
        double[][] m = new double[rowNum][columnNum];
        this.setMatrix(m);
        this.toZero();
    }


    /**
     * 将矩阵置为为零矩阵
     */
    public void toZero() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }


    /**
     * 将矩阵置为对角矩阵
     */
    public void toDiagonal(){
        this.toZero();
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if(i==j)
                this.matrix[i][j] = 1;
            }
        }
    }


    /**
     * 初始化为零矩阵
     * @param rowNum 行数
     * @param columnNum 列数
     */
    public void init(int rowNum, int columnNum) {
        double[][] m = new double[rowNum][columnNum];
        this.setMatrix(m);
        this.toZero();
    }

    /**
     * 获取最大值
     * @return 最大的数
     */
    public double getMax() {
        double max = this.matrix[0][0];
        for (double[] doubles : this.matrix) {
            for (double d : doubles)
                max = Math.max(d, max);
        }
        return max;
    }

    /**
     * 获取最小值
     * @return 最小的数
     */
    public double getMin() {
        double min = this.matrix[0][0];
        for (double[] doubles : this.matrix) {
            for (double d : doubles)
                min = Math.max(d, min);
        }
        return min;
    }


    /**
     * 获取最长指
     * @return 长度最长的数
     */
    public double getLongest() {
        int longest = 0;
        int tagi = 0;
        int tagj = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if ((this.matrix[i][j] + "").length() >= longest) {
                    longest = (this.matrix[i][j] + "").length();
                    tagi = i;
                    tagj = j;
                }
            }
        }
        return this.matrix[tagi][tagj];
    }


    /**
     * 打印
     */
    public void print() {
        double longest = this.getLongest();

        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                System.out.printf("%-" + ((longest + "").length() + 2) + "s", this.matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 保存
     * @param filePath 路径
     */
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

    /**
     * 获取某行
     * @param rowIndex 索引
     * @return 数组
     */
    public double[] getRow(int rowIndex) {
        if (rowIndex < this.matrix.length) {
            return this.matrix[rowIndex];
        } else return null;
    }

    /**
     * 获取某列
     * @param columnIndex 索引
     * @return 数组
     */
    public double[] getColumn(int columnIndex) {
        int rowCount = this.matrix.length;
        double[] column = new double[rowCount];
        for (int i = 0; i < rowCount; i++) {
            column[i] = this.matrix[i][columnIndex];
        }
        return column;
    }


    /**
     * 转为com.google.gson.JsonArray;
     * @return  com.google.gson.JsonArray
     */
    public JsonArray toJsonArray() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this.matrix), JsonArray.class);
    }


    /**
     * 根据左上角索引坐标和右下角索引坐标获取子矩阵
     *
     * @param x1 左上角行下标
     * @param y1 左上角列下标
     * @param x2 右下角行下标
     * @param y2 右下角列下标
     * @return 新矩阵Matrix2D
     */
    public Matrix2D getSubMatrix(int x1, int y1, int x2, int y2) {
        int rowCount = x2 - x1;
        int columnCount = y2 - y1;
        int[] rows = new int[rowCount];
        int[] columns = new int[columnCount];
        for (int i = x1; i < x2; i++) {
            rows[i] = i;
        }
        for (int i = y1; i < y2; i++) {
            columns[i] = i;
        }
        double[][] subMatrix = new double[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                subMatrix[i][j] = this.matrix[rows[i]][columns[j]];
            }
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
     * @return Matrix2D
     */
    public Matrix2D getSubRowToMatrix(String indexRowFormat) throws IndexIllegalException {
        Integer[] indexIntegers = analysisComplexIndex(indexRowFormat, this.matrix.length);
        double[][] db = new double[indexIntegers.length][this.matrix[0].length];
        for (int i = 0; i < indexIntegers.length; i++) {
            db[i] = this.getRow(indexIntegers[i]);
        }
        return new Matrix2D(db);
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
     * 根据列索引获取子矩阵
     *
     * @param indexColumnFormat 正则匹配表达式
     * @return 矩阵Matrix2D
     */
    public Matrix2D getSubColumnToMatrix(String indexColumnFormat) throws IndexIllegalException {
        Integer[] indexIntegers = analysisComplexIndex(indexColumnFormat, this.matrix[0].length);
        double[][] db = new double[this.matrix.length][indexIntegers.length];
        Matrix2D matrix2D = new Matrix2D(db);
        for (int i = 0; i < indexIntegers.length; i++) {
            matrix2D.replaceColumn(i, this.getColumn(indexIntegers[i]));
        }
        return matrix2D;
    }


    /**
     * 替换某行数据
     * @param index 索引
     * @param data 行数据
     */
    public void replaceRow(int index, double[] data) {
        this.matrix[index] = data;
    }

    /**
     * 替换某列数据
     * @param index 索引
     * @param data 列数据
     */
    public void replaceColumn(int index, double[] data) {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if (j == index) this.matrix[i][j] = data[i];
            }
        }
    }


    /**
     * 矩阵转置
     *
     * @return 返回二维数组
     */
    public double[][] transpose() {
        double[][] result_arr = new double[this.matrix[0].length][this.matrix.length];
        /*******进行元素倒置******/
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                result_arr[j][i] = this.matrix[i][j]; //转置核心
            }
        }
        return result_arr;
    }


    /**
     * 插入行
     * @param index 索引
     * @param row 列数组数据
     * @throws Exception 长度不一致异常
     */
    public void insertRow(int index, double[] row) throws Exception {
        if (row.length != this.matrix[0].length) throw new Exception("长度不一致");
        double[][] re = new double[this.matrix.length + 1][this.matrix[0].length];
        for (int i = 0; i < re.length; i++) {
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

    /**
     * 插入列
     * @param index 索引
     * @param column 列数组数据
     * @throws Exception 长度不一致异常
     */
    public void insertColumn(int index, double[] column) throws Exception {
        if (column.length != this.matrix.length) throw new Exception("长度不一致");
        double[][] re = new double[this.matrix.length][this.matrix[0].length + 1];
        for (int i = 0; i < re.length; i++) {
            for (int j = 0; j < re[i].length; j++) {
                if (j < index) re[i][j] = this.matrix[i][j];
                else if (j == index) re[i][j] = column[i];
                else {
                    re[i][j] = this.matrix[i][j - 1];
                }
            }
        }
        this.setMatrix(re);
    }


    /**
     * 移除列
     * @param index 索引
     */
    public void removeColumn(int index) {
        double[][] re = new double[this.matrix.length][this.matrix[0].length - 1];
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                if (j == index) continue;
                if (j < index) re[i][j] = this.matrix[i][j];
                if (j > index) re[i][j - 1] = this.matrix[i][j];
            }
        }
       this.setMatrix(re);
    }


    /**
     * 移除行
     * @param index 索引
     */
    public void removeRow(int index) {
        double[][] re = new double[this.matrix.length - 1][this.matrix[0].length];
        int count = 0;
        for (int i = 0; i < this.matrix.length; i++) {
            if (i == index) continue;
            re[count++] = this.getRow(i);
        }
        this.setMatrix(re);
    }


}
