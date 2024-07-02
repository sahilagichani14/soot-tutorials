package upb.thesis.bodytransformer;

import upb.thesis.RQ1.JB_CBF.JB_CBF;
import upb.thesis.RQ1.JB_LP.JB_LP;
import upb.thesis.RQ1.JB_LS.JB_LS;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Apply Body Transformer");
        List<String> listOfBodyInterceptors = new ArrayList<>();
        //listOfBodyInterceptors.add(JB_CBF.class.getName());
        //listOfBodyInterceptors.add(JB_LS.class.getName());
        listOfBodyInterceptors.add(JB_LP.class.getName());
        new BaseSetup().executeStaticAnalysis(listOfBodyInterceptors);
    }
}
