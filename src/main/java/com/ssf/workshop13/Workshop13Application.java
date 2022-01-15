package com.ssf.workshop13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.ssf.workshop13.util.Directory.createDir;

@SpringBootApplication
public class Workshop13Application {

    private static final Logger logger = LoggerFactory.getLogger(Workshop13Application.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(Workshop13Application.class);
        ApplicationArguments commandInput = new DefaultApplicationArguments(args);

        if (commandInput.containsOption("dataDir")) {
            String dirPath = commandInput.getOptionValues("dataDir").get(0);
            logger.info("Directory path has been set to >> %s".formatted(dirPath));
            createDir(dirPath);
        } else {
            logger.warn("No data directory is provided");
            System.exit(1);
        }

        app.run(args);
    }

}
