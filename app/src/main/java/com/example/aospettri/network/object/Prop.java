package com.example.aospettri.network.object;

public class Prop {

    private String  key;

    private String  value;

    public Prop(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReqProp{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
