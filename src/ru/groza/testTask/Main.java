package ru.groza.testTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
public class Main {
    public static boolean put(String name, String phone) {
        try (FileWriter storeWriter = new FileWriter("store/contactList.txt", true)) {
            storeWriter.write(name + " " + phone + "\n");
            storeWriter.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Ошибка! Не удалось получить доступ к хранилищу справочника.");
            return false;
        }
    }

    public static ArrayList<String> get(String name) {
        try {
            File storeFile = new File("store/contactList.txt");
            if (!storeFile.exists()) {
                System.out.println("Ошибка! Отсутствует файл - хранилище справочника. Добавьте номера снова.");
                ArrayList<String> result = new ArrayList <String>();
                return result;
            } else {
                FileReader storeReader = new FileReader(storeFile);
                BufferedReader storeBuffer = new BufferedReader(storeReader);
                String line = storeBuffer.readLine();
                ArrayList<String> result = new ArrayList <String>();
                while (line != null) {
                    if (name.equals(line.split(" ")[0])) {
                        result.add(line.split(" ")[1]);
                    }
                    line = storeBuffer.readLine();
                }
                return result;
            }
        } catch (IOException e) {
            System.out.println("Ошибка! Не удалось открыть файл - хранилище справочника.");
            ArrayList<String> result = new ArrayList <String>();
            return result;
        }
    }
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = null;

        while (true) {
            System.out.print("> ");
            try {
                command = reader.readLine();
            } catch(IOException e) {
                System.out.println("Ошибка! Не удалось получить доступ к консоли.");
                return;
            }
            String commandKey = null;
            String contactName = null;
            String contactPhone = null;
            String[] splitedCommand = command.trim().split(" ");
            commandKey = splitedCommand[0];
            switch (commandKey) {
                case "put":
                    if (splitedCommand.length == 3) {
                        contactName = splitedCommand[1];
                        contactPhone = splitedCommand[2];
                        put(contactName,contactPhone);
                    } else {
                        System.out.println("Ошибка! Неправильный синтаксис команды 'put'.");
                    }
                    break;
                case "get":
                    if (splitedCommand.length == 2) {
                        contactName = splitedCommand[1];
                        ArrayList <String> result = get(contactName);
                        for (int i = 0; i <= result.size() - 1; i++) {
                            System.out.println(result.get(i));
                        }
                    } else {
                        System.out.println("Ошибка! Неправильный синтаксис команды 'get'.");
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Ошибка! Команда не найдена. Проверьте правильность ввода.");
                    break;
            }
        }
    }
}
