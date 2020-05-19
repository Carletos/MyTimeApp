package it.insubria.mytimeapp.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PersonRoomDatabase extends RoomDatabase {

    public abstract PersonDAO personDAO();

    private static volatile PersonRoomDatabase INSTANCE;
    private static final String DB_NAME = "person_database";
    private static final int NUMBER_OF_THREADS = 1;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PersonRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (PersonRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PersonRoomDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
