package it.insubria.mytimeapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
    This is the fundamental class for the creation of SQLiteDatabase
 */

public class DB extends SQLiteOpenHelper {
    private SQLiteDatabase db = this.getWritableDatabase();
    private final String create = "CREATE TABLE Person (nome VARCHAR (30) NOT NULL, stuff VARCHAR(40) NOT NULL, data text NOT NULL, " +
                                                       "ora_inizio text NOT NULL, ora_fine text NOT NULL, i INTEGER PRIMARY KEY AUTOINCREMENT); ";

    public DB(Context context) {
        super(context,"PersonData.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Person");
        onCreate(db);
    }

    public Boolean executeQuery(String query) {
        try {
            this.db.execSQL(query);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    // this method is used to get from db all the person data in descending order
    public ArrayList<Person> getAllPeople() {
        ArrayList<Person> persons = new ArrayList<Person>();
        String[][] matrix = executeQueryRead("Select * From Person Order by data DESC, ora_inizio DESC", "Person");
        if (matrix == null || matrix[0][0] == null){
            return persons;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        for(int i = 0; i < matrix.length; i++) {
            try {
                Person person = new Person(Integer.parseInt(matrix[i][5]), matrix[i][0], matrix[i][1], sdf.parse(matrix[i][2]), matrix[i][3], matrix[i][4]);
                persons.add(person);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return persons;
    }

    // this method is used to execute queries
    public String[][] executeQueryRead(String query, String tableName) {
        String queryNumRows = "SELECT COUNT(*) AS rowcount FROM " + tableName;
        String[][] Valori;
        try {
            Cursor rs = db.rawQuery(queryNumRows,null);
            Cursor rs2 = null;
            int numRows, numCols;
            rs.moveToNext();
            numRows = rs.getInt(0);
            if(numRows == 0) {
                return null;
            }
            rs2 = db.rawQuery(query,null);
            numCols = rs2.getColumnCount();
            Valori = new String[numRows][numCols];
            int i = 0;
            for (rs2.moveToFirst(); !rs2.isAfterLast(); rs2.moveToNext()) {
                for (int j=0; j < numCols; j++) {
                    Valori[i][j] = String.valueOf(rs2.getString(j));
                }
                i++;
            }
        }
        catch(Exception sql_ex) {
            sql_ex.printStackTrace();
            return null;
        }
        finally {
        }
        return Valori;
    }

    // this method deletes a person from the database with the correct query "DELETE FROM"
    public boolean removePerson(int id){
        String query = "DELETE FROM Person WHERE i = '%s'";
        query = String.format(query, id);
        try {
            db.execSQL(query);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // this method is used to add a person in the database with the correct query "INSERT INTO"
    public boolean addPerson(Person p) {
        String query = "INSERT INTO Person(nome, stuff, data, ora_inizio, ora_fine) VALUES ('%s', '%s', '%s', '%s', '%s') ";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        query = String.format(query, p.getName(), p.getStuff(), sdf.format(p.getDate()), p.getTimeFrom(), p.getTimeTo());
        try {
            db.execSQL(query);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // this method is used for FilterStatistics class to display the statistics using the filters
    public ArrayList<Person> getAllPeopleFilter(String data_inizio, String data_fine, String ora_inizio, String ora_fine) {
        ArrayList<Person> persons = new ArrayList<Person>();
        String query = "";
        query = "SELECT * FROM Person WHERE data >= '%s'  AND data <= '%s' AND ora_inizio >= '%s' AND ora_fine <= '%s'";
        query = String.format(query, data_inizio, data_fine,ora_inizio,ora_fine);

        String[][] matrix = executeQueryRead(query, "Person");
        if (matrix == null || matrix[0][0] == null){
            return persons;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

        for(int i = 0; i < matrix.length; i++) {
            try {
                if(matrix[i][5] != null) {
                    Person person = new Person(Integer.parseInt(matrix[i][5]), matrix[i][0], matrix[i][1], sdf.parse(matrix[i][2]), matrix[i][3], matrix[i][4]);
                    persons.add(person);
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return persons;
    }
}



