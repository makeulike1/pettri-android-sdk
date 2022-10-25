package com.example.aospettri;

public class ReqProp {

    private String  key;

    private String  value;

    public ReqProp(String key, String value){
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


