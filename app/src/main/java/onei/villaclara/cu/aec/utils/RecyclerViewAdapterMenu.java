package onei.villaclara.cu.aec.utils;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import onei.villaclara.cu.aec.R;

public class RecyclerViewAdapterMenu extends RecyclerView.Adapter<RecyclerViewAdapterMenu.DataObjectHolder>{

    private ArrayList<ItemMenu> mDataset;
    private Context context;
    private static MyClickListener myClickListener;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
          ImageView imageView;
          TextView textView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.logoImage);
            textView = (TextView) itemView.findViewById(R.id.activity);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerViewAdapterMenu(ArrayList<ItemMenu> myDataset, Context pcontext) {
        mDataset = myDataset;
        context = pcontext;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_menu, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.imageView.setImageResource(mDataset.get(position).getImagen());
        holder.textView.setText(mDataset.get(position).getActivity());
    }

    public void addItem(ItemMenu dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
