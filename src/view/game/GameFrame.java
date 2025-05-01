package view.game;

import controller.GameController;
import model.MapModel;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton regretBtn;

    //上下左右的箭头
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;

    public GameFrame(int width, int height, MapModel mapModel) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);

        //按钮在view上面的定义
        this.restartBtn = FrameUtil.createButton(this, "重新",
                new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "读档",
                new Point(gamePanel.getWidth() + 80, 180), 80, 50);
        this.regretBtn=FrameUtil.createButton(this, "悔棋",
                new Point(gamePanel.getWidth() + 160, 120), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "开始游戏吧！",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 30), 180, 50);
        this.upBtn=FrameUtil.createButton(this,"👆",
                new Point(gamePanel.getWidth()+100,230),50,50);
        this.downBtn=FrameUtil.createButton(this,"👇",
                new Point(gamePanel.getWidth()+100,280),50,50);
        this.leftBtn=FrameUtil.createButton(this,"👈",
                new Point(gamePanel.getWidth()+50,280),50,50);
        this.rightBtn=FrameUtil.createButton(this,"👉",
                new Point(gamePanel.getWidth()+150,280),50,50);
        gamePanel.setStepLabel(stepLabel);

        //事件监听，将按钮与实际行为产生关联
        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });
        //todo: add other button here
        this.regretBtn.addActionListener(e -> {
            //todo 悔棋按钮
        });
        this.upBtn.addActionListener(e -> {
            gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();
        });
        this.downBtn.addActionListener(e -> {
            gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();
        });
        this.leftBtn.addActionListener(e -> {
            gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();
        });
        this.rightBtn.addActionListener(e -> {
            gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



}
