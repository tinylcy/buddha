package org.tinylcy.service;

import org.tinylcy.annotation.RpcService;

/**
 * Created by chenyangli.
 */
@RpcService(value = IHelloService.class)
public class HelloServiceImpl implements IHelloService {
    public String hello(String name) {
        return "hello! " + name;
    }
}
