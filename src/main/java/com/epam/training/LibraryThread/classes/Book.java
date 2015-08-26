package com.epam.training.LibraryThread.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Книга
 */
public class Book implements Runnable {
    static Logger bookLogger = LogManager.getLogger(Book.class.getName());

    // наименование книги
    private String bookName;

    // время на которое дается книга
    private int timeReady = 2000;

    // если = true, то книга только для чтения в читальном зале
    private boolean byReadyRoom = false;

    // список читателей, которые хотят взять книгу для чтения
    private Queue<PeopleReader> lstPeoplesReader = new ConcurrentLinkedQueue<PeopleReader>();

    // конструктор
    public Book(String bookName, boolean byReadyRoom, int timeReady) {
        this.bookName = bookName;
        this.byReadyRoom = byReadyRoom;
        this.timeReady = timeReady;

        bookLogger.info("Create new book: name = " + bookName +
                ", byReadyRoom = " + byReadyRoom + ", timeReady = " + timeReady);
    }

    // геттеры
    public String getBookName() {
        return bookName;
    }

    public Queue<PeopleReader> getLstPeoplesReader() {
        return lstPeoplesReader;
    }

    public boolean isByReadyRoom() {
        return byReadyRoom;
    }

    // добавляет в очередь на чтение книги читателя
    public void addToQueueForReady(PeopleReader peopleReader) {
        lstPeoplesReader.offer(peopleReader);
    }

    // работа потока для обработки очереди на чтение книги
    @Override
    public void run() {
        try {
            while (!lstPeoplesReader.isEmpty()) {
                PeopleReader peopleReader = lstPeoplesReader.poll();

                readyBook(new Random().nextInt(this.timeReady), peopleReader.getPeopleName());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            bookLogger.error(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            bookLogger.error(e.getMessage());
        }
    }

    // метод процесса чтения книги, параметр timeReady задает время, за которое книга будет читаться
    public void readyBook(int timeReady, String peopleName) throws InterruptedException{
                bookLogger.info(peopleName + ": " + this.bookName + " start is reading...");
                Thread.sleep(timeReady);
                bookLogger.info(peopleName + ": " + this.bookName + " stop reading...");
    }
}
