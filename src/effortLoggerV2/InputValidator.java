// Created by: Thomas Schafer, 1221505363
package riskReductionPrototype;

public class InputValidator {
	private int maxUsernameLength = 30;
	private int maxPasswordLength = 20;
	private int maxDescLength = 250;
	private int maxSearchLength = 100;
	
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
	
	// Identifies whether or not an inputted username consists of valid characters
	// Receives a String representing the inputted username
	// Returns a boolean, in which true indicates that the username is valid
	public boolean validateUsername(String username) {
		boolean validString = false;
		
		// Check that the username is not empty or full of whitespace
		if (username == null || checkStringAllWhitespace(username)) {
			this.error = "Username inputted was null or entirely whitespace\n";
			return false;
		}
		// Check that the username does not exceed its length or have a length of 0
		else if (username.length() > maxUsernameLength || username.length() <= 0) {
			this.error = "Passed username was of invalid length\n";
			return false;
		}
		
		// Check that the username contains the correct characters, noting errors 
		validString = username.matches("[a-zA-Z0-9_]+");
		if (!validString) {
			this.error = "Username contains invalid characters\n";
		}
		else {
			this.error = "No error\n";
		}
		
		// Return the validity of the username 
		return validString;
	}
	
	// Identifies whether or not an inputted password consists of valid characters
	// Receives a String representing the inputted password
	// Returns a boolean, in which true indicates that the password is valid
	public boolean validatePassword(String password) {
		boolean validString = false;
		
		// Check that the password is not empty or full of whitespace
		if (password == null || checkStringAllWhitespace(password)) {
			this.error = "Password inputted was null or entirely whitespace\n";
			return false;
		}
		// Check that the password does not exceed its length or have a length of 0
		else if (password.length() > maxPasswordLength || password.length() <= 0) {
			this.error = "Passed password was of invalid length\n";
			return false;
		}
		
		// Check that the password contains the correct characters, noting errors 
		validString = password.matches("[a-zA-Z0-9_!#$%^&*]+");
		if (!validString) {
			this.error = "Password contains invalid characters\n";
		}
		else {
			this.error = "No error\n";
		}
		
		// Return the validity of the password 
		return validString;
	}
	
	// Identifies whether or not an inputted description consists of valid characters
	// Receives a String representing the inputted description
	// Returns a boolean, in which true indicates that the description is valid
	public boolean validateDescriptive(String description) {
		boolean validString = false;
		
		// Check that the description is not empty or full of whitespace
		if (description == null || checkStringAllWhitespace(description)) {
			this.error = "Description inputted was null or entirely whitespace\n";
			return false;
		}
		// Check that the description does not exceed its length or have a length of 0
		else if (description.length() > maxDescLength || description.length() <= 0) {
			this.error = "Passed description was of invalid length\n";
			return false;
		}
		
		// Check that the description contains the correct characters, noting errors 
		validString = description.matches("[a-zA-Z0-9_., ]+");
		if (!validString) {
			this.error = "Description contains invalid characters\n";
		}
		else {
			this.error = "No error\n";
		}
		
		// Return the validity of the description 
		return validString;
	}
	
	// Identifies whether or not an inputted search entry consists of valid characters
	// Receives a String representing the inputted search entry
	// Returns a boolean, in which true indicates that the search entry is valid
	public boolean validateSearch(String search) {
		boolean validString = false;
		
		// Check that the search is not empty or full of whitespace
		if (search == null || checkStringAllWhitespace(search)) {
			this.error = "Search inputted was null or entirely whitespace\n";
			return false;
		}
		// Check that the search does not exceed its length or have a length of 0
		else if (search.length() > maxSearchLength || search.length() <= 0) {
			this.error = "Passed search was of invalid length\n";
			return false;
		}
		
		// Check that the search contains the correct characters, noting errors 
		validString = search.matches("[a-zA-Z0-9 ]+");
		if (!validString) {
			this.error = "Search contains invalid characters\n";
		}
		else {
			this.error = "No error\n";
		}
		
		// Return the validity of the search 
		return validString;
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