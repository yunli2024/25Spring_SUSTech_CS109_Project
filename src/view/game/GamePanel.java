package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel相较于Frame 是更为轻量级的次级容器。实际view中，这一部分代表了左侧的那个棋盘。
 * 其他的按钮还需要在Frame中布局在右侧。
 * 这个类实现了游戏的初始化等功能，把滑块导入，重绘……
 */
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;
    public int getSteps() {
        return steps;
    }
    //清空的相关逻辑
    public BoxComponent removeBox(BoxComponent box){
        this.remove(box);
        this.revalidate();
        return box;
    }
    public void clearAll(){
        for(BoxComponent box:boxes){
            removeBox(box);
        }
        this.boxes.clear();
        this.repaint();
    }
    //构造函数
    public GamePanel(){
        //不知道。给MusicFrame用的，因为莫名其妙把playBGM的方法写到这里了
    }
    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;
        stepLabel = new JLabel();//这里需要进行一个new 否则空指针了
        initialGame(this.model.getMatrix(),0);//调用initial方法进行初始化
    }
    //初始化游戏的方法
    public void initialGame(int[][] matrix,int steps) {
        this.steps=steps;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        //此处把model中的底层矩阵拷贝成map
        //注意map是临时的，只用于构建里面的滑块
        int[][] map = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                map[i][j] = model.getId(i, j);
            }
        }
        //构建滑块
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                //由此可见map只是matrix的副本 在上面提取box之后就清空。
                if (map[i][j] == 1) {
                    box = new BoxComponent(Color.ORANGE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                } else if (map[i][j] == 2) {
                    box = new BoxComponent(Color.PINK, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                } else if (map[i][j] == 3) {
                    box = new BoxComponent(Color.BLUE, i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                } else if (map[i][j] == 4) {
                    box = new BoxComponent(Color.GREEN, i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.add(box);
                }
            }
        }
        this.repaint();
    }
    //重写paintComponent
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.setBorder(border);
        // 以下代码绘制出口的边框
        int exitCol = model.getWidth() - 1; // 最后一列
        int middleRow = model.getHeight() / 2;
        int exitRow1 = middleRow - 1;
        int exitRow2 = middleRow;
        // 确保行号不越界
        if (exitRow1 < 0) exitRow1 = 0;
        if (exitRow2 >= model.getHeight()) exitRow2 = model.getHeight() - 1;
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setColor(Color.CYAN);
            g2d.setStroke(new BasicStroke(5)); // 设置边框粗细
            // 绘制两个边框
            for (int row : new int[]{exitRow1, exitRow2}) {
                int x = exitCol * GRID_SIZE + GRID_SIZE - 1;  // 右侧边缘坐标
                int y = row * GRID_SIZE;
                g2d.drawLine(x, y, x, y + GRID_SIZE);
            }
        } finally {
            g2d.dispose();
        }
    }
    //实现listener中的事件监听
    @Override
    public void doMouseClick(Point point) {
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }
    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                afterMove();
            }
        }
    }
    @Override
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }
    @Override
    public void doMoveUp() {
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }
    @Override
    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                afterMove();
            }
        }
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }

    //此处是胜利的弹窗界面
    public void showVictoryMessage() {
        //todo 胜利音效已经做了，结算动画？
        playMusicSound("/winGame.wav");
        JOptionPane.showMessageDialog(this,
                "宝宝，你赢了！用了"+(int)(steps+1)+"步哈哈",
                "Victory!",
                JOptionPane.INFORMATION_MESSAGE);

    }
    //————————————————————————————————————————————————————————————————————————————
    private Clip currentClip;
    /**
     * 此处是音频相关，一开始以为就一两个方法所以挤在这里了，
     * 没想到后面滚雪球了，就这样吧。
     *
     */
    public void playBackGroundMusic(String path) {
        try {
            stopBackGroundMusic();
            // 加载新音乐
            InputStream audioSrc = getClass().getResourceAsStream(path);
            if (audioSrc == null) {
                throw new FileNotFoundException("音频文件未找到: " + path);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(audioSrc)
            );

            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentClip.start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法播放音频: " + e.getMessage());
        }
    }
    public void playMusicSound(String path){
        try {
            InputStream audioSrc = getClass().getResourceAsStream(path);
            if (audioSrc == null) {
                throw new FileNotFoundException("音频文件未找到: " + path);
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(audioSrc)
            );

            currentClip = AudioSystem.getClip();
            currentClip.open(audioStream);
            currentClip.loop(1);
            currentClip.start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "无法播放音频: " + e.getMessage());
        }
    }

    public void stopBackGroundMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
//————————————————————————————————————————————————————————————————————————————————————————————————

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }
    public void setController(GameController controller) {
        this.controller = controller;
    }
    public BoxComponent getSelectedBox() {
        return selectedBox;
    }
    public int getGRID_SIZE() {
        return GRID_SIZE;
    }
}
