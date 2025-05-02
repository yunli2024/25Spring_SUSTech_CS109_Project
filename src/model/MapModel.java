package model;

import javax.sql.rowset.FilteredRowSet;

/**
 * This class is to record the map of one game. For example:
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

    public void getFirstMatrix(){
        FirstMatrix=copyMatrix(matrix);
    }

    public void resetMatrixToFirst(){
        this.matrix=copyMatrix(FirstMatrix);
    }


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
