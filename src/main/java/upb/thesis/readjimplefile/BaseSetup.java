package upb.thesis.readjimplefile;

import soot.*;
import soot.baf.BafASMBackend;
import soot.options.Options;
import soot.util.Chain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BaseSetup {

    public void executeStaticAnalysis(List<String> targetTestClassName) throws IOException {
        setupSoot(targetTestClassName);
    }

    protected static void setupSoot(List<String> targetTestClassNames) throws IOException {
        G.reset();
        String userdir = System.getProperty("user.dir");
        //String sootCp = userdir + File.separator + "target" + File.separator + "test-classes"+ File.pathSeparator + "lib" + File.separator + "rt.jar";
        String sootCp = userdir + File.separator + "target" + File.separator + "classes" + File.pathSeparator + "lib" + File.separator + "rt.jar";
        Options.v().set_num_threads(1);
        Options.v().set_soot_classpath(sootCp);

        String sootJimpleFilesSrcPath = userdir + File.separator + "jimplesrc";
        String rtjarPath = userdir + File.separator + "lib" + File.separator + "rt.jar";

//        ArrayList<String> jimpledir = new ArrayList<>();
//        jimpledir.add(sootJimplePath);
//        Options.v().set_process_dir(jimpledir);
//        List<String> processed_dir = Options.v().process_dir();

        Options.v().set_prepend_classpath(true);
        Options.v().set_allow_phantom_refs(true);
        Options.v().set_output_format(Options.output_format_jimple);
//        Options.v().set_process_dir(java.util.List.of(sootJimpleFilesSrcPath, rtjarPath));
//        Options.v().set_src_prec(Options.src_prec_jimple); // Set the source precedence to Jimple or 3
//        Options.v().set_output_format(Options.output_format_class); // Set output format to bytecode


        //Options.v().set_time(true);
        List<String> list = new ArrayList<>();
        //list.add("jb.ls");
        //list.add("jb.lp");
        //list.add("jb.ne");
        Options.v().set_dump_body(list);
        //Options.v().set_allow_phantom_refs(true);
        //List<String> excluded = Options.v().exclude();
        //Options.v().set_whole_program(true);
        //Options.v().set_allow_phantom_refs(true);
        //Options.v().set_no_bodies_for_excluded(true);
        //Options.v().set_include_all(true);
        //System.out.println(Options.v().getPhaseList());

        //Options.v().set_app(true); //Run in application mode

        Options.v().setPhaseOption("bb", "enabled:false"); //bafBody bb bydefault calls bb.lp, bb.ule, bb.ne so disabled
        //Disable all packs
        /*
        Options.v().setPhaseOption("jb", "enabled:false");
        Options.v().setPhaseOption("jj", "enabled:false");
        Options.v().setPhaseOption("wjpp", "enabled:false");
        Options.v().setPhaseOption("wspp", "enabled:false");
        Options.v().setPhaseOption("wstp", "enabled:false");
        Options.v().setPhaseOption("wsop", "enabled:false");
        Options.v().setPhaseOption("wjtp", "enabled:false");
        Options.v().setPhaseOption("wjop", "enabled:false");
        Options.v().setPhaseOption("wjap", "enabled:false");
        Options.v().setPhaseOption("stp", "enabled:false");
        Options.v().setPhaseOption("sop", "enabled:false");
        Options.v().setPhaseOption("jtp", "enabled:false");
        Options.v().setPhaseOption("jop", "enabled:false");
        Options.v().setPhaseOption("gb", "enabled:false");
        Options.v().setPhaseOption("gop", "enabled:false");
        Options.v().setPhaseOption("sop", "enabled:false");
        Options.v().setPhaseOption("bb", "enabled:false");
        Options.v().setPhaseOption("bop", "enabled:false");
        Options.v().setPhaseOption("tag", "enabled:false");
        Options.v().setPhaseOption("db", "enabled:false");
         */
        //Options.v().setPhaseOption("jb", "use-original-names:true");
        //Options.v().setPhaseOption("jb.ls", "use-original-names:true");

        //disable all jb body transformers
        {
            Options.v().setPhaseOption("jb.dtr", "enabled:false"); //Duplicate CatchAll Trap Remover
            Options.v().setPhaseOption("jb.ese", "enabled:false"); //Empty Switch Eliminator
            Options.v().setPhaseOption("jb.ls", "enabled:false");//LocalSplitter
            Options.v().setPhaseOption("jb.sils", "enabled:false"); //Shared Initialization Local Splitter
            Options.v().setPhaseOption("jb.a", "enabled:false"); //Aggregator
            Options.v().setPhaseOption("jb.ule", "enabled:false"); //Unused Local Eliminator
            Options.v().setPhaseOption("jb.tr", "enabled:true"); //Type Assigner
            Options.v().setPhaseOption("jb.ulp", "enabled:false"); //Unsplit-originals Local    Packer
            Options.v().setPhaseOption("jb.lns", "enabled:false"); //Local Name Standardizer
            Options.v().setPhaseOption("jb.cp", "enabled:false"); // CopyPropagator
            Options.v().setPhaseOption("jb.dae", "enabled:false"); // DeadAssignmentEliminator
            Options.v().setPhaseOption("jb.cp-ule", "enabled:false"); // UnusedLocalEliminator
            Options.v().setPhaseOption("jb.lp", "enabled:false"); //Local Packer
            Options.v().setPhaseOption("jb.ne", "enabled:false"); //No operation Eliminator
            Options.v().setPhaseOption("jb.uce", "enabled:false"); // UnreachableCodeEliminator
            Options.v().setPhaseOption("jb.tt", "enabled:false"); //Trap Tightener
            Options.v().setPhaseOption("jb.cbf", "enabled:false"); // ConditionalBranchFolder
        }

        /*
        //disable all jj body transformers
        Options.v().setPhaseOption("jj.ls", "enabled:false");
        Options.v().setPhaseOption("jj.sils", "enabled:false");
        Options.v().setPhaseOption("jj.a", "enabled:false");
        Options.v().setPhaseOption("jj.ule", "enabled:false");
        Options.v().setPhaseOption("jj.tr", "enabled:false");
        Options.v().setPhaseOption("jj.ulp", "enabled:false");
        Options.v().setPhaseOption("jj.lns", "enabled:false");
        Options.v().setPhaseOption("jj.cp", "enabled:false");
        Options.v().setPhaseOption("jj.dae", "enabled:false");
        Options.v().setPhaseOption("jj.cp-ule", "enabled:false");
        Options.v().setPhaseOption("jj.lp", "enabled:false");
        Options.v().setPhaseOption("jj.ne", "enabled:false");
        Options.v().setPhaseOption("jj.uce", "enabled:false");
        */

        //Options.v().setPhaseOption("jj", "use-original-names:true");
        for (String targetTestClassName: targetTestClassNames) {
            SootClass c = Scene.v().forceResolve(targetTestClassName, SootClass.BODIES);
            if (c != null)
                c.setApplicationClass();
        }
        Scene.v().loadNecessaryClasses();
        PackManager.v().runPacks();
        for (String targetTestClassName: targetTestClassNames){
            jimpletobytecode(targetTestClassName);
        }
    }

    protected static void jimpletobytecode(String targetTestClassName) throws IOException {
        //Scene.v().loadClassAndSupport("java.lang.Object");

        SootClass sootClass = Scene.v().getSootClass("upb.thesis.RQ1.JB_LP.JB_LP");
        SootClass sootClassUnsafe = Scene.v().getSootClassUnsafe("upb.thesis.RQ1.JB_LP.JB_LP", false);
        Chain<SootClass> sootClasses = Scene.v().getClasses();
        System.out.println(sootClasses);
        List<SootMethod> sootClassMethods = sootClass.getMethods();
        System.out.println(sootClassMethods);

//        Chain<SootClass> applicationClasses_afterpacks = Scene.v().getApplicationClasses();
//        for (SootClass sootcls: applicationClasses_afterpacks){
//            for (SootMethod sootMethod : sootcls.getMethods()) {
//                if (sootMethod.hasActiveBody()){
//                    JimpleBody jimpleBody =  (JimpleBody) sootMethod.getActiveBody();
//                    System.out.println(jimpleBody);
//                }
//                //LocalSplitter.v().transform(jimpleBody);
//            }
//        }

        // Doesn't work
//        String fileName = SourceLocator.v().getFileNameFor(sootClass, Options.output_format_class);
//        OutputStream streamOut = new JasminOutputStream(new FileOutputStream(fileName));
//        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
//        JasminClass jasminClass = new soot.jimple.JasminClass(sootClass);
//        jasminClass.print(writerOut);
//        writerOut.flush();
//        streamOut.close();

//        int java_version = Options.v().java_version();
//        String fileName1 = SourceLocator.v().getFileNameFor(sootClass, Options.output_format_class);
//        OutputStream streamOut1 = new FileOutputStream(fileName1);
//        BafASMBackend backend = new BafASMBackend(sootClass, java_version);
//        backend.generateClassFile(streamOut1);
//        streamOut1.close();

        //If we wished to output jimple source instead of a .class file, we would use the following code:
        String fileName = SourceLocator.v().getFileNameFor(sootClass, Options.output_format_jimple);
//        StringWriter out = new StringWriter();
//        PrintWriter writerOut = new PrintWriter(out);
        OutputStream streamOut = new FileOutputStream(fileName);
        PrintWriter writerOut = new PrintWriter(new OutputStreamWriter(streamOut));
        Printer.v().printTo(sootClass, writerOut);
        writerOut.flush();
        //System.out.println(writerOut.toString());

    }

}
