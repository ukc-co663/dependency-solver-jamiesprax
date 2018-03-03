package depsolver.model;

import org.junit.Test;

public class ConstraintTest {

    @Test
    public void constraintIsParsedFromString() throws Exception {
        String lte = "thing<=3.1";
        String gte = "thing>=3.1";
        String lt = "thing<3.1";
        String gt = "thing>3.1";
        String e = "thing=3.1";

        assert(Constraint.findInString(lte).equals(Constraint.LTE));
        assert(Constraint.findInString(gte).equals(Constraint.GTE));
        assert(Constraint.findInString(lt).equals(Constraint.LT));
        assert(Constraint.findInString(gt).equals(Constraint.GT));
        assert(Constraint.findInString(e).equals(Constraint.E));
    }

}