package za.co.empirestate.scoop.Adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.empirestate.scoop.Activities.NewsDeatail;
import za.co.empirestate.scoop.Activities.NewsFeed;
import za.co.empirestate.scoop.JavaClasses.News;
import za.co.empirestate.scoop.R;


/**
 * Created by George_Kapoya on 15/11/18.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements View.OnClickListener {
    private static final int VIEW_TYPE_DEFAULT = 1;
    private static final int VIEW_TYPE_LOADER = 2;
    private ImageView icon, cover;
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private static final int ANIMATED_ITEMS_COUNT = 2;
    private final ArrayList<Integer> likedPositions = new ArrayList<>();
    private final Map<ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private int lastAnimatedPosition = -1;
    private int itemsCount = 0;
    private boolean animateItems = false;

    private List<News> newsList;
    private Context context;
    private Context mContext;
     private  OnNewsItemClickListener onNewsItemClickListener;
    public NewsAdapter(Context context,Context mcontext, List<News> newsList) {
        this.context = context;
        this.mContext = mcontext;
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.news_item, viewGroup, false);
        final NewsViewHolder newsViewHolder = new NewsViewHolder(itemView);


        newsViewHolder.ivRoot.setOnClickListener(this);
        newsViewHolder.vComment.setOnClickListener(this);

        return newsViewHolder;
    }



    public void bindNewsItem(int position, NewsViewHolder holder) {
        updateHeartButton(holder, false);
        holder.vShare.setTag(position);
    }


    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {

        News news = newsList.get(position);
        DrawableRequestBuilder<String> request = Glide.with(context)
                .load(news.getPicUrl());
        if (news.getPicUrl().endsWith(".gif")) {
            request.diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.vNewsPic);
        } else {
            request.into(holder.vNewsPic);
        }
        holder.vCategory.setText(news.getCategory());
        holder.vTime.setText(news.getTimes());
        holder.vSource.setText(news.getSources());
        holder.vNewsTitle.setText(news.getNewsTitle());
        holder.vWriter.setText(" " + news.getWriter());
        final NewsViewHolder viewHolder = holder;
        bindNewsItem(position, viewHolder);
        holder.vShare.setTag(position);
        holder.vComment.setTag(position);
        holder.ivRoot.setTag(position);
        bindNewsItem(position, holder);


       holder.vComment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context, "fuck you ", Toast.LENGTH_LONG).show();

           }
       });

        holder.vLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatePhotoLike(holder);
                updateHeartButton(holder, true);

            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    @Override
    public void onClick(View view) {


        final int viewID = view.getId();
        if (viewID == R.id.btnMore) {
            if (onNewsItemClickListener != null) {
                onNewsItemClickListener.onMoreClick(view, (Integer) view.getTag());
            }
        }

        if (viewID == R.id.btnComments) {
            if (onNewsItemClickListener != null) {
                onNewsItemClickListener.onCommentsClick(view, (Integer) view.getTag());
            }
        }

        if (viewID == R.id.vImageRoot) {
            if (onNewsItemClickListener != null) {
                onNewsItemClickListener.onChannelClick(view, (Integer) view.getTag());
            }
        }

        if (viewID == R.id.btnLike) {
            NewsViewHolder holder = (NewsViewHolder) view.getTag();
            if (!likedPositions.contains(holder.getPosition())) {
                likedPositions.add(holder.getPosition());
                updateHeartButton(holder, true);

            }
        }

    }

    public void setonNewsClickListener(OnNewsItemClickListener onNewsItemClickListener) {

        this.onNewsItemClickListener = onNewsItemClickListener;
    }
    private void resetLikeAnimationState(NewsViewHolder holder) {
        likeAnimations.remove(holder);
        holder.vBgLike.setVisibility(View.GONE);
        holder.ivLike.setVisibility(View.GONE);
    }
    private void animatePhotoLike(final NewsViewHolder holder) {
        if (!likeAnimations.containsKey(holder)) {
            holder.vLike.setVisibility(View.VISIBLE);
            holder.ivLike.setVisibility(View.VISIBLE);

            holder.vBgLike.setScaleY(0.1f);
            holder.vBgLike.setScaleX(0.1f);
            holder.vBgLike.setAlpha(1f);
            holder.ivLike.setScaleY(0.1f);
            holder.ivLike.setScaleX(0.1f);

            AnimatorSet animatorSet = new AnimatorSet();
            likeAnimations.put(holder, animatorSet);

            ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleY", 0.1f, 1f);
            bgScaleYAnim.setDuration(200);
            bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(holder.vBgLike, "scaleX", 0.1f, 1f);
            bgScaleXAnim.setDuration(200);
            bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(holder.vBgLike, "alpha", 1f, 0f);
            bgAlphaAnim.setDuration(200);
            bgAlphaAnim.setStartDelay(150);
            bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(holder.ivLike, "scaleY", 0.1f, 1f);
            imgScaleUpYAnim.setDuration(300);
            imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(holder.ivLike, "scaleX", 0.1f, 1f);
            imgScaleUpXAnim.setDuration(300);
            imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(holder.ivLike, "scaleY", 1f, 0f);
            imgScaleDownYAnim.setDuration(300);
            imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
            ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(holder.ivLike, "scaleX", 1f, 0f);
            imgScaleDownXAnim.setDuration(300);
            imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
            animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);
            updateHeartButton(holder, true);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resetLikeAnimationState(holder);
                }
            });
            animatorSet.start();
        }
    }

    private void updateHeartButton(final NewsViewHolder holder, boolean animated) {
        if (animated) {
            if (!likeAnimations.containsKey(holder)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.vLove, "rotation", 0f, 360f);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.vLove, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.vLove, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.vLove.setImageResource(R.drawable.ic_heart_red);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetLikeAnimationState(holder);
                    }
                });

                animatorSet.start();
            }
        } else {
            if (likedPositions.contains(holder.getPosition())) {
                holder.vLove.setImageResource(R.drawable.ic_heart_red);
            } else {
                holder.vLove.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }
    }

    public interface OnNewsItemClickListener {
        void onCommentsClick(View v, int position);

        void onMoreClick(View v, int position);

        void onChannelClick(View v, int position);
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder  {

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
        protected za.co.empirestate.scoop.Widgets.SquaredFrameLayout ivRoot;



        public NewsViewHolder(View itemView) {
            super(itemView);
            cover    = (ImageView)itemView.findViewById(R.id.cover);
            vNewsTitle  = (TextView)itemView.findViewById(R.id.txtNewsTitle);
            vWriter     =  (TextView)itemView.findViewById(R.id.txtWriter);
            vComment    =  (ImageButton)itemView.findViewById(R.id.btnComments);
            vShare      =   (ImageButton)itemView.findViewById(R.id.btnMore);
            vLike       =   (ImageButton)itemView.findViewById(R.id.btnLike);
            ivLike      =   (ImageView)itemView.findViewById(R.id.imgLike);
            vSource     =    (TextView)itemView.findViewById(R.id.txtSource);
            vTime       =     (TextView)itemView.findViewById(R.id.txtTime);
            vBgLike =       itemView.findViewById(R.id.vBgLike);
            vCategory    =  (TextView)itemView.findViewById(R.id.txtCategory);
            vNewsPic =    (ImageView)itemView.findViewById(R.id.ivFeedCenter);
            ivRoot   =   (za.co.empirestate.scoop.Widgets.SquaredFrameLayout)itemView.findViewById(R.id.vImageRoot);
            vLove   = (ImageButton)itemView.findViewById(R.id.btnLike);
        }
    }
}

