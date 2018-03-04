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
            return dependencies.get(ref.getVersion() == null ? resolveRef(ref) : ref);
    }

    // resolve any version of ref
    public DependencyRef resolveRef(DependencyRef ref) {
        List<DependencyRef> all = getAllVersionsOf(ref);
        return all.isEmpty() ? ref : all.get(0);
    }

    // resolve all versions of ref
    public List<DependencyRef> getAllVersionsOf(DependencyRef ref) {
        return dependencies.keySet().stream()
                .filter(r -> r.getName().endsWith(ref.getName()))
                .collect(Collectors.toList());
    }
}
