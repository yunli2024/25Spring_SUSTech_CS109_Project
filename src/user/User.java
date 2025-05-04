package user;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

/**
 * User类 包含每一个用户的相关信息。数据储存在UserData中
 * 注意！每一个User是与GameFrame互相绑定的！
 */

public class User {
    private String username;
    private String userPassword;
    private int winCount;
    public boolean isGuest=false;
    public int getWinCount() {
        return winCount;
    }
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    // todo嵌入到GameFrame设置当前玩家 嵌入到RankFrame中进行排名。
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public User(String username, String userPassword) {
        this.username = username;
        this.userPassword = userPassword;
        if(username.equals("guest")) isGuest=true;
        winCount=findWinCount(username);
    }
    //这个方法用于在文件中寻找到WinCount
    public int findWinCount(String username){
        String path=String.format("UserData/%s/win.txt",this.username);
        if(Files.exists(Path.of(path))){
            //如果有这么一个文件，就直接进行读取就可以了。
            try {
                if(Files.exists(Path.of(path))) {
                    //如果有这么一个文件，就直接进行读取就可以了。
                    String str = Files.readString(Path.of(path));
                    winCount = Integer.parseInt(str);
                }
                else{
                    //如果不存在就变成0 注意后面的属性，不存在文件的时候会自动创建
                    Files.write(Path.of(path),"0".getBytes(), StandardOpenOption.CREATE );
                    winCount=0;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return winCount;
    }
}

/*
String path=String.format("UserData/%s/win.txt",this.username);
        try {
            String str=Files.readString(Path.of(path));
            winCount=Integer.parseInt(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
 */