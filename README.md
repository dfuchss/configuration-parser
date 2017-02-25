# configuration-parser
[![Build Status](https://travis-ci.org/fuchss-dominik/visitors.svg?branch=master)](https://travis-ci.org/fuchss-dominik/visitors)
[![](https://jitpack.io/v/fuchss-dominik/visitors.svg)](https://jitpack.io/#fuchss-dominik/visitors)
[![Coverage Status](https://coveralls.io/repos/github/fuchss-dominik/visitors/badge.svg?branch=master)](https://coveralls.io/github/fuchss-dominik/visitors?branch=master)
[![GitHub issues](https://img.shields.io/github/issues/fuchss-dominik/visitors.svg?style=square)](https://github.com/fuchss-dominik/visitors/issues)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=square)](https://raw.githubusercontent.com/fuchss-dominik/visitors/master/LICENSE.md)

This small project realizes a framework to create visitors to set attributes in classes and objects via configuration files.

# HowTo use ..?
## Use to set Int, Float, ...
If you want to visit a Class / Object, you have to make sure:
* The class **implements Visitable**
* You've set the **VisitInfo** annotation to the class (for ResourceBundle) & set **visit=true**

Then just call
* `Visitor.getNewVisitor().visit(Foo.class);` for visiting static fields of the class

* `Visitor.getNewVisitor().visit(obj);` for visiting non-static fields of an object obj

## Use to set MyType-Fields
If you want to visit a Class / Object which contains fields of types you've created or types which I currently not support you have three options to parse these fields:

* You can annotate the class (-> type of the attribute) you want to parse with `@ClassParser` and set the parser of this type of objects.

* You can annotate the field which can't be parsed with `@SetParser` and set the parser only for the field.

* If the class (-> type of the attribute) only contains basic attributes (int, float, ...) you can also use the MultiLevelParser (Also this parser will be used if no other parser can be found).

# Maven & Co.
If you want to use maven or some similar tool, I would recommend to use something like https://jitpack.io/ to get the code.

# Examples
## Example 1: Simple Visitable Class
* src/main/java/MyClass.java:
```java
@VisitInfo(res = "conf/myconf", visit = true)
public class MyClass implements Visitable {
	public static long longValue;
	public static MoreComplex complex;
}
```
* src/main/resources/conf/myconf.properties:
```
longValue=42
complex.fieldA=ABC
```
* src/main/java/Main.java:
```java
public class Main {
    public static void main(String[] args) {
      Visitor.getNewVisitor().visit(MyClass.class);
    }
}
```
## Example 2: Additional Parsers I
* src/main/java/ClassWithParser.java:
```java
@ClassParser(MyParser.class)
public class ClassWithParser implements Visitable {
    public int attribute;
}
```
* src/main/java/MyParser.java:
```java
  public class MyParser implements Parser {
    @Override
    public boolean parse(Visitable obj, Field field, String definition, String[] path) throws Exception {
        if (!Parser.super.parse(obj, field, definition, path)) {
          return false;
        }
        ClassWithParser c = new ClassWithParser();
        c.attribute = Integer.parseInt(definition);
        field.set(obj, c);
        return true;
      }
    }
```
## Example 3: Additional Parsers II
* src/main/java/ClassWithoutParser.java:
```java
public class ClassWithoutParser implements Visitable {
    public int attribute;
}
```
* src/main/java/MyClass.java:
```java
@VisitInfo(res = "conf/myconf", visit = true)
public class MyClass implements Visitable {
    @SetParser(MyParser.class)
    public static ClassWithoutParser attr;
}
```
