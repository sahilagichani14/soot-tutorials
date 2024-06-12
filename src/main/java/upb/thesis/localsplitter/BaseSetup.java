package upb.thesis.localsplitter;

import soot.*;
import soot.jimple.DefinitionStmt;
import soot.jimple.IntConstant;
import soot.jimple.JimpleBody;
import soot.jimple.toolkits.scalar.ConditionalBranchFolder;
import soot.jimple.toolkits.scalar.EmptySwitchEliminator;
import soot.options.Options;
import soot.toolkits.scalar.LocalSplitter;

import java.io.File;
import java.util.*;

public class BaseSetup {

    public void executeStaticAnalysis(String targetTestClassName) {
        setupSoot(targetTestClassName);
        registerSootTransformers();
        executeSootTransformers();
    }

    private void executeSootTransformers() {
        // This will run the intra-procedural analysis
        PackManager.v().runBodyPacks();

		/*
		Target name of the class file
		Not all the times the queried class exists in Scene. If not, the result of getSootClass can return a phantom class or throws an exception according to the Soot options. A phantom object is created artificially by Soot to continue the analysis even if some information is missing. This option is quite useful when you are only interested only in the application classes (under analysis) and not other libraries.
		*/

        /*
        SootClass sootClass_af = Scene.v().getSootClass("upb.thesis.RQ1.JB_CBF.JB_CBF");
        SootClass sootClassUnsafe_af = Scene.v().getSootClassUnsafe("upb.thesis.RQ1.JB_CBF.JB_CBF", false);
        if (sootClassUnsafe_af == null) {
            System.out.println("No such class found");
        }
        //Chain<SootClass> sootClasses = Scene.v().getClasses();
        //System.out.println(sootClasses);

        List<SootMethod> sootClassMethods_af = sootClass_af.getMethods();
        for (SootMethod sootMethod : sootClassMethods_af) {
            System.out.println(sootMethod.getActiveBody());
        }
         */

    }

    private void registerSootTransformers() {
        // add the analysis to jimple body pack (jb)
        Transform transform = new Transform("jb.analysis", createAnalysisTransformer());
        PackManager.v().getPack("jb").add(transform);
    }

    protected Transformer createAnalysisTransformer() {
        //return new LocalSplitterTransformer();
        //return LocalSplitter.v();
        return ConditionalBranchFolder.v();
    }

    /*
     * This method provides the options to soot to analyze the respective classes.
     */
    protected static void setupSoot(String targetTestClassName) {
        G.reset();
        String userdir = System.getProperty("user.dir");
        //String sootCp = userdir + File.separator + "target" + File.separator + "test-classes"+ File.pathSeparator + "lib" + File.separator + "rt.jar";
        String sootCp = userdir + File.separator + "target" + File.separator + "classes" + File.pathSeparator + "lib" + File.separator + "rt.jar";
        Options.v().set_soot_classpath(sootCp);
        List<String> list = new ArrayList<>();
        list.add("jb.cbf");
        Options.v().set_dump_body(list);
        //Options.v().set_allow_phantom_refs(true);
        //List<String> excluded = Options.v().exclude();
        //Options.v().set_whole_program(true);
        //Options.v().set_allow_phantom_refs(true);
        //Options.v().set_no_bodies_for_excluded(true);
        //Options.v().set_include_all(true);
        //System.out.println(Options.v().getPhaseList());

        //Options.v().setPhaseOption("jb", "enabled:false");
        // Creates a JimpleBody for each method directly from source like its done in SootUp
        Options.v().setPhaseOption("jj","enabled:true");
        Options.v().setPhaseOption("jj", "use-original-names:true");
        Options.v().setPhaseOption("jj.ne","enabled:true"); //No operation Eliminator

        //Options.v().setPhaseOption("jb.a","enabled:false"); //Aggregator
        //Options.v().setPhaseOption("jb.ls","enabled:false"); //LocalSplitter
        //Options.v().setPhaseOption("jb.cp","enabled:false"); // CopyPropagator
        //Options.v().setPhaseOption("jb.cbf","enabled:false"); // ConditionalBranchFolder
        //Options.v().setPhaseOption("jb.uce","enabled:false"); // UnreachableCodeEliminator
        //Options.v().setPhaseOption("jb.dae","enabled:false"); // DeadAssignmentEliminator
        //Options.v().setPhaseOption("jb.cp-ule","enabled:false"); // UnusedLocalEliminator
        SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
        if (c != null)
            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }

}
