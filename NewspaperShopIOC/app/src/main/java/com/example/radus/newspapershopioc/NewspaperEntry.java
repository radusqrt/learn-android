package com.example.radus.newspapershopioc;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by radus on 12/3/2017.
 */

@Entity(primaryKeys = {"name"}, tableName = "newspaper")
public class NewspaperEntry {

    public NewspaperEntry(
            String name, String category, String periodicity,
            String type, String area, int circulation,
            int sold, int imageSrc, double price) {
        this.name = name;
        this.category = category;
        this.periodicity = periodicity;
        this.type = type;
        this.area = area;
        this.circulation = circulation;
        this.sold = sold;
        this.imageSrc = imageSrc;
        this.price = price;
    }

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "periodicity")
    private String periodicity;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "area")
    private String area;

    @ColumnInfo(name = "circulation")
    private int circulation;

    @ColumnInfo(name = "sold")
    private int sold;

    @ColumnInfo(name = "image_src")
    private int imageSrc;

    @ColumnInfo(name = "price")
    private double price;

    @Ignore
    private boolean bookChecked;

    @Ignore
    private boolean cdChecked;

    @Ignore
    private int numberOfCopies;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public boolean getBookChecked() {
        return bookChecked;
    }

    public void setBookChecked(boolean bookChecked) {
        this.bookChecked = bookChecked;
    }

    public boolean getCdChecked() {
        return cdChecked;
    }

    public void setCdChecked(boolean cdChecked) {
        this.cdChecked = cdChecked;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public void printDescription() {
        System.out.println("[name: " + name + ", category: " + category +
                ", periodicity: " + periodicity + ", type: " + type +
                ", area: " + area + ", circulation: " + circulation +
                ", sold: " + sold + ", imageSrc: " + imageSrc + "]");
    }
}
