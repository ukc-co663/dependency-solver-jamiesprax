package depsolver.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class State {

    private final List<Command> commands;
    private final Set<DependencyRef> installed;
    private final Repository repo;
    private final StateValidator validator;

    private State(Set<DependencyRef> installed, Repository repo) {
        this.commands = new ArrayList<>();
        this.installed = installed;
        this.repo = repo;
        this.validator = StateValidator.create(repo, this);
    }

    public static State create(
            List<String> deps,
            Repository repo
    ) {
        return new State(
                deps.stream()
                        .map(DependencyRef::create)
                        .collect(Collectors.toSet()),
                repo
        );
    }

    public void applyCommands(List<Command> commands) {
        commands.forEach(c -> {
            if (validator.canDo(c)) {
                if (c.getType() == Command.Type.INSTALL && !isInstalled(c.getRef())) {
                    install(c.getRef());
                } else {
                    if (isInstalled(c.getRef())) {
                        uninstall(c.getRef());
                    }
                }
            }
        });
    }

    public Set<DependencyRef> getAllInstalledVersions(DependencyRef ref) {
        return installed.stream()
                .filter(r -> r.getName().equals(ref.getName()))
                .collect(Collectors.toSet());
    }

    public List<Command> getCommands() {
        return commands;
    }

    public boolean isInstalled(Dependency dep) {
        return isInstalled(dep.toRef());
    }

    public boolean isInstalled(DependencyRef ref) {
        if (ref.getVersion() == null) {
            return installed.contains(repo.resolveRef(ref));
        } else {
            return installed.contains(ref);
        }
    }

    public void install(Dependency dep) {
        install(dep.toRef());
    }

    public void install(DependencyRef ref) {
        DependencyRef iRef = ref;
        if (ref.getVersion() == null) {
            iRef = repo.resolveRef(ref);
        }

        installed.add(iRef);
        commands.add(iRef.toInstallCmd());
    }

    public void uninstall(Dependency dep) {
        uninstall(dep.toRef());
    }

    public void uninstall(DependencyRef ref) {
        DependencyRef uRef = ref;
        if (ref.getVersion() == null) {
            uRef = repo.resolveRef(ref);
        }
        installed.remove(uRef);
        commands.add(uRef.toUninstallCmd());
    }

    public State copy() {
        return new State(installed, repo);
    }
}
