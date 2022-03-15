package com.group16.fitnessapp.utils;

public class UtilsLoader {
    private UtilsLoader(){}

    private enum Singleton {
        INSTANCE;

        private final UtilsLoader instance;


        Singleton() {
            instance = new UtilsLoader();
        }

        private UtilsLoader getInstance() {
            return instance;
        }
    }

    public static UtilsLoader getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


    public String secondToHHMMSS(Long seconds) {
        long p1 = seconds % 60;
        long p2 = seconds / 60;
        long p3 = p2 % 60;
        p2 = p2 / 60;
        StringBuilder sb = new StringBuilder();
        if(p2 > 0) {
            sb.append(p2).append("hr, ");
        }
        if(p3 > 0) {
            sb.append(p3).append("min, ");
        }
        if(p1 > 0) {
            sb.append(p1).append("sec");
        }
        return sb.toString();
    }
}
