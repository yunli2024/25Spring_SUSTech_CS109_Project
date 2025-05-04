package view.login;

import controller.UserController;
import model.MapGenerator;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;
import view.game.LevelFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton registerBtn;
    private JButton guestBtn;
    private GameFrame gameFrame;
    private RegisterFrame registerFrame;
    private LoginFrame loginFrame;



    public LoginFrame(int width, int height) {
        this.setTitle("超好玩来袭！~");
        this.setLayout(null);
        this.setSize(width, height);

        //定义成员在frame中的位置和显示
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, new Point(50, 80), 70, 40, "password:");
        username = FrameUtil.createJTextField(this, new Point(120, 20), 120, 40);
        password = FrameUtil.createJPasswordField(this, new Point(120, 80), 120, 40);

        submitBtn = FrameUtil.createButton(this, "登录！", new Point(40, 140), 100, 40);
        resetBtn = FrameUtil.createButton(this, "重置", new Point(160, 140), 100, 40);
        registerBtn = FrameUtil.createButton(this,"注册",new Point(40,200),100,40);
        guestBtn=FrameUtil.createButton(this,"游客",new Point(160,200),100,40);

        //事件监听
        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());
            UserController userController=new UserController(registerFrame,loginFrame);
            if(userController.isValidUser(username.getText(),password.getText())) {
                User user = new User(username.getText(), password.getText());
                LevelFrame levelFrame=new LevelFrame(400,400,user);
                levelFrame.setVisible(true);
                this.setVisible(false);
            }
            else System.out.println("登录失败!");
        });


        //游客这方面
        guestBtn.addActionListener(e -> {
            User guest=new User("guest","");
            LevelFrame levelFrame=new LevelFrame(400,400,guest);
                JOptionPane.showMessageDialog(this,
                        "注意，你只是一个游客，不能存档/读档/排行榜！",
                        "Caution!",
                        JOptionPane.INFORMATION_MESSAGE);
                levelFrame.setVisible(true);
                this.setVisible(false);
        });

        //重置
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        //跳转到注册的Frame
        registerBtn.addActionListener(e -> {
            RegisterFrame registerFrame=new RegisterFrame(400,300,this);
            registerFrame.setVisible(true);
            this.setVisible(false);
        });

        //关闭界面就结束程序
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
