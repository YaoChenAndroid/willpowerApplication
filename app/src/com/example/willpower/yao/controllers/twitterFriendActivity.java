package com.example.willpower.yao.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.example.willpower.controllers.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class twitterFriendActivity extends Activity{
	private final static String TAG = "twitterFriendActivity";
	private List<Map<String, String>> data;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social_twitter_suggestion_yao);
		data = new ArrayList<Map<String, String>>();
		//Modify UI to show current trip
		ListView listV = (ListView)findViewById(R.id.listViewTwitterFriend);	
		SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"friends", "message"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		listV.setAdapter(adapter);
	}
	public class asytask extends AsyncTask<Void, Void, Integer>{
		private final static String TAG = "asytask";
		SentimentClassifier sentClassifier;
		int LIMIT= 500; //the number of retrieved tweets
		ConfigurationBuilder cb;
		Twitter twitter;
		Context mContext;
		public asytask(Context context)
		{
			mContext = context;
		}
		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				twitterTest();
				performQuery("lost weight");
				
			}catch(TwitterException e){
				Log.e(TAG, e.getMessage());
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		private void twitterTest() throws TwitterException, IOException {
			// TODO Auto-generated method stub
			try
			{
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
				.setOAuthConsumerKey("8yXwm62e4MlCr74LdraSmrHsr")
				.setOAuthConsumerSecret("zWvVBAJpKtUBlVwSijbvr5PisF0ZduUynbq8ZLScZApKB3Q9Za")
				.setOAuthAccessToken("1707416635-O43IwOoi7KTFELhOETm1NWFrD6HGjnBmk9uPy7b")
				.setOAuthAccessTokenSecret("iOwgtSDKvow4WpAnC0WUTqfylTdxw4bvA27ZC3dUH7C3G");
				TwitterFactory tf = new TwitterFactory(cb.build());
				twitter = tf.getInstance();
				AssetManager am = mContext.getAssets();
			    InputStream in = null;
			    OutputStream out = null;
			      in = am.open("classifier.txt");

			      String outpath= Environment.getExternalStorageDirectory().getAbsolutePath() ; 

			        File outFile = new File(outpath, "/classifier.txt");


			      out = new FileOutputStream(outFile);
		        	byte[] buffer = new byte[1024];
		        	int bytesRead;
		        	while((bytesRead = in.read(buffer)) !=-1){
		        	out.write(buffer, 0, bytesRead);
		        	}
			      in.close();
			      in = null;
			      out.flush();
			      out.close();
				sentClassifier = new SentimentClassifier();
	//
//			    twitter4j.Status status =  twitter.updateStatus("YaoChen test");
//			    System.out.println("Successfully updated the status to [" + status.getText() + "].");
				
//			    Query query = new Query("new year resolution");
//			    
//			    int temp = 10;
//			    long lastID = Long.MAX_VALUE;
//			    long t = 0;
//			    while(temp > 0)
//			    {
//			    	QueryResult result = twitter.search(query);
//				    for (twitter4j.Status status : result.getTweets()) {
//				    	t = status.getId();
//				        Log.i(TAG,"@" + status.getUser().getScreenName() + ":" + t);
//				        if(t < lastID)
//				        	lastID = t;
//				    }   
//				    query.setMaxId(lastID-1);
//				    temp--;
//			    }	
			}catch(Exception e)
			{
				Log.e("tem", e.getMessage());
			}

		}
    	protected void onPostExecute (String res)
    	{
			try {
	    		SimpleAdapter adapter = (SimpleAdapter) ((ListView)findViewById(R.id.listViewTwitterFriend)).getAdapter();
	    		adapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
		public void performQuery(String inQuery) throws InterruptedException, IOException {
			Query query = new Query(inQuery);
			query.setCount(100);
			try {
				int count=0;
				QueryResult r;

				do {
					r = twitter.search(query);
					ArrayList ts= (ArrayList) r.getTweets();
		        	data.clear();
					for (int i = 0; i < ts.size() && count < LIMIT; i++) {
						
						twitter4j.Status t =  (twitter4j.Status)ts.get(i);
						String sent = sentClassifier.classify(t.getText());
						if(sent.compareTo("pos") == 0)
						{
			    		    Map<String, String> datum = new HashMap<String, String>(2);
			    		    datum.put("friends", t.getUser().getScreenName());
			    		    datum.put("message", t.getText());
			    		    data.add(datum);
			    		    count++;
						}
					} 
				} while (count< LIMIT && (query = r.nextQuery()) != null);
			}
			
			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			catch(Exception e)
			{
				Log.e(TAG, e.getMessage());
			}
		}
	}

}
