package com.example.radus.newspapershopioc;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by radus on 12/3/2017.
 */

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM history")
    List<TransactionEntry> getAll();

    @Insert
    void insert(TransactionEntry transactionEntry);

    @Insert
    void insertAll(TransactionEntry... transactionEntries);

    @Delete
    void delete(TransactionEntry transactionEntry);

    @Query("DELETE FROM history")
    void deleteAll();
}
