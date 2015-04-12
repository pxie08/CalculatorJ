package codechallenge;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;

public class CalculatorTest {

	@Test
	public void testCheckInput() {
		Calculator calculator = new Calculator();
		String correctProblem = "10+15- 3 * 6 / 2 % 4 ";
		String wrongProblem = "10++a4* 9";
		assertTrue(calculator.checkInput(correctProblem));
		assertFalse(calculator.checkInput(wrongProblem));
	}

	@Test
	public void testSplitInput() {
		Calculator calculator = new Calculator();
		String problem = "1+2*3-4/5%6";
		LinkedList<String> answer = new LinkedList<String>();
		for(int i = 0; i < problem.length(); i++){
			answer.add(String.valueOf(problem.charAt(i)));
		}
		Assert.assertEquals(answer, calculator.splitInput(problem));
	}

	@Test
	public void testGetNextOperIdx() {
		Calculator calculator = new Calculator();
		LinkedList<String> expected = new LinkedList<String>();
		expected.add("100");
		expected.add("%");
		expected.add("3");
		int answer = 1;
		Assert.assertEquals(answer, calculator.getNextOperIdx(expected));
	}

	@Test
	public void testCalcByOrder() {
		Calculator calculator = new Calculator();
		String problem = "1+2*3-5%3/2";
		LinkedList<String> input = new LinkedList<String>();
		for(int i = 0; i < problem.length(); i++){
			input.add(String.valueOf(problem.charAt(i)));
		}
		LinkedList<String> answer = new LinkedList<String>();
		answer.add("6.0");
		Assert.assertEquals(answer, calculator.calcByOrder(input));
	}

	@Test
	public void testCalculate() {
		Calculator calculator = new Calculator();
		String problem = "10+15- 3 * 6 / 2 % 4 ";
		double answer = 24.0;
		assertEquals(answer, calculator.calculate(problem), 0.00001);
	}

}
