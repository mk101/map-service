package kolesov.maksim.mapping.map.mapper;

import io.hypersistence.utils.hibernate.type.range.Range;
import kolesov.maksim.mapping.map.dto.LayerDto;
import kolesov.maksim.mapping.map.dto.RangeDto;
import kolesov.maksim.mapping.map.model.LayerEntity;
import kolesov.maksim.mapping.map.model.LayerTagEntity;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface LayerMapper extends AbstractMapper<LayerEntity, LayerDto> {

    default Timestamp offsetToTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(offsetDateTime.toLocalDateTime());
    }

    default OffsetDateTime timestampToOffset(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return OffsetDateTime.of(timestamp.toLocalDateTime(), ZoneOffset.UTC);
    }

    default String tagEntity(LayerTagEntity tag) {
        if (tag == null) {
            return null;
        }
        return tag.getValue();
    }

    default LayerTagEntity tagValue(String value) {
        if (value == null) {
            return null;
        }
        return LayerTagEntity.builder()
                .value(value)
                .build();
    }

    default <T extends Comparable<? super T>> Range<T> rangeDto(RangeDto<T> dto) {
        if (dto == null) {
            return null;
        }
        return Range.closed(dto.getUpper(), dto.getLower());
    }

    default <T extends Comparable<? super T>> RangeDto<T> range(Range<T> r) {
        if (r == null) {
            return null;
        }
        return RangeDto.<T>builder()
                .lower(r.lower())
                .upper(r.upper())
        .build();
    }

}
