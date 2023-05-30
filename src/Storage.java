import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Storage {
    ArrayList<HashMap<String, Book>> GenreBooks;
    ArrayList<String> GenreNames;
    Storage(){
        GenreBooks = new ArrayList<HashMap<String, Book>>();
        GenreNames = new ArrayList<String>();
    }
    public void inputfromfile(String filename){
        try{
            BufferedReader br = new BufferedReader( new FileReader(filename));
            String line = br.readLine();
            if(line==null){throw new RuntimeException("Could not input from file. This file is empty");}
            while(line !=null){

                //evil line manipulation
                String[] splitLine = line.split(";; ");
                //System.out.println("evil line manipulation");

                //make genre array
                int end = splitLine.length;
                String[] genres = new String[(end)-3];
                //System.out.println(splitLine.length);
                for(int i = 3; i<end; i++){
                    genres[i-3] = splitLine[i];
                }
                //System.out.println("make the genre array");

                //make and store book object
                try{insert( new Book(splitLine[0], splitLine[1], splitLine[2], genres));}
                catch (Exception e){}
                //System.out.println("make and store book object");

                //get next line
                line = br.readLine();
                //System.out.println("get next line");

            }
        }
        catch(FileNotFoundException e){throw new RuntimeException("there is something wrong with the file location");}
        catch(IOException e){throw new RuntimeException("for some reason it cant read the lines");}
        catch(NegativeArraySizeException e){throw new RuntimeException("there is a problem with the text formatting on the file");}
    }
    public void insert(Book b){
        String title = b.getTitle();
        String author = b.getAuthor();
        String[] genre = b.getGenre();
        for(var g: genre){
            //System.out.println(g);
            //if the genre doesn't exist in the database this will add
            //it to the GenreNames list and make a HashMap for it to
            //store the books in that genre, this HashMap will be in the
            //same index as the Name it corresponds to
            if(GenreNames.isEmpty() || !GenreNames.contains(g)){
                //System.out.println("w");
                GenreNames.add(g);

                GenreBooks.add(new HashMap<String, Book>());
                //System.out.println("new hashmap made for " + g);
            }

            //gets the index of the HashMap for the corresponding genre
            int idx = GenreNames.indexOf(g);
            if(GenreBooks.get(idx).get(title+" " +author)!=null){
                throw new RuntimeException("This books has been added previously");
            }
            //adds the book to said HashMap
            GenreBooks.get(idx).put(title+" " +author, b);
            //System.out.println(title + " added");
        }
    }
    public void remove(Book b){
        String title = b.getTitle();
        String author = b.getAuthor();
        String key = title + " " + author;
        if(!searchByKey(key)) throw new RuntimeException("Book Not Found");
        for(String genre : b.getGenre()){
            if(this.GenreNames.contains(genre)){
                int idx = GenreNames.indexOf(genre);
                GenreBooks.get(idx).remove(key);
            }
        }

    }
    public void remove(String title, String author){
        String key = title + " " + author;
        if(!searchByKey(key)) throw new RuntimeException("Book Not Found");
        for(String genre : this.GenreNames){
            int idx = GenreNames.indexOf(genre);
            if(GenreBooks.get(idx).containsKey(key)){
                GenreBooks.get(idx).remove(key);
            }
        }
    }

    private boolean searchByKey(String key){
        for(var hash:GenreBooks){
            if(hash.containsKey(key)) return true;
        }
        return false;
    }

    public ArrayList<Book> searchByTitle(String title){
        title = title.toLowerCase();
        ArrayList<Book> output = new ArrayList<Book>();
        for(int i = 0; i<GenreBooks.size(); i++){
            Book[] Books = GenreBooks.get(i).values().toArray(new Book[0]);
            for(Book b : Books){
                //System.out.println(b.getTitle());
                //if we find the book
                if(b.getTitle().toLowerCase().equals(title)){
                    //then we check if it's already in the list
                    if(output.contains(b)==false) output.add(b);
                }
            }
        }
        if(output.isEmpty())throw new RuntimeException("Book Not Found");
        return output;
    }

    public ArrayList<Book> searchByContains(String keyword){
        keyword = keyword.toLowerCase();
        ArrayList<Book> output = new ArrayList<Book>();
        for(int i = 0; i<GenreBooks.size(); i++){
            Book[] Books = GenreBooks.get(i).values().toArray(new Book[0]);
            for(Book b : Books){
                //System.out.println(b.getTitle());
                //if we find the book
                if(b.getTitle().toLowerCase().contains(keyword)){
                    //then we check if it's already in the list
                    if(output.contains(b)==false) output.add(b);
                }
            }
        }
        if(output.isEmpty()) throw new RuntimeException("Book Not Found");
        return output;
    }

    public ArrayList<Book> searchByAuthor(String author){
        author = author.toLowerCase();
        ArrayList<Book> output = new ArrayList<Book>();
        for(int i = 0; i<GenreBooks.size(); i++){
            Book[] Books = GenreBooks.get(i).values().toArray(new Book[0]);
            for(Book b : Books){
                //System.out.println(b.getTitle());
                //if we find the book
                if(b.getAuthor().toLowerCase().equals(author)){
                    //then we check if it's already in the list
                    if(output.contains(b)==false) output.add(b);
                }
            }
        }
        if(output.isEmpty()) throw new RuntimeException("Author Not Found");
        return output;
    }

    public ArrayList<Book> searchByGenre(String genre){
        int idx = GenreNames.indexOf(genre);
        if(idx == -1){throw new RuntimeException("Genre Not Found");}
        ArrayList<Book> output = new ArrayList<Book>();
        Book[] Books = GenreBooks.get(idx).values().toArray(new Book[0]);
        for(Book b : Books){
            output.add(b);
        }
        return output;
    }

    public ArrayList<Book> recommendByTitle(String title){
        ArrayList<Book> output = new ArrayList<Book>();
        return output;
    }

    public ArrayList<Book> recommendByGenre(String genre){
        ArrayList<Book> output = new ArrayList<Book>();
        return output;
    }

    public String toString(){
        String out = "";
        for(int i = 0; i < GenreNames.size(); i++){
            //print genre name
            out+="****"+ GenreNames.get(i)+"****\n";
            //make an array of the book titles in the hashmap
            Object[] Books = GenreBooks.get(i).keySet().toArray();
            //print those books titles
            for(int j=0; j< Books.length; j++){
                out+= Books[j].toString() + "\n";
            }
        }
        return out;
    }
}
