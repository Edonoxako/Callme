package com.slyfox.recall.model;

import java.util.List;

/**
 * Created by Eugene on 25.06.2016.
 */
public class ContactModel {

    private long id;
    private String name;
    private List<String> numbers;

    public ContactModel(long id, String name, List<String> numbers) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactModel that = (ContactModel) o;

        if (getId() != that.getId()) return false;
        if (!getName().equals(that.getName())) return false;
        return getNumbers().equals(that.getNumbers());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getNumbers().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numbers=" + numbers +
                '}';
    }
}
