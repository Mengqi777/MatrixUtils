import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 矩阵的基本运算
 *
 * @author MengQi
 * @create 2017-10-11 13:01
 */
public class Matrix {


    private int  rowNum;
    private int columnNum;


    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
        this.rowNum=matrix.length;
        this.columnNum=matrix[0].length;
    }

    private String[][] matrix;
    public Matrix(String[][] matrix){
        this.matrix=matrix;
        this.rowNum=matrix.length;
        if(matrix.length==0)this.columnNum=0;
        else this.columnNum=matrix[0].length;
    }
    public Matrix(){
       super();
    }


public void print(){
        for(int i=0;i<this.rowNum;i++){
            for(int j=0;j<this.columnNum;j++){
                System.out.print(this.matrix[i][j]+" ");
            }
            System.out.println();
        }
}


    public void save(String filePath){
        FileWriter writer=null;

        try {
            writer=new FileWriter(new File(filePath));
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<this.rowNum;i++){
                for(int j=0;j<this.columnNum;j++){
                    sb.append(this.matrix[i][j]).append(",");
                }
                sb.append("\r\n");
            }
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null) try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String[] getRow(int rowIndex){
        int rowCount=this.matrix.length;
        if(rowIndex<rowCount){
            return this.matrix[rowIndex];
        }else return null;
    }

    public String[] getColumn(int columnIndex){
        int rowCount=this.matrix.length;
        String[] column=new String [rowCount];
        for (int i = 0; i < rowCount; i++) {
            column[i]=this.matrix[i][columnIndex];
        }
        return column;
    }


    public boolean isZero(List<String> ss){
        boolean flag=true;
        for(String s:ss){
            if(Double.parseDouble(s)!=0){
                flag=false;
                break;
            }
        }
        return flag;
    }

    public JsonArray toJsonArray(){
        Gson gson=new Gson();
        return gson.fromJson(gson.toJson(this.matrix),JsonArray.class);
    }


    public String[][] getSubMatrix(int x1,int y1,int x2,int y2){
        int rowCount=x2-x1;
        String[][] subMatrix=new String [rowCount][y2-y1];
        for(int i=0;i<rowCount;i++){
            subMatrix[i]=this.getRow(x1++);
        }
        return subMatrix;
    }


    public String[][] getTwoRowToMatrix(int row1,int row2){
        String[] r1 = this.getRow(row1);
        String[] r2 = this.getRow(row2);
        String[][] re=new String [2][this.getColumnNum()];
        re[0]=r1;
        re[1]=r2;
        return re;
    }


    public String[][] getTwoColToMatrix(int column1,int column2){

        String[] c1 = this.getColumn(column1);
        String[] c2 = this.getColumn(column2);
        String[][] re=new String [2][this.getColumnNum()];

        re[0]=c1;
        re[1]=c2;
        return re;
    }


    public void replaceRow(int index,String[] data){

        for (int i = 0; i < this.matrix[index].length; i++) {
            this.matrix[index][i]=data[i];
        }
    }

    public void replaceColumn(int index,String[] data){
        for(int i=0;i<this.rowNum;i++){
            for(int j=0;j<this.columnNum;j++){
                if(j==index) this.matrix[i][j]=data[i];
            }
        }
    }

    public String [][] removeRow(int index){
        String[][] re=new String [this.rowNum-1][this.columnNum];
        int count=0;
        for(int i=0;i<this.rowNum;i++){
            if(i==index) continue;
            re[count++]=this.getRow(i);
        }
        return re;
    }
    public String [][] removeColumn(int index){
        String[][] re=new String [this.rowNum][this.columnNum-1];
        int count=0;
        for(int i=0;i<this.rowNum;i++){
            for(int j=0;j<this.columnNum;j++){

                if(j==index) continue;
                if(j<index) re[i][j]=this.matrix[i][j];
                if(j>index) re[i][j-1]=this.matrix[i][j];
            }
        }
        return re;
    }


    /**
     * 矩阵转置
     * @return
     */
    public String[][] trans(){
        String[][] result_arr=new String [this.columnNum][this.rowNum];
        /*******进行元素倒置******/
        for(int i=0 ; i<this.rowNum;i++){
            for(int j=0; j<this.matrix[i].length;j++){
                result_arr[j][i]=this.matrix[i][j]; //转置核心
            }
        }
        return result_arr;
    }



    public String[][] addOneRow(String[] row){
        String[][] re=new String [this.rowNum+1][this.columnNum];
        for(int i=0;i<this.rowNum+1;i++){
            if(i==this.rowNum){
                re[i]=row;
            }else if(i<this.rowNum){
                re[i]=this.matrix[i];
            }
        }
        return re;
    }

    public String[][] addOneColumn(String[] column){
        String[][] re=new String [this.rowNum][this.columnNum+1];
        Matrix trancMatrix = new Matrix(this.trans());
        Matrix ma = new Matrix(trancMatrix.addOneRow(column));
        return ma.trans();
    }


    public void insertOneRowToSelf(int index,String[] row){
        String[][] re=new String [this.rowNum+1][this.columnNum];
        for(int i=0;i<this.rowNum+1;i++){
            if(i<index){
                re[i]=this.matrix[i];
            }else if(i==index){
                re[i]=row;
            }else {
                re[i]=this.matrix[i-1];
            }
        }
        this.setMatrix(re);
    }

    public void insertOneColumnToSelf(int index,String[] column){
        String[][] re=new String [this.rowNum][this.columnNum+1];
        Matrix trancMatrix = new Matrix(this.trans());
        trancMatrix.insertOneRowToSelf(index,column);
        trancMatrix.trans();
        this.setMatrix(trancMatrix.getMatrix());
    }



    public void addOneRowToSelf(String[] row){
        String[][] re=new String [this.rowNum+1][this.columnNum];
        for(int i=0;i<this.rowNum+1;i++){
            if(i==this.rowNum){
                re[i]=row;
            }else if(i<this.rowNum){
                re[i]=this.matrix[i];
            }
        }
        this.setMatrix(re);
    }

    public void addOneColumnToSelf(String[] column){
        String[][] re=new String [this.rowNum][this.columnNum+1];
        Matrix trancMatrix = new Matrix(this.trans());
        Matrix ma = new Matrix(trancMatrix.addOneRow(column));
        this.setMatrix(ma.trans());
    }





    public void removeSelfColumn(int index){
        String[][] re=new String [this.rowNum][this.columnNum-1];
        int count=0;
        for(int i=0;i<this.rowNum;i++){
            for(int j=0;j<this.columnNum;j++){
                if(j==index) continue;
                if(j<index) re[i][j]=this.matrix[i][j];
                if(j>index) re[i][j-1]=this.matrix[i][j];
            }
        }

        Matrix matrixTemp=new Matrix(re);
        this.matrix=null;
        this.matrix=matrixTemp.matrix;
        this.columnNum=matrixTemp.columnNum;
        this.rowNum=matrixTemp.rowNum;
    }


    public void removeSelfRow(int index){
        String[][] re=new String [this.rowNum-1][this.columnNum];
        int count=0;
        for(int i=0;i<this.rowNum;i++){
            if(i==index) continue;
            re[count++]=this.getRow(i);
        }
        Matrix matrixTemp=new Matrix(re);
        this.matrix=null;
        this.matrix=matrixTemp.matrix;
        this.columnNum=matrixTemp.columnNum;
        this.rowNum=matrixTemp.rowNum;
    }






    /**
     * 将list数据转为二维数组
     *
     * @param dataList
     * @return
     */
    public String[][] transDataToMatrix(List<String> dataList) {
        List<List<String>> dataMatrix = new ArrayList<>();
        for (String dataline : dataList) {
            dataline=dataline.replaceAll("\t"," ");
            String[] line = dataline.split(" ");
            List<String> list = new ArrayList<String>();
            for (String s : line) {
                if (!s.equals("")) {
                    list.add(s);
                }
            }
            dataMatrix.add(list);
        }

        return listListToMatrix(dataMatrix);
    }



    public String[][] listListToMatrix(List<List<String>> lists){

        int size = lists.size();
        int len = lists.get(0).size();
        String[][] matrix = new String[size][len];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = lists.get(i).get(j);
            }
        }

        return matrix;

    }

}
