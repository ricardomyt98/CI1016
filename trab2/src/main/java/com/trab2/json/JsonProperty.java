package com.trab2.json;

import java.util.ArrayList;
import java.util.List;

public class JsonProperty {
    public final String name;
    public JsonPropertyType type;
    public JsonProperty arrayElementType;
    public final List<JsonProperty> attributes;

    public JsonProperty(String name, JsonProperty arrayElementType, List<JsonProperty> attributes) {
        this.name = name;
        this.type = JsonPropertyType.UNDEFINED;

        // Used only when this.type == PropertyType.ARRAY
        this.arrayElementType = arrayElementType;

        // Used only when this.type == PropertyType.OBJECT
        if (attributes == null) {
            attributes = new ArrayList<>();
        }

        this.attributes = attributes;
    }

    public void setArrayElementType(JsonProperty t) {
        this.arrayElementType = t;
    }

    public void addAttribute(JsonProperty a) {
        this.attributes.add(a);
    }

    @Override
    public String toString() {
        switch (this.type) {
        case OBJECT:
            return String.format("{ name: %s, type: %s, attributes: %s }", this.name, this.type,
                    this.attributes.toString());
        case ARRAY:
            return String.format("{ name: %s, type: %s, arrayElementType: %s }", this.name, this.type,
                    this.arrayElementType.toString());
        default:
            return String.format("{ name: %s, type: %s }", this.name, this.type);
        }
    }
}
