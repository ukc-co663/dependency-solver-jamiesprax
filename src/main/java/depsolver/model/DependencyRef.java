package depsolver.model;

import static java.lang.String.format;

public class DependencyRef {

    private final String name;
    private final String version;

    private DependencyRef(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public static DependencyRef create(String str) {
        String[] nameVer = str.split("=");
        return new DependencyRef(nameVer[0], nameVer.length == 2 ? nameVer[1] : null);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Command toInstallCmd() {
        return Command.create(format("+%s=%s", name, version));
    }

    public Command toUninstallCmd() {
        return Command.create(format("-%s=%s", name, version));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DependencyRef that = (DependencyRef) o;

        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        return version != null ? version.equals(that.version) : that.version == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
