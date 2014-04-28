package com.example.willpower.yao.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;


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
//		    twitter4j.Status status =  twitter.updateStatus("YaoChen test");
//		    System.out.println("Successfully updated the status to [" + status.getText() + "].");
			
//		    Query query = new Query("new year resolution");
//		    
//		    int temp = 10;
//		    long lastID = Long.MAX_VALUE;
//		    long t = 0;
//		    while(temp > 0)
//		    {
//		    	QueryResult result = twitter.search(query);
//			    for (twitter4j.Status status : result.getTweets()) {
//			    	t = status.getId();
//			        Log.i(TAG,"@" + status.getUser().getScreenName() + ":" + t);
//			        if(t < lastID)
//			        	lastID = t;
//			    }   
//			    query.setMaxId(lastID-1);
//			    temp--;
//		    }	
		}catch(Exception e)
		{
			Log.e("tem", e.getMessage());
		}

	}

	public void performQuery(String inQuery) throws InterruptedException, IOException {
		Query query = new Query(inQuery);
//		query.setCount(100);
		try {
			int count=0;
			QueryResult r;
			r = twitter.search(query);
			ArrayList ts= (ArrayList) r.getTweets();

			for (int i = 0; i < ts.size() && count < LIMIT; i++) {
				count++;
				twitter4j.Status t =  (twitter4j.Status)ts.get(i);
				String text = t.getText();
				Log.i(TAG, "Text: " + text);
				String name = t.getUser().getScreenName();
				Log.i(TAG, "User: " + name);
				String sent = sentClassifier.classify(t.getText());
				Log.i(TAG, "Sentiment: " + sent);
			} 
//			do {
//				r = twitter.search(query);
//				ArrayList ts= (ArrayList) r.getTweets();
//
//				for (int i = 0; i < ts.size() && count < LIMIT; i++) {
//					count++;
//					twitter4j.Status t =  (twitter4j.Status)ts.get(i);
//					String text = t.getText();
//					Log.i(TAG, "Text: " + text);
//					String name = t.getUser().getScreenName();
//					Log.i(TAG, "User: " + name);
//					String sent = sentClassifier.classify(t.getText());
//					Log.i(TAG, "Sentiment: " + sent);
//				}   
//			} while ((query = r.nextQuery()) != null && count< LIMIT);
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
