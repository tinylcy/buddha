package org.tinylcy;

import org.apache.log4j.Logger;
import org.tinylcy.zookeeper.ZKConstant;
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

    public String discover() {
        manager.connect();
        List<String> services = manager.listChildren(ZKConstant.ZK_REGISTRY_PATH);
        int size = services.size();
        int index = RandomGenerator.randInt(0, size - 1);
        String connectString = services.get(index);
        LOGGER.info("Select connection [" + connectString + "] as service provider.");
        return connectString;
    }

}
