package it.insubria.mytimeapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDAO {

    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person ORDER BY time_from ASC")
    LiveData<List<Person>> getTimingOrder();

    /*@Query("SELECT * FROM person WHERE pid IN (:peopleIds)")
    List<Person> loadAllByIds(int[] peopleIds);
     */

    @Query("SELECT * FROM person WHERE name LIKE :name LIMIT 1 ")
    Person findByName(String name);

    @Query("DELETE FROM person")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Person person);

    @Delete
    void delete(Person person);
}
