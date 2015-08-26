package com.epam.training.LibraryThread.classes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Читатель библиотеки
 */
public class PeopleReader implements Runnable {
    static Logger peopleLogger = LogManager.getLogger(PeopleReader.class.getName());

    // имя читателя
    private String peopleName;

    private static final Random random = new Random();

    private static final int ACTION_NO_VISIT = 0;       // действие НЕ прийти в библиотеку
    private static final int ACTION_IN_LIBRARY = 1;     // действие прийти в библиотеку

    private static final int ACTION_IN_LIBRARY_NO_ACTION = 0;      // действие ничего не делать
    private static final int ACTION_IN_LIBRARY_GET_BOOK = 1;       // действие выбрать книгу для чтения
    private static final int ACTION_IN_LIBRARY_OUT_LIBRARY = 2;    // действие уйти из библиотеки

    // список книг в библиотеке
    private List<Book> lstBooksLibrary;

    // список книг, которые взял на чтение читатель
    private List<Book> lstBooksByReady = new ArrayList<>();
    private List<Thread> threadsBooksByReady = new ArrayList<>();

    // конструктор
    public PeopleReader(String peopleName, List<Book> lstBooksLibrary) {
        this.peopleName = peopleName;
        this.lstBooksLibrary = lstBooksLibrary;

        peopleLogger.info("Create new people reader: name = " + peopleName);
    }

    // геттеры
    public String getPeopleName() {
        return peopleName;
    }

    // метод выбирает случайным образом действие читателя
    private int getActionReader(int actionValuesCount) {
        return random.nextInt(actionValuesCount);
    }

    // метод выбора читателем книги, которую он хочет прочитать
    private Book getBookForReady() {
        Book book = null;

        if (lstBooksLibrary.size() > 0) {
            int indexBook = random.nextInt(lstBooksLibrary.size() + 1);

            int i = 0;
            for (Book bookChose : lstBooksLibrary) {
                if (i == indexBook) {
                    book = bookChose;
                    break;
                }

                i++;
            }
        }

        // если книгу выбрали и ее еще НЕТ в списке у читателя
        if (book != null && !lstBooksByReady.contains(book)) {
            lstBooksByReady.add(book);
        } else { // если книга уже есть в списке у читателя
            book = null;
        }

        if (book != null)
            peopleLogger.info(this.peopleName + " chose the book = " + book.getBookName());

        return book;
    }

    // метод поведения читателя в библиотеке
    @Override
    public void run() {
        try {
            // выбор действия читателя
            int action = getActionReader(2);

            // НЕ придет в библиотеку
            if (action == ACTION_NO_VISIT) {
                peopleLogger.info(this.peopleName + " won't come to library");
                return;
            }

            // пришел в библиотеку
            if (action == ACTION_IN_LIBRARY) {
                Thread.sleep(random.nextInt(100));

                peopleLogger.info(this.peopleName + " came to library");
            }

            action = -1;

            while (action != ACTION_IN_LIBRARY_OUT_LIBRARY) {
                // выбор действия читателя
                action = getActionReader(3);

                // ничего не делать
                if (action == ACTION_IN_LIBRARY_NO_ACTION) {
                    continue;
                }

                if (action == ACTION_IN_LIBRARY_GET_BOOK) {
                    // выбор книги для чтения
                    Book book = getBookForReady();

                    if (book != null) {
                        // если очередь на чтение книги пустая, то запустим поток
                        // для обработки очереди на чтение книги
                        if (book.getLstPeoplesReader().isEmpty()) {
                            // добавим читателя в очередь на чтение книги
                            book.addToQueueForReady(this);

                            // запустим поток чтения книги
                            Thread thread = new Thread(book);
                            threadsBooksByReady.add(thread);
                            thread.start();
                        } else {
                            // просто добавим читателя в очередь на чтение книги
                            book.addToQueueForReady(this);
                        }
                    }

                    Thread.sleep(random.nextInt(100));

                    continue;
                }

                // находится в библиотеке и хочет уйти
                if (action == ACTION_IN_LIBRARY_OUT_LIBRARY) {
                    int countThreads = threadsBooksByReady.size();

                    while (countThreads > 0) {
                        for (Thread thd : threadsBooksByReady) {
                            if (thd.getState() == Thread.State.TERMINATED) {
                                countThreads --;
                            }

                            Thread.sleep(200);
                        }
                    }

                    peopleLogger.info(this.peopleName + " left library");
                    return;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

            peopleLogger.error(e.getMessage());
        }
    }
}
