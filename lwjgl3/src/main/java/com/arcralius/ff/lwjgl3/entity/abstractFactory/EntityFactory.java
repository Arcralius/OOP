package com.arcralius.ff.lwjgl3.entity.abstractFactory;

public class EntityFactory {
    public static Entity_abstract_factory getFactory(String id) {
        if (id.equalsIgnoreCase("playable")) {
            return new PlayableFactory();
        } else if (id.equalsIgnoreCase("non_playable")) {
            return new NonPlayableFactory();
        } else {
            return new FoodFactory();
        }

    }

}
