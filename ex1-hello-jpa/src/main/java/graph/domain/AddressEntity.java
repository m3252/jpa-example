package graph.domain;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Address address;

    public AddressEntity() {

    }

    public AddressEntity(String street1, String street2, String city, String zipcode) {
        this.address = new Address(street1, street2, city, zipcode);
    }

    public AddressEntity(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
