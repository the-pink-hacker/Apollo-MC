package com.ryangar46.apollo.util.config;

import com.google.gson.*;
import com.ryangar46.apollo.Apollo;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {
    private final String id;
    private final List<Group> groups = new ArrayList<>();

    public Config(String id) {
        this.id = id;

        // Add meta data
        addGroup(new Group("meta").addProperty("version", 0));
    }

    public Config load() {
        Path file = getPath();

        // Check if the file exists
        if (file.toFile().isFile()) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                JsonElement tree = JsonParser.parseReader(reader);

                // Group
                for (Map.Entry<String, JsonElement> groupEntry : tree.getAsJsonObject().entrySet()) {
                    String group = groupEntry.getKey();

                    // Property
                    for (Map.Entry<String, JsonElement> propertyEntry : groupEntry.getValue().getAsJsonObject().entrySet()) {
                        String property = propertyEntry.getKey();
                        JsonElement value = propertyEntry.getValue();
                        addProperty(group, property, value);
                    }
                }
            } catch (Exception e) {
                Apollo.LOGGER.error("Failed to load config");
                e.printStackTrace();
            }
        }

        return this;
    }

    public void save() {
        Path file = getPath();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject root = new JsonObject();

        // Groups
        for (Group group : groups) {
            JsonObject groupObject = new JsonObject();

            // Properties
            for (Map.Entry<String, JsonElement> property : group.properties.entrySet()) {
                groupObject.add(property.getKey(), property.getValue());
            }

            root.add(group.name, groupObject);
        }

        try {
            Files.writeString(file, gson.toJson(root));
        } catch (IOException e) {
            Apollo.LOGGER.error("Failed to save config");
            e.printStackTrace();
        }
    }

    public Config addGroup(Group group) {
        groups.add(group);
        return this;
    }

    public Group getGroup(String name) {
        for (Group group : groups) {
            if (Objects.equals(group.name, name)) return group;
        }

        return new Group(name);
    }

    private void addProperty(String groupName, String property, JsonElement value) {
        Group group = getGroup(groupName);
        group.addProperty(property, value);
    }

    private Path getPath() {
        return Path.of(FabricLoader.getInstance().getConfigDir().toString(), id, "config.json");
    }

    public static class Group {
        public final String name;
        public final JsonObject properties = new JsonObject();

        public Group(String name) {
            this.name = name;
        }

        public Group addProperty(String name, boolean defaultValue) {
            properties.addProperty(name, defaultValue);
            return this;
        }

        public Group addProperty(String name, String defaultValue) {
            properties.addProperty(name, defaultValue);
            return this;
        }

        public Group addProperty(String name, Character defaultValue) {
            properties.addProperty(name, defaultValue);
            return this;
        }

        public Group addProperty(String name, Number defaultValue) {
            properties.addProperty(name, defaultValue);
            return this;
        }

        public Group addProperty(String name, JsonElement defaultValue) {
            properties.add(name, defaultValue);
            return this;
        }
    }
}
