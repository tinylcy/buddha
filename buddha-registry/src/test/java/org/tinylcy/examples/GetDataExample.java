package org.tinylcy.examples;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chenyangli.
 */
public class GetDataExample implements Watcher {

    private static final CountDownLatch connectedSignal = new CountDownLatch(1);
    private static ZooKeeper zk;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper("127.0.0.1:2181", 5000, new GetDataExample());
        connectedSignal.await();
        
        String path = "/test-get-data";
        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println("同步读取节点内容：" + new String(zk.getData(path, true, stat)));
        System.out.println("同步读取Stat：czxid=" + stat.getCzxid()
                + ";mzxid=" + stat.getMzxid() + ";version=" + stat.getVersion());

        zk.setData(path, "123".getBytes(), -1);
        Thread.sleep(10000);
    }

    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) { // 连接时的监听事件
                connectedSignal.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) { // 子节点内容变更时的监听
                try {
                    System.out.println("监听获得通知内容：data="
                            + new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println("监听获得通知Stat：czxid=" + stat.getCzxid()
                            + ";mzxid=" + stat.getMzxid() + ";version=" + stat.getVersion());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
