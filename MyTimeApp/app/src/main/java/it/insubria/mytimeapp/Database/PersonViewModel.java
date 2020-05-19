package it.insubria.mytimeapp.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {

    private PersonRepository personRepository;
    private LiveData<List<Person>> mAllPeople;

     public PersonViewModel(Application application) {
        super(application);
        personRepository = new PersonRepository(application);
        mAllPeople = personRepository.getAllPeople();
    }

    public LiveData<List<Person>> getAllPeople() {
        return mAllPeople;
    }

    public void insert(Person person){
        personRepository.insert(person);
    }
}
