package depsolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import depsolver.model.Repository;
import depsolver.model.State;

import java.io.File;
import java.util.List;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        Repository repository = MAPPER.readValue(new File(args[0]), Repository.class);
        State state = State.create(MAPPER.readValue(new File(args[1]), List.class));

        //TODO : Write state validator
        //TODO : Write state creation algorithm
        //TODO : Produce commands
    }
}
