package pl.agh.customers.mysql.entity;

import lombok.*;

import javax.persistence.*;
import pl.agh.customers.mysql.enums.RoleEnum;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Getter
    @Setter
    @Column(name = "role", columnDefinition = "ENUM ('ROLE_USER', 'ROLE_ADMIN')")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public UserRoles(User user, RoleEnum roleEnum) {
        this.user = user;
        this.roleEnum = roleEnum;
    }
}
