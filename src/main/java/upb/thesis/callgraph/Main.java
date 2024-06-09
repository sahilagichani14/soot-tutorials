package upb.thesis.callgraph;

import soot.*;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;

import java.io.File;
import java.util.*;

import static soot.SootClass.SIGNATURES;

// https://stackoverflow.com/questions/59912631/how-do-i-exclude-java-standard-libraries-from-call-graphs-generated-by-soot
// https://gist.github.com/bdqnghi/9d8d990b29caeb4e5157d7df35e083ce
public class Main {
    public static void main(String[] args) {
        G.reset();

        // Set Soot's internal classpath
        String userdir = System.getProperty("user.dir");
        String sootCp = userdir + File.separator + "target" + File.separator + "classes" + File.pathSeparator + "lib" + File.separator + "rt.jar";
        Options.v().set_soot_classpath(sootCp);

        // Enable whole-program mode
        Options.v().set_whole_program(true);
        Options.v().set_app(true);

        Options.v().set_no_bodies_for_excluded(true);
        Options.v().process_dir();
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_prepend_classpath(false);
        //Options.v().set_output_format(Options.output_format_jimple);

        // Call-graph options
        Options.v().setPhaseOption("cg", "safe-newinstance:true");
        Options.v().setPhaseOption("cg.cha","enabled:true");

        // Load the main class
        SootClass c1 = Scene.v().loadClass("upb.thesis.callgraph.CallGraphRoot", SootClass.BODIES);
        c1.setApplicationClass();

        // Load the "main" method of the main class and set it as a Soot entry point
        System.out.println(c1.getMethods());
        SootMethod entryPoint = c1.getMethodByName("generateCallGraph");
        List<SootMethod> entryPoints = new ArrayList<>();
        entryPoints.add(entryPoint);
        Scene.v().setEntryPoints(entryPoints);

//        SootClass c = Scene.v().forceResolve("CallGraphExample", SootClass.BODIES);
//        if (c != null)
//            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();

        // to run call graph generation
        setUp(args);
    }

    private static void setUp(String[] args) {
//        List<String> argsList = new ArrayList<>(Arrays.asList(args));
//        argsList.addAll(Arrays.asList(new String[]{
//                "-w",
//                //"-main-class",//main-class
//                "upb.thesis.callgraph.Main",
//                "upb.thesis.callgraph.CallGraphExample", // argument classes
//                "upb.thesis.callgraph.CallGraphRoot"
//        }));

        PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

            @Override
            protected void internalTransform(String phaseName, Map<String, String> options) {
                CHATransformer.v().transform();
                SootClass a = Scene.v().getSootClass("upb.thesis.callgraph.CallGraphRoot");
                SootMethod src = Scene.v().getMainClass().getMethodByName("generateCallGraph");
                CallGraph cg = Scene.v().getCallGraph();

                Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
                while (targets.hasNext()) {
                    SootMethod tgt = (SootMethod)targets.next();
                    System.out.println(src + " may call " + tgt);
                }
            }
        }));

        //start or run
        //args = argsList.toArray(new String[0]);
        //soot.Main.main(args);
        PackManager.v().runPacks();
    }
}

