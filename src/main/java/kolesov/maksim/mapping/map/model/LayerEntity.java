package kolesov.maksim.mapping.map.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import io.hypersistence.utils.hibernate.type.range.Range;
import jakarta.persistence.*;
import kolesov.maksim.mapping.map.dto.geojson.GeoJson;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "layer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayerEntity {

    @Id
    private UUID id;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "edit_by", nullable = false)
    private UUID editBy;

    @Column(name = "edit_at", nullable = false)
    private Timestamp editAt;

    @Type(JsonType.class)
    @Column(nullable = false)
    private GeoJson data;

    @Type(PostgreSQLRangeType.class)
    @Column(name = "horizontal_area", nullable = false)
    private Range<BigDecimal> horizontalArea;

    @Type(PostgreSQLRangeType.class)
    @Column(name = "vertical_area", nullable = false)
    private Range<BigDecimal> verticalArea;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LayerStatus status;

    @Transient
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "layerEntity")
    private List<LayerTagEntity> tags;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LayerEntity that = (LayerEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
