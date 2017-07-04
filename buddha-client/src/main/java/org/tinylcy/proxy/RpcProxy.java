package org.tinylcy.proxy;

import org.apache.log4j.Logger;
import org.tinylcy.RpcClient;
import org.tinylcy.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chenyangli.
 */
public class RpcProxy implements InvocationHandler {

    private static final Logger LOGGER = Logger.getLogger(RpcProxy.class);

    private Class<?> clazz;
    private RpcClient client;

    public RpcProxy(RpcClient client) {
        this.client = client;
    }

    public <T> T newProxy(Class<?> clazz) {
        this.clazz = clazz;
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        RpcResponse response = (RpcResponse) client.call(clazz, method, args);
        return response.getResult();
    }

}
