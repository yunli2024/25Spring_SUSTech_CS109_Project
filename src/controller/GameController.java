package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        System.out.println("you restart the game! ");
        model.resetMatrixToFirst();
        view.clearAll();
        view.initialGame(model.getMatrix());

    }

    public void regret(){
        //todo 记录每一步操作到data 并且要实现悔棋的功能 悔棋参考restart 还要写按钮
    }

    public boolean doMove(int row, int col, Direction direction) {
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();
        if (model.getId(row, col) == 1) {
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    //更新矩阵的元素值
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;

                    //更新view的部分
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    return true;
                }
            }
        }

        else if(model.getId(row,col)==2){//是一个1*2的横着的格子
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)){//确保不会跨越边界
                if(direction.getRow()==0&&direction.getCol()==-1){//只会分类了，向左
                    if(model.getId(nextRow,nextCol)==0){
                        renewMatrixAndViewFor2(row, col, nextRow, nextCol);
                        return true;
                    }
                }
                else if(direction.getRow()==0&&direction.getCol()==1){//向右的情况，检查会不会越界
                    if(model.checkInWidthSize(nextCol+1)){
                        if(model.getId(nextRow,nextCol+1)==0){
                            renewMatrixAndViewFor2(row, col, nextRow, nextCol);
                            return true;
                        }
                    }

                }
                else if(direction.getCol()==0) {//竖着走的情况
                    //这里因为有个+1 会有数组越界的问题！
                    if (model.checkInWidthSize(nextCol + 1)) {
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow, nextCol + 1) == 0) {
                            renewMatrixAndViewFor2(row, col, nextRow, nextCol);
                            return true;
                        }
                    }
                }



            }

        }
        else if(model.getId(row,col)==3){//移动2*1的情况
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)){
                if(direction.getRow()==0){//横着走的情况
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow + 1, nextCol) == 0) {
                            renewMatrixAndViewFor3(row, col, nextRow, nextCol);
                            return true;
                        }

                }
                else if(direction.getCol()==0&&direction.getRow()==1){//向下走
                    if(model.checkInHeightSize(nextRow+1)){
                        if(model.getId(nextRow+1,nextCol)==0){
                            renewMatrixAndViewFor3(row,col,nextRow,nextCol);
                            return true;
                        }
                    }
                }
                else if(direction.getCol()==0&&direction.getRow()==-1){
                    if (model.getId(nextRow,nextCol)==0){
                        renewMatrixAndViewFor3(row,col,nextRow,nextCol);
                        return true;
                    }
                }
            }
        }
        else if(model.getId(row,col)==4){
            if(direction.getCol()==0&&direction.getRow()==-1){//向上
                //先检查是否越界
                if(model.checkInHeightSize(nextRow)){
                    //再检查是否为0
                    if (model.getMatrix()[nextRow][nextCol]==0&&model.getMatrix()[nextRow][nextCol+1]==0) {
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getCol()==0&&direction.getRow()==1){//向下
                if(model.checkInHeightSize(nextRow+1)){
                    if(model.getMatrix()[nextRow+1][nextCol]==0&&model.getMatrix()[nextRow+1][nextCol+1]==0){
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getRow()==0&&direction.getCol()==-1){//向左
                if(model.checkInWidthSize(nextCol)){
                    if(model.getMatrix()[nextRow][nextCol]==0&&model.getMatrix()[nextRow+1][nextCol]==0){
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getRow()==0&&direction.getCol()==1){ //向右
                if(model.checkInWidthSize(nextCol+1)){
                    if(model.getMatrix()[nextRow][nextCol+1]==0&&model.getMatrix()[nextRow+1][nextCol+1]==0){
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
        }
        return false;
    }

    public void renewMatrixAndViewFor2(int row, int col, int nextRow, int nextCol) {
        model.getMatrix()[row][col] = 0;
        model.getMatrix()[row][col + 1] = 0;
        model.getMatrix()[nextRow][nextCol] = 2;
        model.getMatrix()[nextRow][nextCol + 1] = 2;

        BoxComponent box = view.getSelectedBox();
        box.setRow(nextRow);
        box.setCol(nextCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();
    }

    public void renewMatrixAndViewFor3(int row,int col,int nextRow,int nextCol){
        model.getMatrix()[row][col]=0;
        model.getMatrix()[row+1][col]=0;
        model.getMatrix()[nextRow][nextCol]=3;
        model.getMatrix()[nextRow+1][nextCol]=3;

        BoxComponent box = view.getSelectedBox();
        box.setRow(nextRow);
        box.setCol(nextCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();
    }

    public void renewMatrixAndViewFor4(int row,int col,int nextRow,int nextCol){
        model.getMatrix()[row][col]=0;
        model.getMatrix()[row+1][col]=0;
        model.getMatrix()[row][col+1]=0;
        model.getMatrix()[row+1][col+1]=0;
        model.getMatrix()[nextRow][nextCol]=4;
        model.getMatrix()[nextRow+1][nextCol]=4;
        model.getMatrix()[nextRow][nextCol+1]=4;
        model.getMatrix()[nextRow+1][nextCol+1]=4;

        BoxComponent box = view.getSelectedBox();
        box.setRow(nextRow);
        box.setCol(nextCol);
        box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
        box.repaint();

    }


    //胜利条件的判断
    public boolean isWin(){
        if(model.getId(1,3)==4&&model.getId(1,4)==4&&model.getId(2,3)==4&&model.getId(2,4)==4){
            System.out.println("恭喜你赢了！胜利次数加1（我还没做那玩意）");
            view.showVictoryMessage();
            restartGame();
            return false;
            //todo 存档的时候可以存这个id的胜利次数，搞个rank的页面
            //todo 话说存档是某一局游戏，和用户的信息是不一样的吧？都要搞好文件IO
        }
        return true;
    }

    //todo: add other methods such as loadGame, saveGame...

}
