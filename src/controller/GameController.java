package controller;

import model.Direction;
import model.MapModel;
import user.User;
import view.game.BoxComponent;
import view.game.GamePanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
/**
controller相关用于：连接模型与视图。
 可以在这里添加一些功能的实现逻辑。Frame的很多按钮的逻辑都是在这里实现的。
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private User user;
    //用History类型的栈，来保存之前的状态，进行悔棋。
    private Deque<History> history = new ArrayDeque<>();
    //构造函数，把当前所对应的视图/模型/用户作为参数传递进来，对他们控制。
    public GameController(GamePanel view, MapModel model,User user) {
        this.view = view;
        this.model = model;
        this.user=user;
        view.setController(this);//把当前控制器也传递给view
    }
    //重启的逻辑
    public void restartGame() {
        System.out.println("you restart the game! ");
        model.resetMatrixToFirst();//model层，重新弄回到初始状态
        view.clearAll(); //清空view层的滑块
        view.initialGame(model.getMatrix(),0); //将model导入到view层

    }
    //悔棋的逻辑
    public void regret(){
        if (!history.isEmpty()) {
            History entry = history.pop();//可以悔棋，就出栈
            model.setMatrix(entry.matrix);//设置matrix，然后清空和更新视图！
            view.clearAll();
            view.initialGame(entry.matrix, entry.steps); // 恢复步数
        }
        else {
            JOptionPane.showMessageDialog(this.view,"当前已经无路可退!",
                    "小心！",JOptionPane.INFORMATION_MESSAGE);
        }

    }
    // 内部类保存历史状态，相当于是一个结构体的作用
    private static class History {
        int[][] matrix;
        int steps;
        History(int[][] matrix, int steps) {
            this.matrix = matrix;
            this.steps = steps;
        }
    }
    //深拷贝。每一次操作完都把上一次状态进栈。
    private int[][] copyMatrix(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }
    //当前状态深拷贝之后入栈，每次即将移动的时候都执行这个操作，对当前状态进行保存
    private void saveCurrentState() {
        int[][] currentMatrix = copyMatrix(model.getMatrix());
        int currentSteps = view.getSteps();
        //把一个历史记录类的数据，加到对应类型的栈里面
        history.push(new History(currentMatrix, currentSteps));
    }

    //移动的相关逻辑
    public boolean doMove(int row, int col, Direction direction) {
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();
        if (model.getId(row, col) == 1) {
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    //更新矩阵的元素值
                    //确认要进行变更了，先保存当前的值！
                    saveCurrentState();
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
                        saveCurrentState();
                        renewMatrixAndViewFor2(row, col, nextRow, nextCol);
                        return true;
                    }
                }
                else if(direction.getRow()==0&&direction.getCol()==1){//向右的情况，检查会不会越界
                    if(model.checkInWidthSize(nextCol+1)){
                        if(model.getId(nextRow,nextCol+1)==0){
                            saveCurrentState();
                            renewMatrixAndViewFor2(row, col, nextRow, nextCol);
                            return true;
                        }
                    }

                }
                else if(direction.getCol()==0) {//竖着走的情况
                    //这里因为有个+1 会有数组越界的问题！
                    if (model.checkInWidthSize(nextCol + 1)) {
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow, nextCol + 1) == 0) {
                            saveCurrentState();
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
                            saveCurrentState();
                            renewMatrixAndViewFor3(row, col, nextRow, nextCol);
                            return true;
                        }

                }
                else if(direction.getCol()==0&&direction.getRow()==1){//向下走
                    if(model.checkInHeightSize(nextRow+1)){
                        if(model.getId(nextRow+1,nextCol)==0){
                            saveCurrentState();
                            renewMatrixAndViewFor3(row,col,nextRow,nextCol);
                            return true;
                        }
                    }
                }
                else if(direction.getCol()==0&&direction.getRow()==-1){
                    if (model.getId(nextRow,nextCol)==0){
                        saveCurrentState();
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
                        saveCurrentState();
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getCol()==0&&direction.getRow()==1){//向下
                if(model.checkInHeightSize(nextRow+1)){
                    if(model.getMatrix()[nextRow+1][nextCol]==0&&model.getMatrix()[nextRow+1][nextCol+1]==0){
                        saveCurrentState();
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getRow()==0&&direction.getCol()==-1){//向左
                if(model.checkInWidthSize(nextCol)){
                    if(model.getMatrix()[nextRow][nextCol]==0&&model.getMatrix()[nextRow+1][nextCol]==0){
                        saveCurrentState();
                        renewMatrixAndViewFor4(row,col,nextRow,nextCol);
                        return (isWin());
                    }
                }
            }
            else if(direction.getRow()==0&&direction.getCol()==1){ //向右
                if(model.checkInWidthSize(nextCol+1)){
                    if(model.getMatrix()[nextRow][nextCol+1]==0&&model.getMatrix()[nextRow+1][nextCol+1]==0){
                        saveCurrentState();
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
            //登录的用户
            if(!user.isGuest){
                user.setWinCount(user.getWinCount()+1);
                System.out.printf("恭喜你赢了！胜利次数%d\n",user.getWinCount());
                view.showVictoryMessage();
                restartGame();
                //todo 搞个rank的页面
                String path=String.format("UserData/%s/win.txt",user.getUsername());
                try {
                    Files.write(Path.of(path),Integer.toString(user.getWinCount()).getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            }
            else {
                view.showVictoryMessage();
                restartGame();
            }

        }
        return true;//todo 这里的f和t很奇怪有人发现吗
    }

    //存档功能的逻辑实现部分
    //todo 自动存储定时存储……
    public void saveGame(User user){
        //先把二维数组转换成字符串
        int[][] matrix=model.getMatrix();
        List<String> data=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                stringBuilder.append(matrix[i][j]);
            }
            if(i!=matrix.length-1) stringBuilder.append("\n");
        }
        data.add(stringBuilder.toString());
        stringBuilder.setLength(0);//清空！！
        // test for(String s:data) System.out.println(s);
        String path=String.format("UserData/%s", user.getUsername());
        try {
            Files.write(Path.of(path+"/data.txt"),data);
            Files.write(Path.of(path+"/step.txt"),Integer.toString(view.getSteps()).getBytes());
            System.out.println(view.getSteps());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //读取存档 默认的是上一次按save按钮的存档。
    public void loadGame(User user){
        String pathOfData=String.format("Userdata/%s/data.txt",user.getUsername());
        try {
            List<String> lines=Files.readAllLines(Path.of(pathOfData));
            int[][]map=new int[4][5];
            for(int i=0;i< lines.size();i++){
                for(int j=0;j<lines.get(0).length();j++){
                    map[i][j]=lines.get(i).charAt(j)-(int)'0';
                }
            }
            model.setMatrix(map);
            view.clearAll();
            String pathOfStep=String.format("Userdata/%s/step.txt",user.getUsername());
            int steps=Integer.parseInt(Files.readAllLines(Path.of(pathOfStep)).getFirst().trim());
            view.initialGame(model.getMatrix(),steps);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
