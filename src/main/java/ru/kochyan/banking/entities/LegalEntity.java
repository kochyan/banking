package ru.kochyan.banking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ru.kochyan.banking.enums.EntityStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*@NamedEntityGraph(name = "legalEntity.eager", includeAllAttributes = true,
        attributeNodes = {
                @NamedAttributeNode(value = "checkingAccounts", subgraph = "checkingAccountGraph")
        },
        subgraphs = {
            @NamedSubgraph(name = "checkingAccountGraph", attributeNodes = {
                    @NamedAttributeNode("paymentPurposes")
            })
        }
)*/
@Entity
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LegalEntity extends AbstractEntity {
    private String name;

    @Enumerated(value = EnumType.STRING)
    private EntityStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "legalEntity")
    @ToString.Exclude
    @JsonIgnore
    private Set<CheckingAccount> checkingAccounts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LegalEntity that = (LegalEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}