package org.tinylcy;

import org.apache.log4j.Logger;
import org.tinylcy.zookeeper.ZooKeeperManager;

import java.util.List;

/**
 * Created by chenyangli.
 */
public class ServiceDiscovery {

    private static final Logger LOGGER = Logger.getLogger(ServiceDiscovery.class);

    private ZooKeeperManager manager;

    public ServiceDiscovery(ZooKeeperManager manager) {
        this.manager = manager;
    }

    public void connect() {
        manager.connect();
    }

    public String discover() {
        List<String> services = manager.listChildren(ZooKeeperManager.ZK_REGISTRY_PATH);
        int size = services.size();
        int index = RandomGenerator.randInt(0, size - 1);
        String connectString = services.get(index);
        LOGGER.info("Select connection [" + connectString + "] as service provider.");
        return connectString;
    }

    public void disconnect() {
        manager.close();
    }

}
