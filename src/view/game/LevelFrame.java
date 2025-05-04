package view.game;

import model.MapGenerator;
import model.MapModel;
import user.User;
import view.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//todo 设置关卡 登陆后：关卡选择->进入对应
public class LevelFrame extends JFrame {
    Random random=new Random();
    private JButton randomBtn;
    private JButton easyClassicBtn;
    private JButton difficultClassicBtn;
    private JButton timeLimitBtn;

    public LevelFrame(int width, int height, User user){
        this.setSize(width,height);
        this.setTitle("难度选择页面");
        this.setLayout(null);

        this.easyClassicBtn= FrameUtil.createButton(this,"经典简单",
                new Point(50,30),100,50);
        this.difficultClassicBtn= FrameUtil.createButton(this,"经典困难",
                new Point(50,80),100,50);
        this.randomBtn= FrameUtil.createButton(this,"随机关卡",
                new Point(50,130),100,50);
        this.timeLimitBtn= FrameUtil.createButton(this,"限时模式",
                new Point(50,180),100,50);
        //事件响应
        this.easyClassicBtn.addActionListener(e -> {

            MapModel mapModel=new MapModel(MapGenerator.easyGeneratorMap());
            GameFrame gameFrame=new GameFrame(600,450,mapModel,user);
            if(user.isGuest){
                gameFrame.saveBtn.setEnabled(false);
                gameFrame.loadBtn.setEnabled(false);
            }
            gameFrame.setVisible(true);
            this.setVisible(false);
        });
        this.difficultClassicBtn.addActionListener(e -> {
            MapModel mapModel=new MapModel(MapGenerator.generatorMap());
            GameFrame gameFrame=new GameFrame(600,450,mapModel,user);
            if(user.isGuest){
                gameFrame.saveBtn.setEnabled(false);
                gameFrame.loadBtn.setEnabled(false);
            }
            gameFrame.setVisible(true);
            this.setVisible(false);
        });
        this.randomBtn.addActionListener(e -> {
            MapModel mapModel=new MapModel(MapGenerator.generatorMapRandom());
            GameFrame gameFrame=new GameFrame(600,450,mapModel,user);
            if(user.isGuest){
                gameFrame.saveBtn.setEnabled(false);
                gameFrame.loadBtn.setEnabled(false);
            }
            gameFrame.setVisible(true);
            this.setVisible(false);
        });
        this.timeLimitBtn.addActionListener(e -> {
            //todo 限时模式 先用test代替

            MapModel mapModel=new MapModel(MapGenerator.generatorMapTest());
            GameFrame gameFrame=new GameFrame(600,450,mapModel,user);
            if(user.isGuest){
                gameFrame.saveBtn.setEnabled(false);
                gameFrame.loadBtn.setEnabled(false);
            }
            gameFrame.setVisible(true);
            this.setVisible(false);
        });
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
