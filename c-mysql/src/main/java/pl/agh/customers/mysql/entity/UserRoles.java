package pl.agh.customers.mysql.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles", schema = "customer")
public class UserRoles {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "username")
    private String username;

    @Getter
    @Setter
    @Column(name = "role")
    private String role;
}
