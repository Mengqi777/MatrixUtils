package com.heu.cs.poet.matrix;

import java.util.Vector;

/**
 * 通用矩阵工具
 *
 * @author MengQi
 * @create 2017-12-02 12:53
 */
public class MatrixTool {
    public boolean isZero(double[] doubles) {
        boolean flag = true;
        for (double d : doubles) {
            if (d != 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }


    public boolean isZero(double[][] doubles) {
        boolean flag = true;
        for (double[] d : doubles) {
            if (!isZero(d)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

//
//    /**
//     * 两个矩阵加法
//     * @param m1 第一个矩阵
//     * @param m2 第二个矩阵
//     * @return 返回新的矩阵
//     * @throws Exception 长度不一致异常
//     */
//    public DoubleMatrix addition(DoubleMatrix m1, DoubleMatrix m2) throws Exception {
//        int rowCount=m1.getRowNum();
//        int columnCount=m1.getColumnNum();
//        if(rowCount!=m2.getRowNum()||columnCount!=m2.getColumnNum()) throw new Exception("长度不一致");
//        double[][] doubles=new double[rowCount][columnCount];
//        for (int i = 0; i < rowCount; i++) {
//            for (int j = 0; j < columnCount; j++) {
//                doubles[i][j]=m1.getMatrix()[i][j]+m2.getMatrix()[i][j];
//            }
//        }
//        return new DoubleMatrix(doubles);
//    }
//
//
//    /**
//     * 两个矩阵减法
//     * @param m1 第一个矩阵
//     * @param m2 第二个矩阵
//     * @return 返回新的矩阵
//     * @throws Exception 长度不一致异常
//     */
//    public DoubleMatrix subtraction(DoubleMatrix m1, DoubleMatrix m2) throws Exception {
//        int rowCount=m1.getRowNum();
//        int columnCount=m1.getColumnNum();
//        if(rowCount!=m2.getRowNum()||columnCount!=m2.getColumnNum()) throw new Exception("长度不一致");
//        double[][] doubles=new double[rowCount][columnCount];
//        for (int i = 0; i < rowCount; i++) {
//            for (int j = 0; j < columnCount; j++) {
//                doubles[i][j]=m1.getMatrix()[i][j]-m2.getMatrix()[i][j];
//            }
//        }
//        return new DoubleMatrix(doubles);
//    }
//
//
//    /**
//     * 两个矩阵内积
//     * @param m1 第一个矩阵
//     * @param m2 第二个矩阵
//     * @return 返回新的矩阵
//     * @throws Exception 长度不一致异常
//     */
//    public DoubleMatrix multiplication(DoubleMatrix m1, DoubleMatrix m2) throws Exception {
//        int rn1=m1.getRowNum();
//        int cn1=m1.getColumnNum();
//        int rn2=m2.getRowNum();
//        int cn2=m2.getColumnNum();
//        if(cn1!=rn2) throw new Exception("长度不一致");
//        double[][] db=new double[rn1][cn2];
//        for (int i=0;i<rn1;i++){
//            for (int j = 0; j < cn2; j++) {
//                db[i][j]=mutlipAdd(m1.getRow(i),m2.getColumn(j));
//            }
//        }
//        return new DoubleMatrix(db);
//    }
//
//
//    /**
//     * 两个数组内积
//     * @param a 第一个数组
//     * @param b 第二个数组
//     * @return 返回double数据
//     */
//    public double mutlipAdd(double[] a,double[] b){
//        double d=0;
//        for (int i = 0; i < a.length; i++) {
//            d+=a[i]*b[i];
//        }
//        return d;
//    }
//
//
//
//    public void test(){
//        Vector<String> stringVector=new Vector<>();
//    }
//
//
//    public static void main(String[] args) {
//     Double[][] doubles=new Double[1][1];
//     doubles[0][0]=1.0;
//

//    }

}
