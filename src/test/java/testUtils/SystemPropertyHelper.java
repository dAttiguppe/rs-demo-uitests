package testUtils;

import static rs.com.testDataConstants.TestConstants.TEARDOWN_SYSTEM_PROPERTY_NAME;
import static rs.com.testDataConstants.TestConstants.YES;

public class SystemPropertyHelper {

    public static String getSystemTeardownOption() {
        return System.getProperty(TEARDOWN_SYSTEM_PROPERTY_NAME);
    }

    public static boolean isTeardown() {
        return isYes(getSystemTeardownOption());
    }

     public static boolean isYes(String check) {
        return YES.equalsIgnoreCase(check);
    }
}
