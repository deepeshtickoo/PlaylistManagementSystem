// Deepesh Tickoo - 2093372
// Karthik Narsimha Reddy - 2001192
// Navdeep Kaur - 2024569

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedList;


public class SongManager {

    JFrame frame;
    JButton buttonLoad, buttonId, buttonTitle, buttonArtist, buttonDuration, buttonAscending, buttonDescending, buttonAddS, buttonMoveUp, buttonMoveDown, buttonSave;
    JLabel labelFilLoc, labelOrderBy, labelSortBy, labelMsg;
    JTextField title, artist, duration;
    TextPrompt titleTP, artistTP, durationTP;

    LinkedList<String> lsFilePath = new LinkedList<>();
    LinkedList<String> lsFileName = new LinkedList<String>();
    JComboBox cb = new JComboBox(lsFileName.toArray());

    JList<Song> list;
    DefaultListModel<Song> model;
    JPanel panel;
    LinkedList<Song> playlist;
    File file;
    String str;
    int orderBy;
    Color c = new Color(255, 0, 0);

    public void moveUp() {
        if (list.getSelectedIndex()>0) {
            int i = list.getSelectedIndex();
            Song so = model.get(list.getSelectedIndex());
            model.removeElementAt(list.getSelectedIndex());
            model.add(i-1, so);

            list.setSelectedIndex(i-1);
        }
    }

    public void moveDown() {
        if (list.getSelectedIndex()<model.size()-1) {
            int i = list.getSelectedIndex();
            Song so = model.get(list.getSelectedIndex());
            model.removeElementAt(list.getSelectedIndex());
            model.add(i + 1, so);

            list.setSelectedIndex(i+1);
        }
    }

