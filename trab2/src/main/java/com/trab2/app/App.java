package com.trab2.app;

import com.trab2.code.generator.CodeGenerator;

import com.trab2.json.JsonParser;
import com.trab2.json.JsonProperty;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();

        Path path = null;
        if (args.length > 0) {
            // System.out.println(args[0]);
            path = Paths.get(args[0]);
        }

        JSONObject jsonObject = parser.getJson(path);
        List<JsonProperty> properties = new ArrayList<>();

        parser.parseJsonObject(jsonObject, properties);

        // System.out.println(properties);
        // System.out.println(jsonDict.toString());

        CodeGenerator generator = new CodeGenerator();
        String code = generator.createJavaCode(properties);
        
        // System.out.println(code);

        FileWriter javaFileWriter = new FileWriter("Programa.java");
        javaFileWriter.write(code);
        javaFileWriter.close();
    }
}
