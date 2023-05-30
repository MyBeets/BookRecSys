import java.io.File;
public class Main {
    public static void main(String[] args) {
        //String filename = "C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\ltbooks.txt";
        UnitTester tester = new UnitTester();
        tester.ConstructorTest();
        System.out.println(tester);
        System.out.println();

        tester.inputfromfileTest();
        System.out.println(tester);
        System.out.println();

        tester.InsertandRemoveTest();
        System.out.println(tester);
        System.out.println();

        tester.SearchTest();
        System.out.println(tester);
        System.out.println();

        /*
        System.out.println();
        System.out.println("Linden Tree Books");
        Storage lindentree = new Storage();
        lindentree.inputfromfile("C:\\Users\\isatr\\IdeaProjects\\BookRecSys\\src\\ltbooks.txt");
        System.out.println(lindentree);
        */
    }
}