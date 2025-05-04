package view.game;
import javax.swing.*;
import java.awt.*;
public class BackgroundImagePanel extends JPanel {
    public Image image;
    public BackgroundImagePanel() {
        this(null);
    }
    public BackgroundImagePanel(Image image) {
        this.image = image;
        this.setOpaque(false);
    }
    public void setBackgroundImage(Image image) {
        this.image = image;
    }
    //进行全屏幕的重新绘制
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}