import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    private static final Map<String, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put("I", 1);
        ROMAN_NUMERALS.put("II", 2);
        ROMAN_NUMERALS.put("III", 3);
        ROMAN_NUMERALS.put("IV", 4);
        ROMAN_NUMERALS.put("V", 5);
        ROMAN_NUMERALS.put("VI", 6);
        ROMAN_NUMERALS.put("VII", 7);
        ROMAN_NUMERALS.put("VIII", 8);
        ROMAN_NUMERALS.put("IX", 9);
        ROMAN_NUMERALS.put("X", 10);
        ROMAN_NUMERALS.put("C", 100);
        ROMAN_NUMERALS.put("M", 500);
        ROMAN_NUMERALS.put("M", 1000);
    }

    private static final Map<Integer, String> ARABIC_NUMERALS = new HashMap<>();

    static {
        ARABIC_NUMERALS.put(1, "I");
        ARABIC_NUMERALS.put(2, "II");
        ARABIC_NUMERALS.put(3, "III");
        ARABIC_NUMERALS.put(4, "IV");
        ARABIC_NUMERALS.put(5, "V");
        ARABIC_NUMERALS.put(6, "VI");
        ARABIC_NUMERALS.put(7, "VII");
        ARABIC_NUMERALS.put(8, "VIII");
        ARABIC_NUMERALS.put(9, "IX");
        ARABIC_NUMERALS.put(10, "X");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (для выхода введите 'exit'): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                if (e.getMessage().startsWith("Римские числа")) {
                    break;
                }
            }
        }

        scanner.close();
    }

    static String calc(String input) throws Exception {
        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            throw new Exception("Неверный формат выражения");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        boolean isRoman = isRomanNumeral(operand1) && isRomanNumeral(operand2);

        if ((isRoman && !isRomanNumeral(operand1)) || (!isRoman && isRomanNumeral(operand1))) {
            throw new Exception("Нельзя использовать одновременно арабские и римские числа");
        }

        int num1 = parseNumber(operand1, isRoman);
        int num2 = parseNumber(operand2, isRoman);

        if (isRoman) {
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new Exception("Римские числа могут быть только от I до X включительно");
            }
        } else {
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new Exception("Числа должны быть от 1 до 10 включительно");
            }
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                if (isRoman && (result <= 0 || result > 10)) {
                    throw new Exception("Римские числа не могут быть отрицательными");
                }
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Недопустимая арифметическая операция");
        }

        return isRoman ? convertToRoman(result) : String.valueOf(result);
    }

    private static int parseNumber(String operand, boolean isRoman) throws Exception {
        try {
            return isRoman ? ROMAN_NUMERALS.get(operand) : Integer.parseInt(operand);
        } catch (NumberFormatException | NullPointerException e) {
            throw new Exception("Неверный формат числа");
        }
    }

    private static boolean isRomanNumeral(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    private static String convertToRoman(int number) {
        if (number < 1) {
            throw new IllegalArgumentException();
        }
    
        StringBuilder result = new StringBuilder();
    
        String[] romanNumerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                result.append(romanNumerals[i]);
                number -= values[i];
            }
        }
    
        return result.toString();
    }
}    
