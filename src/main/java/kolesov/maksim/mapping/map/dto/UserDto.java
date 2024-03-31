package kolesov.maksim.mapping.map.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID id;
    @NotEmpty
    @JsonProperty("first_name")
    private String firstName;
    @NotEmpty
    @JsonProperty("last_name")
    private String lastName;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;

    private Boolean active;

    private List<UserRoleDto> roles;

}
