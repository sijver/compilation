package ui;

import compiler.core.utils.FileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 */
//User interface
public class MainFrame extends JFrame {

    private JTextArea textInfoArea;

    private JButton openScriptButton;
    private JFileChooser scriptFileChooser;

    private final static int BUTTON_HEIGHT = 50;
    private final static int ELEMENTS_MARGIN = 20;
    private final static int BOTTOM_MARGIN = 25;

    public MainFrame() {
        super("Perl script translator");
        initFrame();
    }

    private void initFrame() {
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(800, 600);
        initFrameComponents();
        this.setVisible(true);
    }

    private void initFrameComponents() {
        textInfoArea = new JTextArea("Type your script here or open script file...");
        textInfoArea.setEditable(true);
        textInfoArea.setLineWrap(true);
        textInfoArea.setWrapStyleWord(true);
        textInfoArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textInfoArea.selectAll();
        JScrollPane textScroller = new JScrollPane(textInfoArea);
        this.add(textScroller);
        textScroller.setLocation(ELEMENTS_MARGIN, ELEMENTS_MARGIN);
        textScroller.setSize(this.getWidth() - textScroller.getLocation().x * 2 - 5, this.getHeight() - textScroller.getLocation().y - BOTTOM_MARGIN - BUTTON_HEIGHT - 2 * ELEMENTS_MARGIN);

        initButtons();
        initButtonActions();
    }

    private void initButtons() {
        openScriptButton = new JButton("Open script file");
        this.add(openScriptButton);
        openScriptButton.setLocation(ELEMENTS_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);
        openScriptButton.setSize(140, BUTTON_HEIGHT);
    }

    private void initButtonActions(){
        openScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scriptFileChooser = new JFileChooser();
                if (scriptFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        textInfoArea.setText(FileLoader.readTheFile(scriptFileChooser.getSelectedFile().getAbsolutePath()));
                    } catch (IOException e1) {
                        textInfoArea.setText("Error. File can't be opened.");
                    }
                }
            }
        });
    }

}
