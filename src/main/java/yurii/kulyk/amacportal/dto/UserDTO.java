package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    public UUID id;
    public String email;
    public Set<String> roles;
}
