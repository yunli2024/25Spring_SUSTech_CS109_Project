import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(280, 280);
            loginFrame.setVisible(true);
            MapModel mapModel = new MapModel(new int[][]{
                    {2,2,0,0,0},
                    {0,2,2,0,0},
                    {0,0,0,2,2},
                    {1,1,0,0,0}
            });

            /*
            test棋盘1：
            {2,2,0,0,0},
            {0,2,2,0,0},
            {0,0,0,2,2},
            {0,0,0,0,0}
            标准的初始化棋盘格式
            {2,2,2,2,1},
            {4,4,3,1,0},
            {4,4,3,1,0},
            {2,2,2,2,1}
            注意是一个横置的~ 3表示关羽，是一个2*1的
             */
            GameFrame gameFrame = new GameFrame(600, 450, mapModel);
            gameFrame.setVisible(false);
            loginFrame.setGameFrame(gameFrame);
        });
    }
}
