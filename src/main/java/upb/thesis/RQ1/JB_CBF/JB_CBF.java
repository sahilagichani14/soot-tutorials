package upb.thesis.RQ1.JB_CBF;

public class JB_CBF {
    void conditionalBranchFolderTest(){
        int x = 0;
        if (x > 0){
            x++;
        } else if (x + 10 > 21){
            x--;
        } else {
            System.out.println("Do Nothing");
        }
        if (10 > 5){
            x++;
        }
    }
}
