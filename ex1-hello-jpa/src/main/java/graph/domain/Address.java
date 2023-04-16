package graph.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street1, address.street1) && Objects.equals(street2, address.street2) && Objects.equals(city, address.city) && Objects.equals(zipcode, address.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street1, street2, city, zipcode);
    }
}
