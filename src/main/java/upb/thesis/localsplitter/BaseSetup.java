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

        SootClass sootClass_af = Scene.v().getSootClass("upb.thesis.localsplitter.Target");
        SootClass sootClassUnsafe_af = Scene.v().getSootClassUnsafe("upb.thesis.localsplitter.Target", false);
        if (sootClassUnsafe_af == null) {
            System.out.println("No such class found");
        }
        //Chain<SootClass> sootClasses = Scene.v().getClasses();
        //System.out.println(sootClasses);

        List<SootMethod> sootClassMethods_af = sootClass_af.getMethods();
        for (SootMethod sootMethod : sootClassMethods_af) {
            //System.out.println(sootMethodActiveBody);
            //JimpleBody jimpleBody = (JimpleBody) sootMethod;
            //LocalSplitter.v().transform(sootMethod.getActiveBody());
            System.out.println(sootMethod.retrieveActiveBody());
        }

    }

    private void registerSootTransformers() {
        // add the analysis to jimple transform pack (jtp)
        Transform transform = new Transform("jtp.analysis", createAnalysisTransformer());
        PackManager.v().getPack("jtp").add(transform);
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
        //Options.v().set_whole_program(true);
        Options.v().set_soot_classpath(sootCp);
        Options.v().set_no_bodies_for_excluded(true);
        //Options.v().process_dir();
        //Options.v().set_allow_phantom_refs(true);
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb.cbf", "enabled:true");
        //Options.v().setPhaseOption("jb", "enabled:true");
        //Options.v().setPhaseOption("jtp", "enabled:true");
        //Options.v().set_prepend_classpath(false);
        //Options.v().set_output_format(Options.output_format_jimple);
        SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
        if (c != null)
            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }

}
