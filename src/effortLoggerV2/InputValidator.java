// Created by: Thomas Schafer, 1221505363

package effortLoggerV2;

public class InputValidator {
	private String error;
	private String letterAndNum;
	private String letters;
	private String nums;
	
	// Constructor for InputValidator class, only sets default values
	// Receives no value
	// Returns no value
	InputValidator() {
		this.error = "No Error\n";
		this.letterAndNum = "[a-zA-Z0-9 ]+";
		this.letters = "[a-zA-Z ]+";
		this.nums = "[0-9]+";
	}
	
	
	// Getter for the description of the error that most recently occurred
	// Receives no value
	// Returns a String representing the description of the previous error
	public String getError() {
		return this.error;
	}
	
	// Identifies whether or not a given string consists solely of whitespace,
	//so as to avoid a passed string consisting entirely of spaces being accepted
	// Receives a String representing the inputed string
	// Returns a boolean, in which true indicates that the string is only whitespace
	private boolean checkStringAllWhitespace(String stringInput) {
		// Loop over all characters
		for (int i = 0; i < stringInput.length(); ++i) {
			char currChar = stringInput.charAt(i);
			// If the character is not whitespace, stop looping and return false
			if (!Character.isWhitespace(currChar)) {
				return false;
			}
		}
		
		// If the end is reached, everything must be whitespace
		return true;
	}
	
	// Identifies whether a passed string consists of valid characteristics
	// Receives a String representing the string to check, an int representing
	//the max length of said string, and 2 booleans, one representing whether or
	//not letters should be accepted, and one representing whether or not numbers
	//should be accepted
	// Returns a boolean, in which true indicates that the string is valid
	public boolean validateString(String stringInput, int maxLength, boolean letterAllow, boolean numAllow) {
		boolean validString = false;
		
		// Check that the string is not empty or full of whitespace
		if (stringInput == null || checkStringAllWhitespace(stringInput)) {
			this.error = "String inputted was null or entirely whitespace\n";
			return false;
		}
		// Check that the string does not exceed its length or have a length of 0
		else if (stringInput.length() > maxLength || stringInput.length() <= 0) {
			this.error = "Passed String was of invalid length\n";
			return false;
		}
		// If both letters and numbers are allowed
		else if (letterAllow && numAllow) {
			// Check that all characters are either letters or numbers
			validString = stringInput.matches(letterAndNum);
			if (!validString) {
				this.error = "Non-letters or non-numbers detected in string\n";
				return false;
			}
			else {
				this.error = "No error\n";
				return true;
			}
		}
		// If only letters are allowed
		else if (letterAllow) {
			// Check that all characters are letters
			validString = stringInput.matches(letters);
			if (!validString) {
				this.error = "Non-letter characters detected in string\n";
				return false;
			}
			else {
				this.error = "No error\n";
				return true;
			}
		}
		// If only numbers are allowed
		else if (numAllow) {
			// Check that all characters are numbers
			validString = stringInput.matches(nums);
			if (!validString) {
				this.error = "Non-numbers detected in string\n";
				return false;
			}
			else {
				this.error = "No error\n";
				return true;
			}
		}
		// If neither letters or numbers are allowed, the check itself is invalid
		else {
			this.error = "Invalid check--neither character or numbers are allowed";
			return false;
		}
	}
	
	// Identifies whether or not a given string can be cast into an int
	// Receives a string representing the possible int
	// Returns a boolean, in which true indicates that the string can be cast
	public boolean checkIntCast(String inputString) {
		// See if casting throws an error
		try {
			Integer.parseInt(inputString);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	// Identifies whether or not a given string can be cast into a short
	// Receives a string representing the possible short
	// Returns a boolean, in which true indicates that the string can be cast
	public boolean checkShortCast(String inputString) {
		// See if casting throws an error
		try {
			Short.parseShort(inputString);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	// Identifies whether or not a given string can be cast into a long
	// Receives a string representing the possible long
	// Returns a boolean, in which true indicates that the string can be cast
	public boolean checkLongCast(String inputString) {
		// See if casting throws an error
		try {
			Long.parseLong(inputString);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	// Identifies whether or not a given string can be cast into a double
	// Receives a string representing the possible double
	// Returns a boolean, in which true indicates that the string can be cast
	public boolean checkDoubleCast(String inputString) {
		// See if casting throws an error
		try {
			Double.parseDouble(inputString);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	// Identifies whether or not a given string can be cast into a float
	// Receives a string representing the possible float
	// Returns a boolean, in which true indicates that the string can be cast
	public boolean checkFloatCast(String inputString) {
		// See if casting throws an error
		try {
			Float.parseFloat(inputString);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	// Identifies whether or not a given short fits within a range of values
	// Receives 3 short values, representing the input, minimum, and maximum
	// Returns a boolean, where true means the number is valid
	public boolean validateNum(short inputNum, short minSize, short maxSize) {
		// Check that the input fits within the range
		if (inputNum < minSize || inputNum > maxSize) {
			this.error = "Passed short exceeds size contraints\n";
			return false;
		}
		
		this.error = "No error\n";
		return true;
	}

	// Identifies whether or not a given int fits within a range of values
	// Receives 3 int values, representing the input, minimum, and maximum
	// Returns a boolean, where true means the number is valid
	public boolean validateNum(int inputNum, int minSize, int maxSize) {
		// Check that the input fits within the range
		if (inputNum < minSize || inputNum > maxSize) {
			this.error = "Passed integer exceeds size contraints\n";
			return false;
		}
		
		this.error = "No error\n";
		return true;
	}
	
	
	// Identifies whether or not a given long fits within a range of values
	// Receives 3 long values, representing the input, minimum, and maximum
	// Returns a boolean, where true means the number is valid
	public boolean validateNum(long inputNum, long minSize, long maxSize) {
		// Check that the input fits within the range
		if (inputNum < minSize || inputNum > maxSize) {
			this.error = "Passed long exceeds size contraints\n";
			return false;
		}
		
		this.error = "No error\n";
		return true;
	}

	// Identifies whether or not a given double fits within a range of values
	// Receives 3 double values, representing the input, minimum, and maximum
	// Returns a boolean, where true means the number is valid
	public boolean validateNum(double inputNum, double minSize, double maxSize) {
		// Check that the input fits within the range
		if (inputNum < minSize || inputNum > maxSize) {
			this.error = "Passed double exceeds size contraints\n";
			return false;
		}
		
		this.error = "No error\n";
		return true;
	}
	
	// Identifies whether or not a given float fits within a range of values
	// Receives 3 float values, representing the input, minimum, and maximum
	// Returns a boolean, where true means the number is valid
	public boolean validateNum(float inputNum, float minSize, float maxSize) {
		// Check that the input fits within the range
		if (inputNum < minSize || inputNum > maxSize) {
			this.error = "Passed float exceeds size contraints\n";
			return false;
		}
		
		this.error = "No error\n";
		return true;
	}
}