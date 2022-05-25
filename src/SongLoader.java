// Deepesh Tickoo - 2093372
// Karthik Narsimha Reddy - 2001192
// Navdeep Kaur - 2024569

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class SongLoader {

     static String[] elements;
     static String filePath;
     static LinkedList<Song> songList;

    //constructor
    public SongLoader() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter complete local address of the playlist's CSV file: ");
        filePath = in.next();
        getPlaylist(filePath);
    }

    public static LinkedList<Song> getPlaylist(String filePath) {

        songList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                elements = line.split(",");
                Song s = new Song(Integer.parseInt(elements[0]), elements[1], elements[2], Integer.parseInt(elements[3]));
                //Song.idCount++;
                songList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songList;
    }

    public static void saveCSV(LinkedList<Song> songList, String filePath) {

        File file = new File(filePath+".csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            for (int i = 0 ; i < songList.size() ; i++) {
                outputfile.append(String.join(",", Integer.toString(songList.get(i).getId()), songList.get(i).getTitle(), songList.get(i).getArtist(), Integer.toString(songList.get(i).getDuration())));
                outputfile.append("\n");
            }
            outputfile.flush();
            outputfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
