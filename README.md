# visitors
This small project realizes a framework to create visitors to set attributes in classes and objects via configuration files.

# HowTo use ..?
## Use to set Int, Float, ...
If you want to visit a Class / Object, you have to make sure:
* The class implements Visitable
* You've set the VisitInfo annotation to the class (for ResourceBundle) & set visit=true

Then just call
* Visitor.getNewVisitor().visit(Foo.class); for visiting static fields of the class

* Visitor.getNewVisitor().visit(obj); for visiting non-static fields of an object obj

## Use to set MyType-Fields
If you want to visit a Class / Object which contains fields of types you've created or types which I currently not support you have three options to parse these fields:

* You can annotate the class (-> type of the attribute) you want to parse with @ClassParser and set the parser of this type of objects.

* You can annotate the field which can't be parsed with @SetParser and set the parser only for the field.

* If the class (-> type of the attribute) only contains basic attributes (int, float, ...) you can also use the TwoLevelParser.

# Maven & Co.
If you want to use maven or some similar tool, I would recommend to use something like https://jitpack.io/ to get the code.