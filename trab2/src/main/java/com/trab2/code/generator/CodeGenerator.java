package com.trab2.code.generator;

import com.trab2.json.JsonProperty;
import com.trab2.json.JsonPropertyType;

import java.util.List;

import org.ainslec.picocog.PicoWriter;

public class CodeGenerator {
    public String createJavaCode(List<JsonProperty> properties) {
        PicoWriter w = new PicoWriter();

        w.writeln("import java.util.ArrayList;");
        w.writeln("");

        for (JsonProperty p : properties) {
            if (p.type == JsonPropertyType.OBJECT && p.name != "MAIN") {
                String className = p.name.replace("_item", "");
                w.writeln_r(String.format("class %s {", className));

                PicoWriter propertyWriter = w.createDeferredWriter();
                for (JsonProperty attr : p.attributes) {
                    if (attr.type == JsonPropertyType.STRING) {
                        propertyWriter.writeln(String.format("public String %s;", attr.name.toLowerCase()));
                    } else if (attr.type == JsonPropertyType.NUMBER) {
                        propertyWriter.writeln(String.format("public int %s;", attr.name.toLowerCase()));
                    } else if (attr.type == JsonPropertyType.ARRAY) {
                        String arrayElementTypeName = attr.arrayElementType.name.replace("_item", "");
                        propertyWriter.writeln(String.format("public ArrayList<%s> %s;", arrayElementTypeName, attr.name.toLowerCase()));
                    } else if (attr.type == JsonPropertyType.OBJECT) {
                        propertyWriter.writeln(String.format("public %s %s;", attr.name, attr.name.toLowerCase()));
                    }
                }

                w.writeln_l("}");
                w.writeln("");
            }
        }

        w.writeln_r("class Programa {");

        PicoWriter methodWriter = w.createDeferredWriter();
        methodWriter.writeln_r("public static void main(String args[]) {");

        PicoWriter mainCodeWriter = methodWriter.createDeferredWriter();
        mainCodeWriter.writeln("System.out.println(\"Hi, the Java code was generated correctly!!!\");");

        methodWriter.writeln_l("}");

        w.writeln_l("}");
        w.writeln("");

        return w.toString();
    }
}
