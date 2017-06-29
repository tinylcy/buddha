package org.tinylcy.examples;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class CreateNodeExample implements Watcher {

    private static final CountDownLatch connectedSignal = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new CreateNodeExample());
        connectedSignal.await();

        // 同步创建临时节点
        String ephemeralPath = zk.create("/zk-test-create-ephemeral-", "123".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("同步创建临时节点成功: " + ephemeralPath);

        // 同步创建临时顺序节点
        String sequentialPath = zk.create("/zk-test-create-sequential-", "456".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("同步创建临时顺序节点成功: " + sequentialPath);

        // 异步创建临时节点
        zk.create("/zk-test-create-async-ephemeral-", "abc".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new MyStringCallback(), "我是传递内容");

        // 异步创建临时顺序节点
        zk.create("/zk-test-create-async-sequential-", "def".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new MyStringCallback(), "我是传递内容");

        Thread.sleep(10000);
    }

    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
        }
    }

}
