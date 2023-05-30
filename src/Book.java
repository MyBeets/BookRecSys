import java.lang.Class;
public class Book {
    private String Title;
    private String Author;
    private String PubYear;
    private String[] Genre;
    Book(){

    }
    Book(String Title, String Author, String PubYear, String[] Genre){
        this.Title = Title;
        this.Author = Author;
        this.PubYear = PubYear;
        this.Genre = new String[Genre.length];
        for(int i =0; i< Genre.length; i++){
            this.Genre[i] = Genre[i];
        }
    }
    public String getTitle(){
        return Title;
    }
    public String getAuthor(){
        return Author;
    }

    public String getPubYear(){
        return PubYear;
    }
    public String[] getGenre(){
        return Genre;
    }

    public String toString(){
        String out = "";
        out += Title + " by " + Author + " published in " + PubYear + " Genres:";
        for(int i = 0; i<Genre.length-1; i++) out+=" "+Genre[i]+",";
        out+=" "+Genre[Genre.length-1];
        return out;
    }
}
