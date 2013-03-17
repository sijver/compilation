package ui;

import compiler.core.parser.First;
import compiler.core.parser.Follow;
import compiler.core.parser.perl.ParsingTable;
import compiler.core.parser.tree.ParseTree;
import compiler.core.scanner.finiteautomaton.FiniteAutomaton;
import compiler.core.scanner.finiteautomaton.Token;
import compiler.core.scanner.finiteautomaton.perl.PerlAutomatonSettings;
import compiler.core.utils.FileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
//User interface
public class MainFrame extends JFrame {

    private JTextArea textInfoArea;

    private JButton openScriptButton;
    private JFileChooser scriptFileChooser;

    private JButton saveHtmlButton;
    private ButtonGroup htmlChooseGroup;
    private JFileChooser htmlFileChooser;

    private JButton scanButton;
    private JButton generateCodeButton;
    private JButton parseTreeButton;

    private FiniteAutomaton finiteAutomaton;

    private final static int BUTTON_HEIGHT = 50;
    private final static int ELEMENTS_MARGIN = 20;
    private final static int BOTTOM_MARGIN = 25;
    private final static int RIGHT_MARGIN = 5;
    private final static int RADIO_WIDTH = 100;

    public MainFrame() {
        super("Perl script translator");
        initFrame();
    }

    private void initFrame() {
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(880, 660);
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
        textScroller.setSize(this.getWidth() - textScroller.getLocation().x * 2 - RIGHT_MARGIN, this.getHeight() - textScroller.getLocation().y - BOTTOM_MARGIN - BUTTON_HEIGHT - 2 * ELEMENTS_MARGIN);

        initButtons();
        initButtonActions();
    }

    private void initButtons() {
        //OPEN SCRIPT BUTTON
        openScriptButton = new JButton("Open script file");
        this.add(openScriptButton);
        openScriptButton.setLocation(ELEMENTS_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);
        openScriptButton.setSize(140, BUTTON_HEIGHT);

        //SAVE HTML BUTTON
        saveHtmlButton = new JButton("Get html of...");
        this.add(saveHtmlButton);
        saveHtmlButton.setSize(110, BUTTON_HEIGHT);
        saveHtmlButton.setLocation(this.getWidth() - saveHtmlButton.getWidth() - ELEMENTS_MARGIN * 2 - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);

        //HTML CHOOSE
        JRadioButton firstRadioButton = new JRadioButton("First", true);
        firstRadioButton.setActionCommand("First");
        this.add(firstRadioButton);
        firstRadioButton.setSize(RADIO_WIDTH, 20);
        firstRadioButton.setLocation(this.getWidth() - ELEMENTS_MARGIN - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN - 5);

        JRadioButton followRadioButton = new JRadioButton("Follow");
        followRadioButton.setActionCommand("Follow");
        this.add(followRadioButton);
        followRadioButton.setSize(RADIO_WIDTH, 20);
        followRadioButton.setLocation(this.getWidth() - ELEMENTS_MARGIN - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN * 0 - 5);

        JRadioButton parsingTableRadioButton = new JRadioButton("Parsing table");
        parsingTableRadioButton.setActionCommand("Parsing table");
        this.add(parsingTableRadioButton);
        parsingTableRadioButton.setSize(RADIO_WIDTH, 20);
        parsingTableRadioButton.setLocation(this.getWidth() - ELEMENTS_MARGIN - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN * -1 - 5);

        htmlChooseGroup = new ButtonGroup();
        htmlChooseGroup.add(firstRadioButton);
        htmlChooseGroup.add(followRadioButton);
        htmlChooseGroup.add(parsingTableRadioButton);

        //SCANNER BUTTON
        scanButton = new JButton("Scan script");
        this.add(scanButton);
        scanButton.setSize(100, BUTTON_HEIGHT);
        scanButton.setLocation(this.getWidth() - saveHtmlButton.getWidth() - scanButton.getWidth() - ELEMENTS_MARGIN * 3 - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);

        //PARSE TREE BUTTON
        parseTreeButton = new JButton("Parse tree");
        this.add(parseTreeButton);
        parseTreeButton.setSize(100, BUTTON_HEIGHT);
        parseTreeButton.setLocation(this.getWidth() - saveHtmlButton.getWidth() - scanButton.getWidth() - parseTreeButton.getWidth() - ELEMENTS_MARGIN * 4 - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);

        //GENERATE CODE BUTTON
        generateCodeButton = new JButton("Generate code");
        this.add(generateCodeButton);
        generateCodeButton.setSize(120, BUTTON_HEIGHT);
        generateCodeButton.setLocation(this.getWidth() - saveHtmlButton.getWidth() - scanButton.getWidth() - parseTreeButton.getWidth() - generateCodeButton.getWidth() - ELEMENTS_MARGIN * 5 - RADIO_WIDTH - RIGHT_MARGIN, this.getHeight() - BOTTOM_MARGIN - BUTTON_HEIGHT - ELEMENTS_MARGIN);
    }

