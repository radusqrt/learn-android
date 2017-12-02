package com.example.radus.filterlistviewtutorial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.radus.filterlistviewtutorial.R;
import com.example.radus.filterlistviewtutorial.WorldPopulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by radus on 12/1/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<WorldPopulation> worldPopulationList = null;
    private ArrayList<WorldPopulation> arrayList;

    public ListViewAdapter(Context context, List<WorldPopulation> worldPopulationList) {
        mContext = context;
        this.worldPopulationList = worldPopulationList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(worldPopulationList);
    }

    public class ViewHolder {
        TextView rank, country, population;
    }

    @Override
    public int getCount() {
        return worldPopulationList.size();
    }

    @Override
    public WorldPopulation getItem(int i) {
        return worldPopulationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);

            holder.rank = (TextView) view.findViewById(R.id.rank);
            holder.country = (TextView) view.findViewById(R.id.country);
            holder.population = (TextView) view.findViewById(R.id.population);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.rank.setText(worldPopulationList.get(i).getRank());
        holder.country.setText(worldPopulationList.get(i).getCountry());
        holder.population.setText(worldPopulationList.get(i).getPopulation());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, SingleItemView.class);
//                intent.putExtra("rank", (worldPopulationList.get(i).getRank()));
//                intent.putExtra("country", (worldPopulationList.get(i).getCountry()));
//                intent.putExtra("population", (worldPopulationList.get(i).getPopulation()));
//                mContext.startActivity(intent);
            }
        });

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldPopulationList.clear();
        if (charText.length() == 0) {
            worldPopulationList.addAll(arrayList);
        } else {
            for (WorldPopulation wp : arrayList) {
                if (wp.getCountry().toLowerCase(Locale.getDefault()).contains(charText)) {
                    worldPopulationList.add(wp);
                }
            }
        }

        notifyDataSetChanged();
    }
}
