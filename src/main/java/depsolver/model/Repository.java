package depsolver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Repository {

    private final Map<DependencyRef, Dependency> dependencies;

    private Repository(Map<DependencyRef, Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @JsonCreator
    public static Repository create(@JsonProperty List<Dependency> dependencies) {
        return new Repository(
                dependencies.stream().collect(Collectors.toMap(Dependency::toRef, v -> v))
        );
    }

    public Dependency getDependency(DependencyRef ref) {
        return dependencies.get(ref);
    }
}
