package top.andnux.chain;

import android.app.Application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    private static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
    }

    public static Application getApp() {
        if (sApplication == null) {
            sApplication = getApplicationByReflection();
        }
        return sApplication;
    }

    private static Application getApplicationByReflection() {
        try {
            return (Application) Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication").invoke(null, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readFileString(File file) {
        StringBuilder builder = new StringBuilder();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
