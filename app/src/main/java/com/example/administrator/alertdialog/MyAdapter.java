package com.example.administrator.alertdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList data;
    private int singleChoiceIndex = 0;
    private int whichOrder = 0;
    private ArrayList<Food> order;


    public MyAdapter(Context context, ArrayList list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.data = list;
        this.order = new ArrayList<Food>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<Food> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Food> order) {
        this.order = order;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHoder viewHoder = new ViewHoder();

        //當convertView為null,
        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.layout_list, parent, false);

            viewHoder.ll = convertView.findViewById(R.id.lvLl);
            viewHoder.img = convertView.findViewById(R.id.imgList);
            viewHoder.tvItem = convertView.findViewById(R.id.tvA);
            viewHoder.tvPrice = convertView.findViewById(R.id.tvB);
            viewHoder.tvFav = convertView.findViewById(R.id.tvFav);
            viewHoder.ll.setClickable(true);

        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }


        final String item = ((Food) getItem(position)).getName();
        String price = "" + ((Food) getItem(position)).getPrice();
        String fav =((Food) getItem(position)).getFav();

        //設定view
        if (viewHoder != null) {
            viewHoder.tvItem.setText(item);
            price = price+"元";
            viewHoder.tvPrice.setText(price);
            viewHoder.tvFav.setText(fav);

            viewHoder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View dialog = mInflater.inflate(R.layout.layout_dialog, parent, false);
                    dialog.findViewById(R.id.imageView);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(item);
                    builder.setPositiveButton("選擇", new DialogInterface.OnClickListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("口味選項");
                            final String[] fav = new String[]{"麻辣", "咖哩", "泰式酸辣", "日式和風", "韓式風味"};
                            builder.setSingleChoiceItems(fav, singleChoiceIndex, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    singleChoiceIndex = which;

                                    Food food = (Food) getItem(position);
                                    food.setFav(fav[which]);

                                    order.add(food);
                                    System.out.println(order);
                                    dialog.dismiss();

                                }
                            });

                            builder.setNegativeButton("取消", null);
                            EditText et = new EditText(mContext);
                            et.setText("備註");
                            builder.setView(et);
                            builder.show();

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.setView(dialog);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.show();
                }
            });

        }

        return convertView;
    }


    class ViewHoder {
        LinearLayout ll;
        ImageView img;
        TextView tvItem;
        TextView tvPrice;
        TextView tvFav;
    }

}
