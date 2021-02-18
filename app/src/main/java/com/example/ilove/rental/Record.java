package com.example.ilove.rental;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable {
    String houseNumber;
    String month ;
    String roomNumber ;
    String year ;
    String amountPaid;
    String id;
    String dateRecorded;
    String dateEdited = "Not yet edited";

    String collector;
    String methodOfPayment;
    String notes;



    public Record(){

    }

    public Record(String id,String houseNumber, String month, String roomNumber, String year, String amountPaid,String collector, String methodOfPayment, String notes, String dateRecorded) {
        this.houseNumber = houseNumber;
        this.month = month;
        this.roomNumber = roomNumber;
        this.year = year;
        this.amountPaid = amountPaid;
        this.id = id;
        this.collector = collector;
        this.methodOfPayment = methodOfPayment;
        this.notes = notes;
        this.dateRecorded = dateRecorded;
    }
    public Record(String id,String houseNumber, String month, String roomNumber, String year, String amountPaid,String collector, String methodOfPayment, String notes, String dateRecorded, String dateEdited) {
        this.houseNumber = houseNumber;
        this.month = month;
        this.roomNumber = roomNumber;
        this.year = year;
        this.amountPaid = amountPaid;
        this.id = id;
        this.collector = collector;
        this.methodOfPayment = methodOfPayment;
        this.notes = notes;
        this.dateRecorded = dateRecorded;
        this.dateEdited = dateEdited;
    }

    protected Record(Parcel in) {
        houseNumber = in.readString();
        month = in.readString();
        roomNumber = in.readString();
        year = in.readString();
        amountPaid = in.readString();
        id = in.readString();
        collector = in.readString();
        methodOfPayment = in.readString();
        notes = in.readString();
        dateRecorded = in.readString();
        dateEdited = in.readString();
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public String getDateEdited() {
        return dateEdited;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getHouseNumber(){
        return houseNumber;
    }
    public String getMonth(){
        return month;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
    public String getYear(){
        return year;
    }
    public String getAmountPaid(){
        return amountPaid;
    }
    public String getId() {
        return id;
    }
    public String getDateRecorded() {return dateRecorded;}

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setDateEdited(String dateEdited) {this.dateEdited = dateEdited;}

    public String generatePath (){//generates a precise path to store the records
        return year+"/"+month+"/"+houseNumber;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        dest.writeString(houseNumber);
        dest.writeString(month);
        dest.writeString(roomNumber);
        dest.writeString(year);
        dest.writeString(amountPaid);
        dest.writeString(id);
        dest.writeString(collector);
        dest.writeString(methodOfPayment);
        dest.writeString(notes);
        dest.writeString(dateRecorded);
        dest.writeString(dateEdited);

    }
}
