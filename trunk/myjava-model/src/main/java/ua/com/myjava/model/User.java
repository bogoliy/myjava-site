package ua.com.myjava.model;

import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
@javax.persistence.TableGenerator(name = "USER_GEN", table = "GENERATOR_TABLE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VALUE", pkColumnValue = "USER")
@Entity
@Embeddable
@Table(name = "user", schema = "myjava")
public class User {
    @XmlElement
    private int id;
    @XmlElement
    private String name;

    private String login;

    private String password;

    @Id
    @Column(name = "us_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "us_name")
    @Field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "us_login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "us_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
