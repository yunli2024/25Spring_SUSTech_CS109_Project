package view.login;

import controller.UserController;
import model.MapModel;
import user.User;
import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
//todo 完成register的frame 并且存储注册数据

public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton registerBtn;
    private JButton guestBtn;
    private GameFrame gameFrame;
    private RegisterFrame registerFrame;


    public LoginFrame(int width, int height) {
        this.setTitle("快点登录吧~");
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


        //实现按钮什么的与事件之间的关联
        //todo提交你的用户名和密码，还没写check
        submitBtn.addActionListener(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());

            //todo: check login info 这里是要与文件IO什么的相联系了，要读取存档！
            if(UserController.isValidUser(username.getText(),password.getText())) {
                User user = new User(username.getText(), password.getText());
                MapModel mapModel = new MapModel(new int[][]{
                        {2, 2, 2, 2, 1},
                        {3, 0, 0, 0, 1},
                        {3, 0, 0, 4, 4},
                        {0, 0, 0, 4, 4}
                });
                GameFrame gameFrame = new GameFrame(600, 450, mapModel, user);
                gameFrame.setVisible(true);
                this.setVisible(false);
            }
            else System.out.println("登录失败，还没写！");
        });


        //游客这方面
        guestBtn.addActionListener(e -> {
            MapModel mapModel = new MapModel(new int[][]{
                    {2,2,2,2,1},
                    {3,0,0,0,1},
                    {3,0,0,4,4},
                    {0,0,0,4,4}
            });
            User guest=new User(null,null);
            GameFrame gameFrame = new GameFrame(600, 450, mapModel,guest);
                JOptionPane.showMessageDialog(this,
                        "注意，你只是一个游客，不能存档/读档/排行榜！",
                        "Caution!",
                        JOptionPane.INFORMATION_MESSAGE);
                gameFrame.setVisible(true);
                this.setVisible(false);

        });

        //重置
        resetBtn.addActionListener(e -> {
            username.setText("");
            password.setText("");
        });

        //注册按钮，跳转到注册的Frame
        registerBtn.addActionListener(e -> {
            RegisterFrame registerFrame=new RegisterFrame(400,300);
            registerFrame.setVisible(true);
            this.setVisible(false);

        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    //仿照上面的，写指向注册框架的跳转
    public void setRegisterFrame(RegisterFrame registerFrame) {
        this.registerFrame = registerFrame;
    }


}
