package providers;

import org.testng.annotations.DataProvider;

public class DataProviders {

	private static final String[] uniqueEmails = { "veronika+login1@test.sk", "veronika+login2@test.sk",
			"veronika+login3@test.sk", "veronika+login4@test.sk", "veronika+login5@test.sk", "veronika+login6@test.sk",
			"veronika+login7@test.sk", "veronika+login8@test.sk", "veronika+login9@test.sk", "veronika+login10@test.sk",
			"veronika+login11@test.sk" };

	@DataProvider(name = "validRegisterData")
	 public static Object[][] validRegisterData() {
		 
		 return new Object[][] {
			 
			 {"Veronika", "Testovanie1", "veronikalogin1@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie2", "veronikalogin2@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie3", "veronikalogin3@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie4", "veronikalogin4@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie5", "veronikalogin5@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie6", "veronikalogin6@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie7", "veronikalogin7@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie8", "veronikalogin8@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie9", "veronikalogin9@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie10", "veronikalogin10@test.sk", "Test1234!", "Test1234!"},
	         {"Veronika", "Testovanie11", "veronikalogin11@test.sk", "Test1234!", "Test1234!"},
		 };
		 
	 }

	@DataProvider(name = "invalidLoginData")
	public static Object[][] invalidLoginData() {

		return new Object[][] {

				// valid email, invalid password (too short)
				{ uniqueEmails[0], "djf", "Valid email + too short password",
						new String[] { "The credentials provided are incorrect" } },

				// valid email, empty password
				{ uniqueEmails[1], "", "Valid email + empty password",
						new String[] { "The credentials provided are incorrect" } },

				// empty email, valid password
				{ "", "Test1234!", "Empty email + valid password", new String[] { "No customer account found" } },

				// invalid email format, invalid password
				{ "veron@tes.sk", "1254", "Invalid email format + short password",
						new String[] { "No customer account found" } },

				// completely empty credentials
				{ "", "", "Empty email and password", new String[] { "No customer account found" } },

				// too long password
				{ uniqueEmails[2], "a".repeat(101), "Valid email + too long password",
						new String[] { "The credentials provided are incorrect" } },

				// password with no digits
				{ uniqueEmails[3], "PasswordOnly", "Valid email + no digits in password",
						new String[] { "The credentials provided are incorrect" } },

				// password with no letters
				{ uniqueEmails[4], "12345678", "Valid email + no letters in password",
						new String[] { "The credentials provided are incorrect" } },

				// password only with spaces
				{ uniqueEmails[5], "        ", "Valid email + spaces-only password",
						new String[] { "The credentials provided are incorrect" } },

				// password with only special characters
				{ uniqueEmails[6], "!@#$%^&*", "Valid email + special chars only",
						new String[] { "The credentials provided are incorrect" } },

				// password with space inside
				{ uniqueEmails[7], "Tes t12! ", "Valid email + password with space",
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
				{ "", "Forrest", "forrest@gmail.com", "pass1234", "pass1234", "Registration with empty firstname",
						new String[] { "First name is required." } },

				// Empty lastname
				{ "Veronika", "", "forrest@gmail.com", "pass1234", "pass1234", "Registration with empty lastname",
						new String[] { "Last name is required." } },

				// Empty email
				{ "Veronika", "Forrest", "", "pass1234", "pass1234", "Registration with empty email",
						new String[] { "Email is required." } },

				// Empty password
				{ "", "Forrest", "forrest@gmail.com", "", "pass1234", "Registration with empty password",
						new String[] { "Password is required." } },

				// Empty confirm password
				{ "", "Forrest", "forrest@gmail.com", "pass1234", "", "Registration with empty confirm password",
						new String[] { "Password is required." } },

		};
	}

}
