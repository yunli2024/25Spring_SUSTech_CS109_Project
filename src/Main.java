import model.MapModel;
import view.game.GameFrame;
import view.game.MusicFrame;
import view.login.LoginFrame;
import view.login.RegisterFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(480, 480);
            loginFrame.setVisible(true);
            //通过每个类之间互相的set 实现不同frame之间的跳转！
            //一开始只有一个login界面，后面根据不同的选项到时候再new 这样的思路是对的
        });
    }
}
