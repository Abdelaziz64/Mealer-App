package com.example.myloginapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test4 {
    public boolean firstnameisempty(String email){return email.isEmpty();}
    @Test
    public void email_isunvalid() {
        assertEquals(true,firstnameisempty(""));
        assertEquals(false,firstnameisempty("aa"));

    }
}
