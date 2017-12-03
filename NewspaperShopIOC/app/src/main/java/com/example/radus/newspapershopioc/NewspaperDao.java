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
public interface NewspaperDao {
    @Query("SELECT * FROM newspaper")
    List<NewspaperEntry> getAll();

    @Query("SELECT * FROM newspaper ORDER BY sold DESC")
    List<NewspaperEntry> getAllBySoldNumber();

    @Query("SELECT * FROM newspaper WHERE name LIKE :name")
    NewspaperEntry findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewspaperEntry... newspaperEntries);

    @Delete
    void delete(NewspaperEntry newspaperEntry);

    @Query("DELETE FROM newspaper")
    void deleteAll();
}
