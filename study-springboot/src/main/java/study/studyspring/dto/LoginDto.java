package study.studyspring.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NonNull
    @Size(min = 3, max = 50)
    private String username;

    @NonNull
    @Size(min = 3, max = 50)
    private String password;
}
