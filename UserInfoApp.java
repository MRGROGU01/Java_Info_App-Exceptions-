import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserInfoApp {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные на АНГЛИЙСКОМ (Фамилия Имя Отчество дата рождения номер телефона пол), разделенные пробелом:");
        String input = scanner.nextLine();
        String[] data = input.split(" ");
        
        try {
            // Проверяем количество данных
            validateDataCount(data);
            // Проверяем формат и распознаем данные
            String surname = validateName(data[0], "Фамилия");
            String name = validateName(data[1], "Имя");
            String patronymic = validateName(data[2], "Отчество");
            String birthDate = validateBirthDate(data[3]);
            long phoneNumber = parsePhoneNumber(data[4]);
            char gender = parseGender(data[5]);

            String filename = surname + ".txt";
            String userData = String.format("%s %s %s %s %d %c", surname, name, patronymic, birthDate, phoneNumber, gender);

            writeToFile(filename, userData);
            System.out.println("Данные успешно сохранены в файл: " + filename);

        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла непредвиденная ошибка:");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Метод для проверки количества данных
    private static void validateDataCount(String[] data) {
        if (data.length < 6) {
            throw new IllegalArgumentException("Введено меньше данных, чем требуется.");
        }
        if (data.length > 6) {
            throw new IllegalArgumentException("Введено больше данных, чем требуется.");
        }
    }

    // Метод для проверки строк (Фамилия, Имя, Отчество)
    private static String validateName(String name, String fieldName) {
        if (name.matches("[a-zA-Zа-яА-Я]+")) {
            return name;
        } else {
            throw new IllegalArgumentException("Неверный формат для поля " + fieldName + ". Ожидается строка без цифр и специальных символов.");
        }
    }

    // Метод для проверки и форматирования даты рождения
    private static String validateBirthDate(String birthDate) {
        if (birthDate.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            return birthDate;
        } else {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается формат dd.mm.yyyy");
        }
    }

    // Метод для проверки и парсинга номера телефона
    private static long parsePhoneNumber(String phoneStr) {
        try {
            return Long.parseLong(phoneStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Ожидается целое число.");
        }
    }

    // Метод для проверки и парсинга пола
    private static char parseGender(String genderStr) {
        if (genderStr.length() == 1 && (genderStr.equalsIgnoreCase("f") || genderStr.equalsIgnoreCase("m"))) {
            return genderStr.toLowerCase().charAt(0);
        } else {
            throw new IllegalArgumentException("Неверный формат пола. Ожидается символ 'f' или 'm'.");
        }
    }

    // Метод для записи данных в файл
    private static void writeToFile(String filename, String data) throws IOException {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(data + "\n");
        }
    }
}
