package org.example.ObjectType;

public class Virtual implements IObjectFormat {
    protected String virtual;

    public Virtual(String virtual) {
        this.virtual = virtual;
    }

    @Override
    public void artFormat(){
        System.out.println("Format: Virtual object");
        System.out.println("Display details: " + virtual);
    }
}
