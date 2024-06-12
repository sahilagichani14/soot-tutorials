package upb.thesis.javatojimple;

import upb.thesis.RQ1.JB_CBF.JB_CBF;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Java To Jimple Converter");
        new BaseSetup().executeStaticAnalysis(JB_CBF.class.getName());
    }
}
