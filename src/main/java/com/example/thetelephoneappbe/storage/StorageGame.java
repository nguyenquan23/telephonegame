package com.example.thetelephoneappbe.storage;

import java.util.*;

public class StorageGame {
    private Long idRoom;
    private Set<String> nicknames = new HashSet<>();
    private Map<String, Map<Integer, String>> result = new LinkedHashMap<>();
    private List<String> keyNickName;
    private List<String> values = new ArrayList<>();
    private List<String> receiver = new ArrayList<>();

    public StorageGame() {
    }

    public Set<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(Set<String> nicknames) {
        this.nicknames = nicknames;
    }

    public Map<String, Map<Integer, String>> getResult() {
        return result;
    }

    public void setResult(Map<String, Map<Integer, String>> result) {
        this.result = result;
    }

    public List<String> getKeyNickName() {
        return keyNickName;
    }

    public void setKeyNickName(List<String> keyNickName) {
        this.keyNickName = keyNickName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    public Long getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Long idRoom) {
        this.idRoom = idRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageGame that = (StorageGame) o;
        return Objects.equals(idRoom, that.idRoom) && Objects.equals(nicknames, that.nicknames) && Objects.equals(result, that.result) && Objects.equals(keyNickName, that.keyNickName) && Objects.equals(values, that.values) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRoom, nicknames, result, keyNickName, values, receiver);
    }
}
