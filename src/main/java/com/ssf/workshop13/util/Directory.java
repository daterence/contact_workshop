package com.ssf.workshop13.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;


public class Directory {

    private static final Logger logger = LoggerFactory.getLogger(Directory.class);

    public static void createDir(String path) {
        File dir = new File(path);
        if (dir.mkdir()) {
            logger.info("%s >> Directory has been created".formatted(path));
        }
        logger.info("%s >> Directory exists already".formatted(path));

    }
}
