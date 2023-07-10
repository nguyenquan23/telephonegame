package com.example.thetelephoneappbe.DTO;



public class ResultDTO {
    private String namePlay;
    private String data;

    public ResultDTO() {
    }

    public ResultDTO(String namePlay, String data) {
        this.namePlay = namePlay;
        this.data = data;
    }

    public String getNamePlay() {
        return namePlay;
    }

    public void setNamePlay(String namePlay) {
        this.namePlay = namePlay;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "namePlay:" + namePlay  +
                ", data:" + data +
                '}';
    }
}
