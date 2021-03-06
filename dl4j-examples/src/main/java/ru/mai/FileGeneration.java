package ru.mai;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс генерации входных данных для нейросети c одним полем <b>KOLVOROWS</b>
 * @author Евгений
 */
public class FileGeneration {

    /**
     * Переменная для хранения количества строк в выходном файле
     */
    final static int KOLVOROWS = 140;

    /**
     * Функция генерации случайных значений в заданных диапазонах и записи их в файл
     * @throws IOException при ошибки открытия файла
     */
    public void gen() throws IOException {
        int angle;
        int chainstay;
        int wheelbase;
        File fileOutput = new File("output.txt");
        FileWriter fileWrite = new FileWriter(fileOutput);
        for (int i = 0; i < KOLVOROWS / 2; i++) {
            angle = (int) (71 + Math.random() * 2);
            chainstay = (int) (400 + Math.random() * 20);
            wheelbase = (int) (1000 + Math.random() * 100);
            fileWrite.write(angle + ", " + chainstay + ", " + wheelbase + ", " + "0 \n");
        }
        for (int i = KOLVOROWS / 2; i < KOLVOROWS; i++) {
            angle = (int) (62 + Math.random() * 3);
            chainstay = (int) (450 + Math.random() * 20);
            wheelbase = (int) (1100 + Math.random() * 100);
            fileWrite.write(angle + ", " + chainstay + ", " + wheelbase + ", " + "1 \n");
        }
        fileWrite.close();
    }
}
