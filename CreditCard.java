package consolapp;

public class CreditCard {
    private int CREDIT_ID;
    private int CUSTOMER_ID;
    private int CARD_NUMBER;
    private String BRAND;
    private String SECURITY_NUMBER;
    private String EXPERATION_DATE;
    private String NAME_ON_CARD;
    private String STREET;
    private String CITY;
    private int AREA_CODE;

    @Override
    public String toString() {
        return "CreditCard{" + "CREDIT_ID= " + CREDIT_ID + ", CUSTOMER_ID= " + CUSTOMER_ID + ", CARD_NUMBER= " + CARD_NUMBER + ", BRAND= " + BRAND + ", SECURITY_NUMBER= " + SECURITY_NUMBER + ", EXPERATION_DATE= " + EXPERATION_DATE + ", NAME_ON_CARD= " + NAME_ON_CARD + ", STREET= " + STREET + ", CITY= " + CITY + ", AREA_CODE= " + AREA_CODE + '}';
    }

    public int getCREDIT_ID() {
        return CREDIT_ID;
    }

    public void setCREDIT_ID(int CREDIT_ID) {
        this.CREDIT_ID = CREDIT_ID;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public int getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    public void setCARD_NUMBER(int CARD_NUMBER) {
        this.CARD_NUMBER = CARD_NUMBER;
    }

    public String getBRAND() {
        return BRAND;
    }

    public void setBRAND(String BRAND) {
        this.BRAND = BRAND;
    }

    public String getSECURITY_NUMBER() {
        return SECURITY_NUMBER;
    }

    public void setSECURITY_NUMBER(String SECURITY_NUMBER) {
        this.SECURITY_NUMBER = SECURITY_NUMBER;
    }

    public String getEXPERATION_DATE() {
        return EXPERATION_DATE;
    }

    public void setEXPERATION_DATE(String EXPERATION_DATE) {
        this.EXPERATION_DATE = EXPERATION_DATE;
    }

    public String getNAME_ON_CARD() {
        return NAME_ON_CARD;
    }

    public void setNAME_ON_CARD(String NAME_ON_CARD) {
        this.NAME_ON_CARD = NAME_ON_CARD;
    }

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public int getAREA_CODE() {
        return AREA_CODE;
    }

    public void setAREA_CODE(int AREA_CODE) {
        this.AREA_CODE = AREA_CODE;
    }
    
    
    
}

                      