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

    public boolean doMove(int row, int col, Direction direction) {
        if (model.getId(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
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
        //todo 默认选中的是左上角的那个格子吗??
        if(model.getId(row,col)==2){//是一个1*2的横着的格子
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)){//确保不会跨越边界
                if(direction.getRow()==0){//横着走的情况
                    if(model.getId(nextRow,nextCol)==0){
                        System.out.printf("%d %d\n",nextRow,nextCol);
                        model.getMatrix()[row][col]=0;
                        model.getMatrix()[nextRow][nextCol]=2;
                        model.getMatrix()[nextRow][nextCol+direction.getCol()]=2;
                    }
                    else if(direction.getCol()==0){//竖着走的情况
                        if(model.getId(nextRow,nextCol)==0&&model.getId(nextRow,nextCol+1)==0){
                            model.getMatrix()[row][col]=0;
                            model.getMatrix()[row][col+1]=0;
                            model.getMatrix()[nextRow][nextCol]=2;
                            model.getMatrix()[nextRow][nextCol+1]=2;
                        }
                    }
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    return true;//?
                }

            }

        }
        return false;
    }

    //todo: add other methods such as loadGame, saveGame...

}
