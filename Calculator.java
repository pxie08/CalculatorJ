package codechallenge;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculator - Simple calculator that calculates
 *              combinations of binary operations
 * @author Patrick Xie
 */
public class Calculator {

	/**
	 * Addition
	 * @param num1
	 * @param num2
	 * @return addition of num1 and num2
	 */
	private final float add(float num1, float num2){
		return num1 + num2;
	}
	
	/**
	 * Subtraction
	 * @param num1
	 * @param num2
	 * @return subtraction of num1 and num2
	 */
	private final float subtract(float num1, float num2){
		return num1 - num2;
	}
	
	/**
	 * Multiplication
	 * @param num1
	 * @param num2
	 * @return multiplication of num1 and num2
	 */
	private final float multiply(float num1, float num2){
		return num1 * num2;
	}
	
	/**
	 * Division
	 * @param num1
	 * @param num2
	 * @return division of num1 and num2
	 */
	private final float divide(float num1, float num2){
		return num1 / num2;
	}
	
	/**
	 * Modulus
	 * @param num1
	 * @param num2
	 * @return modulus of num1 and num2
	 */
	private final float mod(float num1, float num2){
		return num1 % num2;
	}
	
	/**
	 * Checks input string if containing only digits and +,-,*,/,%,. symbols
	 * @param s String of the problem to be calculated
	 * @return true if string only contains allowed characters, false if not
	 */
	protected boolean checkInput(String s){
		String noSpacesInput = s.replaceAll("\\s", "");
		Pattern p = Pattern.compile("[^\\+\\-\\*\\/\\%\\d+\\.]");
		Matcher m = p.matcher(noSpacesInput);
		
		if(m.find()){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Separates numbers and operators into a LinkedList of Strings
	 * @param s String of the problem to be calculated
	 * @return LinkedList of Strings
	 */
	protected LinkedList<String> splitInput(String s){
		String inputNoSpace = s.replaceAll("\\s", "");
		LinkedList<String> strArray = new LinkedList<String>();
		int idxWithOper = 0;
		
		for(int i = 0; i < inputNoSpace.length(); i++){
			if(i == idxWithOper){
				continue;
			} else if(inputNoSpace.charAt(i) == '+' || 
					inputNoSpace.charAt(i) == '-' ||
					inputNoSpace.charAt(i) == '*' ||
					inputNoSpace.charAt(i) == '/' ||
					inputNoSpace.charAt(i) == '%'){
				strArray.add(inputNoSpace.substring(idxWithOper, i));
				strArray.add(inputNoSpace.substring(i, i+1));
				idxWithOper = i+1;
			}
		}
		
		strArray.add(inputNoSpace.substring(idxWithOper, inputNoSpace.length()));
		return strArray;
	}
	
	/**
	 * Gets the index of the next operator from the beginning of the list
	 * @param inputArray LinkedList returned from splitInput method
	 * @return int index
	 */
	protected int getNextOperIdx(LinkedList<String> inputArray){
		for(int i = 1; i < inputArray.size(); i += 2){
			if(inputArray.get(i).matches("\\*|\\/|\\%")){
				return i;
			}
		}
		for(int j = 1; j < inputArray.size(); j += 2){
			if(inputArray.get(j).matches("\\+|\\-")){
				return j;
			} 
		}
		return 0;
	}
	
	/**
	 * Recursively calculates binary operations in order of associativity
	 * Sets the answer to same index of operator
	 * Removes the numbers in the current operation
	 * Base case is one number in the list
	 * @param inputArray LinkedList returned from splitInput method
	 * @return LinkedList of size one with the answer 
	 */
	protected LinkedList<String> calcByOrder(LinkedList<String> inputArray){
		
		int i = getNextOperIdx(inputArray);
		
		switch(inputArray.get(i)){
			case "*": 
				inputArray.set(i,Float.toString(multiply(Float.parseFloat(inputArray.get(i-1)),Float.parseFloat(inputArray.get(i+1)))));
				inputArray.remove(i-1);
				inputArray.remove(i);
				calcByOrder(inputArray);
				break;
			case "/": 
				inputArray.set(i,Float.toString(divide(Float.parseFloat(inputArray.get(i-1)),Float.parseFloat(inputArray.get(i+1)))));
				inputArray.remove(i-1);
				inputArray.remove(i);
				calcByOrder(inputArray);
				break;
			case "%": 
				inputArray.set(i,Float.toString(mod(Float.parseFloat(inputArray.get(i-1)),Float.parseFloat(inputArray.get(i+1)))));
				inputArray.remove(i-1);
				inputArray.remove(i);
				calcByOrder(inputArray);
				break;
			case "+": 
				inputArray.set(i,Float.toString(add(Float.parseFloat(inputArray.get(i-1)),Float.parseFloat(inputArray.get(i+1)))));
				inputArray.remove(i-1);
				inputArray.remove(i);
				calcByOrder(inputArray);
				break;
			case "-": 
				inputArray.set(i,Float.toString(subtract(Float.parseFloat(inputArray.get(i-1)),Float.parseFloat(inputArray.get(i+1)))));
				inputArray.remove(i-1);
				inputArray.remove(i);
				calcByOrder(inputArray);
				break;
			default:
				break;
		}

		return inputArray;
	}
	
	/**
	 * Calculates the problem
	 * @param input String of the problem
	 * @return total (answer) or a message of invalid input and 0
	 */
	public float calculate(String input){
		if(checkInput(input)){
			LinkedList<String> inputArray = splitInput(input);
			return Float.parseFloat(calcByOrder(inputArray).get(0));
		} else {
			System.out.println("Invalid input.");
			return 0;
		}
	}
	
	/**
	 * Solves the problem 10+3*2*4%2*3-6
	 * @param args
	 */
	public static void main(String[] args) {		
		String input = "10+3*2*4%2*3-6";
		Calculator calculator = new Calculator();
		System.out.println("Problem: " + input + ". Answer: " + calculator.calculate(input) + ".");
	}

}
