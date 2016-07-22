package fr.esgi.iam.uefa.model;

import org.parceler.Parcel;

/**
 * Created by MichaelWayne on 21/07/2016.
 */
@Parcel
public class Stadium {

    public String id;
    public String name;
    public String capacity;
    public String city;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}
