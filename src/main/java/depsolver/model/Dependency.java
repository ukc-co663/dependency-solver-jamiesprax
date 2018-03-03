package depsolver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Dependency {

    private final String name;
    private final int version;
    private final long size;
    private final List<List<DepCon>> dependencies;
    private final List<DepCon> conflicts;

    private Dependency(
            @JsonProperty("name") String name,
            @JsonProperty("version") int version,
            @JsonProperty("depends") List<List<DepCon>> dependencies,
            @JsonProperty("size") long size,
            @JsonProperty("conflicts") List<DepCon> conflicts
    ) {
        this.name = name;
        this.version = version;
        this.dependencies = dependencies;
        this.size = size;
        this.conflicts = conflicts;
    }
}
