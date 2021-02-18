package com.example.ilove.rental;

import android.os.Parcel;
import android.os.Parcelable;

public class Tenant implements Parcelable {
    String name, phone, email, roomNumber, house, startDate, endDate, notes;

    public Tenant(){}

    public Tenant(String name, String phone,String email, String roomNumber, String house, String startDate, String endDate, String notes) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.roomNumber = roomNumber;
        this.house = house;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    protected Tenant (Parcel in){
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        roomNumber = in.readString();
        house = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        notes = in.readString();
    }


    public static final Creator<Tenant> CREATOR = new Creator<Tenant>() {
        @Override
        public Tenant createFromParcel(Parcel in) {
            return new Tenant(in);
        }

        @Override
        public Tenant[] newArray(int size) {
            return new Tenant[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }





    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getHouse() {
        return house;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(roomNumber);
        dest.writeString(house);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(notes);
    }
}
