package depsolver.model;

import static depsolver.model.Command.Type.INSTALL;
import static depsolver.model.Command.Type.UNINSTALL;

public class Command {

    public enum Type {
        INSTALL,
        UNINSTALL
    }

    private final Type type;
    private final DependencyRef ref;

    public Command(Type type, DependencyRef ref) {
        this.type = type;
        this.ref = ref;
    }

    public static Command create(String str) {
        return new Command(
                str.charAt(0) == '+' ? INSTALL : UNINSTALL,
                DependencyRef.create(str.substring(1))
        );
    }

    public Type getType() {
        return type;
    }

    public DependencyRef getRef() {
        return ref;
    }
}
