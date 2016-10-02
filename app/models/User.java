package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class User extends Model {

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String name;

    @Column(name = "username")
    public String userName;

    @Constraints.Email
    public String email;

    public Timestamp createdTimestamp;

    public Timestamp deletedTimestamp;

    public Timestamp updatedTimestamp;

}
