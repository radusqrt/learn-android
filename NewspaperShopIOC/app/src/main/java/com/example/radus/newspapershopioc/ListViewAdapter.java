package com.example.radus.newspapershopioc;

import android.arch.persistence.room.Index;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by radus on 12/3/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<NewspaperEntry> newspaperList = null;
    private ArrayList<NewspaperEntry> arrayList;

    public ListViewAdapter(Context context, List<NewspaperEntry> newspaperList) {
        mContext = context;
        this.newspaperList = newspaperList;
        inflater = LayoutInflater.from(mContext);
        arrayList = new ArrayList<>();
        arrayList.addAll(newspaperList);
    }

    public class ViewHolder {
        TextView name, category, periodicity, area, numberOfCopies;
        ImageView imageView, removeView, addView;
        CheckBox bookCheck, cdCheck;
    }

    @Override
    public int getCount() {
        return newspaperList.size();
    }

    @Override
    public NewspaperEntry getItem(int i) {
        return newspaperList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);

            holder.name = view.findViewById(R.id.name_label);
            holder.category = view.findViewById(R.id.category_label);
            holder.periodicity = view.findViewById(R.id.periodicity_label);
            holder.area = view.findViewById(R.id.area_label);
            holder.imageView = view.findViewById(R.id.image_label);
            holder.removeView = view.findViewById(R.id.remove_button);
            holder.addView = view.findViewById(R.id.add_button);
            holder.numberOfCopies = view.findViewById(R.id.number_of_copies);
            holder.bookCheck = view.findViewById(R.id.book_checkbox);
            holder.cdCheck = view.findViewById(R.id.cd_checkbox);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    newspaperList.get(i).setNumberOfCopies(
                            newspaperList.get(i).getNumberOfCopies() + 1);
                    holder.numberOfCopies.setText(String.valueOf(
                            newspaperList.get(i).getNumberOfCopies()));
                } catch (IndexOutOfBoundsException e) {
                    Log.d("ADD", e.getMessage());
                }
            }
        });

        holder.removeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    newspaperList.get(i).setNumberOfCopies(Math.max(0,
                            newspaperList.get(i).getNumberOfCopies() - 1));
                    holder.numberOfCopies.setText(String.valueOf(
                            newspaperList.get(i).getNumberOfCopies()));
                } catch (IndexOutOfBoundsException e) {
                    Log.d("REMOVE", e.getMessage());
                }
            }
        });

        holder.bookCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    newspaperList.get(i).setBookChecked(b);
                } catch (IndexOutOfBoundsException e) {
                    Log.d("CHECK", e.getMessage());
                }
            }
        });

        holder.cdCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    newspaperList.get(i).setCdChecked(b);
                } catch (IndexOutOfBoundsException e) {
                    Log.d("CHECK", e.getMessage());
                }
            }
        });

        holder.name.setText(newspaperList.get(i).getName());
        holder.category.setText(newspaperList.get(i).getCategory());
        holder.periodicity.setText(newspaperList.get(i).getPeriodicity());
        holder.area.setText(newspaperList.get(i).getArea());
        holder.imageView.setImageResource(newspaperList.get(i).getImageSrc());
        holder.bookCheck.setChecked(newspaperList.get(i).getBookChecked());
        holder.cdCheck.setChecked(newspaperList.get(i).getCdChecked());
        holder.numberOfCopies.setText(String.valueOf(newspaperList.get(i).getNumberOfCopies()));

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        newspaperList.clear();
        if (charText.length() == 0) {
            newspaperList.addAll(arrayList);
        }
        else
        {
            for (NewspaperEntry ne : arrayList)
            {
                if (ne.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    newspaperList.add(ne);
                }
            }
        }

        notifyDataSetChanged();
    }

    List<NewspaperEntry> getList() {
        return newspaperList;
    }
}
