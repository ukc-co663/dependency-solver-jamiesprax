package depsolver.model;

public class Version {

    private Integer major;
    private String minor;
    private String sub;

    private Version(String... versions) {
        switch(versions.length) {
            case 3 : this.sub = versions[2];
            case 2 : this.minor = versions[1];
            case 1 : this.major = Integer.parseInt(versions[0]);
        }
    }

    public static Version create(String str) {
        return new Version(str.split("\\."));
    }

    public Integer getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public String getSub() {
        return sub;
    }

    /**
     * Returns true if v1 is a lesser version than v2
     */
    public boolean isLessThan(Version v) {
        return !this.isGreaterThan(v) && !this.equals(v);
    }

    /**
     * Returns true if v1 is a greater version than v2
     */
    public boolean isGreaterThan(Version v) {
        if (major > v.getMajor()) {
            return true;
        } else if (major < v.getMajor()) {
            return false;
        }

        // cases where v1 major == v2 major
        if (minor != null) {
            if (v.getMinor() == null) {
                return true;
            } else {
                if (stringVerIsGreaterThan(minor, v.getMinor())) {
                    return true;
                } else if (stringVerIsLessThan(minor, v.getMinor())) {
                    return false;
                }
            }
        }

        // cases where v1 minor == v2 minor
        if (sub != null) {
            if (v.getSub() == null) {
                return true;
            } else {
                if (stringVerIsGreaterThan(sub, v.getSub())) {
                    return true;
                } else if (stringVerIsLessThan(sub, v.getSub())) {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if m1 is greater than m2
     */
    private boolean stringVerIsGreaterThan(String m1, String m2) {
        try {
            for (int i = 0; i < m1.length(); i++) {
                if (m1.charAt(i) > m2.charAt(i)) {
                    return true;
                } else if (m1.charAt(i) < m2.charAt(i)) {
                    return false;
                }
            }
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            return true;
        }
    }

    /**
     * Returns true if m1 is less than m2
     */
    private boolean stringVerIsLessThan(String m1, String m2) {
        try {
            for (int i = 0; i < m1.length(); i++) {
                if (m1.charAt(i) < m2.charAt(i)) {
                    return true;
                } else if (m1.charAt(i) > m2.charAt(i)) {
                    return false;
                }
            }
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Version version = (Version) o;

        if (!major.equals(version.major))
            return false;
        if (minor != null ? !minor.equals(version.minor) : version.minor != null)
            return false;
        return sub != null ? sub.equals(version.sub) : version.sub == null;
    }

    @Override
    public int hashCode() {
        int result = major.hashCode();
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (sub != null ? sub.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(major.toString());
        if (minor != null) {
            str.append(".").append(minor);
            if (sub != null) {
                str.append(".").append(sub);
            }
        }
        return str.toString();
    }
}
