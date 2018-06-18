package bleepy.pack.com.bleepy.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Validation {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_PATTERN);
    private static final Pattern UK_NUMBERS_PATTERN = Pattern.compile(Constants.UK_NUMBERS_PATTERN);
    private static final Pattern NAME_PATTERN = Pattern.compile(Constants.NAME_PATTERN);
    private static final Pattern NAME_ONLY_PATTERN = Pattern.compile(Constants.NAME_PATTERN_ONLY);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(Constants.PASSWORD_PATTERN);
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile(Constants.POSTAL_CODE_PATTERN);

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
    public static boolean isMatchesUKNumbersPattern(String number) {
        if(number.length() == 11) {
            number = number.substring(1,number.length());
        }
        Matcher matcher = UK_NUMBERS_PATTERN.matcher(number);
        return matcher.matches();
    }
    public static boolean isMatchesNamePattern(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);
        return matcher.matches();
    }
    public static boolean isMatchesPasswordPattern(String number) {
        Matcher matcher = PASSWORD_PATTERN.matcher(number);
        return matcher.matches();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNumber(EditText textBox) {
        return isNumber(textBox.getText().toString());
    }

    public static boolean isPositiveNumberExcludingZero(EditText textBox) {
        textBox.setText(textBox.getText().toString().trim());
        return isPositiveNumberExcludingZero(textBox.getText().toString());
    }

    public static boolean isStringOfLength(EditText textBox, int length) {
        textBox.setText(textBox.getText().toString().trim());
        return isStringOfLength(textBox.getText().toString(), length);
    }


    public static boolean isNumber(String value) {
        String regex = "^\\d+$";
        return value.matches(regex);
    }

    public static boolean isPositiveNumberExcludingZero(String value) {
        return isNumber(value) && Integer.parseInt(value) > 0;
    }

    public static boolean isStringOfLength(String value, int length) {
        return (value.length() == length);
    }

    public static String convertToCamelCase(String name) {
        if(name == null || name.length() == 0) {
            return "";
        }
        return  name.substring(0,1).toUpperCase() + name.substring(1,name.length());
    }

    public static boolean isEmpty(String value) {
        return isStringOfLength(value, 0);
    }

    public static boolean isEmpty(EditText textBox) {
        return isStringOfLength(textBox.getText().toString().trim(), 0);
    }
    public static boolean isNameValid(EditText textBox) {
        Matcher matcher = NAME_ONLY_PATTERN.matcher(textBox.getText().toString().trim());
        return matcher.matches();
    }

    public static boolean isStringBetweenLength(String text, int minLen, int maxLen) {
        /*if(text.length() >= minLen && text.length() <= maxLen) {
            return true;
        }*/
        return text.length() >= minLen && text.length() <= maxLen;
    }
    public static boolean isStringBetweenLength(EditText textBox, int minLen, int maxLen) {
        textBox.setText(textBox.getText().toString().trim());
        return isStringBetweenLength(textBox.getText().toString(), minLen, maxLen);
    }
    public static boolean validatePostalCodeRegEx(String postalCode) {
        Matcher matcher = POSTAL_CODE_PATTERN.matcher(postalCode);
        return matcher.matches();
    }

    public static boolean isNumericNotZero(EditText textBox) {
        String text = textBox.getText().toString();
        try {
            if(Integer.parseInt(text) == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String changeIfNullToEmpty(String value) {
        if(value == null)
            return "";
        else
            return value;
    }
    public static boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static boolean validatePassword(String password) {
        return password.length() > 5;
    }
    public static boolean validatePasswordRegEx(String password) {
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }


}
