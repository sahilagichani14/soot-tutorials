package upb.thesis.RQ1.JB_TT;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class JB_TT {

    void tc1(){
        try {
            int a = 8 / 0;
            int b = a + 2;
        } catch (ArithmeticException ex) {

        }
    }

    void tc2(){
        try {
            int[] arr = new int[2];
            arr[5] = 10;
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
    }

    void tc3(){
        try {
            try {
                int result = 10 / 0;
            } catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e.getMessage());
            }
            int[] arr = new int[2];
            arr[5] = 10;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Outer catch: " + e.getMessage());
        }
    }

    void tc4(){
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        } finally {
            System.out.println("Finally block executed");
        }
    }

    void tc5(){
        throw new IllegalArgumentException("This is a thrown exception");
    }

    void tc6() throws FileNotFoundException, IOException {
        FileReader file = new FileReader("nonexistentfile.txt");
    }

    void tc7(){
        try {
            throw new CustomException("This is a custom exception");
        } catch (CustomException e) {
            System.out.println("Caught CustomException: " + e.getMessage());
        }
    }
    public class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }
    }

    void tc8(){
        try (FileReader file = new FileReader("nonexistentfile.txt")) {
            // Some file operations
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }

    void tc9() throws IOException, SQLException {
        if (new Random().nextBoolean()) {
            throw new IOException("Random IOException");
        } else {
            throw new SQLException("Random SQLException");
        }
    }

    void tc10() {
        try {
            try {
                throw new IOException("Inner IOException");
            } catch (IOException e) {
                System.out.println("Caught IOException: " + e.getMessage());
                throw e;  // Rethrow the exception
            }
        } catch (IOException e) {
            System.out.println("Caught rethrown IOException: " + e.getMessage());
        }
    }

    void tc11(){
        try{
            int a = 5 / 0;
            String x = "tc";
        } finally {
            System.out.println("No Catch Block");
        }
    }

    void tc12(){
        try{
            int a = 5 / 0;
            String x = "tc";
        } catch (ArithmeticException e){
            System.out.println("Arithmetic Exception" +  e.getMessage());
        } catch (Exception e){
            System.out.println("Exception if Arithmetic Exception didn't get caught");
        }
    }

    void tc13(){
        try{
            int a = 0;
            int b = 5;
            int c = a + b;
            int d = c + c;
        } catch (ArithmeticException e){
            System.out.println("No Exception" +  e.getMessage());
        }
    }

    void tc14(){
        tc15();
    }

    void tc15(){
        try {
            String x = "calling";
            int a = 10/0;
        } catch (Exception e){
            System.out.println("Exception" + e.getMessage());
        }
    }

}
