package view.login;
/*这个类是注册时的框架
 */

import view.FrameUtil;
import view.login.LoginFrame;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
public class RegisterFrame extends JFrame{
    private JTextField username;
    private JTextField password;
    private JTextField password2;
    private JButton confirmBtn;
    private JButton cancelBtn;
    private LoginFrame loginFrame;
    private GameFrame gameFrame;
    private RegisterFrame registerFrame;

    public RegisterFrame(int width,int height){
        this.setTitle("注册来袭！");
        this.setLayout(null);
        this.setSize(width,height);

        //定义位置^-^
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 150, 40, "请输入用户名：");
        JLabel passLabel1 = FrameUtil.createJLabel(this, new Point(50, 80), 150, 40, "请输入密码：");
        JLabel passLabel2 = FrameUtil.createJLabel(this,new Point(50,130),150,40,"确认密码：");
        username=FrameUtil.createJTextField(this,new Point(150,20),100,40);
        password=FrameUtil.createJTextField(this,new Point(150,80),100,40);
        password2=FrameUtil.createJTextField(this,new Point(150,130),100,40);
        confirmBtn=FrameUtil.createButton(this,"确认",new Point(50,200),80,40);
        cancelBtn=FrameUtil.createButton(this,"取消",new Point(140,200),80,40);

        //与事件进行关联
        confirmBtn.addActionListener(e -> {
            //todo 提交用户名啥的，建立这么一个用户
            //if 密码一致，没有重复用户名
            JOptionPane.showMessageDialog(this,
                    "注册成功！",
                    "哈哈",
                    JOptionPane.INFORMATION_MESSAGE);

            if(this.gameFrame!=null){
                this.gameFrame.setVisible(true);
                this.setVisible(false);
            }
        });
        //todo 这里的逻辑不一定是对嘟！看清是否已经有一个Frame了，两种不同的切换页面方式！
        cancelBtn.addActionListener(e -> {
            if(this.loginFrame!=null){
                this.loginFrame.setVisible(true);
                this.setVisible(false);
            }
        });

        //这个设置可以关掉界面的时候就结束程序的运行
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



}
