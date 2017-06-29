package org.tinylcy.examples;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class GetChildrenExample implements Watcher {

    private static final CountDownLatch connectedSignal = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenExample());
        connectedSignal.await();

        // 创建父节点 /test
        zk.create("/ex2", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        // 在父节点/test下创建a1节点
        zk.create("/ex2/a1", "456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 同步获得结果
        List<String> childrenList = zk.getChildren("/ex2", true);
        System.out.println(childrenList);

        // 异步获得结果
        // zk.getChildren("/ex2", true, new MyChildren2Callback(), null);

        // 在父节点/test下面创建a2节点
        zk.create("/ex2/a2", "456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Thread.sleep(10000);

    }

    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (watchedEvent.getType() == Event.EventType.None && watchedEvent.getPath() == null) {
                connectedSignal.countDown();
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("获得children,并注册监听: " + zk.getChildren(watchedEvent.getPath(), true));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
