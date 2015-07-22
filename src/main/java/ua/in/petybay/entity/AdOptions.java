package ua.in.petybay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by slavik on 20.07.15.
 */
@Document(collection = "adoptions")
public class AdOptions {
    @Id
    private String id;

    private List<Option> optionList;

    public AdOptions() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
}
