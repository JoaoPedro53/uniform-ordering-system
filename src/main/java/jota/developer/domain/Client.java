package jota.developer.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Schema(hidden = true)
    private String name;
    @Schema(hidden = true)
    private String number;
}
