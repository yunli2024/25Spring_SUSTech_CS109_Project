package controller;

import model.MapModel;
import user.User;
import view.login.LoginFrame;
import view.login.RegisterFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private RegisterFrame registerFrame;
    private LoginFrame loginFrame;
    public UserController(RegisterFrame registerFrame,LoginFrame loginFrame){
        this.registerFrame=registerFrame;
        this.loginFrame=loginFrame;
    }
    //todo 这些try-catch语句里面的异常处理可能还要加强一下，不要直接报错……
    //判断是否成功注册
    public boolean isValidRegister(String username,String pwd1,String pwd2){
        if(username.isEmpty()) {
            JOptionPane.showMessageDialog( registerFrame,
                    "请输入用户名！",
                    "失败了！",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(pwd1.isEmpty()||pwd2.isEmpty()){
            JOptionPane.showMessageDialog(registerFrame,
                    "请输入密码！",
                    "失败了！",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(!pwd1.equals(pwd2)){
            JOptionPane.showMessageDialog(registerFrame,
                    "两次密码不一致！",
                    "失败了！",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String UserDir=String.format("UserData/%s",username);
        if(Files.exists(Path.of(UserDir))){
            JOptionPane.showMessageDialog(registerFrame,
                    "已经存在该用户！",
                    "失败了！",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else{
            //注意，只有字符数组才可以进行文件的write!!!
            List<String> password=new ArrayList<>();
            password.add(pwd1);
            try {
                File dir=new File(UserDir);
                dir.mkdirs();
                Files.write(Path.of(UserDir+"/password.txt"),password);
                JOptionPane.showMessageDialog(registerFrame,
                        "注册成功！",
                        "哈哈",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
    }
    //判断是否合法用户
    public boolean isValidUser(String username,String password){
        String userPath=String.format("UserData/%s",username);
        if(!Files.exists(Path.of(userPath))){
            JOptionPane.showMessageDialog( loginFrame,
                    "未检索到当前用户！请您先注册吧~",
                    "登录失败了！",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            try {
                //文件的读写，都要用字符串数组来进行操作。
                List<String> correctPassword=Files.readAllLines(Path.of(userPath+"/password.txt"));
                if(!correctPassword.getFirst().equals(password)){
                    JOptionPane.showMessageDialog( loginFrame,
                            "密码错误了~",
                            "登录失败了！",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else{
                    JOptionPane.showMessageDialog( loginFrame,
                            "欢迎",
                            "登录成功！",
                            JOptionPane.INFORMATION_MESSAGE);
                    return true;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
