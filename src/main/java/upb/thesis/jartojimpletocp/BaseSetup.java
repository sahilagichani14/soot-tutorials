package upb.thesis.jartojimpletocp;

//import analysis.IDELinearConstantAnalysisProblem;
//import analysis.data.DFF;
//import eval.EvalHelper;
//import heros.solver.Pair;
//import solver.JimpleIDESolver;
import soot.*;
import soot.jimple.DefinitionStmt;
import soot.jimple.IntConstant;
import soot.jimple.JimpleBody;
import soot.jimple.toolkits.ide.icfg.JimpleBasedInterproceduralCFG;
import soot.jimple.toolkits.scalar.EmptySwitchEliminator;
import soot.options.Options;
import soot.util.Chain;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.*;

public class BaseSetup {
    private static List<SootMethod> entryMethods;
    //private static JimpleIDESolver<?, ?, ?> solver;
    public long defaultPropCount = 0;

    public void executeStaticAnalysis(String targetJar) {
        setupSoot(targetJar);
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
        Chain<SootClass> applicationClasses_afterpacks = Scene.v().getApplicationClasses();
        for (SootClass sootClass: applicationClasses_afterpacks){
            for (SootMethod sootMethod : sootClass.getMethods()) {
                if (sootMethod.hasActiveBody()){
                    JimpleBody jimpleBody =  (JimpleBody) sootMethod.getActiveBody();
                    //System.out.println(jimpleBody);
                }
                //LocalSplitter.v().transform(jimpleBody);
            }
        }

        /*
        SootClass sootClass = Scene.v().getSootClass("upb.thesis.localsplitter.Target");
        SootClass sootClassUnsafe = Scene.v().getSootClassUnsafe("upb.thesis.localsplitter.Target", false);
        if (sootClassUnsafe == null) {
            System.out.println("No such class found");
        }
        Chain<SootClass> sootClasses = Scene.v().getClasses();
        System.out.println(sootClasses);
        List<SootMethod> sootClassMethods = sootClass.getMethods();
        for (SootMethod sootMethod : sootClassMethods) {
            //System.out.println(sootMethodActiveBody);
            //JimpleBody jimpleBody = (JimpleBody) sootMethod;
            LocalSplitter.v().transform(sootMethod.getActiveBody());
            System.out.println(sootMethod.retrieveActiveBody());
        }
         */

    }

    private void registerSootTransformers() {
        // add the analysis to (jb)
        Transform transform = new Transform("wjtp.ifds", createAnalysisTransformer1());
        PackManager.v().getPack("wjtp").add(transform);
        Transform transform1 = new Transform("jb.analysis", createAnalysisTransformer());
        PackManager.v().getPack("jb").add(transform1);
    }

    protected Transformer createAnalysisTransformer() {
        //return new LocalSplitterTransformer();
        return EmptySwitchEliminator.v();
    }

    protected Transformer createAnalysisTransformer1() {
        SceneTransformer sceneTransformer = new SceneTransformer() {
            @Override
            protected void internalTransform(String phaseName, Map<String, String> options) {
                JimpleBasedInterproceduralCFG icfg = new JimpleBasedInterproceduralCFG(false);
//                for (SootMethod method : entryMethods) {
//                    System.out.println("started solving from: " + method.getSignature());
//                    IDELinearConstantAnalysisProblem problem = new IDELinearConstantAnalysisProblem(icfg, method, EvalHelper.getThreadCount());
//                    @SuppressWarnings({"rawtypes", "unchecked"})
//                    JimpleIDESolver<?, ?, ?> mSolver = new JimpleIDESolver<>(problem);
//                    mSolver.solve();
//                    solver = mSolver;
//                    mSolver.addFinalResults(method.getSignature());
//                    getResult(mSolver, method);
//                }
//                if (solver != null) {
//                    solver.dumpResults(EvalHelper.getTargetName());
//                }

            }
        };
        return sceneTransformer;
    }

//    public Set<Pair<String, Integer>> getResult(Object analysis, SootMethod method) {
//        //SootMethod m = getEntryPointMethod();
//        Map<DFF, Integer> res = null;
//        Set<Pair<String, Integer>> result = new HashSet<>();
//        if (analysis instanceof JimpleIDESolver) {
//            JimpleIDESolver solver = (JimpleIDESolver) analysis;
//            res = (Map<DFF, Integer>) solver.resultsAt(method.getActiveBody().getUnits().getLast());
//            defaultPropCount += solver.propagationCount;
//        }
//        for (Map.Entry<DFF, Integer> e : res.entrySet()) {
//            Pair<String, Integer> pair = new Pair<>(e.getKey().toString(), e.getValue());
//            result.add(pair);
//        }
//        return result;
//    }

