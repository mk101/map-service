package kolesov.maksim.mapping.map.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class RangeDto<T extends Comparable<? super T>> {

    @NotNull
    private T upper;

    @NotNull
    private T lower;

}
