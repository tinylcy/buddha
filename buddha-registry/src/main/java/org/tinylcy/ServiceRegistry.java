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

    public void connect() {
        manager.connect();
    }

    /**
     * Service format -> host:port
     *
     * @param host
     * @param port
     */
    public void register(String host, int port) {
        //manager.connect();
        manager.createNode(host + ":" + port);
        LOGGER.info("Register to " + host + ":" + port + " successfully");
    }

    /**
     * Delete all the registered services before rpc server start.
     */
    public void init() {
        //manager.connect();
        manager.deleteNode(ZooKeeperManager.ZK_REGISTRY_PATH);
        LOGGER.info("Delete Node: " + ZooKeeperManager.ZK_REGISTRY_PATH);
    }

    public void disconnect() {
        manager.close();
    }

}
