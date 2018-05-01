package example.manoj_pc.federalassignment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import example.manoj_pc.federalassignment.R;
import example.manoj_pc.federalassignment.app.Config;

/**
 * Created by MANOJ-PC on 4/3/2018.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.MyViewHolder> {

    private List<String> msgList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMsg;

        public MyViewHolder(View view) {
            super(view);
            tvMsg = (TextView) view.findViewById(R.id.tvMsg);

        }
    }


    public MsgListAdapter(List<String> msgList) {
        this.msgList = msgList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String msg = msgList.get(position);
        if(msg!=null){
            holder.tvMsg.setText(msg);
        }else {
            holder.tvMsg.setText(Config.EMPTY);
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
