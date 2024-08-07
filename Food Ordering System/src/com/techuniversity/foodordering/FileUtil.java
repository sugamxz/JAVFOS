package com.techuniversity.foodordering;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String ORDER_FILE = "orders.txt";
    private static final String ITEM_FILE = "items.txt";

    public static List<Order> loadOrders() {
        return loadListFromFile(ORDER_FILE);
    }

    public static List<Item> loadItems() {
        return loadListFromFile(ITEM_FILE);
    }

    public static void saveOrders(List<Order> orders) {
        saveListToFile(orders, ORDER_FILE);
    }

    public static void saveItems(List<Item> items) {
        saveListToFile(items, ITEM_FILE);
    }

    private static <T> List<T> loadListFromFile(String fileName) {
        List<T> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                Object obj = deserialize(line);
                if (obj != null) {
                    list.add((T) obj);
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static <T> void saveListToFile(List<T> list, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T obj : list) {
                bw.write(serialize(obj));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String serialize(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return "Item:" + item.getName() + "," +
                   item.getDescription() + "," +
                   item.getPrice() + "," +
                   item.getImagePath();
        } else if (obj instanceof Order) {
            Order order = (Order) obj;
            return "Order:" + order.getOrderId() + "," +
                   order.getCustomer() + "," +
                   order.getDate() + "," +
                   order.getStatus();
        }
        return "";
    }

    private static Object deserialize(String line) {
        String[] parts = line.split(":", 2);
        if (parts.length < 2) return null;

        String type = parts[0];
        String data = parts[1];

        if ("Item".equals(type)) {
            String[] itemData = data.split(",", 4);
            if (itemData.length < 4) return null;
            String name = itemData[0];
            String description = itemData[1];
            double price = Double.parseDouble(itemData[2]);
            String imagePath = itemData[3];
            return new Item(name, description, price, imagePath);
        } else if ("Order".equals(type)) {
            String[] orderData = data.split(",", 4);
            if (orderData.length < 4) return null;
            String orderId = orderData[0];
            String customer = orderData[1];
            String date = orderData[2];
            String status = orderData[3];
            return new Order(orderId, customer, date, status);
        }
        return null;
    }
}
