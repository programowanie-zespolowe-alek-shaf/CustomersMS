package pl.agh.customers.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.agh.customers.mysql.enums.RoleEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

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
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
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

    @PreRemove
    private void removeCurrentUserRoleFromUser() {
        user.getRoles().remove(this);
    }
}
