package com.mfl.common.entity;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4304602261923969054L;
    private Long id;
    private String account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }



}
