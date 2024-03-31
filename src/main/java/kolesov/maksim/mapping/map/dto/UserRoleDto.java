package kolesov.maksim.mapping.map.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import kolesov.maksim.mapping.map.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class UserRoleDto {

    @JsonProperty("user_id")
    private UUID userId;

    @NotNull
    private Role role;

}
