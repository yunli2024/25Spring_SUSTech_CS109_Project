package user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//注意！每一个User是与GameFrame互相绑定的！
public class User {
    private String username;
    private String userPassword;
    private int winCount;

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
        String path=String.format("UserData/%s/win.txt",this.username);
        try {
            String str=Files.readString(Path.of(path));
            winCount=Integer.parseInt(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
