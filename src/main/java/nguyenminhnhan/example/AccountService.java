package nguyenminhnhan.example;

import java.util.regex.Pattern;

public class AccountService {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!]).{8,}$";

    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public boolean registerAccount(String email, String password) {
        // Chỉ đăng ký nếu cả email và password hợp lệ
        return isValidEmail(email) && isValidPassword(password);
    }
}
