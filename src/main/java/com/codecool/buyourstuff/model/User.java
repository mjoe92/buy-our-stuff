package com.codecool.buyourstuff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@AllArgsConstructor
@Getter
@Setter
public class User extends BaseModel {
    private final String name;
    private final String password;
    private int cartId;

    public User(String name, String password) {
        this.name = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public int getCartId() {
        return cartId;
    }
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
