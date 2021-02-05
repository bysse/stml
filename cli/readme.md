# Structured Typed Meta-Language
STML is a meta-language with the sole purpose of reducing the pain you feel inside
when reading and writing YAML. It only consists of two things, type definitions and 
type constructors.

## CLI

The CLI tool is the main entrypoint for STML manipulation. The most useful flag is
to invoke it with `-h` to get the help screen. The second most useful flag is to
generate auto completion for the shell (only bash/zsh supported).

    source <(stml generate-completion)


The third most useful flag is `--kubectl` which generates the kubectl plugin script. 
To install the plugin use:

    stml --kubectl | sudo tee /usr/local/bin/kubectl-stml 
    sudo chmod +x /usr/local/bin/kubectl-stml

## Types

The core type in STML is the `struct`. It defines a data structure with types and 
optional default values. A `struct` can extend another `struct` and any field with 
the type of the parent `struct` will accept a `struct` that extends it. In fancier 
terms, the typing is covariant.

### Built-in types
The built-in types in STML are:

| Type  | Aliases  | Description  |
|---|---|---|
| Any   | any, *    | Any type of field ie type-less.  |
| Bool  | bool, boolean, Boolean  |  true, false, yes, no, on, off |
| List  |           | A list of objects.  |
| Map   |           | A map with key values.  |
| Number  | number  | Integer or floating point numbers.  |
| String  | string  | String constants.  |
| NULL  | null  | Null or optional value.  |

The types `List`, `Map` and `String` also accepts subtypes which specializes
the types even further:

    List<SubType>
    Map<SubType>
    String</REGEXP/>

## Variables

A variable is declared with the `let` keyword. The type of the variable will be the same as the value it's given.

   let variable = 1


## Type Definitions

Outside of the built-in types STML allows for two different classes of custom types; `enum` and `struct`.
All types are described in the sections below.

### Any

Fields that are declared with the any type can accept any other type. The parser will
recognize the tokens `any`, `Any` and `*` as aliases. 

    let variable = 3.14
    let variable = "hello world"
    let variable = true

### Boolean

Boolean type can be declared with the tokens `Bool`, `bool`,`boolean` and `Boolean`.
The type accepts the values `true`, `false`, `yes`, `no`, `on` and `off`. One caveat
is that during YAML rendering, all values will be translated to either `true` or `false`.  

    let variable = true
    let variable = off
    let variable = yes


### Enum

The enum is a set of named String constants. It's declared like this: 

    enum MySuperEnum {
        First
        Second
    } 

Enum types can inherit other enum types. The child type would 
then contain all fields from its super types and its own declared constants.
Constant collisions are ignored, since they would yield the same result anyway. 
An extension declaration look like this:

    enum MyEnum extends MySuperEnum {
        Third
        Forth
    }
    
    enum MyEnum2 < MySuperEnum {
        Third
        Forth
    } 

When an enum value is emitted it will simply turn into a string value.

    emit MyEnum.FirstConstant

Would yield the following YAML:

    ---
    FirstConstant
    
    
### List

Lists can be both typed and untyped. An untyped list can contain any type of value while
a type list only may contain a specific type. Lists can't be typed with union types.

    struct ListStruct {
        untyped : List
        typed : List<String>
    }
    
Instantiating a list can be done in multiple ways. The most verbose way is to use the keyword
`List` like this:

    ListStruct(
        untyped: List(1, 2, 3, true)
        typed:   List<String>("a", "b", "c")
    )
    
The other way is to use the short hand `[` and `]` symbols, like this:    

    ListStruct(
        untyped: [1, 2, 3, true]
        typed:   <String>["a", "b", "c"]
    )
    
Since STML does implicit type conversion you don't actually have to specify the type of
the list when you construct it.    

    ListStruct(
        untyped: [1, 2, 3, true]
        typed:   ["a", "b", "c"]
    )

### Map

A map are very similar to lists except that they store key values. They are either typed or untyped
and union types can't be used to type a map.

    struct MapStruct {
        untyped : Map
        typed : Map<Number>
    }

Instantiating a Map can, just as for lists be done in multiple ways. The most verbose way is 
to use the keyword `Map` like this:

    MapStruct(
        untyped: Map("a":2, "b": 3)
        typed:   Map<String>(
                    "a": 2 
                    "b": 3
                 )
    )

The other way is to use the short hand `{` and `}` symbols, like this:

    MapStruct(
        untyped: {"a":2, "b": 3}
        typed:   <Map>{"a":2, "b": 3}
    )

Since STML does implicit type conversion you don't actually have to specify the type of
the map when you construct it.

    MapStruct(
        untyped: {"a":2, "b": 3}
        typed:   {"a":2, "b": 3}
    )

