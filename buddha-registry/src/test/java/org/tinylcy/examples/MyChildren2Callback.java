package org.tinylcy.examples;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Created by chenyangli.
 */
public class MyChildren2Callback implements AsyncCallback.Children2Callback {

    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("异步获得getChildren结果，rc=" + rc
                + "；path=" + path + "；ctx=" + ctx + "；children=" + children + "；stat=" + stat);
    }
}
