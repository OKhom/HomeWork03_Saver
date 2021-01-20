package com.javapro;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SaveTo(fileToSave = "HW03task2.txt")
class Container {
    String myText = "How are you?";

    @Saver
    public void saveMyText(String textPath) {
        try {
            FileWriter fw = new FileWriter(textPath);
            fw.write(myText);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Container cont = new Container();
        Class<?> testClass = cont.getClass();
        if (testClass.isAnnotationPresent(SaveTo.class)) {
            SaveTo saveTo = testClass.getAnnotation(SaveTo.class);
            String filePath = saveTo.fileToSave();
            Method[] methods = testClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Saver.class)) {
                    method.invoke(cont, filePath);
                    System.out.println("File saved");
                }
            }
        } else {
            System.out.println("Annotated Class is absent!");
        }
    }
}
