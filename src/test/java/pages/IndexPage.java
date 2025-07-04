package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import demowebshop.base.BasePage;

public class IndexPage extends BasePage {
	
	public IndexPage() {
		super();
	}

	//Elements
    @FindBy(xpath = "//a[@class='ico-register']")
    private WebElement registerLink;

    //Actions
    public void clickRegisterLink() {
        click(registerLink);
    }
    

}
