package org.tinylcy;

import org.apache.log4j.Logger;
import org.tinylcy.zookeeper.ZooKeeperManager;

/**
 * Created by chenyangli.
 */
public class ServiceRegistry {

    private static final Logger LOGGER = Logger.getLogger(ServiceRegistry.class);

    private ZooKeeperManager manager;

    public ServiceRegistry(ZooKeeperManager manager) {
        this.manager = manager;
    }

    public void register(String host, int port) {
        manager.connect();
        manager.createNode(host + ":" + port);
        LOGGER.info("Register to " + host + ":" + port + " successfully");
    }

}
