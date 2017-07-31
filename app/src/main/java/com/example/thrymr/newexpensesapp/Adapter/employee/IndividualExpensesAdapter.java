package com.example.thrymr.newexpensesapp.Adapter.employee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.thrymr.newexpensesapp.Activity.employee.AddIndividualExpensesActivity;
import com.example.thrymr.newexpensesapp.R;
import com.example.thrymr.newexpensesapp.Views.CustomFontTextView;
import com.example.thrymr.newexpensesapp.Views.CustomImageView;
import com.example.thrymr.newexpensesapp.listners.admin.IndividualExpensesItemClickListner;
import com.example.thrymr.newexpensesapp.models.IndividualExpenses;

import java.util.List;

/**
 * Created by thrymr on 28/7/17.
 */

public class IndividualExpensesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IndividualExpenses> individualExpensesList;
    private IndividualExpensesItemClickListner individualExpensesItemClickListner;
    private Context context;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();


    public IndividualExpensesAdapter(Context context, List<IndividualExpenses> individualExpensesList, IndividualExpensesItemClickListner individualExpensesItemClickListner) {
        this.context = context;
        this.individualExpensesList = individualExpensesList;
        this.individualExpensesItemClickListner = individualExpensesItemClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_individual_expenses, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailsViewHolder detailsViewHolder = (DetailsViewHolder) holder;
        final IndividualExpenses individualExpenses = individualExpensesList.get(position);
        if (individualExpensesList != null && 0 <= position && position < individualExpensesList.size()) {
            binderHelper.bind(detailsViewHolder.swipeLayout, String.valueOf(individualExpenses));
            detailsViewHolder.deleteItem(individualExpenses);
            detailsViewHolder.editItem(individualExpenses);
        }
        if (individualExpenses != null) {
            if (individualExpenses.getExpensesName() != null) {
                detailsViewHolder.tripAreaTv.setText(individualExpenses.getExpensesName());
            } else {
                detailsViewHolder.tripAreaTv.setText("Cab");
            }
            if (individualExpenses.getExpensesDate() != null) {
                detailsViewHolder.tripDateTv.setText(individualExpenses.getExpensesDate());
            } else {
                detailsViewHolder.tripDateTv.setText("12-07-2017");
            }
            if (individualExpenses.getExpensesStatus() != null) {
                detailsViewHolder.tripStatusTv.setText(individualExpenses.getExpensesStatus());
            } else {
                detailsViewHolder.tripStatusTv.setText("Accept");
            }
            if (individualExpenses.getTotalAmount() != null) {
                detailsViewHolder.tripCostTv.setText(individualExpenses.getTotalAmount());
            } else {
                detailsViewHolder.tripCostTv.setText("$30");
            }
        }
        detailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                individualExpensesItemClickListner.itemOnClickListner(individualExpenses);
            }
        });


    }

    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    @Override
    public int getItemCount() {
        if (individualExpensesList == null)
            return 0;
        return individualExpensesList.size();
    }


    private class DetailsViewHolder extends RecyclerView.ViewHolder {


        private final SwipeRevealLayout swipeLayout;
        private final CustomFontTextView deleteExpenses;
        private final CustomFontTextView editExpenses;
        private CustomImageView nextScreenTv;
        private CustomFontTextView tripCostTv;
        private CustomFontTextView tripStatusTv;
        private CustomFontTextView tripDateTv;
        private CustomFontTextView tripAreaTv;

        public DetailsViewHolder(View view) {
            super(view);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            tripAreaTv = (CustomFontTextView) view.findViewById(R.id.trip_area_tv);
            tripDateTv = (CustomFontTextView) view.findViewById(R.id.trip_date_tv);
            tripStatusTv = (CustomFontTextView) view.findViewById(R.id.trip_status_tv);
            tripCostTv = (CustomFontTextView) view.findViewById(R.id.trip_cost_tv);
            deleteExpenses = (CustomFontTextView) view.findViewById(R.id.delete_item);
            editExpenses = (CustomFontTextView) view.findViewById(R.id.edit_item);
        }

        public void deleteItem(IndividualExpenses individualExpenses) {
            deleteExpenses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    individualExpensesList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void editItem(final IndividualExpenses individualExpenses) {
            editExpenses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editExpeneseIntent = new Intent(context, AddIndividualExpensesActivity.class);
                    editExpeneseIntent.putExtra("expensesName", "Cab");
                    editExpeneseIntent.putExtra("dateOfBill", "12-12-2017");
                    editExpeneseIntent.putExtra("billAmount","$200");
                    editExpeneseIntent.putExtra("details","I went office to ameerpet");
                    context.startActivity(editExpeneseIntent);
                }
            });
        }
    }
}