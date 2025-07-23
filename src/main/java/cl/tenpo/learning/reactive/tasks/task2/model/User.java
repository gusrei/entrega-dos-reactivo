package cl.tenpo.learning.reactive.tasks.task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("users")
public class User {

    @Id
    private String id;
    private String name;
    private String email;

}
