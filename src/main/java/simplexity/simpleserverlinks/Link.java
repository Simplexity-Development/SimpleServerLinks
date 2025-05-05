package simplexity.simpleserverlinks;

import net.kyori.adventure.text.Component;
import org.bukkit.ServerLinks;

import javax.annotation.Nullable;
import java.net.URI;

@SuppressWarnings("UnstableApiUsage")
public record Link(URI uri, Component displayName, Component description, @Nullable ServerLinks.Type linkType) {
}