    /*
     * This method provides the options to soot to analyze the respective classes.
     */
    protected static void setupSoot(String targetJar) {
        String path = "E:\\germany_details\\Uni-assist LOM\\paderborn admit\\Thesis\\soot-tutorials\\src\\main\\java\\upb\\thesis\\jartojimpletocp";
        G.reset();
        //String userdir = System.getProperty("user.dir");
        String sootCp = path + File.separator + targetJar + File.pathSeparator + "lib" + File.separator + "rt.jar";
        Options.v().set_soot_classpath(sootCp);
        Options.v().set_process_dir(Collections.singletonList(path + File.separator + targetJar));
        Options.v().set_whole_program(true);
        Options.v().set_no_bodies_for_excluded(true);
        Options.v().process_jar_dir();
        Options.v().set_allow_phantom_refs(true);
        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().setPhaseOption("jb.ls", "enabled:true");
        //Options.v().setPhaseOption("-dump-body","jb.ls Target");

        Options.v().set_prepend_classpath(false);
        Options.v().set_output_format(Options.output_format_jimple);
//        List <String> ls = new ArrayList<>();
//        ls.add("jb.ls");
//        Options.v().set_dump_body(ls);
        //Options.v().setPhaseOption("jb.ls", "dump-body");
        System.out.println(Options.v().parse(new String[]{"-dump-body", "jb.ls"}));

        //Options.v().dump_body();

//        SootClass c = Scene.v().forceResolve(path, SootClass.BODIES);
//        if (c != null)
//            c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
        entryMethods = getEntryPointMethods();
    }

    protected static List<SootMethod> getEntryPointMethods() {
        List<SootMethod> methods = new ArrayList<>();
        Set<SootClass> classes = new HashSet<>();
        for(SootClass c: Scene.v().getApplicationClasses()){
            classes.add(c);
        }
        l1:
        for (SootClass c : classes) {
            for (SootMethod m : c.getMethods()) {
                MethodSource source = m.getSource();
                if(source!=null){
                    if(isPublicAPI(m)){
                        m.retrieveActiveBody();
                        if (m.hasActiveBody()) {
                            //if(m.getSignature().contains("normalizeDocument")){
                            //if(m.getReturnType() instanceof IntegerType && m.getParameterTypes().stream().anyMatch(t->t instanceof IntegerType && !t.equals(BooleanType.v()))){
                            UnitPatchingChain units = m.getActiveBody().getUnits();
                            for (Unit unit : units) {
                                if(unit instanceof DefinitionStmt){
                                    DefinitionStmt assign = (DefinitionStmt) unit;
                                    Value rhs = assign.getRightOp();
                                    if(rhs instanceof IntConstant){
                                        methods.add(m);
//                                        if(methods.size()==EvalHelper.getMaxMethod()){
//                                            break l1;
//                                        }
                                    }
                                }
                            }
                            //}
                        }
                    }
                }
            }
        }
        if(!methods.isEmpty()){
            System.out.println(methods.size() + " methods will be used as entry points");
            //EvalHelper.setActualMethodCount(methods.size());
            return methods;
        }
        System.out.println("no entry methods found to start");
        return Collections.EMPTY_LIST;
    }

    private static boolean isPublicAPI(SootMethod method){
        return !method.isStatic() && method.isPublic() && !method.isAbstract() && !method.isConstructor() && !method.isNative() && !method.isStaticInitializer();
    }

}
