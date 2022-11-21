package com.example.myloginapp;

import static org.junit.Assert.assertEquals;

import android.util.Patterns;

import org.junit.Test;

public class test1 {
    @Test
    public void email_isunvalid() {
        assertEquals(true,emailisempty(""));
        assertEquals(false,emailisempty("aa"));

    }


    public boolean emailisempty(String email){return email.isEmpty();}


}
