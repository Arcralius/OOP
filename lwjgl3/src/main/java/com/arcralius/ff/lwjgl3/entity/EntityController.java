package com.arcralius.ff.lwjgl3.entity;

import java.util.List;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityController {
    private List<BaseEntity> entityList;
    private IEntityFactory entityFactory;

    public EntityController(List<BaseEntity> entityList, IEntityFactory entityFactory) {
        this.entityList = entityList;
        this.entityFactory = entityFactory;
    }

    public void addEntity(String type, String texturePath, float x, float y, String id, float speed, float width, float height) {
        BaseEntity entity = null;
        if (type.equalsIgnoreCase("Playable")) {
            entity = entityFactory.createPlayableEntity(texturePath, x, y, id, speed, width, height);
        } else if (type.equalsIgnoreCase("Non_playable")) {
            entity = entityFactory.createNonPlayableEntity(texturePath, x, y, id, speed, width, height);
        }

        if (entity != null) {
            entityList.add(entity);
            System.out.println("Added entity: " + id + " at (" + x + ", " + y + ")");
        } else {
            System.out.println("Entity creation failed for type: " + type);
        }
    }

    public FoodEntity addFoodEntity(String texturePath, float x, float y, String id, float width, float height,
                                    String foodType) {
        FoodEntity food = entityFactory.createFoodEntity(texturePath, x, y, id, width, height, foodType);

        if (food != null) {
            entityList.add(food);
            System.out.println("Added food: " + id + " (" + foodType + ") at (" + x + ", " + y + ")");
            return food;
        } else {
            System.out.println("Food creation failed");
            return null;
        }
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

    public List<BaseEntity> getEntities() {
        return entityList;
    }
}
