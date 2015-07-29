package ua.in.petybay.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by slavik on 20.07.15.
 */
@Document(collection = "adoptions")
@Data
public class AdOptions {
    @Id
    private String id;

    private List<Option> optionList;
}
