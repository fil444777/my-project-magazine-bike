package jdbc.entity;

import java.util.Objects;

public class Manufacturers {
    private Integer id;
    private String name;

    public Manufacturers(Integer id, String nsme) {
        this.id = id;
        this.name = nsme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturers that = (Manufacturers) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Manufacturers{" +
               "id=" + id +
               ", nsme='" + name + '\'' +
               '}';
    }
}
