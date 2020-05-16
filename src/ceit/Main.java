package ceit;

import ceit.gui.CFrame;

import javax.swing.*;

/**
 * Run the program
 */
public class Main {

    public static void main(String[] args) {
        //creat a CFrame
        CFrame frame = new CFrame("iNote");
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
