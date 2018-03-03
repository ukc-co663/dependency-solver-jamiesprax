package depsolver.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Constraint {
    LTE("<="),
    GTE(">="),
    LT("<"),
    GT(">"),
    E("="),
    ;

    public static final List<String> SIGNS = Arrays.stream(Constraint.values())
            .map(Constraint::getSign)
            .collect(Collectors.toList());

    private final String sign;

    Constraint(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public static Constraint fromSign(String sign) {
        for (Constraint constraint : Constraint.values()) {
            if (constraint.sign.equals(sign)) {
                return constraint;
            }
        }
        return null;
    }

    public static Constraint findInString(String str) {
        Optional<String> sign = SIGNS.stream().filter(str::contains).findFirst();
        return Constraint.fromSign(sign.orElse(""));
    }
}
