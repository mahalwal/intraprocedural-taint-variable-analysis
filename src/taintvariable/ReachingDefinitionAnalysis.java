//Manish Mahalwal
//2016054

package taintvariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import soot.Value;
import soot.toolkits.graph.UnitGraph;
import soot.Body;
import soot.Local;
import soot.Unit;
import soot.ValueBox;
import soot.JastAddJ.ReturnStmt;
import soot.jimple.AssignStmt;
import soot.jimple.Constant;
import soot.jimple.IdentityStmt;
import soot.jimple.Stmt;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JReturnStmt;
import soot.tagkit.LineNumberTag;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.BackwardFlowAnalysis;
import soot.toolkits.scalar.FlowSet;
import soot.toolkits.scalar.AbstractFlowSet;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import soot.toolkits.scalar.Pair;
import soot.util.Chain;

public class ReachingDefinitionAnalysis extends BackwardFlowAnalysis {

    Body b;
    FlowSet inval, outval;

    public ReachingDefinitionAnalysis(UnitGraph g) {
		super(g);
		b = g.getBody();
		doAnalysis();
    }

    @Override
    protected void flowThrough(Object in, Object unit, Object out) {
        inval = (FlowSet) in;
        outval = (FlowSet) out;
        Stmt u = (Stmt) unit;
        inval.copy(outval);
        String var;
        LineNumberTag tag = (LineNumberTag) u.getTag("LineNumberTag");
//        System.out.println(tag + " Class: " + u.getClass().toString());

        if(u instanceof JReturnStmt) {
//        	System.out.println("stmt: " + u.toString());
        	Iterator<ValueBox> useIt = u.getUseBoxes().iterator();
        	while (useIt.hasNext()) {
        		String second = useIt.next().getValue().toString();
        		outval.add(second);
//        		System.out.println("Generating " + second);
        	}
        }
        
        if(u instanceof JInvokeStmt && u.toString().contains("print")) {
//        	System.out.println("JInvokeStmt stmt: " + u.toString());
        	Iterator<ValueBox> useIt = u.getUseBoxes().iterator();
        	while (useIt.hasNext()) {
        		Value rhsvar = useIt.next().getValue();
        		String second = rhsvar.toString();
        		if(rhsvar instanceof Local) {
            		outval.add(second);
//            		System.out.println("Generating " + second);
        		}
        	}
        }
        
        if(u instanceof AssignStmt) {

    		Iterator<ValueBox> defIt = u.getDefBoxes().iterator();
        	while (defIt.hasNext()) {
        		boolean constAndDelete = true;
        		String first = defIt.next().getValue().toString();
//        		if(!first.contains("$")) {
//        			System.out.println("{" + outval.toString() + "}");
        			if(outval.contains(first)) {
        				Iterator<ValueBox> useIt = u.getUseBoxes().iterator();
        	        	while (useIt.hasNext()) {
        	        		Value rhsvar = useIt.next().getValue();
        	        		String second = rhsvar.toString();
        	        		
        	        		if(rhsvar instanceof Constant && !u.toString().contains("staticinvoke")) {
//        	        			constAndDelete = true;
//        	        			System.out.println(second + "CONSTANT!");
        	        		} else {
        	        			if(rhsvar instanceof Local){
        	        				constAndDelete = false;
//        	        				System.out.println("Generating " + second);
        	        				outval.add(second);
        	        			}
//        	        			else
//        	        				outval.add(second);
        	        		}
        	        	}
        	        	if(constAndDelete == true) {
        	        		
//        	        		System.out.println("killing " + first);
//    	        			System.out.println("{"+outval.toString()+"}");
    	        			outval.remove(first);
//    	        			System.out.println("{"+outval.toString()+"}");
        	        	}
        			}
//        		}
        	}
        }
        
        //TODO check if variable is already there in inval.
        //case of sc = new Scanner();
//        if(u instanceof AssignStmt) {
//        	if(u.toString().contains("Scanner")) {
//        		Iterator<ValueBox> defIt = u.getDefBoxes().iterator();
//            	while (defIt.hasNext()) {
//            		String first = defIt.next().getValue().toString();
//            		outval.add(first);
//            	}
//        	}
//        }
        
//        if(u instanceof IdentityStmt) {
//        	if(u.toString().contains("parameter")) {
//        		Iterator<ValueBox> defIt = u.getDefBoxes().iterator();
//            	while (defIt.hasNext()) {
//            		String first = defIt.next().getValue().toString();
////            		System.out.println("adding parameter");
//            		outval.add(first);
//            	}
//        	}
//        }
        
        System.out.println(u.getClass().toString());
        System.out.println("In: " + inval.toString());
//        System.out.println(u.getClass());
        System.out.println("Unit " + tag + ": "+ u.toString() );
        System.out.println("Out: " + outval.toString());
        System.out.println();
    }

    @Override
    protected void copy(Object source, Object dest) {
        FlowSet srcSet = (FlowSet) source;
        FlowSet destSet = (FlowSet) dest;
        srcSet.copy(destSet);
    }

    //Setting the entry set for BInit
    @Override
    protected Object entryInitialFlow() {
        ArraySparseSet as = new ArraySparseSet();
        return as;
    }

    @Override
    protected void merge(Object in1, Object in2, Object out) {
        FlowSet inval1 = (FlowSet) in1;
        FlowSet inval2 = (FlowSet) in2;
        FlowSet outVal = (FlowSet) out;
        inval1.union(inval2, outVal); //Merging in may analysis

    }

    @Override
    protected Object newInitialFlow() //Initializing entryy set of each statement
    {
        ArraySparseSet as = new ArraySparseSet();
        return as;
    }

}
