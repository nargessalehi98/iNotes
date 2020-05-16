package ceit.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Provide program window
 */
public class CFrame extends JFrame implements ActionListener {

    //creat a main panel to keep list and text area
    private CMainPanel mainPanel;
    //creat a menu Items
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;


    /**
     * creat a new CFrame with given name
     * @param title name
     */
    public CFrame(String title) {
        super(title);

        initMenuBar(); //create menuBar

        initMainPanel(); //create main panel
    }

    /**
     * creat menuBar
     */
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("File");

        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        newItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        jmenu.add(newItem);
        jmenu.add(saveItem);
        jmenu.add(exitItem);

        menuBar.add(jmenu);
        setJMenuBar(menuBar);
    }

    /**
     * creat main panel
     */
    private void initMainPanel() {
        //creat a new main panel
        mainPanel = new CMainPanel();
        //add to CFrame
        add(mainPanel);
    }

    @Override
    /**
     * set each action for each item in menu bar
     */
    public void actionPerformed(ActionEvent e) {
        //new item add a new tab
        if (e.getSource() == newItem) {
            mainPanel.addNewTab();
        } else if (e.getSource() == saveItem) { //save selected item
            try {
                mainPanel.saveNote();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == exitItem) { //exit program and save all tabs
            //TODO: Phase1: check all tabs saved ... :done
            JTabbedPane jTabbedPane= (JTabbedPane) mainPanel.getComponent(1);

            for(int num=0 ;num<jTabbedPane.getTabCount();num++){
                try {
                    mainPanel.saveNote();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            System.exit(0);
        }
        else {
            System.out.println("Nothing detected...");
        }
    }
}
