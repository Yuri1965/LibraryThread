package com.epam.training.LibraryThread.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookLibrary {
    static Logger libraryLogger = LogManager.getLogger(BookLibrary.class.getName());

    // список книг в библиотеке
    private List<Book> lstBooksLibrary = new ArrayList<>();
    // количество книг в библиотеке
    private int booksCount;

    // список читателей библиотеки
    private List<PeopleReader> lstPeoplesReader = new ArrayList<>();
    // количество читателей в библиотеке
    private int peoplesCount;

    // время работы библиотеки (в миллисекундах)
    private int timeWorkLibrary = 30000;

    // конструктор
    public BookLibrary(int booksCount, int peoplesCount, int timeWorkLibrary) {


        this.booksCount = booksCount;

        for (int i = 0; i < booksCount; i++) {
            Book book = new Book("Book" + i, new Random().nextBoolean());
            lstBooksLibrary.add(book);
        }

        for (int i = 0; i < peoplesCount; i++) {
            PeopleReader people = new PeopleReader();
            lstPeoplesReader.add(people);
        }

    }


}
