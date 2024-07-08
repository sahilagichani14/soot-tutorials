package upb.thesis.bodytransformer;

import upb.thesis.RQ1.JB_LP.JB_LP;
import upb.thesis.RQ1.JB_TT.JB_TT;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Apply Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        //listOfBodyInterceptors.add(JB_CBF.class.getName());
        //listOfBodyInterceptors.add(JB_LS.class.getName());
        //listOfBodyInterceptors.add(JB_LP.class.getName());
        listOfBodyInterceptors.add(JB_TT.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
