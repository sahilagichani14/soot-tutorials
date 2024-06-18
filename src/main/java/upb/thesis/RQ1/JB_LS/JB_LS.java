package upb.thesis.RQ1.JB_LS;

public class JB_LS {

    int global = 10;

    //Test Case 1: Simple Assignment
    void tc1(){
        int a, b;
        a = 1;
        b = a;
        b = b + 1;
    }

    //Test Case 2: Compound Assignment
    void tc2(){
        int a, b;
        a = 1;
        b = a + 3;
    }

    //Test Case 3: Conditional Statements
    void tc3(){
        int a, b;
        a = 1;
        if (a == 2){
            b = 5;
        } else {
            b = 10;
        }
        int x = b + 1;
    }

    //Test Case 4: Loops
    void tc4(){
        int a = 0;
        int b;
        for (int i = 0; i < 3; i++){
            b = a + i;
        }
    }

    //Test Case 5: Switch Case
    void tc5(){
        int a = 3;
        switch (a){
            case 1: a = a + 1; int b = 1; break;
            case 2: a = a + 1; b = 2; break;
            case 3: a = a + 1; b = 3; break;
            default: a = a - 1;
        }
    }

    //Test Case 6: Variable Shadowing
    void tc6(){
        {
            int b = 2;
        }
        int b = 5;
    }

    //Test Case 7: Error Handling
    void tc7(){
        try {
            int a = 8/0;
            int b = a + 2;
        } catch (ArithmeticException ex){

        }
    }

    //Test Case 8: Global var
    void tc8(){
        int a = global + 5;
    }

    //Test Case 9: String Concat
//    void tc9(){
//        String x = "abc";
//        String y = "bcd";
//        String z = x + y;
//    }

    void localSplitterTest(){
        int x = 0;
        if (x > 0){
            x++;
        } else if (x + 10 > 21){
            x--;
        } else {
            System.out.println("Do Nothing");
        }
    }

}
