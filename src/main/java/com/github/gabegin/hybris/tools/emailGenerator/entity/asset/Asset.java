package com.github.gabegin.hybris.tools.emailGenerator.entity.asset;

import com.github.gabegin.hybris.tools.emailGenerator.watcher.PacketEvent;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;

public interface Asset {
    Path getPath();

    default String getFilename() {
        final Path path = getPath();

        if (path == null) {
            return null;
        }

        return path.getFileName().toString();
    }

    default String getName() {
        final String filename = this.getFilename();

        if (filename == null) {
            return null;
        }

        return FilenameUtils.getBaseName(this.getFilename());
    }

    default void onCreate(final PacketEvent packetEvent) {
        packetEvent.registerAsset();
    }

    default void onModify(final PacketEvent packetEvent) {
        packetEvent.generateEmail();
    }

    default void onDelete(final PacketEvent packetEvent) {
        packetEvent.unregisterAsset();
    }
}
