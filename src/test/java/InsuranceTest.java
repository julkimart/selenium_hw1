import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InsuranceTest {
    private WebDriver driver;
    private String baseUrl;


    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");

        driver = new ChromeDriver();
        //  baseUrl = "http://www.sberbank.ru/ru/person";

        baseUrl = "http://www.sberbank.ru/ru/person/bank_inshure/insuranceprogram/travel_and_shopping";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void testInsurance() throws Exception {
        driver.get(baseUrl + "/");
        //   driver.findElement(By.xpath("//SPAN[@class='lg-menu__text'][text()='Страхование']")).click();

        //  Wait<WebDriver> wait = new WebDriverWait(driver, 5, 1000);
        //  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//A[@href='//www.sberbank.ru/ru/person/bank_inshure/insuranceprogram/travel_and_chopping'][text()='Путешествия и покупки']"))));
        //  driver.findElement(By.xpath("//A[@href='//www.sberbank.ru/ru/person/bank_inshure/insuranceprogram/travel_and_chopping'][text()='Путешествия и покупки']")).click();


        //наличие Страхование путешественников
        assertEquals("Страхование путешественников", driver.findElement(By.xpath("//H3[text()='Страхование путешественников']")).getText());


        //нажать на оформить-онлайн
        driver.findElement(By.xpath("//a[@href='https://online.sberbankins.ru/store/vzr/index.html#/viewCalc']")).click();


        //переход на другое окно
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));

        //выбрать минимальная
        driver.findElement(By.xpath("//*[contains(text(),'Минимальная')]")).click();

        //нажать на оформить
        driver.findElement(By.xpath("//SPAN[@ng-click='save()'][text()='Оформить']")).click();



        //заполняем фамилию и имя и дату рождения
        fillField(By.name("insured0_surname"), "Ivanov");
        fillField(By.name("insured0_name"), "Ivan");
        fillField(By.name("insured0_birthDate"), "05.06.1990");

        //чекбокс гражданин россии
        driver.findElement(By.xpath("//INPUT[@ng-model='formdata.insurer.CITIZENSHIP']")).click();
        //заполняем фамилию и имя и отчество и дату рождения
        fillField(By.name("surname"), "Иванов");
        fillField(By.name("name"), "Иван");
        fillField(By.name("middlename"), "Иванович");
        fillField(By.name("birthDate"), "05.06.1990");
        //чекбокс пол
        driver.findElement(By.xpath("(//INPUT[@ng-model='formdata.insurer.GENDER'])[2]")).click();
        //паспорт
        fillField(By.name("passport_series"), "4215");
        fillField(By.name("passport_number"), "569865");
        fillField(By.name("issueDate"), "20.06.2013");
        fillField(By.name("issuePlace"), "уфмс рф города Казани");



        //проверки
        assertEquals("Ivanov", driver.findElement(By.name("insured0_surname")).getAttribute("value"));
        assertEquals("Ivan", driver.findElement(By.name("insured0_name")).getAttribute("value"));
        assertEquals("Иванов", driver.findElement(By.name("surname")).getAttribute("value"));
        assertEquals("Иван", driver.findElement(By.name("name")).getAttribute("value"));
        assertEquals("Иванович", driver.findElement(By.name("middlename")).getAttribute("value"));

        assertEquals("05.06.1990", driver.findElement(By.name("insured0_birthDate")).getAttribute("value"));
        assertEquals("05.06.1990", driver.findElement(By.name("birthDate")).getAttribute("value"));
        assertEquals("20.06.2013", driver.findElement(By.name("issueDate")).getAttribute("value"));
        assertEquals("4215", driver.findElement(By.name("passport_series")).getAttribute("value"));
        assertEquals("569865", driver.findElement(By.name("passport_number")).getAttribute("value"));
        assertEquals("уфмс рф города Казани", driver.findElement(By.name("issuePlace")).getAttribute("value"));



        //нажать Продолжить
        driver.findElement(By.xpath("//SPAN[@ng-click='save()'][text()='Продолжить']")).click();


        //проверка что контактные данные не заполнены
        assertTrue("Номер телефона вводится в 10-ти значном формате", isElementPresent(driver.findElement(By.xpath("//SPAN[@class='b-text-field-error'][text()='Номер телефона вводится в 10-ти значном формате']"))));

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    private void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }

    public boolean isElementPresent(WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        finally {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
    }


}