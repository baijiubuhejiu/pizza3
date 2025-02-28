import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class PizzaStore {

    private String storeName;
    private String storeAddress;
    private String storeEmail;
    private String storePhone;

    private List<String> storeMenu;
    private Map<String, List<String>> pizzaIngredients;
    private Map<String, Double> pizzaPrice;
    private List<String> sides;
    private List<String> drinks;

    private String orderID;
    private double orderTotal;
    private List<Order> orders;

    private static final String DEF_ORDER_ID = "DEF-SOH-099";
    private static final String DEF_PIZZA_INGREDIENTS = "Mozzarella Cheese";
    private static final double DEF_ORDER_TOTAL = 15.00;

    private static final List<String> BLACKLISTED_CARDS = new ArrayList<>();

    static {
        BLACKLISTED_CARDS.add("1234567890123456");
    }

    public PizzaStore() {
        storeName = "Slice-o-Heaven";
        storeAddress = "123 Pizza Street";
        storeEmail = "info@sliceoheaven.com";
        storePhone = "123 - 456 - 7890";

        storeMenu = new ArrayList<>();
        storeMenu.add("Margherita");
        storeMenu.add("Pepperoni");
        storeMenu.add("Hawaiian");

        pizzaIngredients = new HashMap<>();
        List<String> defaultIngredients = new ArrayList<>();
        defaultIngredients.add(DEF_PIZZA_INGDREDIENTS);
        for(String pizza : storeMenu){
            pizzaIngredients.put(pizza,defaultIngredients);
        }

        pizzaPrice = new HashMap<>();
        pizzaPrice.put("Margherita", 10.99);
        pizzaPrice.put("Pepperoni", 12.99);
        pizzaPrice.put("Hawaiian", 13.99);

        sides = new ArrayList<>();
        sides.add("Garlic Bread");
        sides.add("Onion Rings");

        drinks = new ArrayList<>();
        drinks.add("Coke");
        drinks.add("Pepsi");
        
        orderID = DEF_ORDER_ID;
        orderTotal = DEF_ORDER_TOTAL;
        Orders = new ArrayList<>();
    }

    public PizzaStore(String orderID,Map<String, List<String>> pizzaIngredients, double orderTotal){
        this();
        this.orderID = orderID;
        this.pizzaIngredients = pizzaIngredients;
        this.orderTotal = orderTotal;
    }

    public String getOrderID(){
        return orderID;
    }

    public void setOrderID(String orderID){
        this.orderID = orderID;
    }

    public double getOrderTotal(){
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal){
        this.orderTotal = orderTotal;
    }

    public String getStoreName(){
        return storeName;
    }

    public void setStoreName(){
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public List<String> getStoreMenu() {
        return storeMenu;
    }

    public void setStoreMenu(List<String> storeMenu) {
        this.storeMenu = storeMenu;
    }

    public Map<String, List<String>> getPizzaIngredients() {
        return pizzaIngredients;
    }

    public void setPizzaIngredients(Map<String, List<String>> pizzaIngredients) {
        this.pizzaIngredients = pizzaIngredients;
    }

    public Map<String, Double> getPizzaPrice() {
        return pizzaPrice;
    }

    public void setPizzaPrice(Map<String, Double> pizzaPrice) {
        this.pizzaPrice = pizzaPrice;
    }

    public List<String> getSides() {
        return sides;
    }

    public void setSides(List<String> sides) {
        this.sides = sides;
    }

    public List<String> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<String> drinks) {
        this.drinks = drinks;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to " + storeName + "!");
        System.out.println("Menu:");
        for (String pizza : storeMenu) {
            System.out.println(pizza + ": $" + pizzaPrice.get(pizza));
        }
        System.out.println("Sides: " + String.join(", ", sides));
        System.out.println("Drinks: " + String.join(", ", drinks));

        System.out.print("Please enter the name of the pizza you want to order: ");
        String pizzaChoice = scanner.nextLine();
        if (!storeMenu.contains(pizzaChoice)) {
            System.out.println("Sorry, that pizza is not on the menu.");
            return;
        }

        System.out.print("How many of this pizza would you like to order? ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Would you like any sides? (Enter side name or 'None'): ");
        String sideChoice = scanner.nextLine();

        System.out.print("Would you like any drinks? (Enter drink name or 'None'): ");
        String drinkChoice = scanner.nextLine();

        double pizzaCost = pizzaPrice.get(pizzaChoice) * quantity;
        double sideCost = 0;
        double drinkCost = 0;

        if (sides.contains(sideChoice)) {
            sideCost = 3.99;
        }
        if (drinks.contains(drinkChoice)) {
            drinkCost = 2.99;
        }

        double totalCost = pizzaCost + sideCost + drinkCost;
        orderTotal += totalCost;

        Order order = new Order(orderID, pizzaChoice, quantity, sideChoice, drinkChoice, totalCost);
        orders.add(order);
        orderID = String.valueOf(Integer.parseInt(orderID.split("-")[2]) + 1);

        System.out.println("Order received! Your order ID is " + order.getOrderID() + ". Total cost: $" + totalCost);
        makePizza(pizzaChoice, quantity);
        printReceipt();

        System.out.print("Enter your credit card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter the expiry date (MM/YY): ");
        String expiryDate = scanner.nextLine();
        System.out.print("Enter the CVV: ");
        int cvv = scanner.nextInt();
        processCardPayment(cardNumber,expiryDate,cvv);
    }

    public void makePizza(String pizzaType, int quantity) {
        System.out.println("Preparing " + quantity + " " + pizzaType + " pizza(s)...");
        List<String> ingredients = pizzaIngredients.get(pizzaType);
        System.out.println("Ingredients needed:");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
        System.out.println("Pizza(s) are being made!");
    }

    public void printReceipt() {
        System.out.println("Receipt for " + storeName);
        System.out.println("Address: " + storeAddress);
        System.out.println("Email: " + storeEmail);
        System.out.println("Phone: " + storePhone);
        System.out.println("------------------------------");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Pizza: " + order.getPizza() + " x " + order.getQuantity());
            if (!order.getSide().equals("None")) {
                System.out.println("Side: " + order.getSide());
            }
            if (!order.getDrink().equals("None")) {
                System.out.println("Drink: " + order.getDrink());
            }
            System.out.println("Total: $" + order.getTotal());
            System.out.println("------------------------------");
        }
        System.out.println("Grand Total: $" + orderTotal);
    }

    public void processCardPayment(String cardNumber, String expiryDate,int cvv){
        if(cardNumber.length() != 16){
            System.out.println("Invalid card");
            return;
        }
        System.out.println("Card accepted");

        int firstDight = Integer.paresInt(cardNumber.substring(0,1));
        System.out.println("First dight of the card number: " + firstDight);

        if (BLACKLISTED_CARDS.CONTAINS(cardNumber)){
            System.out.println("This card is blacklisted. Payment declined.");
            return;
        }

        int lastFourDights = Integer.parseInt(cardNumber.substring(12));
        System.out.println("Last four dight of the card number: " + lastFourDights);

        String maskedCardNumber = cardNumber.substring(0,4) + "**** ****" + cardNumber.substring(12);
        System.out.println("Masked card number: " + maskedCardNumber);
    }

    public void specialOfTheDay(String pizzaOfTheDay, String sideOfTheDay, String specialPrice){
        StringBuilder specialInfo = new StringBuilder();
        specialInfo.append("Special of the day:\n");
        specialInfo.append("Pizza: ").append(pizzaOfTheDay).append("\n");
        specialInfo.append("Side: ").append(sideOfTheDay).append("\n");
        specialInfo.append("Price: $").append(specialPrice).append("\n");
        System.out.println(specialInfo.toString());
    }
}

class Order {
    private int orderID;
    private String pizza;
    private int quantity;
    private String side;
    private String drink;
    private double total;

    public Order(int orderID, String pizza, int quantity, String side, String drink, double total) {
        this.orderID = orderID;
        this.pizza = pizza;
        this.quantity = quantity;
        this.side = side;
        this.drink = drink;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getPizza() {
        return pizza;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSide() {
        return side;
    }

    public String getDrink() {
        return drink;
    }

    public double getTotal() {
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        PizzaStore pizzaStore = new PizzaStore();
        pizzaStore.takeOrder();
    }
}