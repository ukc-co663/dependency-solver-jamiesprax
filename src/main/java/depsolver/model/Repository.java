package depsolver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Repository {

    private final List<Dependency> dependencies;

    private Repository(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @JsonCreator
    public static Repository create(@JsonProperty List<Dependency> dependencies) {
        return new Repository(dependencies);
    }
}
