package home.fox.visitors.parser;

import java.lang.reflect.Field;

import home.fox.visitors.Visitable;
import home.fox.visitors.toParse.StringB;

/**
 * An internal StringB parser.
 *
 * @author Dominik Fuchss
 *
 */
public class StringBParser implements Parser {
	@Override
	public boolean parse(Visitable obj, Field field, String definition) throws Exception {
		if (!Parser.super.parse(obj, field, definition)) {
			return false;
		}
		StringB string = new StringB();
		string.string = "" + definition;
		field.set(obj, string);
		return true;
	}
}
