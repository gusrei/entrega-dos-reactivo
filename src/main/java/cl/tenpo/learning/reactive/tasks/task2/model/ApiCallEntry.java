package cl.tenpo.learning.reactive.tasks.task2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "endpoint_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallEntry {

    @Id
    private String id;
    private String endpoint;
    private String method;
    private String timestamp;
    private String requestBody;
    private String responseBody;

}
