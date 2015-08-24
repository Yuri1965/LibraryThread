package com.epam.training.LibraryThread.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Book {
    static Logger bookLogger = LogManager.getLogger(Book.class.getName());

    // наименование книги
    private String bookName;

    // если = true, то книга только для чтения в читальном зале
    private boolean byReadyRoom = false;

    // конструктор
    public Book(String bookName, boolean byReadyRoom) {
        this.bookName = bookName;
        this.byReadyRoom = byReadyRoom;

        bookLogger.info("Create new book: name = " + bookName + ", byReadyRoom = " + byReadyRoom);
    }

    // сеттеры и геттеры
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public boolean isByReadyRoom() {
        return byReadyRoom;
    }

    public void setByReadyRoom(boolean byReadyRoom) {
        this.byReadyRoom = byReadyRoom;
    }
}
