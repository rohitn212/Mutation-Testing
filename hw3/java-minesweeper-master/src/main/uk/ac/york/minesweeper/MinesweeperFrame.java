package uk.ac.york.minesweeper;

import static uk.ac.york.minesweeper.TemplateClass.instrum;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javafx.util.Pair;

public class MinesweeperFrame extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;

    // Constants
    private static final String[] DIFFICULTIES = { "Easy", "Medium", "Hard" };

    private static final String INCREMENT = "incr";
    private static final String RESET = "reset";

    // Interface
    private JPanel mainPanel =  new JPanel(new BorderLayout(10, 10));
    private JComboBox<String> difficultyBox = new JComboBox<String>(DIFFICULTIES);
    private MinefieldPanel minePanel;

    // Timer
    private Timer scoreTimer = new Timer(1000, this);
    private JLabel topTimer;
    private int time = 0;

    // Button Images
    private JButton topResetBtn;

    public MinesweeperFrame()
    {
        // Basic Interface Settings
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0,0));
        this.getContentPane().setBackground(Color.white);
        this.setSize(new Dimension(400, 500));
        this.setMinimumSize(new Dimension(400, 500));
        this.setTitle("Minesweeper");

        // Interface Structure
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBackground(Color.white);
		instrum(57,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.topPanel",topPanel));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBackground(Color.white);
		instrum(61,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.centerPanel",centerPanel));

        JPanel centerMidPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        minePanel = new MinefieldPanel(new Minefield(16, 16, 40));
		instrum(66,"Assign",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel));
        minePanel.addStateChangeListener(new MinefieldStateChangeListener()
        {
            @Override
            public void stateChanged(MinefieldStateChangeEvent event)
            {
                Minefield minefield = minePanel.getMinefield();

                if (minefield.isFinished())
                {
                    // Stop timer and set icon
                    scoreTimer.stop();

                    //if (minefield.getGameState() == GameState.WON)
                        //topResetBtn.setIcon(new ImageIcon(Images.FACE_WON));
                    //else
                        //topResetBtn.setIcon(new ImageIcon(Images.FACE_LOST));
                }
                else
                {
                    // Set normal face and start timer if we've just started
                    //topResetBtn.setIcon(new ImageIcon(Images.FACE_NORMAL));

                    if (minefield.getGameState() == GameState.RUNNING)
                        scoreTimer.start();
                }

                topResetBtn.repaint();
            }
        });
//		instrum(68,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.stateChanged.event",event),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.stateChanged.minefield",minefield),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.scoreTimer",scoreTimer),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn));

        centerMidPanel.add(minePanel);
		instrum(99,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.centerMidPanel",centerMidPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel));

        // Difficulty Chooser
        difficultyBox.setSelectedIndex(1);
		instrum(103,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.difficultyBox",difficultyBox));

        // Reset Button
        topResetBtn = new JButton();
		instrum(107,"Assign",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn));
        topResetBtn.setPreferredSize(new Dimension(50, 50));
		instrum(109,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn));
        topResetBtn.setActionCommand(RESET);
		instrum(111,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.RESET",RESET));
        topResetBtn.addActionListener(this);
		instrum(113,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn));
        centerPanel.add(topResetBtn);
		instrum(115,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.centerPanel",centerPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topResetBtn",topResetBtn));

        //topResetBtn.setIcon(new ImageIcon(Images.FACE_NORMAL));

        // Labels
        topTimer = new JLabel(String.valueOf(time) + " Seconds");
		instrum(121,"Assign",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topTimer",topTimer),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.time",time));
        scoreTimer.setActionCommand(INCREMENT);
		instrum(123,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.scoreTimer",scoreTimer),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.INCREMENT",INCREMENT));

        // Adding Items to Grid
        topPanel.add(difficultyBox);
		instrum(127,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.topPanel",topPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.difficultyBox",difficultyBox));
        topPanel.add(centerPanel);
		instrum(129,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.topPanel",topPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.centerPanel",centerPanel));
        topPanel.add(topTimer);
		instrum(131,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.topPanel",topPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topTimer",topTimer));
        mainPanel.add(topPanel, BorderLayout.NORTH);
		instrum(133,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.mainPanel",mainPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.topPanel",topPanel));
        mainPanel.add(centerMidPanel, BorderLayout.CENTER);
		instrum(135,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.mainPanel",mainPanel),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.MinesweeperFrame.centerMidPanel",centerMidPanel));

        this.getContentPane().add(mainPanel, BorderLayout.NORTH);
		instrum(138,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.mainPanel",mainPanel));
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        instrum(147,"if",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.actionPerformed.event",event),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.INCREMENT",INCREMENT));
		if(event.getActionCommand().equals(INCREMENT))
        {
            time++;
			instrum(149,"PostFix",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.time",time));
        }else {instrum(152,"if",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.actionPerformed.event",event),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.RESET",RESET));if (event.getActionCommand().equals(RESET)){scoreTimer.stop();instrum(154,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.scoreTimer",scoreTimer));time=0;instrum(156,"Assign",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.time",time));instrum(161,"if",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.difficultyBox",difficultyBox));if (difficultyBox.getSelectedIndex() == 0){minePanel.setMinefield((new Minefield(9,9,10)));instrum(163,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel));} else {instrum(166,"if",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.difficultyBox",difficultyBox));if (difficultyBox.getSelectedIndex() == 2){minePanel.setMinefield((new Minefield(30,16,99)));instrum(167,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel));} else {instrum(170,"if",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.difficultyBox",difficultyBox));if (difficultyBox.getSelectedIndex() == 1){minePanel.setMinefield((new Minefield(16,16,40)));instrum(171,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.minePanel",minePanel));}}}pack();}}

        topTimer.setText((time) + " Seconds   ");
		instrum(178,"method call",new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.topTimer",topTimer),new Pair<>("uk.ac.york.minesweeper.MinesweeperFrame.time",time));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new MinesweeperFrame().setVisible(true);
            }
        });
    }
}
