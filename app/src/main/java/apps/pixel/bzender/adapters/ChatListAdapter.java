package apps.pixel.bzender.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.List;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;
import apps.pixel.bzender.activities.chatRoom.ChatRoom;
import de.hdodenhof.circleimageview.CircleImageView;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final List<ChatModel> itemList;
    private SharedPreferences sharedPreferences;
    private Context context;
    private String catId;


    public ChatListAdapter(Context context, List<ChatModel> itemList) {
        this.context = context;
        this.itemList = itemList;
        sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    private void removeAt(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        try {

            switch (itemList.get(listPosition).getCatId()) {
                case "10021":
                    holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.cars));
                    catId = "10021";
                    break;
                case "10022":
                    holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.electrical));
                    catId = "10022";
                    break;
                case "10023":
                    holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_real_estate));
                    catId = "10023";
                    break;

                default:
                    holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.bzender));

                    break;
            }

        } catch (NullPointerException ignored) {
            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.bzender));
        }

        Log.d("CAT_ID_", "onBindViewHolder: " + catId);


        holder.name.setText(itemList.get(listPosition).getName());
        holder.msg.setText(itemList.get(listPosition).getMsg());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatRoom.class);
            intent.putExtra(Constant.SENDER_ID, itemList.get(listPosition).getSenderId());
            intent.putExtra(Constant.ROOM_ID, itemList.get(listPosition).getRoomId());
            intent.putExtra(Constant.STATUS_ID_CHAT, itemList.get(listPosition).getStatusID());
            intent.putExtra(Constant.CAT_ID, itemList.get(listPosition).getCatId());
            intent.putExtra(Constant.USER_CHAT_STATUS, itemList.get(listPosition).getUserChatStatus());
            intent.putExtra(Constant.PAGES_COUNT, itemList.get(listPosition).getPagesCount());
            intent.putExtra(Constant.TENDER_NAME, itemList.get(listPosition).getName());
//            intent.putExtra(Constant.CAT_ID, catId);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constant.PAGES_COUNT, itemList.get(listPosition).getPagesCount());
            editor.putInt(Constant.ROOM_ID, itemList.get(listPosition).getRoomId());
            editor.putString(Constant.CAT_ID, itemList.get(listPosition).getCatId());
            editor.apply();

            int roomId = sharedPreferences.getInt(Constant.ROOM_ID, 1);
            int pages_size = sharedPreferences.getInt(Constant.PAGES_COUNT, 0);

            Log.d("DATA_SIZE_PAGE", "ItemDataSource: " + pages_size);
            Log.d("DATA_ROOM_ID", "ItemDataSource: " + roomId);

            context.startActivity(intent);
            Animatoo.animateSlideRight(context);
            ((Activity) context).finish();
            ((Activity) context).overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_right);
        });

//
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreference", Context.MODE_PRIVATE);
//        String USER_ID = sharedPreferences.getString(Constant.USER_ID_CHAT, "");
//        if (itemList.get(listPosition).getSenderId().equals(USER_ID)) {
//            holder.itemView.setVisibility(View.GONE);
//            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//        }
//
//        if (itemList.get(listPosition).getReceiverId().equals(USER_ID)) {
//            holder.itemView.setVisibility(View.VISIBLE);
//            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        }


    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CircleImageView img;
        final TextView name;
        final TextView msg;


        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.desc);

        }

        @Override
        public void onClick(View view) {
        }
    }
}
