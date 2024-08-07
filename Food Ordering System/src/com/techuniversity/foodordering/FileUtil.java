package com.techuniversity.foodordering;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String ORDER_FILE = "orders.ser";
    private static final String ITEM_FILE = "items.ser";

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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            list = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, return empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static <T> void saveListToFile(List<T> list, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
