package upb.thesis.javatojimple;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Java To Jimple Converter");
        new BaseSetup().executeStaticAnalysis(Target.class.getName());
    }
}
