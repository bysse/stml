# Structured Typed Meta-Language
STML is a meta-language with the sole purpose of reducing the pain you feel inside
when reading and writing YAML.

## Features
### Statically typed
All fields and structs have a type. Wrong usage of types will result in compilation errors.
String type can be sub-typed with a regex, meaning that for instance naming conventions of kubernetes pods can be enforced by the type system. 

### Structured
Fields in structs need to be declared. Missing required field will result in compilation error.

### Familiar concepts
* Variable definitions are used instead of YAMLs solution of anchor / references.
* STML files can be nested with the #include / import statements

### Field / map imports
Field values or entire maps can be imported from the filesystem.

### Flexible syntax
A lot of the syntax in STML have alternative forms and different casing alternatives for keywords
to be forgiving to the user.  

## Modules
### cli
[Language definition and CLI tool.](https://github.com/bysse/stml/tree/master/cli)
