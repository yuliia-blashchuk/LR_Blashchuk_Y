package model;

/**
 * Клас, що представляє модель "Покупець" (Customer).
 */
public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String address;
    private String creditCardNumber;
    private double accountBalance;

    /**
     * Конструктор за замовчуванням.
     */
    public Customer() {
    }

    /**
     * Конструктор з параметрами.
     */
    public Customer(int id, String lastName, String firstName, String patronymic, String address, String creditCardNumber, double accountBalance) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.address = address;
        this.creditCardNumber = creditCardNumber;
        this.accountBalance = accountBalance;
    }

    // Відкриті методи getValue() / Гетери
    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getPatronymic() { return patronymic; }
    public String getAddress() { return address; }
    public String getCreditCardNumber() { return creditCardNumber; }
    public double getAccountBalance() { return accountBalance; }

    // Відкриті методи setValue() / Сетери
    public void setId(int id) { this.id = id; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }
    public void setAddress(String address) { this.address = address; }
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }

   @Override
    public String toString() {
        return String.format("Customer %d\n Прізвище %s\n Ім'я %s\n По батькові %s\n Адреса %s\n Номер картки %s\n Баланс %.2f",
                id, lastName, firstName, patronymic, address, creditCardNumber, accountBalance);
    }
}