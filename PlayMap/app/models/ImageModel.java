package models;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

@Entity
public class ImageModel extends Model {

    @Id
    public Long id;

    public String imageURL;

    @ManyToMany(mappedBy = "imagesWithTag")
    @MapKey(name = "name")
    public Map<String,Tag> tags = new HashMap<String,Tag>();

    public static Finder<Long,ImageModel> finderImageModel = new Finder<Long, ImageModel>(
            Long.class, ImageModel.class
    );

    public void addTag(String tagName, String value) {
        Tag tag = Tag.finderTag.where().eq("name", tagName).eq("value", value).findUnique();
        if(tag == null) {
            tag = Tag.newTag(tagName, value);
        }
        this.tags.put(tag.name, tag);
        this.saveManyToManyAssociations("tags");
    }

}
