package org.tinylcy.serialize.kryo;

import org.junit.Test;
import org.tinylcy.serialize.Group;
import org.tinylcy.serialize.SerializerFactory;
import org.tinylcy.serialize.User;

import java.util.ArrayList;

/**
 * Created by chenyangli.
 */
public class KryoSerializerTest {

    @Test
    public void kryoTest() {
        KryoSerializer serializer = (KryoSerializer) SerializerFactory.loadSerializer();
        User a = new User(1L, "a");
        User b = new User(2L, "b");
        ArrayList<User> users = new ArrayList<User>();
        users.add(a);
        users.add(b);
        Group group = new Group(23L, "G", users);
        byte[] bytes = serializer.serialize(group);
        System.out.println(bytes.length);

        Group g = serializer.deserialize(bytes, Group.class);
        System.out.println(g);
    }
}