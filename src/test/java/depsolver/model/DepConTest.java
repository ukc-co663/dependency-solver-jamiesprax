package depsolver.model;

import org.junit.Test;

public class DepConTest {

    @Test
    public void depConIsParsedCorrectly() throws Exception {
        String s1 = "thing-4-2<=6.2";
        String s2 = "thingy>=6.2.1";
        String s3 = "thing-4-2<6";
        String s4 = "thing-4-2>6.2";
        String s5 = "1thing1=6.2";
        String s6 = "thing-4-2";

        DepCon dc1 = DepCon.create(s1);
        DepCon dc2 = DepCon.create(s2);
        DepCon dc3 = DepCon.create(s3);
        DepCon dc4 = DepCon.create(s4);
        DepCon dc5 = DepCon.create(s5);
        DepCon dc6 = DepCon.create(s6);

        check(dc1, "thing-4-2", Constraint.LTE, Version.create("6.2"));
        check(dc2, "thingy", Constraint.GTE, Version.create("6.2.1"));
        check(dc3, "thing-4-2", Constraint.LT, Version.create("6"));
        check(dc4, "thing-4-2", Constraint.GT, Version.create("6.2"));
        check(dc5, "1thing1", Constraint.E, Version.create("6.2"));
        check(dc6, "thing-4-2", null, null);

    }

    public void check(DepCon dep, String name, Constraint cons, Version version) {
        assert dep.getName().equals(name);

        if (cons == null) {
            assert dep.getConstraint() == null;
        } else {
            assert dep.getConstraint().equals(cons);
        }

        if (version == null) {
            assert dep.getVersion() == null;
        } else {
            assert dep.getVersion().equals(version);
        }
    }
}