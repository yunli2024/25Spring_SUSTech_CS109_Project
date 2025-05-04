package model;

import javax.sql.rowset.FilteredRowSet;

/**
 * 这个model类是当前矩阵的状态，数字代表滑块而且不会修改（底层）
 */
public class MapModel {
    int[][] matrix;
    int[][] FirstMatrix;

    public int [][] copyMatrix(int [][] matrix){
        int [][] temp=new int[matrix.length][matrix[0].length];
        for(int i=0;i< matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                temp[i][j]=matrix[i][j];
            }
        }
        return temp;
    }
    //记录初始化的矩阵情况。
    public void getFirstMatrix(){
        FirstMatrix=copyMatrix(matrix);
    }
    //重置的方法
    public void resetMatrixToFirst(){
        this.matrix=copyMatrix(FirstMatrix);
    }

    //getter和setter 注意可以重新设置矩阵和获得当前矩阵状态。
    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        //在构造函数里面，就拷贝一份初始状态！
        getFirstMatrix();
    }
    public int getWidth() {
        return this.matrix[0].length;
    }
    public int getHeight() {
        return this.matrix.length;
    }
    public int getId(int row, int col) {
        return matrix[row][col];
    }
    public int[][] getMatrix() {
        return matrix;
    }
    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }
    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
