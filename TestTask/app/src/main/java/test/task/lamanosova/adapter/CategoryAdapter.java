package test.task.lamanosova.adapter;



import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import test.task.lamanosova.MainActivity;
import test.task.lamanosova.R;
import test.task.lamanosova.model.CategoryModel;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryModel> categoryModeList;

    public CategoryAdapter(List<CategoryModel> categoryModeList, MainActivity mainActivity) {
        this.categoryModeList = categoryModeList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String name =categoryModeList.get(position).getName();
        String  dir =categoryModeList.get(position).getDir();
        holder.setCategory(name,dir,position);

    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryModeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private TextView categoryDir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.textView1);
            categoryDir = itemView.findViewById(R.id.textView2);
        }

        private void setCategoryIcon() {

        }

        private void setCategory(final String name,final String dir, final int position) {
            categoryName.setText(name);
            categoryDir.setText(dir);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position!=0) {
                        Intent categoryIntent = new Intent(itemView.getContext(), MainActivity.class);
                        categoryIntent.putExtra("CategoryName", name);
                        itemView.getContext().startActivity(categoryIntent);
                    }
                }
            });
        }

    }
}

