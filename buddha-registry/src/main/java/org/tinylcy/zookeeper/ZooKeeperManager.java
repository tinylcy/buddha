package org.tinylcy.zookeeper;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class ZooKeeperManager {

    private static final Logger LOGGER = Logger.getLogger(ZooKeeperManager.class);

    private String connectString;
    private ZooKeeper zk;

    public ZooKeeperManager(String connectString) {
        this.connectString = connectString;
    }

    public void connect() {
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        try {
            zk = new ZooKeeper(connectString, ZKConstant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    connectedSignal.countDown();
                }
            });
            connectedSignal.await();

            LOGGER.info("Successfully connected to " + connectString);
        } catch (IOException e) {
            LOGGER.info("Failed to connect to " + connectString);
            e.printStackTrace();
        } catch (InterruptedException e) {
            LOGGER.info("Failed to connect to " + connectString);
            e.printStackTrace();
        }
    }

    public void createNode(String nodePath) {
        try {
            if (zk.exists(ZKConstant.ZK_REGISTRY_PATH, true) == null) {
                zk.create(ZKConstant.ZK_REGISTRY_PATH, null,
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            zk.create(ZKConstant.ZK_REGISTRY_PATH + "/" + nodePath, null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            LOGGER.info(nodePath + " have been created successfully.");
            zk.close();
        } catch (InterruptedException e) {
            LOGGER.error("Creating node " + nodePath + " failed.");
            e.printStackTrace();
        } catch (KeeperException e) {
            LOGGER.error("Creating node " + nodePath + " failed.");
            e.printStackTrace();
        }
    }

    public List<String> listChildren(String nodePath) {
        checkGroupPath(nodePath);
        List<String> result = new ArrayList<String>();
        try {
            List<String> children = zk.getChildren(nodePath, true);
            for (String c : children) {
                result.add(c);
            }
            zk.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteNode(String nodePath) {
        checkGroupPath(nodePath);
        try {
            List<String> children = zk.getChildren(nodePath, true);
            for (String c : children) {
                zk.delete(nodePath + "/" + c, -1);
            }
            zk.delete(nodePath, -1);
            zk.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void checkGroupPath(String path) {
        if (Strings.isNullOrEmpty(path)) {
            String msg = "group path can not be null or empty: " + path;
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (!path.startsWith(ZKConstant.ZK_REGISTRY_PATH)) {
            String msg = "Illegal access to group: " + path;
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

}
