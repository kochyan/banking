package ru.kochyan.banking.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import ru.kochyan.banking.enums.LogLevel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Log extends AbstractEntity{
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User author;

    @Enumerated(EnumType.STRING)
    private LogLevel level;

    @CreatedDate
    private Date created;


    @PrePersist
    protected void onCreate(){
        this.created = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Log log = (Log) o;
        return getId() != null && Objects.equals(getId(), log.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
