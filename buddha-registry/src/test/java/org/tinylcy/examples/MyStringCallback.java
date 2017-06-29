package org.tinylcy.examples;

import org.apache.zookeeper.AsyncCallback;

/**
 * Created by chenyangli.
 */
public class MyStringCallback implements AsyncCallback.StringCallback {

    public void processResult(int i, String s, Object o, String s1) {
        System.out.println("异步创建回调结果 - 状态: " + i + ", 创建路径: " + s
                + ", 传递信息: " + o + ", 实际节点名称: " + s1);
    }
}
