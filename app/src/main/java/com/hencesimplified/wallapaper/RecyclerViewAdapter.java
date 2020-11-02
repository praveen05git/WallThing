package com.hencesimplified.wallapaper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewFolder> {
    private Context mcontext;
    private List<SamplePhotos> mdata;

    public RecyclerViewAdapter(Context context, List<SamplePhotos> listBook) {
        mcontext = context;
        mdata = listBook;
    }


    @NonNull
    @Override
    public MyViewFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater minflater = LayoutInflater.from(mcontext);
        view = minflater.inflate(R.layout.cardview_layout_photos, parent, false);
        return new MyViewFolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewFolder holder, final int position) {

        holder.tv_book_title.setText(mdata.get(position).getName());

        Picasso.get()
                .load(mdata.get(position).getUrl())
                .fit()
                .centerCrop()
                .into(holder.book_thumbnil);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog alert_dia1 = new androidx.appcompat.app.AlertDialog.Builder(mcontext).create();
                alert_dia1.setTitle("Watch Ad!");
                alert_dia1.setMessage("Do you want to watch an Ad to download or set the wallpaper?");

                alert_dia1.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String img = mdata.get(position).getUrl();
                        Intent PhotoIntent = new Intent(mcontext, AdsActivity.class);
                        PhotoIntent.putExtra("img_url", img);
                        mcontext.startActivity(PhotoIntent);
                    }
                });
                alert_dia1.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert_dia1.setButton(AlertDialog.BUTTON_NEUTRAL, "Remove Ads", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog alert_dialog1 = new AlertDialog.Builder(mcontext).create();
                        alert_dialog1.setTitle("Remove Ads!");
                        alert_dialog1.setMessage("Download the Wall Thing Pro to enjoy Ad-free service!! Download Now?");

                        alert_dialog1.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mcontext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.hencesimplified.wallpaperpro")));
                            }
                        });
                        alert_dialog1.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert_dialog1.show();

                    }
                });
                alert_dia1.setCanceledOnTouchOutside(false);
                alert_dia1.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class MyViewFolder extends RecyclerView.ViewHolder {
        TextView tv_book_title;
        ImageView book_thumbnil;
        CardView cardView;

        public MyViewFolder(@NonNull View itemView) {
            super(itemView);
            tv_book_title = itemView.findViewById(R.id.photo_title_id);
            book_thumbnil = itemView.findViewById(R.id.photo_image_id);
            cardView = itemView.findViewById(R.id.cardview_id);

        }
    }

}
