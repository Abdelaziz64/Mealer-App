package com.example.myloginapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test2 {
    public boolean passwordisshort(String password){
        return password.length()<6;
    }
    @Test
    public void password() {
        assertEquals(true,passwordisshort("anas"));
        assertEquals(false,passwordisshort("aaaaaaa"));

    }

}
