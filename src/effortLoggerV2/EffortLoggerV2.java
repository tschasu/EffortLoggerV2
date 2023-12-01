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
import javafx.collections.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.Group;

// programmed in VIM, Alvin Wang 1222816023 Tu27, cse360 T 9am, individual assignment 5

public class EffortLoggerV2 extends Application {
	private Stage stage;
	private HashMap<String, String> credentials;
	private InputValidator inputValidator;
	private String storeduser;
	private ArrayList<String[]> loglist;
	private ArrayList<String[]> manlist;
	private Label showuser;

	public static void main(String[] args) {
        launch(args);
	}
	@Override
	// Written by Aaron Cisler
	public void start(Stage primaryStage) throws NoSuchAlgorithmException, InvalidKeySpecException {
		stage = primaryStage;
		credentials = new HashMap<String, String>();
		//for testing purposes, an admin user is shown here to demonstrate how the login procedure will work
		credentials.put("demoemployee", hashGen("pass"));
		credentials.put("demomanager", hashGen("word"));
		inputValidator = new InputValidator();
		storeduser = "";
		loglist = new ArrayList<String[]>();
		manlist = new ArrayList<String[]>();
		showuser = new Label("Hello " + storeduser + "!");

		try {
			Scene loginscene = loginScene(credentials, inputValidator);
			primaryStage.setTitle("EffortLogger V2 Login");
			primaryStage.setScene(loginscene);
			primaryStage.show();
		} catch (Exception exception) {
			System.out.println(exception);
		}
	}

