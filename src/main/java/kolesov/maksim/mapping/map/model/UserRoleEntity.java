package kolesov.maksim.mapping.map.model;

import jakarta.persistence.*;
import kolesov.maksim.mapping.map.model.key.UserRoleKey;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@ToString(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(UserRoleKey.class)
public class UserRoleEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Id
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            updatable = false,
            insertable = false
    )
    private UserEntity user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return getUserId() != null && Objects.equals(getUserId(), that.getUserId())
                && getRole() != null && Objects.equals(getRole(), that.getRole());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(userId, role);
    }
}
