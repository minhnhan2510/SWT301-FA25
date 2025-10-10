import com.opencsv.CSVReader;
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
        assertTrue(accountService.isValidEmail("user@example.com"), "Email hợp lệ nhưng bị đánh sai");
        assertFalse(accountService.isValidEmail("wrongemail.com"), "Email sai định dạng vẫn hợp lệ");
        assertFalse(accountService.isValidEmail(""), "Email rỗng vẫn hợp lệ");
        assertFalse(accountService.isValidEmail(null), "Email null vẫn hợp lệ");
    }

    @Test
    @DisplayName("Kiểm tra hàm isValidPassword()")
    void testIsValidPassword() {
        // Hợp lệ
        assertTrue(accountService.isValidPassword("Abc@1234"), "Mật khẩu hợp lệ nhưng bị đánh sai");

        // Không hợp lệ
        assertFalse(accountService.isValidPassword("abc123"), "Thiếu ký tự đặc biệt và hoa mà vẫn hợp lệ");
        assertFalse(accountService.isValidPassword("Abc123"), "Thiếu ký tự đặc biệt mà vẫn hợp lệ");
        assertFalse(accountService.isValidPassword("Ab@1"), "Mật khẩu quá ngắn mà vẫn hợp lệ");

        // Rỗng hoặc null
        assertFalse(accountService.isValidPassword(""), "Mật khẩu rỗng vẫn hợp lệ");
        assertFalse(accountService.isValidPassword(null), "Mật khẩu null vẫn hợp lệ");
    }

    @Test
    @DisplayName("Kiểm tra đăng ký tài khoản từ file CSV (OpenCSV)")
    void testRegisterAccountFromCSV() {
        String fileName = "test-data.csv"; // file nằm trong src/test/resources

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            assertNotNull(inputStream, "Không tìm thấy file " + fileName + " trong thư mục resources!");

            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReader(reader);

            String[] data;
            int lineNum = 0;

            while ((data = csvReader.readNext()) != null) {
                lineNum++;
                if (lineNum == 1) continue; // bỏ qua header

                if (data.length < 4) {
                    System.out.println("⚠️ Bỏ qua dòng " + lineNum + " vì thiếu dữ liệu");
                    continue;
                }

                String username = data[0].trim();
                String password = data[1].trim();
                String email = data[2].trim();
                boolean expected = Boolean.parseBoolean(data[3].trim());

                boolean actual = accountService.registerAccount(email, password);

                assertEquals(expected, actual,
                        "Sai kết quả ở dòng " + lineNum +
                                " với dữ liệu: " + username + " - " + email);
            }

            csvReader.close();

        } catch (IOException e) {
            fail("Không đọc được file test-data.csv: " + e.getMessage());
        } catch (Exception e) {
            fail("Lỗi khi xử lý CSV: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        accountService = null;
    }
}
