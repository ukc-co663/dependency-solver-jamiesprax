package depsolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import depsolver.model.Command;
import depsolver.model.DependencyRef;
import depsolver.model.Repository;
import depsolver.model.State;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        Repository repository = MAPPER.readValue(new File(args[0]), Repository.class);
        State state = State.create(MAPPER.readValue(new File(args[1]), List.class));
        List<String> commands = MAPPER.readValue(new File(args[2]), List.class);
        state.applyConstraints(commands.stream().map(Command::create).collect(Collectors.toList()));

        repository.getDependency(DependencyRef.create("test"));

        //TODO : Write state validator
        //TODO : Write state creation algorithm
        //TODO : Produce commands
    }
}
