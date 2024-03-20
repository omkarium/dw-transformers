/* 
MIT License

Copyright (c) 2023 Venkatesh Omkaram

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

%dw 2.0
output application/yaml
import isNumberType, isArrayType, isObjectType, isStringType, isBooleanType from dw::core::Types
import filterObjectLeafs, filterArrayLeafs from dw::util::Tree

fun findTyped(a,b,c) = typeOf(a) match {
    case "Object" -> {
        "properties": a filterObjectLeafs ((value, path) -> isStringType(typeOf(value)) or isNumberType(typeOf(value)) or isBooleanType(typeOf(value))) mapObject ((value, key, index) -> '$key': findTyped(value,key,c))
    }
    case "String" -> {
        "type": a match {
            case x if x matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$/ -> "date-only"
            case y if y matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$/ -> "datetime-only"
            case z if z matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z$/ -> "datetime"
            else -> "string"
        },
        ("example": a) if((c contains "example")),
        ("required": false) if((c contains "required")),
        ("maxlength": sizeOf(a)) if((a match {
            case x if x matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$/ -> false
            case y if y matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}$/ -> false
            case z if z matches /^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}Z$/ -> false
            else -> true
        }) and (c contains "maxLength"))
    }
    case "Number" -> {
        "type": "number",
        ("example": a) if((c contains "example")),
        ("required": false) if((c contains "required")),
    }
    case "Boolean" -> {
        "type": "boolean",
        ("example": a) if((c contains "example")),
        ("required": false) if((c contains "required")),
    }
    case "Array" -> {
        "type": "array",
        ("required": false) if((c contains "required")),
        "items": findTyped((a filterArrayLeafs ((value, path) -> isArrayType(typeOf(value)) or isNumberType(typeOf(value))))[0], "", c)
    }
     else -> {
     }
}

fun yaml_generate(data) = {
    "displayName" : data.displayName default "Give a display Name",
    "description": data.description default "Provide some description",
    ("type": lower(typeOf(data.json))) if(isObjectType(typeOf(data.json))),
    ("additionalProperties": false) if(isObjectType(typeOf(data.json)))
} ++ findTyped(data.json, "",data.fieldsToInclude)
---
read((write(yaml_generate(payload), "application/yaml") replace("%YAML 1.2") with "Developer: github.com/omkarium\n#%RAML 1.0 DataType"), "application/yaml")

