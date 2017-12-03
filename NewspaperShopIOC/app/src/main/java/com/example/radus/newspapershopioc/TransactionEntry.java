package com.example.radus.newspapershopioc;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * Created by radus on 12/3/2017.
 */

@Entity(tableName = "history")
public class TransactionEntry {

    TransactionEntry(ArrayList<String> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<String> transactions) {
        this.transactions.clear();;
        this.transactions.addAll(transactions);
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "transactions")
    private ArrayList<String> transactions = new ArrayList<>();
}
