package io.github.justfoxx.outset

import com.electronwill.nightconfig.core.file.FormatDetector
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.ResourceType

class Events {
    init {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA)
            .registerReloadListener(
                object : SimpleSynchronousResourceReloadListener {
                    override fun reload(resourceManager: ResourceManager) {
                        onOriginDataReload(resourceManager)
                    }

                    override fun getFabricId() = "outsets".getId()
                }
            )
    }

    fun onOriginDataReload(manager: ResourceManager) {
        val files =
            manager.findResources("outsets") { FormatDetector.detectByName(it.path) != null }
                .mapKeys { it.key.path.substringBefore('.') }
        this::class.getLogger().info("Loaded ${files.keys}")
    }
}
