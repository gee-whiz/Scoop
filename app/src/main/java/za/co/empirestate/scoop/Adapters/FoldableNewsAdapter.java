package za.co.empirestate.scoop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.bumptech.glide.Glide;

import java.util.List;

import za.co.empirestate.scoop.JavaClasses.News;
import za.co.empirestate.scoop.R;

/**
 * Created by George_Kapoya on 15/12/01.
 */
public class FoldableNewsAdapter extends ItemsAdapter<News>  implements View.OnClickListener {

    private Context context;
    private Context mContext;
    private List<News> newsList;
    public FoldableNewsAdapter(Context context) {
        super(context);
    }


    @Override
    protected View createView(News item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_foldable_item, parent, false);
        ViewHolder vh = new ViewHolder();



       vh.vNewsTitle  = (TextView)itemView.findViewById(R.id.txtNewsTitle);
        vh.vWriter     =  (TextView)itemView.findViewById(R.id.txtWriter);
        vh.vComment    =  (ImageButton)itemView.findViewById(R.id.btnComments);
        vh.vShare      =   (ImageButton)itemView.findViewById(R.id.btnMore);
        vh.vLike       =   (ImageButton)itemView.findViewById(R.id.btnLike);
        vh.ivLike      =   (ImageView)itemView.findViewById(R.id.imgLike);
        vh.vSource     =    (TextView)itemView.findViewById(R.id.txtSource);
        vh.vTime       =     (TextView)itemView.findViewById(R.id.txtTime);
       vh.vBgLike =       itemView.findViewById(R.id.vBgLike);
        vh.vCategory    =  (TextView)itemView.findViewById(R.id.txtCategory);
        vh.vNewsPic =    (ImageView)itemView.findViewById(R.id.ivFeedCenter);

       vh.vLove   = (ImageButton)itemView.findViewById(R.id.btnLike);
        itemView.setTag(vh);
        return itemView;
    }

    @Override
    protected void bindView(News item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.vNewsPic.setTag(R.id.ivFeedCenter, item);
        Glide.with(convertView.getContext())
                .load(item.getPicUrl())
                .dontTransform()
                .dontAnimate()
                .into(vh.vNewsPic);
        vh.vNewsTitle.setText(item.getNewsTitle());
        vh.vCategory.setText(item.getCategory());

    }

    @Override
    public void onClick(View v) {

    }



    private static class ViewHolder {
        protected TextView vNewsTitle;
        protected ImageButton vLove;
        protected TextView vSource;
        protected TextView vWriter;
        protected ImageButton vLike;
        protected ImageView vNewsPic;
        protected ImageButton vComment;
        protected android.widget.ImageButton vShare;
        protected View vBgLike;
        protected TextView vTime, vCategory;
        protected ImageView ivLike;
    }
}
