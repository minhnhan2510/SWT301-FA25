
import nguyenminhnhan.example.AccountService;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private static AccountService accountService;

    @BeforeAll
    static void setup() {
        accountService = new AccountService();
    }

    @Test
    @DisplayName("Kiểm tra hàm isValidEmail()")
    void testIsValidEmail() {
        assertTrue(accountService.isValidEmail("user@example.com"));
        assertFalse(accountService.isValidEmail("wrongemail.com"));
        assertFalse(accountService.isValidEmail(""));
        assertFalse(accountService.isValidEmail(null));
    }

    @Test
    @DisplayName("Kiểm tra hàm isValidPassword()")
    void testIsValidPassword() {
        assertTrue(accountService.isValidPassword("Abc@1234"));
        assertFalse(accountService.isValidPassword("abc123"));  // không có ký tự đặc biệt và chữ hoa
        assertFalse(accountService.isValidPassword(""));         // rỗng
        assertFalse(accountService.isValidPassword(null));       // null
    }

    @Test
    @DisplayName("Kiểm tra đăng ký tài khoản từ file CSV")
    void testRegisterAccountFromCSV() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-data.csv")) {
            assertNotNull(inputStream, "Không tìm thấy file test-data.csv trong resources!");

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                if (lineNum == 1) continue; // bỏ qua header

                String[] data = line.split(",");
                if (data.length < 3) continue;

                String email = data[0].trim();
                String password = data[1].trim();
                boolean expected = Boolean.parseBoolean(data[2].trim());

                boolean actual = accountService.registerAccount(email, password);

                assertEquals(expected, actual, "Sai tại dòng " + lineNum + ": " + email);
            }

        } catch (IOException e) {
            fail("Không đọc được file test-data.csv: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        accountService = null;
    }
}
