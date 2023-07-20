package ru.cft.shift.intensive.template.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import ru.cft.shift.intensive.template.enumeration.PersonRole;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class Users {
    @PrimaryKey
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String role;

    public void setRole(PersonRole role) {
        this.role = role.getRole();
    }
}
