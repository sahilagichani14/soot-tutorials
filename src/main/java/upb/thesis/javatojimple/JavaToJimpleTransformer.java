package upb.thesis.javatojimple;

import soot.Body;
import soot.BodyTransformer;

import java.util.Map;

public class JavaToJimpleTransformer extends BodyTransformer {

    /*
    Defines how each method body is transformed.
    This method is called to perform the transformation itself. It is declared abstract; subclasses must implement this method by making it the entry point to their actual Body transformation.
    Parameters:
    b - the body on which to apply the transformation
    phaseName - the phasename for this transform; not typically used by implementations.
    options - the actual computed options; a combination of default options and Scene specified options.
     */
    @Override
    protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
//        Map options1 = PhaseOptions.v().getPhaseOptions(phaseName);
//        if(PhaseOptions.getBoolean(options1, "enabled" ) ) {
//            if(Options.v().verbose() ) {
//                G.v().out.println( "Applying phase "+phaseName+" to "+body.getMethod()+"." );
//            }
//        }
        System.out.println("Method name:" + body.getMethod().getName());
        System.out.println(body.toString());
    }

}
