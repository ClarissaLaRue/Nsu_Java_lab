package ru.nsu.fit.korobova;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args){
        PropertyConfigurator.configure(Main.class.getClassLoader().getResource("log4j2.properties"));
        System.out.println("Hello world");
        logger.info("Log4j2 configuration is successful !!");
    }
}
