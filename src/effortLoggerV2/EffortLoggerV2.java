package effortLoggerV2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.math.BigInteger;
import java.util.HashMap; 

public class EffortLoggerV2 extends Application {
	public static void main(String[] args) {
        launch(args);
	}
	
	@Override
	// Written by Aaron Cisler
	public void start(Stage primaryStage) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Initialize all UI entities
		GridPane root = new GridPane();
		Button submitButton = new Button("Submit");
		Label acceptState = new Label("");
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		Button addUser = new Button("Add User");
		HashMap<String, String> credentials = new HashMap<String, String>();
		//for testing purposes, an admin user is shown here to demonstrate how the login procedure will work
		credentials.put("admin", hashGen("password"));
		InputValidator inputValidator = new InputValidator();
		
		// Setup the window
		root.setHgap(10);
		root.setVgap(10);
		root.add(username, 1, 0);
		root.add(password, 1, 1);
		root.add(submitButton, 1, 2);
		root.add(acceptState, 1, 3);
		root.add(addUser, 2, 2);
		
		// Create button action: call verifyLogin() with the user's given username and password
		// If verifyLogin() returns true, displays to the user that the login was accepted
		// Otherwise, displays to the user that the login was rejected
		submitButton.setOnAction(e -> {
			try {
				boolean userCheck = inputValidator.validateString(username.getText(), 50, true, true);
				boolean passCheck = inputValidator.validateString(password.getText(), 50, true, true);
				
				if (userCheck && passCheck) {				
					boolean authorized = verifyLogin(username.getText(), password.getText(), credentials);
					if (authorized) {
						acceptState.setText("Login Accepted");
						username.clear();
						password.clear();
					}
					else {
						acceptState.setText("Login Rejected");
					}
				}
				else {
					acceptState.setText("Invalid Input");
				}
			} 
			catch (Exception exception) {
				System.out.println(exception);
			}
		});

		addUser.setOnAction(e -> {
			boolean userCheck = inputValidator.validateString(username.getText(), 50, true, true);
			boolean passCheck = inputValidator.validateString(password.getText(), 50, true, true);
			boolean authorized = true;
			
			try {
				if (userCheck && passCheck) {
					for (HashMap.Entry<String, String> entry : credentials.entrySet()) {
						if (username.getText().equals(entry.getKey())) {
							authorized = false;
						}
					}
					
					if (authorized) {
						credentials.put(username.getText(), hashGen(password.getText()));
						acceptState.setText("User Added");
						username.clear();
						password.clear();
					}
					else {
						acceptState.setText("Username Taken");
					}
				}
				else {
					acceptState.setText("Username/Password Invalid");
				}
			}
			catch (Exception exception) {
				System.out.println(exception);
			}
		});

		// Create the Scene
		Scene scene = new Scene(root, 300, 200);
		primaryStage.setTitle("EffortLogger V2");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//Written by Aaron Cisler
	private boolean verifyLogin(String username, String password, HashMap<String, String>credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
		boolean authorized = false;
		for (HashMap.Entry<String, String> entry : credentials.entrySet()) {
			if (username.equals(entry.getKey())) {
				authorized = validatePassword(password, entry.getValue());
				break;
			}
			else {
				continue;
			}
		}
		return authorized;
	}
	
	// Written by Alvin Wang
	//this function generates the hash used for credential checking
    private static String hashGen(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //generate a salt for the user
        byte[] salt = saltGen();
        //generate a PBE key using the password (converted to a char array) and the salt in 65536 iterations to generate a 512 length key
        PBEKeySpec key = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
        //instantiate a key factory to make the hash using PDKDF2
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        //create the hash using the key factory and the key previously generated
        byte[] hash = keyFact.generateSecret(key).getEncoded();
        //return a string containing the iterations, the salt in hex, and the hash in hex
        return "65536:" + toHex(salt) + ":" + toHex(hash);
    }
    // Written by Alvin Wang
    //this function generates the salt for hash generation
    private static byte[] saltGen() throws NoSuchAlgorithmException {
        //using a secure random number generator, randomly generate and return 16 bytes
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    // Written by Alvin Wang
    //this function validates the user password inputted with the database
    private static boolean validatePassword(String password, String check) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //split the database password into 3 parts to get the values
        String[] split = check.split(":");
        int it = Integer.parseInt(split[0]);
        byte[] salt = fromHex(split[1]);
        byte[] hash = fromHex(split[2]);
        //generate another hash using the same values from the database password
        PBEKeySpec key = new PBEKeySpec(password.toCharArray(), salt, it, hash.length * 8);
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] checkHash = keyFact.generateSecret(key).getEncoded();
        //perform the xor operation on the hash from the database and the hash just generated
        int validated = hash.length ^ checkHash.length;
        for(int i = 0; i < hash.length && i < checkHash.length; i++) {
            validated |= hash[i] ^ checkHash[i];
        }
        //if the result of the xor operation is 0, it means there is no difference between the hashes, and the password is validated
        //if the result of the xor operation is 1, it means there IS a difference between the hashes, and the password is rejected
        return validated == 0;
    }
    // Written by Alvin Wang
    //converts a byte array into a hex string
    private static String toHex(byte[] ba) throws NoSuchAlgorithmException {
        BigInteger bigInt = new BigInteger(1, ba);
        String s = bigInt.toString(16);
        int pad = (ba.length * 2) - s.length();
        if (pad > 0) {
            return String.format("%0" + pad + "d", 0) + s;
        } else {
            return s;
        }
    }
    // Written by Alvin Wang
    //converts a hex string into a byte array
    private static byte[] fromHex(String s) throws NoSuchAlgorithmException {
        byte[] ba = new byte[s.length() / 2];
        for (int i = 0; i < ba.length ; i++) {
            ba[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
}
   






