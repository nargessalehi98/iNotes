package ceit.gui;

import ceit.utils.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Provide panel with list and text area
 */
public class CMainPanel extends JPanel {
    //creat a tabbedPane to open and write notes
    private JTabbedPane tabbedPane;
    //creat a j list to keep notes
    private JList<File> directoryList;

    /**
     * creat new Main Panel
     */
    public CMainPanel() {

        setLayout(new BorderLayout());

        initDirectoryList(); // add JList to main Panel

        initTabbedPane(); // add TabbedPane to main panel

        addNewTab(); // open new empty tab when user open the application
    }

    /**
     * creat tabbedPane and set layout
     */
    private void initTabbedPane() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * creat
     */
    private void initDirectoryList() {
        File[] files = FileUtils.getFilesInDirectory();
        directoryList = new JList<>(files);

        directoryList.setBackground(new Color(211, 211, 211));
        directoryList.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        directoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        directoryList.setVisibleRowCount(-1);
        directoryList.setMinimumSize(new Dimension(130, 100));
        directoryList.setMaximumSize(new Dimension(130, 100));
        directoryList.setFixedCellWidth(130);
        directoryList.setCellRenderer(new MyCellRenderer());
        directoryList.addMouseListener(new MyMouseAdapter());

        add(new JScrollPane(directoryList), BorderLayout.WEST);
    }


    /**
     * Add a new tab
     */
    public void addNewTab() {
        JTextArea textPanel = createTextPanel();
        textPanel.setText("Write Something here...");
        tabbedPane.addTab("Tab " + (tabbedPane.getTabCount() + 1), textPanel);
    }

    /**
     * creat
     *
     * @param content
     */
    public void openExistingNote(String content) {
        JTextArea existPanel = createTextPanel();
        existPanel.setText(content);

        int tabIndex = tabbedPane.getTabCount() + 1;
        tabbedPane.addTab("Tab " + tabIndex, existPanel);
        tabbedPane.setSelectedIndex(tabIndex - 1);
    }

    public void saveNote() throws IOException {
        JTextArea textPanel = (JTextArea) tabbedPane.getSelectedComponent();
        String note = textPanel.getText();
        if (!note.isEmpty()) {  //3 methods is available - uncomment them
            // FileUtils.fileWriter(note);
            // FileUtils.fileWriter2(note);
            FileUtils.writeObject(note);
            // ----------------------------------------------------------------------------------------------------------
        }
        updateListGUI();
    }

    /**
     * @return
     */
    private JTextArea createTextPanel() {
        JTextArea textPanel = new JTextArea();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return textPanel;
    }

    /**
     * Update GUI - JList
     */
    private void updateListGUI() {
        File[] newFiles = FileUtils.getFilesInDirectory();
        directoryList.setListData(newFiles);
    }


    /**
     * Control Mouse event on jList
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent eve) {
            // Double-click detected
            if (eve.getClickCount() == 2) {
                int index = directoryList.locationToIndex(eve.getPoint());
                System.out.println("Item " + index + " is clicked...");
                //TODO: Phase1: Click on file is handled... Just load content into JTextArea :done
                File curr[] = FileUtils.getFilesInDirectory();
                String content = null;
                try { //3 methods is available - uncomment them
                    //content = FileUtils.fileReader(curr[index]);
                    // content = FileUtils.fileReader2(curr[index]);
                    content = FileUtils.readObject(curr[index]);
                    //----------------------------------------------------------------------------------------------------------
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openExistingNote(content);
            }
        }
    }


    /**
     * Control selected item and disSelected
     */
    private class MyCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object object, int index, boolean isSelected, boolean cellHasFocus) {
            if (object instanceof File) {
                File file = (File) object;
                setText(file.getName());
                setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }
                setEnabled(list.isEnabled());
            }
            return this;
        }
    }
}
