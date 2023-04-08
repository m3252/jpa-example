package jpashop.domain;

import javax.persistence.Entity;

@Entity
public class Album extends Item {

    private String etc;
    private String artist;

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
