package depsolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import depsolver.model.Repository;

import java.io.File;

public class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        Repository repository = MAPPER.readValue(new File(args[0]), Repository.class);

        //TODO : Complete parsing of complete repository
        //TODO : Parse initial repository
        //TODO : Parse constraints
        //TODO : Write state validator
        //TODO : Write state creation algorithm
        //TODO : Produce commands
    }
}
