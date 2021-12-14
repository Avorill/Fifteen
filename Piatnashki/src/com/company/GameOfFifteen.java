package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;



public class GameOfFifteen extends JFrame implements ActionListener {
    protected int  rows = 4;
    protected int col = 4;

    private JPanel panel = new JPanel(new GridLayout(rows, col,1,1));
    private Buttons [][] buttons;
    JLabel [][] labels = new JLabel[rows][col];
    private Font font = new Font("TimesRoman",Font.PLAIN,0);
    private Font MAIN_FONT = new Font("Arial", Font.BOLD + Font.ITALIC, 16);
    private Random random;
    JMenuBar menuBar;
    JMenu file;
    JMenuItem newGame;
    public int[][] board;
   // KeyHandler keyHandler = new KeyHandler(this);

    public GameOfFifteen(){

        board = new int[rows][col];
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.setBorder(null);
        file = new JMenu(" File");
        menuBar.add(file);
        newGame = new JMenuItem("New");
        newGame.addActionListener(this);
        newGame.setActionCommand("NewGame");
        file.add(newGame);
        initBoard();
    }
    public void initBoard(){

        buttons = new Buttons[rows][col];
        labels = new JLabel[rows][col];
        this.repaint();
        int count = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < col; j++){
                count += 1;
                int val = board[i][j];
                buttons[i][j] = new Buttons();
                labels[i][j] = new JLabel();
                String text = i+ "," + j;
                buttons[i][j].setText(text);
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                buttons[i][j].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        super.keyPressed(e);
                        Boolean flag = isSolve();
                        if(flag == false) {
                            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_LEFT) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 1; j < col; j++) {
                                        if (board[i][j - 1] == -1) {
                                            left(i, j);
                                        }
                                    }
                                }
                            }
                            if ( e.getKeyCode() == KeyEvent.VK_LEFT) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 1; j < col; j++) {
                                        if (board[i][j - 1] == -1) {
                                            left(i, j);
                                            break;
                                        }
                                    }
                                }
                            }
                            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col-1; j++) {
                                        if (board[i][j + 1] == -1 ) {
                                            right(i, j);
                                        }
                                    }
                                }
                            }
                            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col-1; j++) {
                                        while (board[i][j + 1] == -1 ) {
                                            right(i, j);

                                        }

                                    }

                                }
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col-1; j++) {
                                        if (board[i][j + 1] == -1 ) {
                                            right(i, j);
                                        }
                                    }
                                }
                            }
                            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_UP) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col; j++) {
                                        if (i-1>=0 && board[i-1][j]==-1) {
                                            up(i, j);
                                        }
                                    }
                                }
                            }
                            if ( e.getKeyCode() == KeyEvent.VK_UP) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col; j++) {
                                        if (i-1>=0 && board[i-1][j]==-1) {
                                            up(i, j);
                                            i=rows; j = col;

                                        }
                                    }
                                }
                            }
                            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col; j++) {
                                        if (i+1<rows && board[i+1][j]==-1) {
                                            down(i, j);
                                        }
                                    }
                                }
                            }
                            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_DOWN) {
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col; j++) {
                                        while (i+1<rows && board[i+1][j]==-1) {
                                            down(i, j);
                                        }
                                    }
                                }
                                for (int i = 0; i < rows; i++) {
                                    for (int j = 0; j < col; j++) {
                                        if (i+1<rows && board[i+1][j]==-1) {
                                            down(i, j);
                                        }
                                    }
                                }
                            }
                        }
                        recolor();
                        flag = isSolve();
                        if(flag == true)
                        {
                            JOptionPane.showMessageDialog(null, "You Win The Game.");
                        }
                    }
                });


                if(val != -1){

                    labels[i][j] = new JLabel(String.valueOf(val), JLabel.CENTER);
                    labels[i][j].setFont(MAIN_FONT);
                    labels[i][j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
                }
                else{
                    labels[i][j]= new JLabel("", JLabel.CENTER);
                    labels[i][j].setFont(MAIN_FONT);
                    labels[i][j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
                }

                buttons[i][j].add(labels[i][j]);


                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.black,2));

                panel.add(buttons[i][j]);
            }
        }
        recolor();
        add(panel);

        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dim.width/5,dim.width/5+10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void repaint(){
        random = new Random();
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        for(int i = 0; i < rows*col; i++){
              solutions.add(i+1);
                    }
        solutions.set(15, -1);
        for(int i = 0; i<rows*col; i++){
            int index =  random.nextInt(16);
            Collections.swap(solutions, i, index);
        }
        int count = 0;
        for(int i  = 0; i <rows; i++){
            for(int j = 0; j < col; j++ ){
                board[i][j] = solutions.get(count);
                count += 1;
                System.out.print(board[i][j] + "\t");
            }
            System.out.println(" ");
        }
    }
    Boolean isSolve(){
        int count = 1;
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<col;j++)
            {
                if(board[i][j]!=count && board[i][j]!=-1)
                {
                    return false;
                }
                count = count + 1;
            }
        }
        return true;
    }

    public void recolor(){
        int count = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < col; j++) {
                count += 1;
                int value = board[i][j];
                if (value == count) {
                    buttons[i][j].setBackground(Buttons.RIGHT);

                } else {
                    buttons[i][j].setBackground(Buttons.WRONG);
                }
            }
        }
    }
    public void down(int r, int c){
        String text = labels[r][c].getText();
        labels[r][c].setText("");
        labels[r+1][c].setText(text);
        int temp = board[r][c];
        board[r][c] = board[r+1][c];
        board[r+1][c] = temp;
    }
    public void up(int r, int c){
        String text = labels[r][c].getText();
        labels[r][c].setText("");
        labels[r-1][c].setText(text);
        int temp = board[r][c];
        board[r][c] = board[r-1][c];
        board[r-1][c] = temp;
    }
    public void right(int r, int c){
        String text = labels[r][c].getText();
        labels[r][c].setText("");
        labels[r][c+1].setText(text);
        int temp = board[r][c];
        board[r][c] = board[r][c+1];
        board[r][c+1] = temp;
    }
    public void left(int r, int c){
        String text = labels[r][c].getText();
        labels[r][c].setText("");
        labels[r][c-1].setText(text);
        int temp = board[r][c];
        board[r][c] = board[r][c-1];
        board[r][c-1] = temp;
    }
    public static void main(String[] args){
        new GameOfFifteen();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s == "NewGame"){
            panel.removeAll();
            initBoard();

        }
        else{
        Boolean flag = isSolve();
        if(flag == false){
             s = e.getActionCommand().toString();

            int r = Integer.parseInt(s.split(",")[0]);
            int c = Integer.parseInt(s.split(",")[1]);
            if(board[r][c] != -1){
                if(r+1<rows && board[r+1][c]==-1){ down(r,c); }
                else if(r-1>=0 && board[r-1][c]==-1){ up(r,c); }
                else if(c+1<col && board[r][c+1]==-1){right(r,c);}
                else if(c-1>=0 && board[r][c-1]==-1){left(r,c);}
            }
            recolor();
            flag = isSolve();
            if(flag == true)
            {
                JOptionPane.showMessageDialog(null, "You Win The Game.");
                panel.removeAll();
                initBoard();
            }
        }
    }
    }
}
