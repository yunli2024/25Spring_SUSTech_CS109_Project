package user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//注意！每一个User是与GameFrame互相绑定的！
public class User {
    private String username;
    private String userPassword;
    private int winCount;

    private boolean isGuest;//增加一个游客的属性判断

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    //todo 嵌入到登陆页面 并进行valid逻辑处理，文件读写对应；
    // 嵌入到GameFrame设置当前玩家 嵌入到RankFrame中进行排名。
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User(String username, String userPassword) {
        this.username = username;
        this.userPassword = userPassword;

    }
}

//todo 这里胜利次数的逻辑没有想清楚，导致展示胜利次数的功能失效了。
//todo 可能可以将id于胜利次数绑定？写一个内部类什么的！争取独立完成这个功能
/*
String path=String.format("UserData/%s/win.txt",this.username);
        try {
            String str=Files.readString(Path.of(path));
            winCount=Integer.parseInt(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
 */