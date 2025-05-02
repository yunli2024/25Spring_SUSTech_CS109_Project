package view.game;

import controller.GameController;
import controller.UserController;
import model.MapModel;
import user.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private UserController userController;


    private User user;  //每个游戏界面均与对应的User相绑定！
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private JLabel userLabel;

    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton regretBtn;

    private JButton musicPlayBtn;
    private JButton musicStyleBtn;

    //上下左右的箭头
    private JButton upBtn;
    private JButton downBtn;
    private JButton leftBtn;
    private JButton rightBtn;

    private JLabel stepLabel;
    private GamePanel gamePanel;
    private MusicFrame musicFrame;

    //frame 框架主要的布局是在构造函数里面进行的 定义各个组件的位置以及事件的监听
    public GameFrame(int width, int height, MapModel mapModel ,User user) {
        this.setTitle("2025 CS109 Project by 云离");
        this.setLayout(null);
        this.setSize(width, height);
        this.setUser(user);
        gamePanel = new GamePanel(mapModel);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel,user);

        this.musicFrame=new MusicFrame(400,400);

       gamePanel.playBackGroundMusic("/bgm_piano.wav"); // 按按钮的时候再……

        //按钮在view上面的定义
        this.restartBtn = FrameUtil.createButton(this, "重新",
                new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "读档",
                new Point(gamePanel.getWidth() + 80, 180), 80, 50);
        this.saveBtn=FrameUtil.createButton(this,"存档",
                new Point(gamePanel.getWidth()+200,240),80,50);
        this.regretBtn=FrameUtil.createButton(this, "悔棋",
                new Point(gamePanel.getWidth() + 160, 120), 80, 50);
        this.musicPlayBtn=FrameUtil.createButton(this, "音乐",
                new Point(gamePanel.getWidth() + 160, 180), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "开始游戏吧！",
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 30), 180, 50);
        this.userLabel= FrameUtil.createJLabel(this,user.getUsername(),
                new Font("serif", Font.ITALIC, 22),
                new Point(gamePanel.getWidth() + 80, 60), 180, 50);
        this.musicStyleBtn=FrameUtil.createButton(this,"风格",
                new Point(gamePanel.getWidth() + 160, 220),80,50);
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
            //String string = JOptionPane.showInputDialog(this, "Input path:");
            String path=String.format("Userdata/%s/data.txt",user.getUsername());

            controller.loadGame(user);

            gamePanel.requestFocusInWindow();//enable key listener
        });

        this.saveBtn.addActionListener(e ->{
            //userController.save(user);//saveGame传递的是user
            controller.saveGame(user);


            gamePanel.requestFocusInWindow();
        });

        this.regretBtn.addActionListener(e -> {
            //todo 悔棋按钮

            gamePanel.requestFocusInWindow();
        });
        this.musicPlayBtn.addActionListener(e -> {
            gamePanel.playBackGroundMusic("/musicTest.wav");
            gamePanel.requestFocusInWindow();
        });

        this.musicStyleBtn.addActionListener(e -> {
            gamePanel.stopBackGroundMusic();
            if(this.musicFrame!=null){
                this.musicFrame.setVisible(true);
            }
            gamePanel.requestFocusInWindow();
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
