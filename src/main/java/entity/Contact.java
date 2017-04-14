package entity;

import javax.persistence.*;

@Entity
@Table(name="contacts")
public class Contact {

    @Id
    @SequenceGenerator(name = "id_seq", sequenceName = "contacts_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Integer id;
    private String name;
    private String birthday;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
