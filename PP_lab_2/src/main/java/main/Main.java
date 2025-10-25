package main;

import model.Customer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final String FILE_PATH = "D:\\Yuliya\\Customers.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();

        System.out.println("Оберіть джерело даних:");
        System.out.println("1 — Зчитати клієнтів з файлу");
        System.out.println("2 — Ввести клієнтів вручну");
        System.out.print("Ваш вибір: ");

        int choice;
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine();
        } else {
            System.out.println("Невірний вибір. Завершення програми.");
            return;
        }

        if (choice == 1) {
            File file = new File(FILE_PATH);
            if (!file.exists() || file.length() == 0) {
                System.out.println("Файл порожній або не існує. Будь ласка, створіть його вручну.");
            }
            customers = readCustomersFromFile();
            System.out.println("\n=== Всі клієнти з файлу ===");
            printCustomersTable(customers);

        } else if (choice == 2) {
            System.out.print("Скільки клієнтів бажаєте ввести? ");
            int count = sc.nextInt();
            sc.nextLine();

            int nextId = getNextAvailableId(customers);

            for (int i = 1; i <= count; i++) {
                System.out.println("\n--- Клієнт #" + i + " ---");
                System.out.print("Прізвище: ");
                String lastName = sc.nextLine();
                System.out.print("Ім'я: ");
                String firstName = sc.nextLine();
                System.out.print("По батькові: ");
                String middleName = sc.nextLine();
                System.out.print("Адреса (використовуйте пробіли замість ком): ");
                String address = sc.nextLine();
                System.out.print("Номер кредитної картки (16 цифр): ");
                String cardNum = sc.nextLine();
                System.out.print("Баланс рахунку: ");
                double balance = sc.nextDouble();
                sc.nextLine();

                customers.add(new Customer(nextId++, lastName, firstName, middleName, address, cardNum, balance));
            }
            System.out.println("\n=== Введені клієнти ===");
            printCustomersTable(customers);

        } else {
            System.out.println("Невірний вибір. Завершення програми.");
            return;
        }


        while (true) {
            System.out.println("\n==============================");
            System.out.println("МЕНЮ КЛІЄНТІВ:");
            System.out.println("1 — Пошук клієнта за ID");
            System.out.println("2 — Додати нового клієнта");
            System.out.println("3 — Видалити клієнта за ID");
            System.out.println("4 — Показати всіх клієнтів)");
            System.out.println("a) Список покупців із заданим ім'ям");
            System.out.println("b) Список покупців, у яких номер кредитної картки в заданому інтервалі");
            System.out.println("c) Кількість та список покупців, які мають заборгованість");
            System.out.println("0 — Вийти та зберегти");
            System.out.print("Ваш вибір (1, 2, 3, 4, a, b, c, 0): ");
            String option = sc.next().toLowerCase();

            int id;

            switch (option) {
                case "1" -> {
                    System.out.print("Введіть ID клієнта: ");
                    if (sc.hasNextInt()) {
                        int idSearch = sc.nextInt();
                        sc.nextLine();
                        Optional<Customer> found = customers.stream()
                                .filter(c -> c.getId() == idSearch)
                                .findFirst();
                        if (found.isPresent()) {
                            System.out.println("Знайдено: " + found.get());
                        } else {
                            System.out.println("Клієнта з таким ID не знайдено.");
                        }
                    } else {
                        System.out.println("Невірний формат ID.");
                        sc.next();
                    }
                }

                case "2" -> {
                    sc.nextLine();
                    System.out.println("=== Додавання нового клієнта ===");

                    System.out.print("Прізвище: ");
                    String lastName = sc.nextLine();
                    System.out.print("Ім'я: ");
                    String firstName = sc.nextLine();
                    System.out.print("По батькові: ");
                    String middleName = sc.nextLine();
                    System.out.print("Адреса (використовуйте пробіли замість ком): ");
                    String address = sc.nextLine();
                    System.out.print("Номер кредитної картки (16 цифр): ");
                    String cardNum = sc.nextLine();
                    System.out.print("Баланс рахунку: ");
                    double balance = sc.nextDouble();
                    sc.nextLine();

                    int newId = getNextAvailableId(customers);
                    customers.add(new Customer(newId, lastName, firstName, middleName, address, cardNum, balance));
                    System.out.println("Клієнта з ID " + newId + " додано.");

                    saveCustomersToFile(customers);
                }

                case "3" -> {
                    System.out.print("Введіть ID клієнта для видалення: ");
                    if (sc.hasNextInt()) {
                        id = sc.nextInt();
                        sc.nextLine();
                        boolean removed = customers.removeIf(c -> c.getId() == id);
                        if (removed) {
                            System.out.println("Клієнта з ID " + id + " видалено.");
                            saveCustomersToFile(customers);
                        } else {
                            System.out.println("Не знайдено клієнта з таким ID.");
                        }
                    } else {
                        System.out.println("Невірний формат ID.");
                        sc.next();
                    }
                }

                case "4" -> {
                    System.out.println("=== Усі клієнти ===");
                    printCustomersTable(customers);
                }

                case "a" -> {
                    sc.nextLine();
                    System.out.print("\n(a) Введіть ім'я клієнта для пошуку: ");
                    String targetFirstName = sc.nextLine().trim();
                    printCustomersByFirstName(customers, targetFirstName);
                }

                case "b" -> {
                        sc.nextLine();
                        System.out.print("\n(b) Введіть мінімальний номер картки (16 цифр): ");
                        String minCard = sc.nextLine().trim();
                        System.out.print("Введіть максимальний номер картки (16 цифр): ");
                        String maxCard = sc.nextLine().trim();
                        printCustomersByCardNumberRange(customers, minCard, maxCard);
                    }

                case "c" -> {
                    System.out.println("\n(c) Клієнти, які мають заборгованість:");
                    printCustomersWithDebt(customers);
                }

                case "0" -> {
                    System.out.println("Вихід із програми. Збереження даних у файл...");
                    saveCustomersToFile(customers);
                    return;
                }

                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }
    }

    /**
     * Пошук найбільшого ID у списку та повернення наступного вільного ID.
     * @param customers Список наявних клієнтів.
     * @return Наступний доступний ID.
     */
    private static int getNextAvailableId(List<Customer> customers) {
        return customers.stream()
                .mapToInt(Customer::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Форматоване виведення списку клієнтів у вигляді таблиці.
     * @param customers Список клієнтів для виведення.
     */
    public static void printCustomersTable(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Список клієнтів порожній.");
            return;
        }

        System.out.println("+-----+---------------+---------------+---------------+-------------------------------------+--------------------+------------+");
        System.out.printf("| %-3s | %-13s | %-13s | %-13s | %-35s | %-18s | %-10s |%n",
                "ID", "Прізвище", "Ім'я", "По батькові", "Адреса", "Номер картки", "Баланс");
        System.out.println("+-----+---------------+---------------+---------------+-------------------------------------+--------------------+------------+");

        for (Customer c : customers) {
            String address = c.getAddress().length() > 27 ? c.getAddress().substring(0, 24) + "..." : c.getAddress();
            String cardNum = c.getCreditCardNumber().length() > 18 ? c.getCreditCardNumber().substring(0, 15) + "..." : c.getCreditCardNumber();

            System.out.printf("| %-3d | %-13s | %-13s | %-13s | %-35s | %-18s | %-10.2f |%n",
                    c.getId(),
                    c.getLastName(),
                    c.getFirstName(),
                    c.getPatronymic(),
                    address,
                    cardNum,
                    c.getAccountBalance());
        }

        System.out.println("+-----+---------------+---------------+---------------+-------------------------------------+--------------------+------------+");
        System.out.println("Всього клієнтів: " + customers.size());
    }

    /**
     * Виводить список покупців із заданим ім'ям.
     * @param customers Список усіх клієнтів.
     * @param firstName Ім'я для пошуку.
     */
    public static void printCustomersByFirstName(List<Customer> customers, String firstName) {
        List<Customer> filtered = customers.stream()
                .filter(c -> c.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Клієнтів з іменем '" + firstName + "' не знайдено.");
        } else {
            System.out.println("Знайдено клієнтів з іменем " + firstName + ":");
            printCustomersTable(filtered);
        }
    }

    /**
     * Виводить список покупців, у яких номер кредитної картки знаходиться в заданому інтервалі.
     * @param customers Список усіх клієнтів.
     */
    public static void printCustomersByCardNumberRange(List<Customer> customers, String minCardNumber, String maxCardNumber) {

        String minCard = minCardNumber.replaceAll("[\\s-]", "");
        String maxCard = maxCardNumber.replaceAll("[\\s-]", "");

        if (minCard.length() != 16 || maxCard.length() != 16 || minCard.compareTo(maxCard) > 0) {
            System.out.println("Помилка: Введіть 16-значні номери картки, причому мінімальне значення має бути менше або дорівнювати максимальному.");
            return;
        }

        List<Customer> filtered = customers.stream()
                .filter(c -> {
                    String cleanCard = c.getCreditCardNumber().replaceAll("[\\s-]", "");

                    return cleanCard.compareTo(minCard) >= 0 && cleanCard.compareTo(maxCard) <= 0;
                })
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Клієнтів з номером картки в інтервалі від " + minCardNumber + " до " + maxCardNumber + " не знайдено.");
        } else {
            System.out.println("Знайдено " + filtered.size() + " клієнтів з номером картки в інтервалі від " + minCardNumber + " до " + maxCardNumber + ":");
            printCustomersTable(filtered);
        }
    }

    /**
     * Виводить кількість та список покупців, які мають заборгованість (від'ємний баланс).
     * @param customers Список усіх клієнтів.
     */
    public static void printCustomersWithDebt(List<Customer> customers) {
        List<Customer> debtCustomers = customers.stream()
                .filter(c -> c.getAccountBalance() < 0)
                .collect(Collectors.toList());

        System.out.println("Загальна кількість клієнтів із заборгованістю: " + debtCustomers.size());
        if (!debtCustomers.isEmpty()) {
            printCustomersTable(debtCustomers);
        } else {
            System.out.println("Не знайдено клієнтів із заборгованістю.");
        }
    }

    /**
     * Зчитує дані клієнтів з текстового файлу.
     * @return Список об'єктів Customer.
     */
    public static List<Customer> readCustomersFromFile() {
        List<Customer> customers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String lastName = parts[1].trim();
                        String firstName = parts[2].trim();
                        String middleName = parts[3].trim();
                        String address = parts[4].trim();
                        String creditCardNumber = parts[5].trim();
                        double accountBalance = Double.parseDouble(parts[6].trim());
                        customers.add(new Customer(id, lastName, firstName, middleName, address, creditCardNumber, accountBalance));
                    } catch (NumberFormatException e) {
                        System.err.println("Помилка при парсингу даних у рядку: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
        return customers;
    }

    /**
     * Зберігає поточний список клієнтів у текстовий файл.
     * @param customers Список клієнтів для збереження.
     */
    private static void saveCustomersToFile(List<Customer> customers) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Customer c : customers) {
                writer.write(String.format(Locale.US, "%d,%s,%s,%s,%s,%s,%.2f\n",
                        c.getId(), c.getLastName(), c.getFirstName(), c.getPatronymic(),
                        c.getAddress(), c.getCreditCardNumber(), c.getAccountBalance()));
            }
        } catch (IOException e) {
            System.err.println("Помилка запису у файл: " + e.getMessage());
        }
    }
}