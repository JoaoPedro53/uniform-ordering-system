package jota.developer.domain;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private String name;
    private String number;
}
