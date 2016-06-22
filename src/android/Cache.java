/*
 The MIT License (MIT)
 Copyright © 2014-2015 Andrew Stevens <andy@andxyz.com>, Modern Alchemits <office@modalog.at>

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 to permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
*/

package org.apache.cordova.plugin.cache;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;
import android.app.PendingIntent;
import  android.content.Intent;
import android.app.AlarmManager;

@TargetApi(19)
public class Cache extends CordovaPlugin {
	private static final String LOG_TAG = "Cache";
	private CallbackContext callbackContext;

	/**
	 * Constructor.
	 */
	public Cache() {

	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		/*try
		{
		*/
		if (action.equals("clear")) {
			Log.v(LOG_TAG, "Cordova Android Cache.clear() called.");
			this.callbackContext = callbackContext;

			final Cache self = this;
			cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
					try {


						// clear the cache
						self.webView.clearCache(true);

						// clear the data
						self.clearApplicationData();

						// send success result to cordova
						PluginResult result = new PluginResult(PluginResult.Status.OK);
						result.setKeepCallback(false);
						self.callbackContext.sendPluginResult(result);
					} catch (Exception e) {
						String msg = "Error while clearing webview cache.";
						Log.e(LOG_TAG, msg);

						// return error answer to cordova
						PluginResult result = new PluginResult(PluginResult.Status.ERROR, msg);
						result.setKeepCallback(false);
						self.callbackContext.sendPluginResult(result);
					}
				}
			});
			return true;
		}
		return false;
		/*
		}
		catch (JSONException e)
		{
		// TODO: signal JSON problem to JS
		//callbackContext.error("Problem with JSON");
		return false;
		}
		*/
	}

	// http://www.hrupin.com/2011/11/how-to-clear-user-data-in-your-android-application-programmatically
	private void clearApplicationData() {


//		android.os.Process.killProcess(android.os.Process.myPid());
		Context context = this.cordova.getActivity().getApplicationContext();
		Intent mStartActivity = new Intent(context, this.cordova.getActivity().getClass());
		int mPendingIntentId = 123456;
		PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);

		clearApplicationData1();
		//System.gc();
		//System.exit(0);


	}

	public void clearApplicationData1() {
		Context context = this.cordova.getActivity().getApplicationContext();
		File cache =context.getCacheDir();
		File appDir = new File(cache.getParent());
		if(appDir.exists()){
			String[] children = appDir.list();
			for(String s : children){
				if(!s.equals("lib")){
					deleteDir(new File(appDir, s));
					Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
				}
			}
		}
	}

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}
}
