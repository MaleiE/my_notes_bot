package com.malei.bot.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "notes")
public class Notes {
    private Long id;
    private String textNotes;
    private DateTime createDate;
    private DateTime alertDate;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTES_ID",unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TEXT_NOTES")
    public String getTextNotes() {
        return textNotes;
    }

    public void setTextNotes(String textNotes) {
        this.textNotes = textNotes;
    }

    @Column(name = "CREATE_DATE", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }

    @Column(name = "ALERT_DATE", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(DateTime alertDate) {
        this.alertDate = alertDate;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notes notes = (Notes) o;
        return Objects.equals(id, notes.id) &&
                Objects.equals(textNotes, notes.textNotes) &&
                Objects.equals(createDate, notes.createDate) &&
                Objects.equals(alertDate, notes.alertDate) &&
                Objects.equals(user, notes.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textNotes, createDate, alertDate, user);
    }


    @Override
    public String toString() {
        return "Notes{" + "id=" + id +
                ", textNotes='" + textNotes + '\'' +
                ", createDate=" + createDate +
                ", alertDate=" + alertDate +
                ", user=" + user +
                '}';
    }
}
