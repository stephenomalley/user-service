package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class User extends Model {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_id_seq")
    public Integer id;

    public String name;

    @Column(unique = true)
    @Constraints.Required
    public String username;

    @Constraints.Email
    public String email;

    @Column(updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreatedTimestamp
    public Timestamp createdTimestamp;

    public Timestamp deletedTimestamp;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdatedTimestamp
    public Timestamp updatedTimestamp;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Timestamp getDeletedTimestamp() {
        return deletedTimestamp;
    }

    public void setDeletedTimestamp(Timestamp deletedTimestamp) {
        this.deletedTimestamp = deletedTimestamp;
    }

    public Timestamp getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }
}
