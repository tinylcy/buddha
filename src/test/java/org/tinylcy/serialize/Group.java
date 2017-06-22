package org.tinylcy.serialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyangli.
 */
public class Group {
    private Long id;
    private String name;
    private List<User> users = new ArrayList<User>();

    public Group() {

    }

    public Group(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
