package controllers;

import java.util.List;
import models.ImageModel;
import models.Tag;
import org.codehaus.jackson.node.ObjectNode;
import play.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(views.html.index.render("Your new application is ready."));
    }

    public static Result getImages() {
        ObjectNode response = Json.newObject();
        response.put("images", Json.toJson(ImageModel.finderImageModel.all()));
        return ok(response);
    }

    public static Result addImage() {
        ImageModel image = new ImageModel();
        image.save();
        ObjectNode response = Json.newObject();
        response.put("id", image.id);
        return ok(response);
    }

    public static Result addTag(Long id) {
        DynamicForm form = DynamicForm.form().bindFromRequest();
        String author = form.get("author");
        String theme = form.get("theme");
        ImageModel image = ImageModel.finderImageModel.byId(id);
        image.addTag("author", author);
        image.addTag("theme", theme);

        return ok();
    }

    public static Result getImagesByTag(String tagName, String tagValue) {
        List<Long> imageIds = Tag.getIDsImagesWithTag(tagName, tagValue);
        ObjectNode response = Json.newObject();
        response.put("images", Json.toJson(imageIds));
        return ok(response);
    }
  
}
