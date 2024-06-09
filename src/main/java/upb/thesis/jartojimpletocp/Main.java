package upb.thesis.jartojimpletocp;

public class Main {
    public static void main(String[] args) {
        System.out.println("Jar to jimple, jimple to get entry methods, apply cp");
        String targetJar = "lombok-1.18.26.jar";
        //String targetJar = "JarExample.jar";
        new BaseSetup().executeStaticAnalysis(targetJar);
    }
}
