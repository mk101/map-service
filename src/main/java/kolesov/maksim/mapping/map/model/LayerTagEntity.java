package kolesov.maksim.mapping.map.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "layerEntity")
@Entity
@Table(name = "layer_tag")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayerTagEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String value;

    @Column(name = "layer_id", nullable = false)
    private UUID layerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "layer_id",
            referencedColumnName = "id",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private LayerEntity layerEntity;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LayerTagEntity that = (LayerTagEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
