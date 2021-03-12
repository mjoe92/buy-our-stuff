package com.codecool.buyourstuff.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ShippingInfoTest {

    String NAME_DEFAULT = "Mohandas Karamchand Gandhi";
    String EMAIL_DEFAULT = "AtomicGandhi1869@hotmail.com";
    String PHONE_DEFAULT = "(91)235-95-139";
    String ADDRESS_DEFAULT = "4215/1 Ansari Road, Daryaganj, New Delhi, Delhi, 110002, India";
    ShippingInfo SI_DEFAULT = new ShippingInfo(NAME_DEFAULT, EMAIL_DEFAULT, PHONE_DEFAULT, ADDRESS_DEFAULT, ADDRESS_DEFAULT);

    @Test
    void successfulCreation() {
        assertNotNull(SI_DEFAULT);
    }

    /*
    @Test
    void invalidNameThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new ShippingInfo(NAME_DEFAULT, EMAIL_DEFAULT, PHONE_DEFAULT, ADDRESS_DEFAULT, ADDRESS_DEFAULT));
    }
    */
    @Test
    void invalidEmailRegexThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new ShippingInfo(NAME_DEFAULT, "wrongemail.com@imstillwrong", PHONE_DEFAULT, ADDRESS_DEFAULT, ADDRESS_DEFAULT));
    }
    @Test
    void invalidPhoneNumberRegexThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new ShippingInfo(NAME_DEFAULT, EMAIL_DEFAULT, "123-INVALID-456", ADDRESS_DEFAULT, ADDRESS_DEFAULT));
    }
    @Test
    void invalidAddressRegexThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new ShippingInfo(NAME_DEFAULT, EMAIL_DEFAULT, PHONE_DEFAULT, "Invalid St 123, Thingamajig County, Nowhere AS 123 GH, Non-existent Kingdom", ADDRESS_DEFAULT));
    }


    @Test
    void lombokNullNameThrows() {
        assertThrows(NullPointerException.class, () ->
                new ShippingInfo(null, SI_DEFAULT.getEmail(), SI_DEFAULT.getPhoneNumber(), SI_DEFAULT.getBillingAddress(), SI_DEFAULT.getShippingAddress()));
    }
    @Test
    void lombokNullEmailThrows() {
        assertThrows(NullPointerException.class, () ->
                new ShippingInfo(SI_DEFAULT.getEmail(), null, SI_DEFAULT.getPhoneNumber(), SI_DEFAULT.getBillingAddress(), SI_DEFAULT.getShippingAddress()));
    }
    @Test
    void lombokNullPhoneNumberThrows() {
        assertThrows(NullPointerException.class, () ->
                new ShippingInfo(SI_DEFAULT.getEmail(), SI_DEFAULT.getEmail(), null, SI_DEFAULT.getBillingAddress(), SI_DEFAULT.getShippingAddress()));
    }
    @Test
    void lombokNullBillingAddressThrows() {
        assertThrows(NullPointerException.class, () ->
                new ShippingInfo(SI_DEFAULT.getEmail(), SI_DEFAULT.getEmail(), SI_DEFAULT.getPhoneNumber(), null, SI_DEFAULT.getShippingAddress()));
    }
    @Test
    void lombokNullShippingAddressThrows() {
        assertThrows(NullPointerException.class, () ->
                new ShippingInfo(SI_DEFAULT.getEmail(), SI_DEFAULT.getEmail(), SI_DEFAULT.getPhoneNumber(), SI_DEFAULT.getBillingAddress(), null));
    }

}