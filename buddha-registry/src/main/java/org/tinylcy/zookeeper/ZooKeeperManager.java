package org.tinylcy.zookeeper;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.tinylcy.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class ZooKeeperManager {

    private static final Logger LOGGER = Logger.getLogger(ZooKeeperManager.class);
    private static final PropertyReader READER = new PropertyReader("zookeeper.properties");
    //超时时间
    public static final int ZK_SESSION_TIMEOUT = Integer.parseInt(READER.getProperty("ZK_SESSION_TIMEOUT"));
    //路径
    public static final String ZK_REGISTRY_PATH = READER.getProperty("ZK_REGISTRY_PATH");

    private String zkAddress;
    private ZooKeeper zk;

    public ZooKeeperManager(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public void connect() {
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        try {
            zk = new ZooKeeper(zkAddress, ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    connectedSignal.countDown();
                }
            });
            connectedSignal.await();

            LOGGER.info("Successfully connected to " + zkAddress);
        } catch (IOException | InterruptedException e) {
            LOGGER.info("Failed to connect to " + zkAddress);
            e.printStackTrace();
        }
    }

    public void createNode(String nodePath) {
        try {
            if (zk.exists(ZK_REGISTRY_PATH, true) == null) {
                zk.create(ZK_REGISTRY_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            //创建子节点为临时节点，在服务下线时自动删除
            zk.create(ZK_REGISTRY_PATH + "/" + nodePath, null,
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            LOGGER.info("Node " + nodePath + " have been created successfully.");
            //zk.close();
        } catch (InterruptedException | KeeperException e) {
            LOGGER.error("Creating node " + nodePath + " failed.");
            e.printStackTrace();
        }
    }

    public List<String> listChildren(String nodePath) {
        checkGroupPath(nodePath);
        List<String> result = new ArrayList<String>();
        try {
            List<String> children = zk.getChildren(nodePath, true);
            result.addAll(children);
            //zk.close();
        } catch (KeeperException | InterruptedException e) {
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
            //zk.close();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkGroupPath(String path) {
        if (Strings.isNullOrEmpty(path)) {
            String msg = "group path can not be null or empty: " + path;
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (!path.startsWith(ZK_REGISTRY_PATH)) {
            String msg = "Illegal access to group: " + path;
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

}