    private void initButtonActions() {
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

        saveHtmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                htmlFileChooser = new JFileChooser();
                String action = htmlChooseGroup.getSelection().getActionCommand();
                htmlFileChooser.setSelectedFile(new File(action + ".html"));
                if (htmlFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(htmlFileChooser.getSelectedFile().getAbsolutePath()));
                        if (action.equals("First")) {
                            writer.print(First.getFirstHtml());
                        } else if (action.equals("Follow")) {
                            writer.print(Follow.getFollowHtml());
                        } else if (action.equals("Parsing table")) {
                            writer.print(ParsingTable.getParsingTableHtml());
                        }
                        writer.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder tokensString = new StringBuilder();
                initFiniteAutomaton();

                List<Token> tokensList = finiteAutomaton.getScannedTokens(textInfoArea.getText());
                for (Token t : tokensList) {
                    tokensString.append(String.format("%s         %s\n", t.getLexicalUnit(), t.getReturnValue()));
                }

                new InfoFrame("Scanned tokens of the script", tokensString.toString());
            }
        });

        parseTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initFiniteAutomaton();
                ParseTree parseTree = new ParseTree(finiteAutomaton.getScannedTokens(textInfoArea.getText()));
                if (parseTree.doParsing()) {
                    new InfoFrame("Parse tree", parseTree.getTreeRoot().getTreeText());
                } else {
                    new InfoFrame("Parse tree", parseTree.getParsingErrorMessage());
                }
            }
        });

        generateCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initFiniteAutomaton();
                ParseTree parseTree = new ParseTree(finiteAutomaton.getScannedTokens(textInfoArea.getText()));
                if (parseTree.doParsing()) {
                    new InfoFrame("ASM/ARM Code generated", "The code generation tool doesn't work due to the absence of time to finish it...\n\n" + parseTree.getTreeRoot().generateCode().toString());
                } else {
                    new InfoFrame("ASM/ARM Code generated", "The code generation tool doesn't work due to the absence of time to finish it...\n\n" + parseTree.getParsingErrorMessage());
                }
            }
        });
    }

    //Init DFA
    private void initFiniteAutomaton() {
        if (finiteAutomaton == null) {
            finiteAutomaton = new FiniteAutomaton();
            finiteAutomaton.setStartState(PerlAutomatonSettings.getStartState());
            finiteAutomaton.setAcceptStatesList(PerlAutomatonSettings.getAcceptStatesList());
            finiteAutomaton.setTransitionsList(PerlAutomatonSettings.getTransitions());
            finiteAutomaton.setBackTransitions(PerlAutomatonSettings.getBackTransitions());
            finiteAutomaton.setCommentSymbol(PerlAutomatonSettings.getCommentSymbol());
            finiteAutomaton.setStringStates(PerlAutomatonSettings.getStringStates());
        }
    }

}
