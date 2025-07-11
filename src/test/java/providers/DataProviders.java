package providers;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "invalidLoginData")
	public static Object[][] invalidLoginData() {

	    return new Object[][] {

	        // valid email, invalid password (too short)
	        { "veronika.test9@test.sk", "djf", "Login with valid email + too short password",
	            new String[] { "The credentials provided are incorrect" } },

	        // valid email, empty password
	        { "veronika.test9@test.sk", "", "Login with valid email + empty password",
	            new String[] { "The credentials provided are incorrect" } },

	        // empty email, valid password
	        { "", "Test1234!", "Login with empty email + valid password",
	            new String[] { "No customer account found" } },

	        // invalid email format, invalid password
	        { "veron@tes.sk", "1254", "Login with invalid email format + invalid too short password",
	            new String[] { "No customer account found"} },

	        // completely empty credentials
	        { "", "", "Login with empty credentials",
	            new String[] { "No customer account found" } },

	        // too long password
	        { "veronika.test9@test.sk", "a".repeat(101), "Login with valid email + too long password",
	            new String[] { "The credentials provided are incorrect" } },

	        // password with no digits
	        { "veronika.test9@test.sk", "PasswordOnly", "Login with valid email + password missing digits",
	            new String[] { "The credentials provided are incorrect" } },

	        // password with no letters
	        { "veronika.test9@test.sk", "12345678", "Login with valid email + password missing letters",
	            new String[] { "The credentials provided are incorrect" } },

	        // password only with spaces
	        { "veronika.test9@test.sk", "        ", "Valid email + password with only spaces",
	            new String[] { "The credentials provided are incorrect" } },

	        // password with only special characters
	        { "veronika.test9@test.sk", "!@#$%^&*", "Valid email + password with only special characters",
	            new String[] { "The credentials provided are incorrect" } },

	        // password with space inside
	        { "veronika.test9@test.sk", "Tes t12! ", "Valid email + password with space inside",
	            new String[] { "The credentials provided are incorrect" } },

	    };
	}

	// This test is a demonstration of a potential XSS input scenario.
	// It is NOT intended to exploit the target system but to show awareness of
	// common web vulnerabilities.
	@DataProvider(name = "securityInputData")
	public static Object[][] securityInputData() {
		return new Object[][] {
				// XSS attempts
				{ "<script>alert('XSS')</script>", "Test1234!", "XSS attack in email field" },
				{ "veronika@test.sk", "<script>alert('XSS')</script>", "XSS attack in password field" },

				// SQL Injection attempts
				{ "' OR '1'='1", "password", "SQL Injection in email field" },
				{ "veronika@test.sk", "' OR '1'='1", "SQL Injection in password field" },
				{ "' OR 1=1 --", "' OR 1=1 --", "SQL Injection in both fields" } };
	};
	
	@DataProvider(name = "invalidRegisterData")
	public static Object[][] invalidRegisterData() {
		return new Object[][] {
			
			// Empty firstname
			{"", "Forrest", "forrest@gmail.com", "pass1234", "pass1234", "Registration with empty firstname",
				new String[] {"First name is required."}},
			
			// Empty lastname
			{"Veronika", "", "forrest@gmail.com", "pass1234", "pass1234", "Registration with empty lastname",
				new String[] {"Last name is required."}},
			
			// Empty email
			{"Veronika", "Forrest", "", "pass1234", "pass1234", "Registration with empty email",
				new String[] {"Email is required."}},
			
			// Empty password
			{"", "Forrest", "forrest@gmail.com", "", "pass1234", "Registration with empty password",
				new String[] {"Password is required."}},
			
			// Empty confirm password
			{"", "Forrest", "forrest@gmail.com", "pass1234", "", "Registration with empty confirm password",
				new String[] {"Password is required."}},
			
		};
	}

}
