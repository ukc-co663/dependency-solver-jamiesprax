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

    public void applyConstraints(List<Command> commands) {
        commands.forEach(c -> {
            if (c.getType() == Command.Type.INSTALL && !isInstalled(c.getRef())) {
                install(c.getRef());
            } else {
                if (isInstalled(c.getRef())) {
                    uninstall(c.getRef());
                }
            }
        });
    }

    public boolean isInstalled(Dependency dep) {
        return installed.contains(dep.toRef());
    }

    public boolean isInstalled(DependencyRef ref) {
        return installed.contains(ref);
    }

    public void install(Dependency dep) {
        installed.add(dep.toRef());
    }

    public void install(DependencyRef ref) {
        installed.add(ref);
    }

    public void uninstall(Dependency dep) {
        installed.remove(dep.toRef());
    }

    public void uninstall(DependencyRef ref) {
        installed.remove(ref);
    }
}
