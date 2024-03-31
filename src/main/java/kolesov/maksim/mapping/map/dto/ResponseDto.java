package kolesov.maksim.mapping.map.dto;

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
public class ResponseDto<T> {

    private T data;
    private boolean success;
    private String error;

    public ResponseDto(T data) {
        this.success = true;
        this.error = "";
        this.data = data;
    }

}
