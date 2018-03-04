package depsolver.model;

import java.util.List;
import java.util.stream.Collectors;

public class StateValidator {

    private final Repository repo;
    private final State state;

    private StateValidator(Repository repo, State state) {
        this.repo = repo;
        this.state = state;
    }

    public static StateValidator create(Repository repo, State state) {
        return new StateValidator(repo, state);
    }

    public boolean canDo(Command command) {
        Dependency dep = repo.getDependency(command.getRef());

        if (dep.getConflicts() != null) {
            List<DepCon> installedConflicts = dep.getConflicts().stream()
                    .filter(c -> state.isInstalled(c.toRef()))
                    .collect(Collectors.toList());

            if (!installedConflicts.isEmpty()) {
                List<DepCon> conflictingVersions = dep.getConflicts().stream()
                        .filter(installedConflicts::contains)
                        .filter(c -> {
                            switch (c.getConstraint()) {
                            case LTE:
                                return c.getVersion().isLessThan(dep.getVersion())
                                        || c.getVersion().equals(dep.getVersion());
                            case LT:
                                return c.getVersion().isLessThan(dep.getVersion());
                            case GTE:
                                return c.getVersion().isGreaterThan(dep.getVersion())
                                        || c.getVersion().equals(dep.getVersion());
                            case GT:
                                return c.getVersion().isGreaterThan(dep.getVersion());
                            default:
                                return c.getVersion().equals(dep.getVersion());
                            }
                        })
                        .collect(Collectors.toList());

                if (!conflictingVersions.isEmpty()) {
                    for (DepCon c : conflictingVersions) {
                        if (canDo(c.toUninstallCmd())) {
                            state.uninstall(c.toRef());
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        if (dep.getDependencies() != null) {
            for (List<DepCon> depList : dep.getDependencies()) {
                List<DepCon> requiredDeps = depList.stream()
                        .filter(d -> !state.isInstalled(d.toRef()))
                        .collect(Collectors.toList());

                if (!requiredDeps.isEmpty()) {
                    for (DepCon d : requiredDeps) {
                        if (canDo(d.toInstallCmd())) {
                            state.install(d.toRef());
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
