package view.game;

import controller.GameController;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;

public class MusicFrame extends JFrame{
    private JButton musicBtn1;
    private JButton musicBtn2;
    private JButton musicBtn3;

    private GameController controller;
    private GamePanel gamePanel=new GamePanel();

    public MusicFrame(int width,int height){
        this.setSize(width,height);
        this.setTitle("选择喜欢的音乐风格~");
        this.setLayout(null);
       // this.controller=???
        //按钮的位置定义
        this.musicBtn1= FrameUtil.createButton(this,"风格1",
                new Point(50,50),100,40);
        this.musicBtn2= FrameUtil.createButton(this,"风格2",
                new Point(50,150),100,40);
        this.musicBtn3= FrameUtil.createButton(this,"风格3",
                new Point(50,250),100,40);

        //事件的关联
        this.musicBtn1.addActionListener(e -> {
            gamePanel.stopBackGroundMusic();//先要停止掉之前的音乐
            //todo 停止之后发出一瞬间诡异的声音，有点影响体验
            gamePanel.playBackGroundMusic("/music_handinhand.wav");
            gamePanel.requestFocusInWindow();
        });
        this.musicBtn2.addActionListener(e -> {
            gamePanel.stopBackGroundMusic();
            gamePanel.playBackGroundMusic("/music_miku.wav");
            gamePanel.requestFocusInWindow();
        });
        this.musicBtn3.addActionListener(e ->{
            gamePanel.stopBackGroundMusic();
            gamePanel.playBackGroundMusic("/music_fddm.wav");
            gamePanel.requestFocusInWindow();
        });

        this.setLocationRelativeTo(null);


    }
}
