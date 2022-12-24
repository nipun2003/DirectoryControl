package org.example;

import org.example.data.database.PostgresDatabase;
import org.example.data.repository.FileDataRepositoryImpl;
import org.example.data.repository.MainRepositoryImpl;
import org.example.domain.repository.FileDataRepository;
import org.example.domain.repository.MainRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileDataRepository fileDataRepository = new FileDataRepositoryImpl(
                PostgresDatabase.getDatabase()
        );
        MainRepository mainRepository = new MainRepositoryImpl(fileDataRepository);
        char terminate = takeCommand();
        while (true) {
            boolean shouldOutOfTheLoop = false;
            switch (terminate) {
                case 'e' -> {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter directory path: ");
                    String directoryPath = scanner.next();
                    boolean inserted = mainRepository.insertFileIntoDatabase(directoryPath);
                    String res = inserted ? "File inserted into database" : "error processing directory";
                    System.out.println(res);
                }
                case 'l' -> {
                    mainRepository.getAllFilesInsertedInDatabase().forEach(System.out::println);
                }
                default -> {
                    shouldOutOfTheLoop = true;
                }
            }
            if (shouldOutOfTheLoop) break;
            terminate = takeCommand();
        }
    }

    private static char takeCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nPress commands\nl -> For listing all files in database\ne -> For entering directory to database\nPress any other button to terminate");
        return scanner.next().charAt(0);
    }
}