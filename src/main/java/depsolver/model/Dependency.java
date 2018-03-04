package depsolver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Dependency {

    private final String name;
    private final long size;
    private final Version version;
    private final List<DepCon> conflicts;
    private final List<List<DepCon>> dependencies;

    private Dependency(
            String name,
            long size,
            Version version,
            List<DepCon> conflicts,
            List<List<DepCon>> dependencies
    ) {
        this.name = name;
        this.size = size;
        this.version = version;
        this.conflicts = conflicts;
        this.dependencies = dependencies;
    }

    @JsonCreator
    public static Dependency create(
            @JsonProperty("name") String name,
            @JsonProperty("size") long size,
            @JsonProperty("version") String version,
            @JsonProperty("conflicts") List<String> conflicts,
            @JsonProperty("depends") List<List<String>> dependencies
    ) {
        List<List<DepCon>> deps = new ArrayList<>();
        if (dependencies != null && !dependencies.isEmpty()) {
            deps = dependencies.stream()
                    .map(l -> l.stream()
                            .map(DepCon::create)
                            .collect(Collectors.toList())
                    )
                    .collect(Collectors.toList());
        }

        List<DepCon> confs = new ArrayList<>();
        if (conflicts != null && !conflicts.isEmpty()) {
            confs = conflicts.stream().map(DepCon::create).collect(Collectors.toList());
        }

        return new Dependency(
                name,
                size,
                Version.create(version),
                confs,
                deps
        );
    }

    public String getName() {
        return name;
    }

    public Version getVersion() {
        return version;
    }

    public long getSize() {
        return size;
    }

    public List<List<DepCon>> getDependencies() {
        return dependencies;
    }

    public List<DepCon> getConflicts() {
        return conflicts;
    }

    public DependencyRef toRef() {
        return DependencyRef.create(format("%s=%s", name, version.toString()));
    }
}
