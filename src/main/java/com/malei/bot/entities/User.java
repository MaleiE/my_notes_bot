package com.malei.bot.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "user")
public class User implements Serializable {
    private Long id;
    private Integer userIdTelegram;
    private List<Notes> notes;
    private String name;
    private Integer step;

    public User() {
    }

    public User(Integer userIdTelegram) {
        this.userIdTelegram = userIdTelegram;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID",unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "USER_ID_TELEGRAM")
    public Integer getUserIdTelegram() {
        return userIdTelegram;
    }

    public void setUserIdTelegram(Integer userIdTelegram) {
        this.userIdTelegram = userIdTelegram;
    }

    @Column(name = "STEP",nullable = false, columnDefinition = "int default 0")
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userIdTelegram, user.userIdTelegram) &&
                Objects.equals(notes, user.notes) &&
                Objects.equals(name, user.name) &&
                Objects.equals(step, user.step);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userIdTelegram, notes, name, step);
    }
    /*
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", userIdTelegram=").append(userIdTelegram);
        sb.append(", notes=").append(notes);
        sb.append(", name='").append(name).append('\'');
        sb.append(", step=").append(step);
        sb.append('}');
        return sb.toString();
    }
    */
}
