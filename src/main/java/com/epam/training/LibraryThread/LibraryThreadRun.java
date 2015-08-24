package com.epam.training.LibraryThread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LibraryThreadRun {

    static Logger myLogger = LogManager.getLogger(LibraryThreadRun.class.getName());

    public static void main(String[] args) {
        myLogger.info("Application started");

        System.out.println("LibraryThreadRun");

        myLogger.info("Application stopped");
    }

}
