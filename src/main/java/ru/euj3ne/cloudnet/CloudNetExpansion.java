package ru.euj3ne.cloudnet;

import eu.cloudnetservice.common.log.LogManager;
import eu.cloudnetservice.common.log.Logger;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import eu.cloudnetservice.wrapper.holder.ServiceInfoHolder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class CloudNetExpansion extends PlaceholderExpansion {

    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private final Logger cloudnet = LogManager.logger(CloudNetExpansion.class);

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cloudnet";
    }

    @Override
    public @NotNull String getAuthor() {
        return "ShadowChildUK";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String rawParams) {
        String[] args;
        ServiceInfoHolder serviceInfoHolder = (ServiceInfoHolder)InjectionLayer.ext().instance(ServiceInfoHolder.class);
        String params = rawParams.toLowerCase();
        PlayerManager players = ServiceRegistry.first(PlayerManager.class);

        if (params.startsWith("playercount_")) {
            args = params.split("playercount_");
            if (args.length < 2) return null;
            return String.valueOf(players.taskOnlinePlayers(args[1]).count());
        } else {
            switch (params) {
                case "service_name" -> {
                    return serviceInfoHolder.serviceInfo().serviceId().name();
                }
                case "service_static" -> {
                    return "" + serviceInfoHolder.serviceInfo().configuration().staticService();
                }
                case "service_autodelete" -> {
                    return "" + serviceInfoHolder.serviceInfo().configuration().autoDeleteOnStop();
                }
                case "service_port" -> {
                    return "" + serviceInfoHolder.serviceInfo().address().port();
                }
                case "service_host" -> {
                    return "" + serviceInfoHolder.serviceInfo().address().host();
                }
                case "service_address" -> {
                    return serviceInfoHolder.serviceInfo().address().host() + ":" + serviceInfoHolder.serviceInfo().address().port();
                }
                case "task_name" -> {
                    return serviceInfoHolder.serviceInfo().serviceId().taskName();
                }
                case "total_online" -> {
                    return "" + players.onlineCount();
                }
                case "service_number" -> {
                    return "" + serviceInfoHolder.serviceInfo().serviceId().taskServiceId();
                }
            }
        }
        return null;
    }

}
