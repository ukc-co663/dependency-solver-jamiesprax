package depsolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import depsolver.model.Command;
import depsolver.model.Repository;
import depsolver.model.State;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        String filepathToSave = args[0].replace("repository", "commands");
        File file = new File(filepathToSave);

        Arrays.stream(args).forEach(System.out::println);

        Repository repository = MAPPER.readValue(new File(args[0]), Repository.class);
        State state = State.create(MAPPER.readValue(new File(args[1]), List.class), repository);
        List<String> cmds = MAPPER.readValue(new File(args[2]), List.class);

        List<Command> commands = cmds.stream().map(Command::create).collect(Collectors.toList());
        state.applyCommands(commands);

        for (Command com : commands) {
            if (com.getType().equals(Command.Type.INSTALL)) {
                if (!state.isInstalled(com.getRef())) {
                    throw new IllegalStateException();
                }
            } else {
                if (state.isInstalled(com.getRef())) {
                    throw new IllegalStateException();
                }
            }
        }

        try (FileWriter wr = new FileWriter(file)) {
            List<String> strCmds = state.getCommands().stream()
                    .map(Command::toString)
                    .collect(Collectors.toList());

            wr.write(strCmds.toString());
        }
    }
}
