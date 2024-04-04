package com.thepinkhacker.apollo.resource;

import com.google.gson.Gson;
import com.thepinkhacker.apollo.Apollo;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public abstract class ApolloResources {
    public static final HashMap<Identifier, SpaceBody> spaceBodies = new HashMap<>();
    public static final String RESOURCES_PATH = "apollo";
    private static final ResourceFinder BODIES_FINDER = ResourceFinder.json(RESOURCES_PATH + "/bodies");
    public static void register() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(newSimpleResourceReload("bodies", manager -> {
            // Clear cache
            spaceBodies.clear();

            BODIES_FINDER.findResources(manager).forEach((id, resource) -> {
                try (InputStream stream = resource.getInputStream()) {
                    String[] path = id.getPath().split("/");
                    Identifier dimensionId = new Identifier(id.getNamespace(), path[path.length - 1].split("\\.")[0]);
                    SpaceBody body = new Gson().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), SpaceBody.class);
                    spaceBodies.put(dimensionId, body);
                } catch (Exception e) {
                    Apollo.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                }
            });
        }));
    }

    private static SimpleSynchronousResourceReloadListener newSimpleResourceReload(String path, ReloadResources reloadResources) {
        return new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(Apollo.MOD_ID, path);
            }

            @Override
            public void reload(ResourceManager manager) {
                reloadResources.onReloadResources(manager);
            }
        };
    }

    @FunctionalInterface
    private interface ReloadResources {
        void onReloadResources(ResourceManager manager);
    }
}
