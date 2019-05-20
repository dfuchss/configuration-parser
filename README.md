# configuration-parser
[![Build Status](https://travis-ci.org/dfuchss/configuration-parser.svg?branch=master)](https://travis-ci.org/dfuchss/configuration-parser)
[![Coverage Status](https://coveralls.io/repos/github/dfuchss/configuration-parser/badge.svg?branch=master)](https://coveralls.io/github/dfuchss/configuration-parser?branch=master)
[![GitHub issues](https://img.shields.io/github/issues/dfuchss/configuration-parser.svg?style=square)](https://github.com/dfuchss/configuration-parser/issues)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=square)](https://github.com/dfuchss/configuration-parser/blob/master/LICENSE.md)

This repo contains a small project to set attributes of classes and objects.

# HowTo use ..?
## Use to set Int, Float, ...
If you want to set attributes of a class / Object, you have to make sure:
* The class **implements Configurable**
* You've set the **SetterInfo** annotation to the class (for ResourceBundle)

Then just call
* `new ResourceBundleSetter().setAttributes(Foo.class);` for setting static fields of the class

* `new ResourceBundleSetter().setAttributes(obj);` for setting non-static fields of an object obj

## Use to set MyType-Fields
If you want to set attributes of a class / Object which contains fields of types you've created or types which I currently not support you have three options to parse these fields:

* You can annotate the class (-> type of the attribute) you want to parse with `@ClassParser` and set the parser of this type of objects.

* You can annotate the field which can't be parsed with `@SetParser` and set the parser only for the field.

* If the class (-> type of the attribute) only contains basic attributes (int, float, ...) you can also use the MultiLevelParser (Also this parser will be used if no other parser can be found).

# Maven & Co.
If you want to use maven or some similar tool add the following code to your pom:
```xml
<repositories>
	<repository>
		<id>maven-fuchss</id>
		<url>https://raw.githubusercontent.com/dfuchss/maven/releases</url>
	</repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>org.fuchss</groupId>
    <artifactId>configuration-parser</artifactId>
    <version>1.2</version>
  </dependency>
</dependencies>
```

# Examples
## Example 1: Simple Configurable Class
* src/main/java/MyClass.java:
```java
@SetterInfo(res = "conf/myconf")
public class MyClass implements Configurable {
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
      new ResourceBundleSetter().setAttributes(MyClass.class);
    }
}
```
## Example 2: Additional Parsers I
* src/main/java/ClassWithParser.java:
```java
@ClassParser(MyParser.class)
public class ClassWithParser implements Configurable {
    public int attribute;
}
```
* src/main/java/MyParser.java:
```java
  public class MyParser implements Parser {
    @Override
    public Object parseIt(String definition, String[] path) throws Exception {
        ClassWithParser c = new ClassWithParser();
        c.attribute = Integer.parseInt(definition);
        return c;
      }
    }
```
## Example 3: Additional Parsers II
* src/main/java/ClassWithoutParser.java:
```java
public class ClassWithoutParser implements Configurable {
    public int attribute;
}
```
* src/main/java/MyClass.java:
```java
@SetterInfo(res = "conf/myconf")
public class MyClass implements Configurable {
    @SetParser(MyParser.class)
    public static ClassWithoutParser attr;
}
```
