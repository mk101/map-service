package kolesov.maksim.mapping.map.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kolesov.maksim.mapping.map.dto.geojson.GeoJson;
import kolesov.maksim.mapping.map.model.LayerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class LayerDto {

    private UUID id;

    private UUID createdBy;

    private OffsetDateTime createdAt;

    private UUID editBy;

    private OffsetDateTime editAt;

    @NotNull
    private GeoJson data;

    private RangeDto<BigDecimal> horizontalArea;

    private RangeDto<BigDecimal> verticalArea;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private LayerStatus status;

    @NotNull
    private List<String> tags;

}
