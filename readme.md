# Structured Typed Meta-Language
STML is a meta-language with the sole purpose of reducing the pain you feel inside
when reading and writing YAML. The language definition can be found in the [cli module](https://github.com/bysse/stml/tree/master/cli). For non technical features keep reading.

## History
During a recent training where there were extensive usage of YAML I started to question the survival of our species. If YAML was the pinnacle of humanity's effort to write configuration, what chances do we really have at solving the climate crisis?
In a desperate attempt at proving myself wrong and making the world a better place I started writing STML. It was supprisingly easy to make something that I percieved to be far superior to YAML. If I could do it in a couple of weeks. Imagine what a well resourced organization could do! 
Are you up for the challange? 
A YAML free world is within reach. 
If we work together.

### Disclaimer
STML is not mature enough for any type of production use. The test suite is serverly lacking and not even all base functionality is tested thoroughly. 

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

[A small kubernetes example.](https://github.com/bysse/stml/tree/master/types/k8s)
