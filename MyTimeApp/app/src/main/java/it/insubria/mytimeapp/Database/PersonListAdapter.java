package it.insubria.mytimeapp.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import it.insubria.mytimeapp.R;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonViewHolder> {

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private final TextView personItemView;

        public PersonViewHolder(View itemView) {
            super(itemView);
            personItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Person> mPeople;
    private DB db;

    public PersonListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        db = new DB(context);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        if(mPeople != null){
            Person current = mPeople.get(position);
            String txt = "%s : %s  - %s  %s-%s";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            txt = String.format(txt, current.getName(),current.getStuff(),sdf.format(current.getDate()),current.getTimeFrom(),current.getTimeTo());
            holder.personItemView.setText(txt);
        } else{
            holder.personItemView.setText("No person");
        }
    }


    public void setPeople(List<Person> people){
        mPeople = people;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mPeople != null)
            return mPeople.size();
        else return 0;
    }

    public Person getPerson(int i){
        return mPeople.get(i);
    }

    public boolean deletePerson(int pid){
        return db.removePerson(pid);
    }



}
