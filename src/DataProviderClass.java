package src;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider(name="loginData")
    public Object[][] getDataFromDataProvider(){
        return new Object[][]{
                {"", ""}
        };
    }
}
