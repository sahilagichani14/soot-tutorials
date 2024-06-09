package upb.thesis.localsplitter;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Local Splitter Body Transformer");
        new BaseSetup().executeStaticAnalysis(Target.class.getName());
    }
}
