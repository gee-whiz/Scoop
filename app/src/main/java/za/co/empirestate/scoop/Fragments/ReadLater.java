package za.co.empirestate.scoop.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvasilkov.foldablelayout.FoldableListLayout;
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

import za.co.empirestate.scoop.Adapters.FoldableNewsAdapter;
import za.co.empirestate.scoop.Adapters.NewsAdapter;
import za.co.empirestate.scoop.JavaClasses.AppConfig;
import za.co.empirestate.scoop.JavaClasses.AppController;
import za.co.empirestate.scoop.JavaClasses.News;
import za.co.empirestate.scoop.R;


public class ReadLater extends Fragment implements NewsAdapter.OnNewsItemClickListener {

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

    public static ReadLater newInstance(int pos) {
        ReadLater fragment = new ReadLater();
        Bundle args = new Bundle();
        args.putInt("pos",pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_read_later, container, false);
        ctx = getActivity().getApplicationContext();
getNews();
        newsAdapter =  new NewsAdapter(ctx,mContext,newsList);
        FoldableListLayout foldableListLayout = (FoldableListLayout) rootView.findViewById(R.id.foldable_list);
       //foldableListLayout.setAdapter(newsAdapter);






        return rootView;
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
                //swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               // swipeRefreshLayout.setRefreshing(false);

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

    }

    @Override
    public void onMoreClick(View v, int position) {

    }

    @Override
    public void onChannelClick(View v, int position) {

    }
}
