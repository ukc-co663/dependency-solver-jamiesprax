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
    public static boolean isLessThan(Version v1, Version v2) {
        return !isGreaterThan(v1,v2) && !v1.equals(v2);
    }

    /**
     * Returns true if v1 is a greater version than v2
     */
    public static boolean isGreaterThan(Version v1, Version v2) {
        if (v1.getMajor() > v2.getMajor()) {
            return true;
        } else if (v1.getMajor() < v2.getMajor()) {
            return false;
        }

        // cases where v1 major == v2 major
        if (v1.getMinor() != null) {
            if (v2.getMinor() == null) {
                return true;
            } else {
                if (stringVerIsGreaterThan(v1.getMinor(), v2.getMinor())) {
                    return true;
                } else if (stringVerIsLessThan(v1.getMinor(), v2.getMinor())) {
                    return false;
                }
            }
        }

        // cases where v1 minor == v2 minor
        if (v1.getSub() != null) {
            if (v2.getSub() == null) {
                return true;
            } else {
                if (stringVerIsGreaterThan(v1.getSub(), v2.getSub())) {
                    return true;
                } else if (stringVerIsLessThan(v1.getSub(), v2.getSub())) {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if m1 is greater than m2
     */
    private static boolean stringVerIsGreaterThan(String m1, String m2) {
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

    private static boolean stringVerIsLessThan(String m1, String m2) {
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

    @Override public boolean equals(Object o) {
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

    @Override public int hashCode() {
        int result = major.hashCode();
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        result = 31 * result + (sub != null ? sub.hashCode() : 0);
        return result;
    }
}
