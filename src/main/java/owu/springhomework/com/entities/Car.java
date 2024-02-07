package owu.springhomework.com.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Model can not be blank or null")
    private String model;

    @NotBlank(message = "Producer can not be blank or null")
    private String producer;

    @Min(value = 1, message = "Power can not be lower than 1")
    private Integer power;

    @Lob
    @Column(name = "image_bytes")
    private byte[] image;
}
