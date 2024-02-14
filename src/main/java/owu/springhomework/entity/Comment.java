package owu.springhomework.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("comments")
public class Comment {

    @MongoId
    private ObjectId id;

    private Long postId;

    private String text;
}
