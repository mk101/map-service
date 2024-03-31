package kolesov.maksim.mapping.map.model.key;

import kolesov.maksim.mapping.map.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleKey implements Serializable {

    private UUID userId;

    private Role role;

}
