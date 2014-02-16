package taskmgt;

import java.util.regex.Pattern;

/**
 *
 * @author Ray
 */
public class Utilities {
    public static boolean checkEmail(String email){
        Pattern rfc2822 = Pattern.compile(
        "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        return rfc2822.matcher(email).matches();
    }
        public static boolean isName(String testStr){
        Pattern rfc2822 = Pattern.compile("^[a-zA-Z ]+$");
        return rfc2822.matcher(testStr).matches();
    }
}