package org.tinylcy;

import org.junit.Test;
import org.tinylcy.zookeeper.ZooKeeperManager;

import java.util.List;

/**
 * Created by chenyangli.
 */
public class ZooKeeperManagerTest {

    @Test
    public void testCreateNode() {
        ZooKeeperManager manager = new ZooKeeperManager("127.0.0.1:2181");
        manager.connect();
        manager.createNode("127.0.0.1:9090");
    }

    @Test
    public void testDeleteNode() {
        ZooKeeperManager manager = new ZooKeeperManager("127.0.0.1:2181");
        manager.connect();
        manager.deleteNode(ZooKeeperManager.ZK_REGISTRY_PATH);
    }

    @Test
    public void testListChildren() {
        ZooKeeperManager manager = new ZooKeeperManager("127.0.0.1:2181");
        manager.connect();
        List<String> list = manager.listChildren(ZooKeeperManager.ZK_REGISTRY_PATH);
        System.out.println(list);

    }
}
