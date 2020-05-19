package it.insubria.mytimeapp.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

    public PersonListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
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
            holder.personItemView.setText(current.getName());
            holder.personItemView.setText(current.getStuff());
            holder.personItemView.setText((CharSequence) current.getDate());
            holder.personItemView.setText(current.getTimeFrom());
            holder.personItemView.setText(current.getTimeTo());
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
}
