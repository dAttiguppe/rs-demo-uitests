package rs.com.test_utils;

import static rs.com.test_data_constants.TestConstants.TEARDOWN_SYSTEM_PROPERTY_NAME;
import static rs.com.test_data_constants.TestConstants.YES;

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
