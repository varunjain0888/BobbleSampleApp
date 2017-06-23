package com.dao.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by varun jain on 23/06/17.
 */
public class BobbleDaoGenerator {

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1017, "com.bobble.bobblesampleapp");

        addSchema(schema);

        new DaoGenerator().generateAll(schema, "../BobbleSampleApp/dao-generator/src/main/java/com/dao/generator/");

    }


    private static void addSchema(Schema schema) {
        Entity gifs = schema.addEntity("Gifs");
        gifs.addLongProperty("id").primaryKey().notNull().autoincrement();
        gifs.addStringProperty("gifName");
        gifs.addStringProperty("path");

        Entity sticker = schema.addEntity("sticker");
        sticker.addLongProperty("id").primaryKey().notNull().autoincrement();
        sticker.addStringProperty("stickerName");
        sticker.addStringProperty("path");
    }
}
