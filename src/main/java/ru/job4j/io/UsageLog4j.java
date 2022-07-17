package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Petr Arsentev";
        byte bt = 127;
        short sh = 32000;
        int in = 99999;
        long ln = 100500L;
        float fl = 1.05F;
        double db = 100500.99D;
        boolean bl = true;
        char ch = 'Y';
        LOG.debug("User info name : {}", name);
        LOG.warn("byte : {}, short : {}, int : {}, long : {}, float : {}, "
                + "double : {}, boolean : {}, char : {}.", bt, sh, in, ln, fl, db, bl, ch);
        if (args.length == 0) {
            LOG.error("The arguments were not found");
        }
    }
}
