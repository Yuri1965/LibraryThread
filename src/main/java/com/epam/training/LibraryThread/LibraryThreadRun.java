package com.epam.training.LibraryThread;

import com.epam.training.LibraryThread.Utils.PropertiesFileUtil;
import com.epam.training.LibraryThread.classes.BookLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class LibraryThreadRun {

    static Logger myLogger = LogManager.getLogger(LibraryThreadRun.class.getName());

    public static void main(String[] args) throws IOException {
        myLogger.info("Application started");

        // заполним параметры для запуска приложения
        PropertiesFileUtil.loadPropertiesFromFile();
        int booksFreeCount =  Integer.valueOf(PropertiesFileUtil.myProperties.getProperty("booksFreeCount"));
        int booksByReadyRoomCount = Integer.valueOf(PropertiesFileUtil.myProperties.getProperty("booksByReadyRoomCount"));
        int peoplesCount = Integer.valueOf(PropertiesFileUtil.myProperties.getProperty("peoplesCount"));

        BookLibrary bookLibrary = new BookLibrary(booksFreeCount, booksByReadyRoomCount, peoplesCount);

        try {
            bookLibrary.startWorkLibrary();

        }   catch (InterruptedException e) {
            e.printStackTrace();
            myLogger.error(e.getMessage());
        }

        myLogger.info("Application stopped");
    }
}
