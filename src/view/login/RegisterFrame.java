package view.login;
import controller.UserController;
import view.FrameUtil;
import view.login.LoginFrame;
import view.game.GameFrame;
import javax.swing.*;
import java.awt.*;

/**
 * 接下来写登录和注册的一些逻辑
 * 1.判断注册是否成功：两次密码一致性？是否已经存在对应的文件夹？
 * 2.在注册成功的时候生成对应的用户文件夹。
 * 3.登录的时候进行检索，如果没有对应文件夹则提示应该注册。
 * 4.登录的时候要完成密码的比对验证。
 * 找对应文件夹的方法写在controller/？？？
 */
public class RegisterFrame extends JFrame{
    private JTextField username;
    private JTextField password1;
    private JTextField password2;
    private JButton confirmBtn;
    private JButton cancelBtn;
    private LoginFrame loginFrame;
    private GameFrame gameFrame;
    private RegisterFrame registerFrame;

    public RegisterFrame(int width,int height,LoginFrame loginFrame){
        this.setTitle("注册来袭！");
        this.setLayout(null);
        this.setSize(width,height);
        this.loginFrame=loginFrame;


        //定义位置^-^
        JLabel userLabel = FrameUtil.createJLabel(this, new Point(50, 20), 150, 40, "请输入用户名：");
        JLabel passLabel1 = FrameUtil.createJLabel(this, new Point(50, 80), 150, 40, "请输入密码：");
        JLabel passLabel2 = FrameUtil.createJLabel(this,new Point(50,130),150,40,"确认密码：");
        username=FrameUtil.createJTextField(this,new Point(150,20),100,40);
        password1=FrameUtil.createJTextField(this,new Point(150,80),100,40);
        password2=FrameUtil.createJTextField(this,new Point(150,130),100,40);
        confirmBtn=FrameUtil.createButton(this,"确认",new Point(50,200),80,40);
        cancelBtn=FrameUtil.createButton(this,"取消",new Point(140,200),80,40);

        //与事件进行关联
        confirmBtn.addActionListener(e -> {
           String username=this.username.getText();
           String pwd1=this.password1.getText();
           String pwd2=this.password2.getText();
           UserController userController=new UserController(this,loginFrame);
           //成功注册！
           if(userController.isValidRegister(username,pwd1,pwd2)){
               this.setVisible(false);
               loginFrame.setVisible(true);
           }
        });

        cancelBtn.addActionListener(e -> {
                loginFrame.setVisible(true);
                this.setVisible(false);
        });

        this.setLocationRelativeTo(null);
    }



}
