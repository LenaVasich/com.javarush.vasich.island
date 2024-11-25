package application;


import java.util.Scanner;

public class IslandApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Давайте создадим остров! Введите размеры для запуска эмуляции или Q для выхода.");
        System.out.println("Введите размеры острова: ");
        //System.out.print("Длина: ");
        int length = 1;//scanner.nextInt();
        //System.out.print("Ширина: ");
        int width = 1;//scanner.nextInt();
        Island island = new Island(length, width);
    }
}