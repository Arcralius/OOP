package com.arcralius.ff.lwjgl3.entity.Entitybuilder;




import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.EntityFactory;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.Entity_abstract_factory;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.PlayableFactory;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.NonPlayableFactory;
import com.arcralius.ff.lwjgl3.entity.abstractFactory.FoodFactory;

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
