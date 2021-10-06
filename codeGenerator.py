from __future__ import annotations
from pprint import pprint
from typing import Any
from enum import Enum

import sys
import json


# Class to enumerate possible types for this project
class PropertyType(Enum):
    STRING = 0
    OBJECT = 1
    NUMBER = 2
    ARRAY = 3
    NULL = 4
    UNDEFINED = 5


class Property:
    def __init__(self, name: str, arrayElementType: Property = None, attributes: list[Property] = None) -> None:
        self.name = name
        self.type = PropertyType.UNDEFINED

        # Used only when self.type == PropertyType.ARRAY
        self.arrayElementType = arrayElementType

        # Used only when self.type == PropertyType.OBJECT
        if attributes is None:
            attributes = []

        self.attributes = attributes

    def setArrayElementType(self, t: Property) -> None:
        self.arrayElementType = t

    def addAttribute(self, a: Property) -> None:
        self.attributes.append(a)

    def __str__(self) -> str:
        if self.type == PropertyType.OBJECT:
            return "{{ name: {}, type: {}, attributes: {} }}".format(self.name, self.type, self.attributes)
        elif self.type == PropertyType.ARRAY:
            return "{{ name: {}, type: {}, arrayElementType: {} }}".format(self.name, self.type, self.arrayElementType)
        else:
            return "{{ name: {}, type: {} }}".format(self.name, self.type)

    def __repr__(self) -> str:
        if self.type == PropertyType.OBJECT:
            return "{{ name: {}, type: {}, attributes: {} }}".format(self.name, self.type, self.attributes)
        elif self.type == PropertyType.ARRAY:
            return "{{ name: {}, type: {}, arrayElementType: {} }}".format(self.name, self.type, self.arrayElementType)
        else:
            return "{{ name: {}, type: {} }}".format(self.name, self.type)


# Function to read the input file
def get_json() -> dict:
    if len(sys.argv) >= 2:
        file = open(sys.argv[1])
    else:
        file = sys.stdin

    # Opening JSON file
    return json.load(file)


# Get a Property list item indexed by key name
def getPropertyWithName(properties: list[Property], propertyName: str) -> Property:
    for p in properties:
        if p.name == propertyName:
            return p
    return None


def addAttributeIfExists(prop: Property, attribute: str) -> None:
    if prop is not None and prop.type == PropertyType.OBJECT and not attribute in prop.attributes:
        prop.addAttribute(attribute)


def parseJsonList(jsonList: list, properties: list[Property], currentProperty: Property = None) -> None:
    for item in jsonList:
        if type(item) is dict:
            propertyName = "{}_item".format(currentProperty.name)
            existingProperty = getPropertyWithName(properties, propertyName)

            if existingProperty is None:
                existingProperty = Property(propertyName)
                existingProperty.type = PropertyType.OBJECT
                properties.append(existingProperty)
                currentProperty.setArrayElementType(existingProperty)

            parseJsonObject(item, properties, existingProperty)


def parseJsonObject(jsonObject: dict[str, Any], properties: list[Property], currentProperty: Property = None) -> None:
    if currentProperty is None:
        currentProperty = Property("MAIN")
        currentProperty.type = PropertyType.OBJECT
        properties.append(currentProperty)

    for key in jsonObject.keys():
        existingProperty = getPropertyWithName(properties, key)
        
        if existingProperty is None:
            existingProperty = Property(key)
            properties.append(existingProperty)

        if type(jsonObject[key]) is list:
            existingProperty.type = PropertyType.ARRAY
            parseJsonList(jsonObject[key], properties, existingProperty)
            addAttributeIfExists(currentProperty, existingProperty)
        
        elif type(jsonObject[key]) is dict:
            existingProperty.type = PropertyType.OBJECT
            parseJsonObject(jsonObject[key], properties, existingProperty)
            addAttributeIfExists(currentProperty, existingProperty)
        
        elif type(jsonObject[key]) is str:
            existingProperty.type = PropertyType.STRING
            addAttributeIfExists(currentProperty, existingProperty)
        
        elif type(jsonObject[key]) is int or type(jsonObject[key]) is float:
            existingProperty.type = PropertyType.NUMBER
            addAttributeIfExists(currentProperty, existingProperty)


def createJavaFile(properties: list[Property]) -> str:
    lines = []

    # Imports
    lines.append("import java.util.ArrayList;\n")

    for p in properties:
        if p.type == PropertyType.OBJECT and p.name != "MAIN":
            className = p.name.replace("_item", "")
            lines.append("class {} {{".format(className))
            
            for attr in p.attributes:
                if attr.type == PropertyType.STRING:
                    lines.append("    public String {};".format(attr.name.lower()))
                
                elif attr.type == PropertyType.NUMBER:
                    lines.append("    public int {};".format(attr.name))
                
                elif attr.type == PropertyType.ARRAY:
                    arrayElementTypeName = attr.arrayElementType.name.replace("_item", "")
                    lines.append("    public ArrayList<{}> {};".format(arrayElementTypeName, attr.name.lower()))

                elif attr.type == PropertyType.OBJECT:
                    lines.append("    public {} {};".format(attr.name, attr.name.lower()))

            lines.append("}\n")

    # Generating main()
    lines.append("class Programa {")
    lines.append("    public static void main(String args[]) {")
    lines.append("        System.out.println(\"Hi, the Java code was generated correctly!!!\");")
    lines.append("    }")
    lines.append("}\n")

    return "\n".join(lines)


def main():
    jsonDict = get_json()
    properties = list()

    parseJsonObject(jsonDict, properties)
    # print(properties)
    file = createJavaFile(properties)
    output = open("Programa.java", "w")
    output.write(file)

    # PascalCase
    # camelCase
    # snake_case


main()
