package za.co.empirestate.scoop.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.empirestate.scoop.Activities.NewsDeatail;
import za.co.empirestate.scoop.Activities.NewsFeed;
import za.co.empirestate.scoop.Adapters.NewsAdapter;
import za.co.empirestate.scoop.JavaClasses.AppConfig;
import za.co.empirestate.scoop.JavaClasses.AppController;
import za.co.empirestate.scoop.JavaClasses.News;
import za.co.empirestate.scoop.R;
import za.co.empirestate.scoop.interfaces.ScreenShotable;

public class ReadNow extends android.support.v4.app.Fragment implements NewsAdapter.OnNewsItemClickListener  {
    private static final String LOG = "hey gee";
    private static final String TAG = "hey Gee";
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recList;
    Context ctx;
    Context mContext;
    protected ImageView icon, cover;
    private  View rootView;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<News>();
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    public static ReadNow newInstance(int pos) {
        ReadNow fragment = new ReadNow();
        Bundle args = new Bundle();
        args.putInt("d", pos);

        fragment.setArguments(args);
        return fragment;
    }

    public ReadNow() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getNews();

        rootView = inflater.inflate(R.layout.fragment_read_now, container, false);
        ctx = getActivity().getApplicationContext();

        recList = (RecyclerView) rootView.findViewById(R.id.rvNewsList);
        LinearLayoutManager llm = new LinearLayoutManager(ctx);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        setupSwipeRefreshLayout();
        cover = (ImageView)rootView.findViewById(R.id.cover);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setRefreshing(true);
        newsAdapter =  new NewsAdapter(ctx,mContext,newsList);
        recList.setAdapter(newsAdapter);
        recList.setHasFixedSize(true);
        newsAdapter.setonNewsClickListener(this);









        return rootView;
    }
    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               newsList.clear();
                getNews();
                newsAdapter.notifyDataSetChanged();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }




    public  void getNews(){
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ACWTV, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG, "news response " + response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONArray jsonArray = new JSONArray(response);
                        News news = new News();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String headline = jsonObject.getString("headline");
                        String writer =  jsonObject.getString("writer");
                        String category  =  jsonObject.getString("cartegory");
                        String time_published = jsonObject.getString("time_published");
                        String sources =  jsonObject.getString("sources");
                        String newsImage   =  jsonObject.getString("News_picture");
                        news.setCategory(category);
                        news.setSources(sources);
                        news.setPicUrl(newsImage);
                        news.setTimes(time_published);
                         news.setNewsTitle(headline);
                        news.setWriter(writer);
                        newsList.add(news);

                        Log.e(LOG, "news headline" + headline);


                    } catch (JSONException e) {
                    }
                }
                newsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipeRefreshLayout.setRefreshing(false);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("option", "getNews");
                Log.d(LOG, "vod channels parms " + params);


                return params;
            }

        };
        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_json_obj);




    }


    @Override
    public void onCommentsClick(View v, int position) {
        Toast.makeText(ctx,"fuck you ",Toast.LENGTH_LONG).show();
        Log.d(LOG,"Clicked");
    }

    @Override
    public void onMoreClick(View v, int position) {
        Toast.makeText(ctx,"fuck you ",Toast.LENGTH_LONG).show();
        Log.d(LOG,"Clicked");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChannelClick(View v, int position) {
        Toast.makeText(ctx,"fuck you ",Toast.LENGTH_LONG).show();
        Log.d(LOG, "Clicked");
        Intent intent = new Intent(ctx, NewsDeatail.class);

        startActivity(intent);

    }
}
