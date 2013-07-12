package models;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

@Entity
public class Tag extends Model {

    @Id
    public Long id;

    public String name;

    public String value;

    @ManyToMany
    @JsonIgnore
    public List<ImageModel> imagesWithTag = new ArrayList<ImageModel>();

    public static List<Long> getIDsImagesWithTag(String tagName, String tagValue) {

        /* Yes this is probably a slow way of doing it, just hacking this up for quick demonstration */
        /* Will use JSON mapping and efficiency later */

        Tag tag = finderTag.where().eq("name", tagName).eq("value", tagValue).findUnique();

        List<Long> imageIds = new ArrayList<Long>();
        if(tag == null) return null;
        for(ImageModel model : tag.imagesWithTag) {
            imageIds.add(model.id);
        }
        return imageIds;
    }

    public static Finder<Long,Tag> finderTag = new Finder<Long, Tag>(
            Long.class, Tag.class
    );

    public static Tag newTag(String tagName, String tagValue) {
        Tag tag = new Tag();
        tag.name = tagName;
        tag.value = tagValue;
        tag.save();
        return tag;
    }

}
