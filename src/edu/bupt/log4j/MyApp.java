package edu.bupt.log4j;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Created by shixu on 2016-08-01.
 */
public class MyApp {

    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    private static final Logger logger = LogManager.getLogger(MyApp.class);

    public static void main(final String... args) {

        // Set up a simple configuration that logs on the console.

        logger.trace("Entering application.");
        logger.error("Didn't do it.");
        logger.trace("Exiting application.");
        logger.info("heheda");
        logger.debug("hehe");
    }
}
