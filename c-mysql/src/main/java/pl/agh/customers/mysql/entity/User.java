package pl.agh.customers.mysql.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "customer")
public class User {

    @Id
    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email")
    private String email;

    @Getter
    @Setter
    @Column(name = "phone")
    private Integer phone;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    @Getter
    @Setter
    @Column(name = "enabled")
    private Boolean enabled;
}