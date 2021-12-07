package util;

import com.epam.courses.java.final_project.util.PasswordCryptoPbkdf2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class
PasswordCryptoPbkdf2Test {

    private final String pwd = "pwd";

    @Test
    void cryptoTest() {
        String expected = PasswordCryptoPbkdf2.hashPwd(pwd);
        Assertions.assertTrue(PasswordCryptoPbkdf2.validatePwd(pwd, expected));
    }
}
