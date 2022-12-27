package org.example;

import org.example.data.repository.MainRepositoryImpl;
import org.example.domain.repository.MainRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        var mainRepository = context.getBean("mainRepository", MainRepositoryImpl.class);
        char terminate = takeCommand();
        while (true) {
            boolean userWantToQuit = false;
            switch (terminate) {
                case 'e' -> {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter directory path: ");
                    String directoryPath = scanner.next();
                    System.out.print("Enter destination directory: ");
                    String destinationDirectory = scanner.next();
                    boolean inserted = mainRepository.processDirectory(directoryPath, destinationDirectory);
                    String res = inserted ? "File inserted into database" : "error processing directory";
                    System.out.println(res);
                }
                case 'l' -> {
                    mainRepository.getAllFilesInsertedInDatabase().forEach(System.out::println);
                }
                default -> {
                    userWantToQuit = true;
                }
            }
            if (userWantToQuit) break;
            terminate = takeCommand();
        }
    }

    private static char takeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press commands\nl -> For listing all files in database\ne -> For entering directory to database\nPress any other button to terminate");
        return scanner.next().charAt(0);
    }
}