package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 */
public class InfoFrame extends JFrame {

    private String infoText;

    public InfoFrame(String frameName, String _infoText) {
        super(frameName);
        infoText = _infoText;
        initFrame();
    }

    private void initFrame() {
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(700, 600);
        initFrameComponents();
        this.setVisible(true);
    }

    private void initFrameComponents() {
        JTextArea textInfoArea = new JTextArea(infoText);
        textInfoArea.setEditable(false);
        textInfoArea.setLineWrap(true);
        textInfoArea.setWrapStyleWord(true);
        textInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane textScroller = new JScrollPane(textInfoArea);
        this.add(textScroller);
        textScroller.setLocation(20, 20);
        textScroller.setSize(this.getWidth() - textScroller.getLocation().x * 2 - 5, this.getHeight() - textScroller.getLocation().y * 2 - 25);
    }

}
