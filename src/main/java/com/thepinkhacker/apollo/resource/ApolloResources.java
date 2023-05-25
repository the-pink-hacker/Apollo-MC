package com.thepinkhacker.apollo.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thepinkhacker.apollo.Apollo;
import com.thepinkhacker.apollo.world.dimension.SpaceBody;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class ApolloResources {
    private static final ResourceFinder BODIES_FINDER = ResourceFinder.json(Apollo.MOD_ID + "/bodies");
    public static void register() {
        SpaceBodyManager spaceBodyManager = SpaceBodyManager.getInstance();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(newSimpleResourceReload("bodies", manager -> {
            // Clear cache
            spaceBodyManager.clearSpaceBodies();

            BODIES_FINDER.findResources(manager).forEach((id, resource) -> {
                try (InputStream stream = resource.getInputStream()) {
                    String[] path = id.getPath().split("/");
                    Identifier dimensionId = new Identifier(id.getNamespace(), path[path.length - 1].split("\\.")[0]);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    SpaceBody.registerGsonType(gsonBuilder);
                    SpaceBody spaceBody = gsonBuilder.create().fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), SpaceBody.class);
                    spaceBodyManager.addSpaceBody(dimensionId, spaceBody);
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
                return Apollo.getIdentifier(path);
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
