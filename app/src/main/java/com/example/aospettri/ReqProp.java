package com.example.aospettri;

public class ReqProp {

    private String  key;

    private String  value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public ReqProp(String key, String vallue){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReqEventCreate{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}


