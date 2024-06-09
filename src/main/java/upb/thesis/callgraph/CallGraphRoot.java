package upb.thesis.callgraph;

public class CallGraphRoot {
    public static void main(String[] args) {
        generateCallGraph();
    }
    private static void generateCallGraph() {
        new CallGraphExample().firstcall();
    }
}
