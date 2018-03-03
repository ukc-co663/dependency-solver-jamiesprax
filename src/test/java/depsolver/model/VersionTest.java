package depsolver.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VersionTest {

    @Test
    public void MajorVersionsCompareCorrectly() throws Exception {
        Version v1 = Version.create("1");
        Version v2 = Version.create("0");

        Version v3 = Version.create("100");
        Version v4 = Version.create("56");

        assertTrue(Version.isGreaterThan(v1, v2));
        assertFalse(Version.isGreaterThan(v4, v3));
        assertFalse(Version.isGreaterThan(v1, v1));
    }

    @Test
    public void MajorMinorVersionsCompareCorrectly() throws Exception {
        Version v1 = Version.create("1.5");
        Version v2 = Version.create("1.0");

        Version v3 = Version.create("100.7");
        Version v4 = Version.create("100.07");

        assertTrue(Version.isGreaterThan(v1, v2));
        assertFalse(Version.isGreaterThan(v4, v3));
        assertFalse(Version.isGreaterThan(v1, v1));
    }

    @Test
    public void MajorMinorSubVersionsCompareCorrectly() throws Exception {
        Version v1 = Version.create("1.5.999");
        Version v2 = Version.create("1.5.991");

        Version v3 = Version.create("100.07.01");
        Version v4 = Version.create("100.07.005");

        assertTrue(Version.isGreaterThan(v1, v2));
        assertFalse(Version.isGreaterThan(v4, v3));
        assertFalse(Version.isGreaterThan(v1, v1));
    }

}