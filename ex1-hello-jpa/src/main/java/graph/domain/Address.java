package graph.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String street1;
    private String street2;
    private String city;
    private String zipcode;

    public Address() {
    }

    public Address(String street1, String street2, String city, String zipcode) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.zipcode = zipcode;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }
}
