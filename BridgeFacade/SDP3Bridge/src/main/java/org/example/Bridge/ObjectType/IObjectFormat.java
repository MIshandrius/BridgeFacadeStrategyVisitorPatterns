package org.example.Bridge.ObjectType;

public interface IObjectFormat {
    default void artFormat(){
        System.out.println("Format");
    }
}
