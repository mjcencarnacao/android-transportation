package com.mjceo.transportes.geolocation;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.auth.User;

import java.util.Date;

public class UserLocation {

    private GeoPoint geo_Point;
    private @ServerTimestamp Date timestamp;


    private User user;

    public UserLocation(GeoPoint geo_Point, Date timestamp, User user) {
        this.geo_Point = geo_Point;
        this.timestamp = timestamp;
        this.user = user;
    }

    public UserLocation() {}

    public GeoPoint getGeo_Point() {
        return geo_Point;
    }

    public void setGeo_Point(GeoPoint geo_Point) {
        this.geo_Point = geo_Point;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
