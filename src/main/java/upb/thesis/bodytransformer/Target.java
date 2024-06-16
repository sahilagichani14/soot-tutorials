package upb.thesis.bodytransformer;

public class Target {
    public static void main(String[] args) {

    }
    int a, b, c;

    void test(){
        a = 0;
        b = 5;
        a = a + 1;
        b = a + 4;
        c = 8;
    }

    void conditionalBranchFolderTest2(){
        int x = 0;
        if (x > 0){
            x++;
        } else if (x + 10 > 21){
            x--;
        } else {
            System.out.println("Do Nothing");
        }
    }

    String testEmptySwitchEliminator(){
        String x = "init";
        switch (x){
            default: {
                x = new String("Test");
            }
        }
        return x;
    }
}
