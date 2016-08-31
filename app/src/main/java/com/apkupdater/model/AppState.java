package com.apkupdater.model;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@EBean(scope = EBean.Scope.Singleton)
public class AppState
{
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	final static String SELECTED_TAB_KEY = "selected_tab_key";
	final static String CURRENT_THEME_KEY = "current_theme_key";
	final static String UPDATE_LIST_KEY = "update_list_key";
	final static String SETTINGS_ACTIVE_KEY ="settings_active_key";
	final static String LOG_ACTIVE_KEY ="log_active_key";

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@RootContext
	Context mContext;

	static private boolean mFirstStart = true;

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public AppState(
		Context context
	) {
		mContext = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getSelectedTab(
	) {
		return getSelectedTabFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean getSettingsActive(
	) {
		return getSettingsActiveFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean getFirstStart(
	) {
		return mFirstStart;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean getLogActive(
	) {
		return getLogActiveFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public int getCurrentTheme(
	) {
		return getCurrentThemeFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public List<Update> getUpdates(
	) {
		return getUpdatesFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setSelectedTab(
		int selectedTab
	) {
		setSelectedTabToSharedPrefs(mContext, selectedTab);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setSettingsActive(
		boolean active
	) {
		if (active) {
			setLogActive(false);
		}
		setSettingsActiveToSharedPrefs(mContext, active);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setLogActive(
		boolean active
	) {
		if (active) {
			setSettingsActive(false);
		}
		setLogActiveToSharedPrefs(mContext, active);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCurrentTheme(
		int currentTheme
	) {
		setCurrentThemeToSharedPrefs(mContext, currentTheme);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setUpdates(
		List<Update> updates
	) {
		setUpdatesToSharedPrefs(mContext, updates);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void clearUpdates(
	) {
		clearUpdatesFromSharedPrefs(mContext);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setFirstStart(
		boolean firstStart
	) {
		mFirstStart = firstStart;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int getSelectedTabFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getInt(SELECTED_TAB_KEY, 0);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setSelectedTabToSharedPrefs(
		Context context,
		int tab
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().putInt(SELECTED_TAB_KEY, tab).apply();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean getSettingsActiveFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getBoolean(SETTINGS_ACTIVE_KEY, false);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setSettingsActiveToSharedPrefs(
		Context context,
		boolean active
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().putBoolean(SETTINGS_ACTIVE_KEY, active).apply();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private boolean getLogActiveFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getBoolean(LOG_ACTIVE_KEY, false);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setLogActiveToSharedPrefs(
		Context context,
		boolean active
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().putBoolean(LOG_ACTIVE_KEY, active).apply();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private int getCurrentThemeFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPref.getInt(CURRENT_THEME_KEY, 0);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setCurrentThemeToSharedPrefs(
		Context context,
		int theme
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().putInt(CURRENT_THEME_KEY, theme).commit();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<Update> getUpdatesFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		List<Update> updates = new ArrayList<>();

		// Get the string, if its empty return empty array
		String s = sharedPref.getString(UPDATE_LIST_KEY, "");
		if (s.isEmpty()) {
			return updates;
		}

		// Try to convert the json string to an object
		try {
			updates = new Gson().fromJson(s, new TypeToken<List<Update>>(){}.getType());
		} catch (Exception ignored) {

		}

		return updates;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpdatesToSharedPrefs(
		Context context,
		List<Update> updates
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().putString(UPDATE_LIST_KEY, new Gson().toJson(updates)).commit();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void clearUpdatesFromSharedPrefs(
		Context context
	) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		sharedPref.edit().remove(UPDATE_LIST_KEY).commit();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
