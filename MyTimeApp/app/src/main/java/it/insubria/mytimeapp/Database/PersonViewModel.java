package it.insubria.mytimeapp.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

/*
    This class is used to keep our data separate from our application’s UI, it is designed to persist across configuration changes
 */

public class PersonViewModel extends AndroidViewModel {

    private ArrayList<Person> mAllPeople;

    DB Person = new DB(getApplication());

     public PersonViewModel(Application application) {
        super(application);
        mAllPeople = Person.getAllPeople();
    }

    public ArrayList<Person> getAllPeople() {
        mAllPeople = Person.getAllPeople();
        return mAllPeople;
    }
}
