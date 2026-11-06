package org.example.ObjectType;

public class Physical implements IObjectFormat {
    protected String physical;

    public Physical(String physical) {
        this.physical = physical;
    }
    @Override
    public void artFormat(){
        System.out.println("Format: Physical object");
        System.out.println("Display details: " + physical);
    }
}
