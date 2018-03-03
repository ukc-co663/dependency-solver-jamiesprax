package depsolver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DepCon {

    private final Constraint constraint;
    private final String name;
    private final Version version;

    private DepCon(String name, Constraint constraint, Version version) {
        this.name = name;
        this.constraint = constraint;
        this.version = version;
    }

    @JsonCreator
    public static DepCon create(String str) {
        Constraint constraint = Constraint.findInString(str);
        if (constraint == null) {
            return new DepCon(str, null, null);
        }
        String[] nameVer = str.split(constraint.getSign());
        return new DepCon(nameVer[0], constraint, Version.create(nameVer[1]));
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }
}
