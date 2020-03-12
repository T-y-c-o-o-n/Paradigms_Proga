package expression;

import expression.exceptions.ParsingException;
import expression.generic.GenericTabulator;
import expression.generic.Tabulator;

public class Main {
	public static void main(String[] args) throws Exception {
		Tabulator tabulator = new GenericTabulator();
		Object[][][] res = tabulator.tabulate("l", "10", -5,0,0,0,0,0);
		return;
	}
}