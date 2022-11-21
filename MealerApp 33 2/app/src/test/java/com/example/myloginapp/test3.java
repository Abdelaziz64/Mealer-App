package com.example.myloginapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class test3 {

    public boolean passwordisempty(String email){return email.isEmpty();}
    @Test
    public void email_isunvalid() {
        assertEquals(true,passwordisempty(""));
        assertEquals(false,passwordisempty("aa"));

    }




}
