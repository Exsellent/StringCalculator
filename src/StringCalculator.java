import java.util.Scanner;
import java.util.regex.Pattern;

public class StringCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение для вычисления:");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String addStrings(String s1, String s2) {
        return s1 + s2;
    }

    public static String subtractStrings(String s1, String s2) {
        return s1.contains(s2) ? s1.replaceFirst(Pattern.quote(s2), "") : s1;
    }

    public static String multiplyStrings(String s1, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(s1);
        }
        return result.toString();
    }

    public static String divideStrings(String s1, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("Нельзя делить на ноль");
        }
        int length = Math.floorDiv(s1.length(), n);
        return s1.substring(0, length);
    }

    public static boolean isInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String calculate(String input) {
        String[] tokens = input.split("\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        if (tokens.length == 3) {
            String operand1 = tokens[0];
            String operator = tokens[1];
            String operand2 = tokens[2];

            if (!operand1.matches("\"[^\"]{1,10}\"") || (!operand2.matches("\"[^\"]{1,10}\"") && !isInteger(operand2))) {
                throw new IllegalArgumentException("Строки должны быть длиной от 1 до 10 символов в кавычках, и второй операнд должен быть числом от 1 до 10 или строкой");
            }

            String op1 = operand1.substring(1, operand1.length() - 1);
            String op2 = operand2;

            if (op2.matches("\"[^\"]{1,10}\"")) {
                op2 = op2.substring(1, op2.length() - 1);
            }

            String result;
            switch (operator) {
                case "+":
                    result = addStrings(op1, op2);
                    break;
                case "-":
                    result = subtractStrings(op1, op2);
                    break;
                case "*":
                    if (isInteger(op2)) {
                        result = multiplyStrings(op1, Integer.parseInt(op2));
                    } else {
                        throw new IllegalArgumentException("Второй операнд должен быть целым числом");
                    }
                    break;
                case "/":
                    if (isInteger(op2)) {
                        result = divideStrings(op1, Integer.parseInt(op2));
                    } else {
                        throw new IllegalArgumentException("Второй операнд должен быть целым числом");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }

            if (result.length() > 40) {
                result = result.substring(0, 40) + "...";
            }

            return result;
        } else {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }
    }
}