	public Scene empMenu() {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		Button recorder = new Button("Record New Effort Log");
		Button viewData = new Button("View Data");
		Button poker = new Button("Start Planning Poker");
		Button logout = new Button("Log Out");
		root.add(showuser, 2, 0);
		root.add(recorder, 1, 2);
		root.add(viewData, 2, 2);
		root.add(poker, 3, 2);
		root.add(logout, 2, 5);
		
		recorder.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = effortRecorder();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 New Effort Log");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});

		viewData.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = logData();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Viewing All Logs");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});

		poker.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = poker();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Planning Poker");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});

		logout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				storeduser = "";
				try {
					Scene newScene = loginScene(credentials, inputValidator);
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Login");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		Scene newScene = new Scene(root, 600, 400);
		return newScene;
	}

	public Scene manMenu() {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		Button viewData = new Button("View Data");
		Button logout = new Button("Log Out");
		root.add(showuser, 2, 0);
		root.add(viewData, 2, 2);
		root.add(logout, 2, 5);
		viewData.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = manData();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Viewing Logs For Managers");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		logout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				storeduser = "";
				try {
					Scene newScene = loginScene(credentials, inputValidator);
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Login");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		Scene newScene = new Scene(root, 600, 400);
		return newScene;
	}

	public Scene effortRecorder() {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		Button starttimer = new Button("Start Timer");
    	StopWatch stopWatch = new StopWatch();
		Label projLabel = new Label("Select Project");
		ComboBox<String> projectName = new ComboBox<>();
		projectName.getItems().addAll(
			"proj1",
			"proj2",
			"proj3"
		);
		Label stepLabel = new Label("Select Life Cycle Step");
		ComboBox<String> lifeCycleStep = new ComboBox<>();
		lifeCycleStep.getItems().addAll(
			"step1",
			"step2",
			"step3",
			"step4",
			"step5"
		);
		Label catLabel = new Label("Select Effort Log Type");
		ComboBox<String> effortCategory = new ComboBox<>();
		effortCategory.getItems().addAll(
			"cat1",
			"cat2",
			"cat3",
			"cat4",
			"cat5",
			"cat6",
			"cat7",
			"cat8",
			"cat9"
		);
		TextField logName = new TextField();
		logName.setPromptText("Enter Effot Log Name");
		CheckBox check = new CheckBox("Let Manager View This Log");
		Button endtimer = new Button("End Timer and Submit Log");
		Label errmsg = new Label("");
		Button back = new Button("Back to Main Menu");
		starttimer.setOnAction(e -> {
	        if (starttimer.getText().equals("Start Timer") || starttimer.getText().equals("Resume Timer")) {
				stopWatch.start();
				starttimer.setText("Pause Timer");
         	} else {
				stopWatch.pause();
				starttimer.setText("Resume Timer");
        	}
      	});

      	endtimer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				if (stopWatch.getTime().equals("00:00:00")) {
					errmsg.setText("Timer never started");
				} else if (projectName.getValue() == null) {
					errmsg.setText("Project Name Not Selected");
				} else if (lifeCycleStep.getValue() == null) {
					errmsg.setText("Life Cycle Step Not Selected");
				} else if (effortCategory.getValue() == null) {
					errmsg.setText("Effort Category Not Selected");
				} else if (logName.getText().equals("")) {
					errmsg.setText("Log Name Was Not Inputted");
				} else {
					errmsg.setText("");
					stopWatch.stoptimer();
					System.out.println(logName.getText());
					System.out.println(stopWatch.getTime());
					System.out.println(projectName.getValue());
					System.out.println(lifeCycleStep.getValue());
					System.out.println(effortCategory.getValue());
					System.out.println(Boolean.toString(check.isSelected()));
					String[] temp = new String[6];
					temp[0] = logName.getText();
					temp[1] = stopWatch.getTime();
					temp[2] = projectName.getValue();
					temp[3] = lifeCycleStep.getValue();
					temp[4] = effortCategory.getValue();
					temp[5] = Boolean.toString(check.isSelected());
					loglist.add(temp);
					if (temp[5].equals("true")) {
						manlist.add(temp);
					}
					stopWatch.clear();
					try {
						showuser.setText("New Effort Log Was Submitted");
						Scene newScene = empMenu();
						stage.setScene(newScene);
						stage.setTitle("EffortLogger V2 Employee Menu");
					} catch (Exception exception) {
						System.out.println(exception);
					}
				}
			}
    	});

		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = empMenu();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Employee Menu");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		root.add(starttimer, 2, 1);
		root.add(stopWatch, 2, 2);
		root.add(projLabel, 1, 3);
		root.add(projectName, 1, 4);
		root.add(stepLabel, 2, 3);
		root.add(lifeCycleStep, 2, 4);
		root.add(catLabel, 3, 3);
		root.add(effortCategory, 3, 4);
		root.add(logName, 2, 5);
		root.add(check, 3, 5);
		root.add(endtimer, 2, 6);
		root.add(errmsg, 2, 7);
		root.add(back, 2, 8);
		Scene newScene = new Scene(root, 600, 400);
		return newScene;
	}

	public Scene logData() {
		GridPane root = new GridPane();
		Label msg = new Label();
		if (loglist.size() == 0) {
			msg.setText("No Logs Made");
		} else {
			msg.setText(storeduser + "'s Effort Logs");
		}
		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = empMenu();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Employee Menu");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		Label logDisplay = new Label("");
		root.add(msg, 1, 0);
		root.add(back, 3, 0);
		root.add(logDisplay, 2, 1);
		for (int i = 0; i < loglist.size(); i++) {
			Button newLabel = new Button(loglist.get(i)[0]);
			int j = i;
			newLabel.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent ae) {
					logDisplay.setText("Log Name: " + loglist.get(j)[0] + 
					"\nTime Taken: " + loglist.get(j)[1] +
					"\nProject Name: " + loglist.get(j)[2] +
					"\nLife Cycle Step: " + loglist.get(j)[3] +
					"\nEffort Category: " + loglist.get(j)[4]);
				}
			});
			root.add(newLabel, 1, i + 3);
		}
		Scene newScene = new Scene(root, 600, 400);
		return newScene;
	}

	public Scene manData() {
		GridPane root = new GridPane();
		Label msg = new Label();
		if (manlist.size() == 0) {
			msg.setText("No Logs Made");
		} else {
			msg.setText(storeduser + "'s Effort Logs");
		}
		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = manMenu();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Manager Menu");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		Label logDisplay = new Label("");
		root.add(msg, 1, 0);
		root.add(back, 3, 0);
		root.add(logDisplay, 2, 1);
		for (int i = 0; i < manlist.size(); i++) {
			Button newLabel = new Button(manlist.get(i)[0]);
			int j = i;
			newLabel.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent ae) {
					logDisplay.setText("Log Name: " + loglist.get(j)[0] + 
					"\nTime Taken: " + manlist.get(j)[1] +
					"\nProject Name: " + manlist.get(j)[2] +
					"\nLife Cycle Step: " + manlist.get(j)[3] +
					"\nEffort Category: " + manlist.get(j)[4]);
				}
			});
			root.add(newLabel, 1, i + 3);
		}
		Scene newScene = new Scene(root, 600, 400);
		return newScene;
	}

	public Scene poker() {
		//button
		Button createCard = new Button("New Card");

		TextField search = new TextField();
		search.setPromptText("Search cards");
		search.setMaxWidth(150);
		Text info1 = new Text("Project X");
		info1.setX(20);
		info1.setY(30);
		info1.getTransforms().add(new Rotate(-20, 50, 30));
		Text info2 = new Text("Project Y");
		info2.setX(110);
		info2.setY(30);
		Text info3 = new Text("Project Z");
		info3.setX(200);
		info3.setY(30);
		info3.getTransforms().add(new Rotate(20, 50, 30));
		Text point1 = new Text("15 points");
		point1.setX(20);
		point1.setY(48);
		point1.getTransforms().add(new Rotate(-20, 50, 30));
		Text point2 = new Text("50 points");
		point2.setX(110);
		point2.setY(48);
		Text point3 = new Text("39 points");
		point3.setX(200);
		point3.setY(48);
		point3.getTransforms().add(new Rotate(20, 50, 30));
		Text hour1 = new Text("5 hours");
		hour1.setX(20);
		hour1.setY(66);
		hour1.getTransforms().add(new Rotate(-20, 50, 30));
		Text hour2 = new Text("20 hours");
		hour2.setX(110);
		hour2.setY(66);
		Text hour3 = new Text("3 hours");
		hour3.setX(200);
		hour3.setY(66);
		hour3.getTransforms().add(new Rotate(20, 50, 30));

		//cards
		Rectangle card1 = new Rectangle(10, 10, 75, 125);
		card1.setStroke(Color.BLACK);
		card1.setFill(Color.GREY);
		card1.getTransforms().add(new Rotate(-20, 50, 30));
		Rectangle card2 = new Rectangle(100, 10, 75, 125);
		card2.setStroke(Color.BLACK);
		card2.setFill(Color.GREY);
		Rectangle card3 = new Rectangle(190, 10, 75, 125);
		card3.setStroke(Color.BLACK);
		card3.setFill(Color.GREY);
		card3.getTransforms().add(new Rotate(20, 50, 30));
		
		BorderPane no = new BorderPane();
		Group root = new Group();
		root.getChildren().addAll(card1, card2, card3, info1, info2, info3, point1, point2, point3, hour1, hour2, hour3);
		no.setCenter(root);
		VBox stuff = new VBox(createCard);
		stuff.setAlignment(Pos.CENTER);
		no.setBottom(stuff);
		no.setTop(search);

		Button back = new Button("Back");
		back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					Scene newScene = empMenu();
					stage.setScene(newScene);
					stage.setTitle("EffortLogger V2 Employee Menu");
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		no.setLeft(back);

		Scene newScene = new Scene(no, 600, 400);
		return newScene;
	}

	public Scene loginScene(HashMap<String, String> credentials, InputValidator inputValidator) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Initialize all UI entities
		GridPane root = new GridPane();
		Button submitButton = new Button("Submit");
		Label acceptState = new Label("");
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		Button addUser = new Button("Add User");
		
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
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				try {
					boolean userCheck = inputValidator.validateString(username.getText(), 50, true, true);
					boolean passCheck = inputValidator.validateString(password.getText(), 50, true, true);
					
					if (userCheck && passCheck) {				
						boolean authorized = verifyLogin(username.getText(), password.getText(), credentials);
						if (authorized) {
							acceptState.setText("Login Accepted");
							storeduser = username.getText();
							showuser.setText("Hello " + storeduser + "!");
							if (username.getText().equals("demomanager")) {
								Scene newScene = manMenu();
								stage.setScene(newScene);
								stage.setTitle("EffortLogger V2 Manager Menu");
							} else {
								Scene newScene = empMenu();
								stage.setScene(newScene);
								stage.setTitle("EffortLogger V2 Employee Menu");
							}
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
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		// OLD LOGIN EVENT HANDLER
		// submitButton.setOnAction(e -> {
		// 	try {
		// 		boolean userCheck = inputValidator.validateString(username.getText(), 50, true, true);
		// 		boolean passCheck = inputValidator.validateString(password.getText(), 50, true, true);
				
		// 		if (userCheck && passCheck) {				
		// 			boolean authorized = verifyLogin(username.getText(), password.getText(), credentials);
		// 			if (authorized) {
		// 				acceptState.setText("Login Accepted");
		// 				// Scene newScene = effortRecorder(username.getText());
		// 				GridPane newRoot = effortRecorder(username.getText());
		// 				username.clear();
		// 				password.clear();
		// 				Scene newScene = new Scene(newRoot, 300, 200);
		// 				//return newScene;
		// 			}
		// 			else {
		// 				acceptState.setText("Login Rejected");
		// 			}
		// 		}
		// 		else {
		// 			acceptState.setText("Invalid Input");
		// 		}
		// 	} 
		// 	catch (Exception exception) {
		// 		System.out.println(exception);
		// 	}
		// });

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
		Scene scene = new Scene(root, 600, 400);
		return scene;
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
