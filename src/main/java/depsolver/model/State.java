package depsolver.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class State {

    private final Set<DependencyRef> installed;

    private State(Set<DependencyRef> installed) {
        this.installed = installed;
    }

    @JsonCreator
    public static State create(
            List<String> deps
    ) {
        return new State(
                deps.stream()
                        .map(DependencyRef::create)
                        .collect(Collectors.toSet())
        );
    }

    public boolean isIntalled(String name) {
        return installed.contains(name);
    }

    public void install(Dependency dep) {
        installed.add(dep.toRef());
    }

    public void uninstall(Dependency dep) {
        installed.remove(dep.toRef());
    }


}