    public SongManager() {

        frame = new JFrame("Song Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(515, 700);
        frame.setLayout(null);

        buttonLoad = new JButton("Select file");
        labelFilLoc = new JLabel(str);
        labelOrderBy = new JLabel("Order by ::");
        buttonId = new JButton("ID");
        buttonTitle = new JButton("Title");
        buttonArtist = new JButton("Artist");
        buttonDuration = new JButton("Duration");
        labelSortBy = new JLabel("Sort by ::");
        buttonAscending = new JButton("Ascending");
        buttonDescending = new JButton("Descending");
        labelMsg = new JLabel("<html> Use BACKSPACE to delete</html>");
        labelMsg.setForeground(Color.red); labelMsg.setFont(new Font("Ariel", Font.PLAIN, 12)); labelMsg.setVisible(true);

        list = new JList<>();
        model = new DefaultListModel<>();
        list.setModel(model);
        list.setFixedCellHeight(17);

        buttonAddS = new JButton("Add Song");
        buttonMoveUp = new JButton("1-UP");
        buttonMoveDown = new JButton("1-Down");
        buttonSave = new JButton("Save as CSV");

        title = new JTextField();
        titleTP = new TextPrompt("Title", title);
        artist = new JTextField();
        artistTP = new TextPrompt("Artist", artist);
        duration = new JTextField();
        durationTP = new TextPrompt("Duration", duration);

        playlist = new LinkedList<>();
        str = "No File Selected";

        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                }
                str = file.getAbsolutePath();
                lsFileName.add(file.getName());
                lsFilePath.add(file.getAbsolutePath());
                cb.addItem(file.getName());
                playlist = SongLoader.getPlaylist(str);
            }
        });

        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cb.getSelectedItem();
                labelFilLoc.setText(lsFilePath.get(lsFileName.indexOf(selectedItem)));
                SongLoader.getPlaylist(lsFilePath.get(lsFileName.indexOf(selectedItem)));
                model.removeAllElements();
                for (int i = 0; i < SongLoader.getPlaylist(lsFilePath.get(lsFileName.indexOf(selectedItem))).size() ; i++) {
                    model.addElement(SongLoader.getPlaylist(lsFilePath.get(lsFileName.indexOf(selectedItem))).get(i));
                }
            }
        });

        buttonId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderBy = 1;
            }
        });

        buttonTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderBy = 2;
            }
        });

        buttonArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderBy = 3;
            }
        });

        buttonDuration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderBy = 4;
            }
        });

        buttonAscending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeAllElements();
                if (orderBy == 1){
                    playlist.sort(new SortbyId_Ascending());

                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }

                }
                else if (orderBy == 2){
                    playlist.sort(new SortbyTitle_Ascending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
                else if (orderBy == 3){
                    playlist.sort(new SortbyArtist_Ascending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
                else if(orderBy == 4){
                    playlist.sort(new SortbyDuration_Ascending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
            }
        });

        buttonDescending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeAllElements();
                if (orderBy == 1){
                    playlist.sort(new SortbyId_Descending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
                else if (orderBy == 2){
                    playlist.sort(new SortbyTitle_Descending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
                else if (orderBy == 3){
                    playlist.sort(new SortbyArtist_Descending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
                else if(orderBy == 4){
                    playlist.sort(new SortbyDuration_Descending());
                    for(int i=0 ; i<playlist.size() ; i++) {
                        model.addElement(playlist.get(i));
                    }
                }
            }
        });

        buttonAddS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Song s = new Song(title.getText(), artist.getText(), Integer.parseInt(duration.getText()));
                playlist.add(s);
                model.addElement(s);
                title.setText("");
                artist.setText("");
                duration.setText("");
            }
        });

        buttonMoveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveUp();
            }
        });

        buttonMoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    SongLoader.saveCSV(playlist, fileChooser.getSelectedFile().getAbsolutePath());
                }
                String s = fileChooser.getSelectedFile().getName().concat(".csv");
                cb.addItem(s);
                lsFileName.add(s);
                lsFilePath.add(fileChooser.getSelectedFile().getAbsolutePath().concat(".csv"));
            }
        });

        list.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    model.removeElementAt(list.getSelectedIndex());
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        buttonLoad.setBounds(30, 40, 100, 30);
        cb.setBounds(140, 40, 100, 30);
        labelFilLoc.setBounds(250, 40, 365, 30);
        labelOrderBy.setBounds(30, 80, 100, 30);
        buttonId.setBounds(30, 120, 100, 30);
        buttonTitle.setBounds(140, 120, 100, 30);
        buttonArtist.setBounds(250, 120, 100, 30);
        labelMsg.setBounds(30, 246, 150, 14);
        buttonDuration.setBounds(360, 120, 100, 30);
        labelSortBy.setBounds(30, 160, 100, 30);
        buttonAscending.setBounds(30, 200, 100, 30);
        buttonDescending.setBounds(140, 200, 100, 30);
        list.setBounds(30, 260, 450, 310);
        buttonAddS.setBounds(30, 580, 100, 30);
        buttonMoveUp.setBounds(140, 580, 100, 30);
        buttonMoveDown.setBounds(250, 580, 100, 30);
        buttonSave.setBounds(360, 580, 100, 30);
        title.setBounds(30, 615, 100, 30);
        titleTP.setForeground(Color.gray);
        artist.setBounds(140, 615, 100, 30);
        artistTP.setForeground(Color.GRAY);
        duration.setBounds(250, 615, 100, 30);
        durationTP.setForeground(Color.GRAY);

        frame.add(buttonLoad);
        frame.add(cb);
        frame.add(labelFilLoc);
        frame.add(labelOrderBy);
        frame.add(buttonId);
        frame.add(buttonTitle);
        frame.add(buttonArtist);
        frame.add(buttonDuration);
        frame.add(labelSortBy);
        frame.add(buttonAscending);
        frame.add(buttonDescending);
        frame.add(list);
        frame.add(buttonAddS);
        frame.add(buttonMoveUp);
        frame.add(buttonMoveDown);
        frame.add(buttonSave);
        frame.add(title);
        frame.add(artist);
        frame.add(duration);
        frame.add(labelMsg);

        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SongManager songManager = new SongManager();
    }



}
