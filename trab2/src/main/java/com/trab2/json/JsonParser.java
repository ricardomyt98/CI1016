package com.trab2.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {
    // Function to read the input file
    public JSONObject getJson(Path path) throws IOException {
        String content;
        if (path == null) {
            System.out.println("Reading from STDIN");
            content = new BufferedReader(new InputStreamReader(System.in)).lines().collect(Collectors.joining("\n"));
        } else {
            System.out.println("Reading from " + path.getFileName());
            content = Files.readString(path);
        }

        return new JSONObject(content);
    }

    // Get a Property list item indexed by key name
    public JsonProperty getPropertyWithName(List<JsonProperty> properties, String propertyName) {
        for (JsonProperty p : properties) {
            if (p.name.equals(propertyName)) {
                return p;
            }
        }
        return null;
    }

    public void addAttributeIfExists(JsonProperty prop, JsonProperty attribute) {
        if (prop != null && prop.type == JsonPropertyType.OBJECT && !prop.attributes.contains(attribute)) {
            prop.addAttribute(attribute);
        }
    }

    public void parseJsonArray(JSONArray jsonArray, List<JsonProperty> properties) {
        this.parseJsonArray(jsonArray, properties, null);
    }

    public void parseJsonArray(JSONArray jsonArray, List<JsonProperty> properties, JsonProperty currentProperty) {
        int length = jsonArray.length();
        for (int i = 0; i < length; i++) {
            Object value = jsonArray.opt(i);

            if (value instanceof JSONObject) {
                String propertyName = String.format("%s_item", currentProperty.name);
                JsonProperty existingProperty = this.getPropertyWithName(properties, propertyName);

                if (existingProperty == null) {
                    existingProperty = new JsonProperty(propertyName, null, null);
                    existingProperty.type = JsonPropertyType.OBJECT;
                    properties.add(existingProperty);
                    currentProperty.setArrayElementType(existingProperty);
                }

                this.parseJsonObject((JSONObject)value, properties, existingProperty);
            }
        }
    }

    public void parseJsonObject(JSONObject jsonObject, List<JsonProperty> properties) {
        this.parseJsonObject(jsonObject, properties, null);
    }

    public void parseJsonObject(JSONObject jsonObject, List<JsonProperty> properties, JsonProperty currentProperty) {
        if (currentProperty == null) {
            currentProperty = new JsonProperty("MAIN", null, null);
            currentProperty.type = JsonPropertyType.OBJECT;
            properties.add(currentProperty);
        }

        for (String key : jsonObject.keySet()) {
            JsonProperty existingProperty = this.getPropertyWithName(properties, key);

            if (existingProperty == null) {
                existingProperty = new JsonProperty(key, null, null);
                properties.add(existingProperty);
            }

            Object value = jsonObject.opt(key);

            if (value instanceof JSONArray) {
                existingProperty.type = JsonPropertyType.ARRAY;
                this.parseJsonArray((JSONArray)value, properties, existingProperty);
                this.addAttributeIfExists(currentProperty, existingProperty);
            } else if (value instanceof JSONObject) {
                existingProperty.type = JsonPropertyType.OBJECT;
                this.parseJsonObject((JSONObject)value, properties, existingProperty);
                this.addAttributeIfExists(currentProperty, existingProperty);
            } else if (value instanceof String) {
                existingProperty.type = JsonPropertyType.STRING;
                this.addAttributeIfExists(currentProperty, existingProperty);
            } else if (value instanceof Number) {
                existingProperty.type = JsonPropertyType.NUMBER;
                this.addAttributeIfExists(currentProperty, existingProperty);
            }
        }
    }
}
