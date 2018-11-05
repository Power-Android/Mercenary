package com.power.mercenary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.power.mercenary.R;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class GridViewAddImgesAdpter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<LocalMedia> list;

    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 9;

    public GridViewAddImgesAdpter(List<LocalMedia> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     * 当到达最大张数时不再显示+号
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        /*int count = datas == null ? 1 : datas.size()+ 1;
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }*/
        if (list.size() < maxImages) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grid, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**代表+号之前的需要正常显示图片**/
        if (list != null && position < list.size()) {
//            final File file = new File(list.get(position).getPath());
            Logger.e(list.get(position).getPath());
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.colorPrimary);
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(list.get(position).getPath())
                    .apply(options)
                    .into(viewHolder.ivimage);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (file.exists()) { //删除图片文件
                        file.delete();
                    }*/
                    if (list.size() > 0){
                        list.remove(position);
                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            /**代表+号的需要+号图片显示图片**/
            Glide.with(context)
                    .load(R.drawable.add_pub_img_iv)
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
        }

        return convertView;

    }

    public class ViewHolder {
        public final ImageView ivimage;
        public final Button btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_image);
            btdel = (Button) root.findViewById(R.id.bt_del);
            this.root = root;
        }
    }
}
