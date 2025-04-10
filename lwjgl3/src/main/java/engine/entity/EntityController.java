package engine.entity;

import java.util.List;

import application.Entity.FoodData;
import application.Entity.abstractFactory.FoodFactory;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityController {
    private List<BaseEntity> entityList;


    public EntityController(List<BaseEntity> entityList) {
        this.entityList = entityList;

    }

    public void addEntity(BaseEntity entity) {


        if (entity != null) {
            entityList.add(entity);
            System.out.println("Added entity: " + entity.getId() + " at (" + entity.getX() + ", " + entity.getY() + ")");
        } else {
            System.out.println("Entity creation failed");
        }
    }

//    public FoodEntity addFoodEntity(String texturePath, float x, float y, String id, float width, float height,
//                                    String foodType) {
//        FoodEntity food = entityFactory.createFoodEntity(texturePath, x, y, id, width, height, foodType);
//
//        if (food != null) {
//            entityList.add(food);
//            System.out.println("Added food: " + id + " (" + foodType + ") at (" + x + ", " + y + ")");
//            return food;
//        } else {
//            System.out.println("Food creation failed");
//            return null;
//        }
//    }

    public FoodEntity addFoodEntity(String foodType, float x, float y, String id, float width, float height) {
        String texturePath = FoodData.getTexturePath(foodType);
        FoodFactory factory = new FoodFactory(id, foodType, texturePath, x, y, width, height);
        FoodEntity food = (FoodEntity) factory.createEntity();

        if (food != null) {
            entityList.add(food);
            System.out.println("Added food: " + id + " (" + foodType + ") at (" + x + ", " + y + ")");
        } else {
            System.out.println("Food creation failed");
        }

        return food;
    }


    public BaseEntity getEntityById(String id) {
        for (BaseEntity entity : entityList) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    public void removeEntity(BaseEntity entity) {
        entityList.remove(entity);
    }

    public void draw(SpriteBatch batch) {
        for (BaseEntity entity : entityList) {
            entity.draw(batch);
        }
    }


}