To reference fields in a map just separate the field names with dots like this.

    let example = MapStruct(
        untyped: {"a":2, "b": 3}
        typed:   {"a":2, "b": 3}
    )
    emit example.typed

If you need to declare a field name that collides with a keyword you can wrap the field name
with quotes.

    let example = MapStruct(
        "struct": 4
    )
    emit example."struct"

### Null

The null type can only be assigned the value `NULL` or `null`.


### Number

Numbers can be declared by using either `Number` or `number`. It can hold both integer and 
fractional numbers and accepts only decimal, hexadecimal or decimal fractions.

    let num = 3 
    let num = 0x03
    let num = 3.1415    

### Strings

String types can be declared in two ways, normally or as a regex string type.

    struct StringStruct {
        normal      : String
        regexString : String</[a-z0-9]+/>
    }

To be able to assign a string to a regex string type the contents of the string have to
match the regex in the type declaration. This is useful to enforce things like naming standards.

The actual string constants can be declared in this way:

    let str = "Hello world"
    let str = "Hello?\nHello!?"
    let str = "Hello \"world\""
   
### Struct

A struct is a structure that consists of fields with typed values. 
Structs are more complicated than enums and supports optional and constant fields
and field default values.

    struct MyStruct < SuperStruct {
        First   : String        
        Second? : String    // optional field
        Third   : String | MyEnum = MyEnum.Third
        Forth   : const : true        
    }

The alert readers might notice that in the above example both `=` and `:` can
be used to set default value. In general, STML is trying to be more accommodating 
for new users by allowing different syntax. Another example is that field separation
can be done with `\n`, `,` and `;`. Like this:

    struct MyCodeMyChoice { name: String, age: Number; newLine: bool
    payload: *} 

The above definition is valid even though is looks weird and is not encouraged. 

#### Optional fields

If a field is optional, the field name can be suffixed by a question mark (`?`).

    struct MyStruct {
        optional? : String
    }
 
Internally the STML parser will then create a union type and a null default value like this.

    struct MyStruct {
        optional : String | NULL = null
    }

 
#### Union types

A union type is when you define a struct field with multiple types separated by
a pipe (`|`) character. Union typed field can, as you would expect, be assigned any 
of the specified types. 

    struct MyStruct {
        alphaNum : String | Number
    }

If a field with union type in a super type is overloaded, the assigned type must 
be assignable to the original field. This is illustrated in the example below.

    struct MySuper {
        alphaNum : String | Number
    }
    
    struct OkStruct < MySuper {
        alphaNum : String   // OK
    }
    
    struct FailStruct < MySuper {
        alphaNum : bool     // Error, bool can't be assigned to String | Number
    }

#### Constants

A field can also be declared as a constant by using the `const` keyword. When a 
field is declared as a constant it must have a default value.

    struct MyPod {
        version : const : 1 
        kind    : const = "Pod" 
    }

Fields is a super type can be redeclared as constants in a child type.

## Emitting YAML

For STML to be useful it needs to produce output, ie YAML. To emit an STML struct or object
as YAML you'll use the `emit` keyword.

    emit 5
    emit MyStruct(alphaNum: 5)

Each `emit` statement will produce a separate YAML structure in the output. The above example will
produce this YAML.

    ---
    5
    ---
    alphaNum: 5

## Functions

In this section a few special functions in STML are described. 

### Include

This is actually a pre-processor step and is used just like in C to import another file into the current.
True to STML you can both use `include` and `import` when doing includes. Include loops will automatically
be detected and stopped a warning will be generated but it won't raise any error.

    #include <another-file.stml>
    import another-file.stml


### Import file

The `import` keyword can be used to import the contents of a file directly from the filesystem. This can be
used for instance for ConfigMaps in kubernetes manifests like this:

    emit ConfigMap(
            name: configuration
            data: {
                id_rsa: import("static/id_rsa")
            }
        )

One important thing to note is that the contents will be read like it's a UTF-8 text content and typed to a string.

### Import directory

Importing individual files can be a good choice if you have only a few files that needs to be read. If you have a lot
of files you can use the `import` keyword to read entire directories. Each file will produce its own key in a 
`Map<String, String>` type and it looks like this: 

    emit ConfigMap(
            name: "configuration"
            data: import(
                    from: ["static", "application"],
                    include: ".*\.txt",
                    exclude: "readme.*"
                  )
        )

This constructor support a few more features than the single file import. 
Only the `from` key needs to be specified, the other ones are optional. All of them can either be a string or
a list of strings. Like this:

    struct import {
        from     : String | List<String>
        include? : String | List<String>
        exclude? : String | List<String>
    }

If `include` and `exclude` is specified any file being read most match one or more include regex and not match
any of the exclude patterns to be used.