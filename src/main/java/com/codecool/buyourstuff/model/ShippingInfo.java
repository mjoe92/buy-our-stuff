package com.codecool.buyourstuff.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ShippingInfo {

    private static final int NAME_MINLENGTH = 5;    //user
    private static final int NAME_MAXLENGTH = 30;
    private static final int PHONE_MINLENGTH = 7;
    private static final int PHONE_MAXLENGTH = 18;

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String billingAddress;
    @NonNull
    private String shippingAddress;

    public ShippingInfo(@NonNull String name,
                        @NonNull String email,
                        @NonNull String phoneNumber,
                        @NonNull String billingAddress,
                        @NonNull String shippingAddress) {

        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!isAddressValid(billingAddress) || !isAddressValid(shippingAddress)) {
            throw new IllegalArgumentException("Invalid address");
        }

        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return String.format("%1$s={" +
                        "name: %2$s, " +
                        "email: %3$s, " +
                        "phoneNumber: %4$s, " +
                        "billingAddress: %5$s, " +
                        "shippingAddress: %6$s}",
                getClass().getSimpleName(),
                name,
                email,
                phoneNumber,
                billingAddress,
                shippingAddress
        );
    }

    private boolean isEmailValid(String email) {
        if (email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return true;
        }
        return false;
    }
    private boolean isPhoneNumberValid (String phoneNumber) {
        if (phoneNumber.matches("[0-9()-]+")) {
            return true;
        }
        return false;
    }
    private boolean isAddressValid (String addrezz) {
        //format example: 84 Westbourne Rd, Peverell, Plymouth PL3 4JY, United Kingdom

        @Getter
        @Setter
        class Address {
            private String street;
            private String settlement;
            private String postalCode;
            private String country;

            private boolean isValid() {
                if (!street.matches("((\\d){0,4}(\\.)?(/)?((\\w){0,2})? ((\\w)+(\\.)?| )*)")) {
                    return false;
                }
                if (!settlement.matches("((\\w| )+,{1} )+(\\w)*")) {
                    return false;
                }
                if (!postalCode.matches("([\\w|\\d| ]{3,12})")) {
                    return false;
                }
                if (!country.matches("((\\w| ){4,56})") &&
                        !country.matches(ISO3166_ALPHA2) &&
                        !country.matches(ISO3166_ALPHA3) &&
                        !country.matches(ISO3166_NUM)) {
                    return false;
                }

                return true;
            }
        }

        String[] addressArray = addrezz.split(",");
        Address address = new Address();

        String settlementStr = "";
        for (int i = 1; i < addressArray.length - 2; i++) {
            settlementStr = settlementStr + addressArray[i] + ",";
        }

        address.setStreet(addressArray[0].trim());
        address.setSettlement(settlementStr.trim().replaceAll(",$", ""));
        address.setPostalCode(addressArray[addressArray.length - 2].trim());
        address.setCountry(addressArray[addressArray.length - 1].trim());

        return address.isValid();
    }



    private String ISO3166_ALPHA2 = "A[^ABCHJKNPVY]|" + "B[^CKPUX]|" + "C[^BEJPQST]|" + "D[EJKMOZ]|" + "E[CEGHRST]|" +
            "F[IJKMOR]|" + "G[^CJKOVXZ]|" + "H[KMNRTU]|" + "I[DEL-OQ-T]|" + "J[EMOP]|" + "K[EGHIMNPRWYZ]|" + "L[ABCIKR-VY]|" +
            "M[^BIJ]|" + "N[ACEFGILOPRUZ]|" + "OM|" + "P[AE-HK-NRSTWY]|" + "QA|" + "R[EOSUW]|" + "S[^FPQUW]|" + "T[^ABEIPQSUXY]|"
            + "U[AGMSYZ]|" + "V[ACEGINU]|" + "WF|" + "WS|" + "YE|" + "YT|" + "Z[AMW" + "]$/ix";

    private String ISO3166_ALPHA3 = "A(BW|FG|GO|IA|L[AB]|ND|R[EGM]|SM|T[AFG]|U[ST]|ZE)|" +
            "B(DI|E[LNS]|FA|G[DR]|H[RS]|IH|L[MRZ]|MU|OL|R[ABN]|TN|VT|WA)|" + "C(A[FN]|CK|H[ELN]|IV|MR|O[DGKLM]|PV|RI|U [BW]|XR|Y[MP]|ZE)|" +
            "D(EU|JI|MA|NK|OM|ZA)|" + "E(CU|GY|RI|S[HPT]|TH)|" + "F(IN|JI|LK|R[AO]|SM)|" + "G(AB|BR|EO|GY|HA|I[BN]|LP|MB|N[BQ]|R[CDL]|TM|U[FMY])|" +
            "H(KG|MD|ND|RV|TI|UN)|" + "I(DN|MN|ND|OT|R [LNQ]|S[LR]|TA)|" + "J(AM|EY|OR|PN)|" + "K(AZ|EN|GZ|HM|IR|NA|OR|WT)|" +
            "L(AO|B[NRY]|CA|IE|KA|SO|TU|UX|VA)|" + "M(A[CFR]|CO|D[AGV]|EX|HL|KD|L[IT]|MR|N[EGP]|OZ|RT|SR|TQ|US|WI|Y[ST])|" +
            "N(AM|CL|ER |FK|GA|I[CU]|LD|OR|PL|RU|ZL)|" + "OMN|" + "P(A[KN]|CN|ER|HL|LW|NG|OL|R[IKTY]|SE|YF)|" + "QAT|" + "R(EU|OU|US|WA)|" +
            "S(AU|DN|EN|G[PS]|HN|JM|L[BEV]|MR|OM|PM|RB|SD|TP|UR|V[KN]|W[EZ]|XM|Y[CR])|" + "T(C[AD]|GO|HA|JK|K[LM]|LS|ON|TO|U[NRV]|WN|ZA)|" +
            "U(GA|KR|MI|RY|SA |ZB)|" + "V(AT|CT|EN|GB|IR|NM|UT)|" + "W(LF|SM)|YEM|" + "Z(AF|MB|WE)" + "$/ix";

    private String ISO3166_NUM = "(86[0|2])|" + "((12|48|69)[0|4])|" + "(71[0|6])|" + "(54[0|8])|" + "(59[1|8])|" +
            "((1[1|3|6]|2[2|9]|3[1|5|7]|46|5[1|6]|61|7[5|7])[2|6])|" + "(88[2|7])|" + "(39[2|8])|" + "(08[4|6])|" +
            "((00|55)[4|8])|" + "((0[1|9]|44)[0|2|6])|" + "(62[0|4|6])|" + "(80[0|4|7])|" +
            "((0[2|4|6]|1[0|4|8]|25|3[2|4|6|8]|4[0|3|5|7]|5[0|2|7]|6[0|3|7]|7[4|8]|85)[0|4|8])|" + "(27[0|5|6])|" +
            "((03|19)[1|2|6])|" + "(64[2|3|6])|" + "(33[2|4|6])|" + "(21[2|4|8])|" + "(65[2|4|9])|" +
            "(0(15|24|42|68)[2|6|8])|" + "(20[3|4|8])|" + "(72[4|8|9])|" + "(05[0|1|2|6])|" + "(66[0|2|3|6])|" +
            "(07[0|2|4|6])|" + "(76[0|2|4|8])|" + "(26[0|2|6|8])|" + "(17[0|4|5|8])|" + "(41[0|4|7|8])|" +
            "(83[1|2|3|4])|" + "(53[1|3|4|5])|" + "(79[2|5|6|8])|" + "(49[2|6|8|9])|" + "(70[2|3|4|5|6])|" +
            "(58[0|1|3|4|5|6])|" + "(23[1|2|3|4|8|9])|" + "288|732|818|826|840|876|894";

}
