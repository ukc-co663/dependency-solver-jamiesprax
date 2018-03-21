package depsolver.model;

import static depsolver.model.Command.Type.INSTALL;
import static depsolver.model.Command.Type.UNINSTALL;
import static java.lang.String.format;

public class Command {

    public enum Type {
        INSTALL("+"),
        UNINSTALL("-")
        ;

        private String sign;
        Type(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
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

    @Override
    public String toString() {
        return format("\"%s%s=%s\"", type.getSign(), ref.getName(), ref.getVersion());
    }
}
