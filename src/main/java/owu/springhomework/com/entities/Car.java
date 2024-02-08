package owu.springhomework.com.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("cars")
public class Car {

    @MongoId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ObjectId id;

    @NotBlank
    private String model;

    @NotBlank
    private String producer;

    @NotBlank
    @Min(100)
    private Integer power;
}
