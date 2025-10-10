package nguyenminhnhan.example;

import java.util.*;
import java.util.regex.Pattern;

public class AccountService {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!]).{6,}$";

    // Mô phỏng "database" chứa tài khoản đã đăng ký
    private Set<String> registeredEmails = new HashSet<>();
    private Set<String> registeredUsernames = new HashSet<>();

    // Kiểm tra email hợp lệ
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return Pattern.matches(EMAIL_REGEX, email);
    }

    // Kiểm tra password hợp lệ
    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    // Kiểm tra username hợp lệ
    public boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) return false;
        return username.length() > 3; // phải dài hơn 3 ký tự
    }

    // Kiểm tra tài khoản đã tồn tại hay chưa
    public boolean isAccountExists(String username, String email) {
        return registeredEmails.contains(email) || registeredUsernames.contains(username);
    }

    public boolean registerAccount(String email, String password) {
        if (!isValidEmail(email)) return false;
        if (!isValidPassword(password)) return false;
        if (registeredEmails.contains(email)) return false;

        registeredEmails.add(email);
        return true;
    }

    public boolean registerAccount(String username, String email, String password) {
        if (!isValidUsername(username)) return false;
        if (!isValidEmail(email)) return false;
        if (!isValidPassword(password)) return false;
        if (isAccountExists(username, email)) return false;

        registeredEmails.add(email);
        registeredUsernames.add(username);
        return true;
    }
}
