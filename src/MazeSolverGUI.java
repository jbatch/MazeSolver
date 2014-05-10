//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MazeSolverGUI extends JFrame
{
        JPanel appletPanel = new JPanel();
        JPanel topPanel = new JPanel();
        MazePanel mazePanel = new MazePanel(12, 10);
        JButton loadBtn = new JButton("Load");
        JButton startStackBtn = new JButton("Start (stack)");
        JButton startQueueBtn = new JButton("Start (queue)");
        JButton stepBtn = new JButton("Step");
        JButton toggleAnimationBtn = new JButton("Toggle Animation");
        JTextField mazePathFld = new JTextField(5);
        JLabel statusLbl = new JLabel("Maze not yet loaded");
        Timer timer;
        char mode = '-';

        public MazeSolverGUI(){
                SwingUtilities.invokeLater(new Runnable(){
                        public void run() {
                                initUI();
                        }});
        }

        public final void initUI(){
                GroupLayout layout = new GroupLayout(topPanel);
                topPanel.setBackground(Color.RED);
                mazePanel.setBackground(Color.BLACK);
                topPanel.setLayout(layout);
                layout.setAutoCreateGaps(true);
                layout.setAutoCreateContainerGaps(true);

                mazePathFld.setText("mazes/test.maze");
                stepBtn.setEnabled(false);
                startQueueBtn.setEnabled(false);
                startStackBtn.setEnabled(false);
                toggleAnimationBtn.setEnabled(false);

                /*Setting up the group layout for the topPanel*/
                layout.setHorizontalGroup(
                        layout.createSequentialGroup()
                        .addComponent(mazePathFld)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addComponent(loadBtn)
                                  .addComponent(stepBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addComponent(startStackBtn)
                                  .addComponent(toggleAnimationBtn))
                        .addComponent(startQueueBtn));

                layout.setVerticalGroup(
                        layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                  .addComponent(mazePathFld)
                                  .addComponent(loadBtn)
                                  .addComponent(startStackBtn)
                                  .addComponent(startQueueBtn))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                  .addComponent(stepBtn)
                                  .addComponent(toggleAnimationBtn)));

                //Event Listeners
                loadBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        loadButtonEvent();
                                }
                        });
                stepBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        stepButtonEvent();
                                }
                        });
                startQueueBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        queueButtonEvent();
                                }
                        });
                startStackBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        stackButtonEvent();
                                }
                        });
                toggleAnimationBtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        animationButtonEvent();
                                }
                        });

                appletPanel.setLayout(new BoxLayout(appletPanel, BoxLayout.Y_AXIS));
                appletPanel.add(topPanel);
                appletPanel.add(statusLbl);
                appletPanel.add(mazePanel);
                pack();

                setVisible(true);
                setTitle("Maze Solver by Josh Batchelor");
                setSize(500, 500);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                add(appletPanel);
        }

        private void loadButtonEvent() {
                System.out.println("Load Button Pressed!");
                //mazePanel = new MazePanel(12, 10);
                mazePanel.loadMaze(mazePathFld.getText());
                mazePanel.repaint();
                statusLbl.setText("Maze Loaded");
                startQueueBtn.setEnabled(true);
                startStackBtn.setEnabled(true);
                toggleAnimationBtn.setEnabled(false);
                stepBtn.setEnabled(false);
                mode ='-';
                timer = new Timer(250, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        stepButtonEvent();
                                }
                        });
        }

        private void stepButtonEvent () {
                switch(mode) {
                case 's':
                        mazePanel.solver.stepStack();
                        if(mazePanel.solver.stack.isEmpty()) {
                                stepBtn.setEnabled(false);
                                if(mazePanel.solver.goalFound) {
                                        statusLbl.setText("Solution Found");
                                }
                                else {
                                        statusLbl.setText("Solution Not Found");
                                }

                                toggleAnimationBtn.setEnabled(false);
                                timer.stop();
                        }
                        break;
                case 'q': mazePanel.solver.stepQueue();
                        if(mazePanel.solver.queue.isEmpty()) {
                                stepBtn.setEnabled(false);
                                if(mazePanel.solver.goalFound) {
                                        statusLbl.setText("Solution Found");
                                }
                                else {
                                        statusLbl.setText("Solution Not Found");
                                }
                                toggleAnimationBtn.setEnabled(false);
                                timer.stop();
                        }
                        break;
                default: break;
                }
                mazePanel.validate();
                mazePanel.repaint();
        }

        private void stackButtonEvent() {
                stepBtn.setEnabled(true);
                startQueueBtn.setEnabled(false);
                startStackBtn.setEnabled(false);
                toggleAnimationBtn.setEnabled(true);
                mode ='s';
                statusLbl.setText("Stack search in progress");
        }

        private void queueButtonEvent() {
                startStackBtn.setEnabled(false);
                startQueueBtn.setEnabled(false);
                toggleAnimationBtn.setEnabled(true);
                stepBtn.setEnabled(true);
                mode = 'q';
                statusLbl.setText("Queue search in progress");
        }

        private void animationButtonEvent () {
                if(timer.isRunning()){
                        timer.stop();
                }
                else {
                        timer.start();
                }
        }

        private class MazePanel extends JPanel {

                private static final int YOFFSET = 30, XOFFSET = 50;

                Solver solver;

                Square[][] squareArray;

                public void loadMaze(String fileName) {
                        solver = new Solver(fileName);
                        for(int i=0;i<squareArray.length;i++) {
                                for(int j=0;j<squareArray[0].length;j++) {
                                        try {
                                                squareArray[i][j] = new Square(32*j+XOFFSET, 32*i+YOFFSET, solver.mazeArray[i][j]);
                                        }
                                        catch(IOException e){System.out.println("Could not open square");}

                                }
                        }
                }

                public MazePanel(int width, int height) {
                        setDoubleBuffered(true);
                        squareArray = new Square[height][width];
                        solver = new Solver();
                        for(int i=0;i<height;i++) {
                                for(int j=0;j<width;j++) {
                                        try {
                                                squareArray[i][j] = new Square(32*j+XOFFSET, 32*i+YOFFSET, solver.mazeArray[i][j]);
                                        }
                                        catch(IOException e){System.out.println("Could not open square");}

                                }
                        }
                        setBorder(BorderFactory.createLineBorder(Color.white));
                }

                public void paintComponent(Graphics g) {
                        for(int i=0;i<squareArray.length;i++) {
                                for(int j=0;j<squareArray[0].length;j++) {
                                        squareArray[i][j].setTile(solver.charAtPoint(j,i));
                                        squareArray[i][j].paintSquare(g);
                                }
                        }
                }


        }
}
