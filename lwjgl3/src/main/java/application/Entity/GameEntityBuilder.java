package application.Entity;




import engine.entity.BaseEntity;
import application.Entity.abstractFactory.EntityFactory;
import application.Entity.abstractFactory.Entity_abstract_factory;
import application.Entity.abstractFactory.PlayableFactory;
import application.Entity.abstractFactory.NonPlayableFactory;
import application.Entity.abstractFactory.FoodFactory;

public class GameEntityBuilder {

    public static BaseEntity createEntity(String type, String id, String texturePath,
                                          float x, float y, float speed, float width, float height, float hp) {
        // Use the existing EntityFactory to determine the concrete type.
        Entity_abstract_factory baseFactory = EntityFactory.getFactory(type);

        // Use the concrete class info from baseFactory to create a new instance with parameters.
        if (baseFactory.getClass() == PlayableFactory.class) {
            return new PlayableFactory(id, texturePath, x, y, speed, width, height, hp).createEntity();
        } else if (baseFactory.getClass() == NonPlayableFactory.class) {
            return new NonPlayableFactory(id, texturePath, x, y, speed, width, height).createEntity();
        }  else {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }

    public static BaseEntity createFoodEntity(String id, String foodType, String texturePath,
                                              float x, float y, float width, float height) {
        return new FoodFactory(id, foodType, texturePath, x, y, width, height).createEntity();
    }
}
