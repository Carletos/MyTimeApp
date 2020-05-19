package it.insubria.mytimeapp.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonRepository {

    private PersonDAO mPersonDao;
    private LiveData<List<Person>> mAllPeople;

    public PersonRepository(Application application) {
        PersonRoomDatabase db = PersonRoomDatabase.getDatabase(application);
        mPersonDao = db.personDAO();
        mAllPeople = mPersonDao.getTimingOrder();
    }

    LiveData<List<Person>> getAllPeople(){
        return mAllPeople;
    }

    void insert(final Person person){
        PersonRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.insert(person);
        });
    }
}
