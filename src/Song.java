// Deepesh Tickoo - 2093372
// Karthik Narsimha Reddy - 2001192
// Navdeep Kaur - 2024569

import java.util.Objects;
import java.util.Comparator;

public class Song implements Comparable<Song>{

    private int id = 1;
    String title, artist;
    int duration;
    static int idCount = 0;

    public int getId() { return this.id; }

    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return this.artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getDuration() { return this.duration; }
    public void setDuration(int duration) { this.duration = duration; }


    //parameterized constructor 1
    public Song(int id, String title, String artist, int duration) {
        this.id = id;
        setTitle(title);
        setArtist(artist);
        setDuration(duration);
    }

    //parameterized constructor 2
    public Song(String title, String artist, int duration) {
        setCounter();
        this.id = idCount;
        setTitle(title);
        setArtist(artist);
        setDuration(duration);

    }

    //method to set id to increment last id from loaded csv file
    public void setCounter() {
        if (SongLoader.songList != null) { SongLoader.songList.sort(new SortbyId_Ascending()); idCount = SongLoader.songList.getLast().getId() + 1; }
        else idCount++;
    }

    @Override
    public int compareTo(Song otherSong) {
        return Integer.compare(getId(), otherSong.getId());
    }

    @Override
    public String toString() {
        return String.format("%-5d%-35s%-25s%-5d seconds%n", getId(), getTitle(), getArtist(), getDuration());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Song anotherSong = (Song)o;
        return Objects.equals(anotherSong.title, this.title) && Objects.equals(anotherSong.artist, this.artist) && this.duration == anotherSong.duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, duration);
    }

}

//Comparators
class SortbyId_Ascending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return Integer.compare(s1.getId(), s2.getId()); }
}

class SortbyId_Descending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return Integer.compare(s2.getId(), s1.getId()); }
}

class SortbyTitle_Ascending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return s1.getTitle().compareToIgnoreCase(s2.getTitle()); }
}

class SortbyTitle_Descending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return s2.getTitle().compareToIgnoreCase(s1.getTitle()); }
}

class SortbyArtist_Ascending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return s1.getArtist().compareToIgnoreCase(s2.getArtist()); }
}

class SortbyArtist_Descending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return s2.getArtist().compareToIgnoreCase(s1.getArtist()); }
}

class SortbyDuration_Ascending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return Integer.compare(s1.getDuration(), s2.getDuration()); }
}

class SortbyDuration_Descending implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) { return Integer.compare(s2.getDuration(), s1.getDuration()); }
}