package depsolver.model;

public class DependencyRef {

    private final String name;
    private final String version;

    private DependencyRef(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static DependencyRef create(String str) {
        String[] nameVer = str.split("=");
        return new DependencyRef(nameVer[0], nameVer[1]);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
