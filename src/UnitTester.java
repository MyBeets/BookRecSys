import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitTester {
    int pass;
    int total;
    UnitTester(){
        pass = 0;
        total = 0;
    }
    public boolean ConstructorTest(){
        reset();
        System.out.println("Constructor Test");
        //test 1 - everything needed
        Book b = new Book("a", "b", "c", new String[] {"d","e"});
        String s1 = "a by b published in c Genres: d, e";
        //System.out.println(b);
        if(b.toString().equals(s1)) pass++;
        total++;

        //test 2 - empty book
        Book empty = new Book();
        if(empty.getTitle() == null) pass++;
        total++;

        //test 3 - storage empty
        Storage emptyWarehouse = new Storage();
        try{emptyWarehouse.GenreBooks.get(0);}
        catch(Exception e){pass++;}
        total++;

        //test 4 - storage empty
        try{emptyWarehouse.GenreNames.get(0);}
        catch(Exception e){pass++;}
        total++;

        //test 5 - storage insert
        emptyWarehouse.GenreBooks.add(new HashMap());
        if(emptyWarehouse.GenreBooks.get(0)!=null) pass++;
        total++;

        //test 6 - storage insert
        emptyWarehouse.GenreNames.add("bingus");
        if(emptyWarehouse.GenreNames.get(0).equals("bingus")) pass++;
        total++;

        //return
        //System.out.println(pass + " " + total);
        return pass == total;
    }
    public boolean inputfromfileTest(){
        reset();
        System.out.println("Input From File Test");
        //test 1 - one line
        Storage storage1 = new Storage();
        String test1 = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\test1.txt";
        storage1.inputfromfile(test1);
        String s1 = "****genre1****\ntitle author\n" +
                "****genre2****\ntitle author\n";
        if(storage1.toString().equals(s1)) pass++;
        total++;

        //test 2 - longer file
        Storage storage2 = new Storage();
        String test2 = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\test2.txt";
        storage2.inputfromfile(test2);
        String s2 = "****genre3****\ntitle1 author\n" +
                "****genre4****\ntitle1 author\n" +
                "****genre5****\ntitle2 author\n" +
                "****genre6****\ntitle2 author\n";
        //System.out.println(storage2);
        if(storage2.toString().equals(s2)) pass++;
        total++;

        //test 3 - multiple books of the same genre
        Storage storage3 = new Storage();
        String test3 = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\test3.txt";
        storage3.inputfromfile(test3);
        String s3 = "****genre1****\ntitle1 author\n" +
                "****genre2****\ntitle1 author\n" +
                "title2 author\n"+
                "****genre3****\ntitle2 author\n";
        //System.out.println(storage3);
        if(storage3.toString().equals(s3)) pass++;
        total++;

        //test 4 - empty file
        Storage storage4 = new Storage();
        String testempty = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\empty.txt";
        try{storage4.inputfromfile(testempty);}
        catch(RuntimeException e){pass++;}
        total++;

        //test 5 - bad format
        String testbad = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\badform.txt";
        try{storage4.inputfromfile(testbad);}
        catch(RuntimeException e){pass++;}
        total++;

        //test 6 - two of the same book
        storage4.inputfromfile(test1);
        String first = storage4.toString();
        storage4.inputfromfile(test1);
        String second = storage4.toString();
        if(first.equals(second)) pass++;
        total++;

        //test 7 - multiple files
        storage4.inputfromfile(test2);
        String s7 = "****genre1****\ntitle author\n" +
                "****genre2****\ntitle author\n" +
                "****genre3****\ntitle1 author\n" +
                "****genre4****\ntitle1 author\n" +
                "****genre5****\ntitle2 author\n" +
                "****genre6****\ntitle2 author\n";
        //System.out.println(storage3);
        if(storage4.toString().equals(s7)) pass++;
        total++;

        //test 8 - multiple files where one of the titles overlap
        Storage sDoubles = new Storage();
        sDoubles.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\ltbooks.txt");
        sDoubles.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\booksinc.txt");
        String strDoubled = "****Dystopian****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Handmaid's Tale Margaret Atwood\n" +
                "1984 George Orwell\n" +
                "****Science Fiction****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Handmaid's Tale Margaret Atwood\n" +
                "1984 George Orwell\n" +
                "****Allegory****\n" +
                "Animal Farm George Orwell\n" +
                "****Satire****\n" +
                "Animal Farm George Orwell\n" +
                "****Fantasy****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Hobbit J.R.R. Tolkien\n" +
                "****Young Adult****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Romance****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Autobiography****\n" +
                "I Know Why the Caged Bird Sings Maya Angelou\n" +
                "****Biography****\n" +
                "I Know Why the Caged Bird Sings Maya Angelou\n" +
                "****Historical Fiction****\n" +
                "Beloved Toni Morrison\n" +
                "****Magical Realism****\n" +
                "Beloved Toni Morrison\n" +
                "****Classics****\n" +
                "Beloved Toni Morrison\n" +
                "****Literary Fiction****\n" +
                "Beloved Toni Morrison\n";
        if(sDoubles.toString().equals(strDoubled))pass++;
        total++;
        //System.out.println(sDoubles);

        //return
        //System.out.println(pass + " " + total);
        return pass==total;
    }
    public boolean InsertandRemoveTest(){
        reset();
        System.out.println("Insert and Remove Test");
        //** Insert Tests **//
        Storage lindentree = new Storage();
        lindentree.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\ltbooks.txt");
        //test 1 - insert new book
        lindentree.insert(new Book("Murder on the Orient Express", "Agatha Christie", "1934", new String[] {"Mystery", "Detective Fiction", "Crime Fiction"}));
        String newString = "****Dystopian****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Handmaid's Tale Margaret Atwood\n" +
                "1984 George Orwell\n" +
                "****Science Fiction****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Handmaid's Tale Margaret Atwood\n" +
                "1984 George Orwell\n" +
                "****Allegory****\n" +
                "Animal Farm George Orwell\n" +
                "****Satire****\n" +
                "Animal Farm George Orwell\n" +
                "****Fantasy****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Hobbit J.R.R. Tolkien\n" +
                "****Young Adult****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Romance****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Mystery****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Detective Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Crime Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n";
        if(lindentree.toString().equals(newString)) pass++;
        total++;

        //test 2 - inserting an existing book (object method)
        try{lindentree.insert(new Book("The Handmaid's Tale", "Margaret Atwood", "1985", new String[] {"Dystopian", "Science Fiction"}));}
        catch(Exception e){pass++;}
        total++;

        //**Remove Tests**//

        //test 3 - removing an existing book (string method)
        lindentree.remove("The Handmaid's Tale", "Margaret Atwood");
        String removedString = "****Dystopian****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "1984 George Orwell\n" +
                "****Science Fiction****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "1984 George Orwell\n" +
                "****Allegory****\n" +
                "Animal Farm George Orwell\n" +
                "****Satire****\n" +
                "Animal Farm George Orwell\n" +
                "****Fantasy****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "The Hobbit J.R.R. Tolkien\n" +
                "****Young Adult****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Romance****\n" +
                "The Hunger Games Suzanne Collins\n" +
                "****Mystery****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Detective Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Crime Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n";
        if(lindentree.toString().equals(removedString)) pass++;
        total++;

        //test 4 - removing an existing book (string method)
        lindentree.remove(new Book("The Hunger Games", "Suzanne Collins", "2008", new String[] {"Young Adult", "Fantasy", "Romance", "Science Fiction", "Dystopian"}));
        String removedAgainString = "****Dystopian****\n" +
                "1984 George Orwell\n" +
                "****Science Fiction****\n" +
                "1984 George Orwell\n" +
                "****Allegory****\n" +
                "Animal Farm George Orwell\n" +
                "****Satire****\n" +
                "Animal Farm George Orwell\n" +
                "****Fantasy****\n" +
                "The Hobbit J.R.R. Tolkien\n" +
                "****Young Adult****\n" +
                "****Romance****\n" +
                "****Mystery****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Detective Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n" +
                "****Crime Fiction****\n" +
                "Murder on the Orient Express Agatha Christie\n";
        if(lindentree.toString().equals(removedAgainString)) pass++;
        total++;

        //test 5 removing a book that's not there (object method)
        try{lindentree.remove(new Book("The Handmaid's Tale", "Margaret Atwood", "1985", new String[] {"Dystopian", "Science Fiction"}));}
        catch(Exception e){pass++;}
        total++;

        //test 6 removing a book that's not present (String method)
        try{lindentree.remove("The Handmaid's Tale", "Margaret Atwood");}
        catch(Exception e){pass++;}
        total++;

        //System.out.println(lindentree);
        return pass==total;
    }
    public boolean SearchTest(){
        reset();
        System.out.println("Search Test");
        //**Search by Title Tests**//
        Storage lindentree = new Storage();
        lindentree.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\ltbooks.txt");

        //test 1 search by title for a book that is present
        ArrayList<Book> handmaids = lindentree.searchByTitle("The Handmaid's Tale");
        if(handmaids.get(0).getTitle().equals("The Handmaid's Tale") && handmaids.get(0).getAuthor().equals("Margaret Atwood")&&handmaids.size()==1) pass++;
        total++;

        //test 2 search by title for a book that is present
        ArrayList<Book> hungergames = lindentree.searchByTitle("The Hunger Games");
        if(hungergames.get(0).getTitle().equals("The Hunger Games") && hungergames.get(0).getAuthor().equals("Suzanne Collins")&&hungergames.size()==1) pass++;
        total++;

        //test 3 search by title for a book that is not there
        try{lindentree.searchByTitle("error 404");}
        catch(Exception e){pass++;}
        total++;

        //test 4 search by contains
        ArrayList<Book> the = lindentree.searchByContains("the");
        if(the.get(0).getTitle().equals("The Hunger Games")&&the.get(1).getTitle().equals("The Handmaid's Tale")&&the.get(2).getTitle().equals("The Hobbit") && the.size()==3) pass++;
        total++;

        //test 5 search by contains - making sure it's not case sensitive
        Book bingusing = new Book("the bingusing", "pseud keller", "2023", new String[] {"cringe"});
        lindentree.insert(bingusing);
        ArrayList<Book> caseSens = lindentree.searchByContains("The");
        if(caseSens.get(3).getTitle().equals("the bingusing") && caseSens.size()==4)pass++;
        total++;

        //test 6 search by contains - for object not there
        try{lindentree.searchByContains("error 404");}
        catch (Exception e){pass++;}
        total++;

        //test 7 search by author
        ArrayList<Book> orwell = lindentree.searchByAuthor("George Orwell");
        if(orwell.get(0).getTitle().equals("1984") && orwell.get(1).getTitle().equals("Animal Farm")&&orwell.size()==2) pass++;
        total++;

        //test 8 search by author - dne
        try{lindentree.searchByAuthor("James Baldwin");}
        catch(Exception e){ pass++;}
        total++;

        //test 9 search by author - case
        ArrayList<Book> orwellCase = lindentree.searchByAuthor("george orwell");
        if(orwellCase.get(0).getTitle().equals("1984") && orwellCase.get(1).getTitle().equals("Animal Farm")&&orwellCase.size()==2) pass++;
        total++;

        //test 10 search by genre
        ArrayList<Book> dystopian = lindentree.searchByGenre("Dystopian");
        if(dystopian.get(0).getTitle().equals("The Hunger Games")
        && dystopian.get(1).getTitle().equals("The Handmaid's Tale")
        && dystopian.get(2).getTitle().equals("1984")
        && dystopian.size()==3) pass++;
        total++;

        //test 11 search by genre case sensitive
        try{lindentree.searchByGenre("dystopian");}
        catch(Exception e){pass++;}
        total++;

        //test 12 search by genre - dne
        try{lindentree.searchByGenre("Novellas About Dogs");}
        catch(Exception e){pass++;}
        total++;

        //test 13 search by genre - only one book in genre
        lindentree.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\booksinc.txt");
        ArrayList<Book> LitFic = lindentree.searchByGenre("Literary Fiction");
        if(LitFic.size()==1 && LitFic.get(0).getTitle().equals("Beloved")) pass++;
        total++;

        return pass==total;
    }
    public boolean RecommendTest(){
        reset();
        return pass==total;
    }
    public void reset(){
        pass = 0;
        total = 0;
    }
    public String toString(){
        String out = "";
        out += pass +"/"+total+" tests passed";
        return out;
    }

}
