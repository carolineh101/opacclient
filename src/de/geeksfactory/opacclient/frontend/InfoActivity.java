package de.geeksfactory.opacclient.frontend;

import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;

import de.geeksfactory.opacclient.R;

public class InfoActivity extends OpacActivity {

	private WebView wvInfo;

	public void load() {
		wvInfo = (WebView) findViewById(R.id.wvInfo);
		TextView tvErr = (TextView) findViewById(R.id.tvErr);
		wvInfo.loadData(getString(R.string.loading), "text/html", null);
		try {
			String infoUrl = app.getLibrary().getData()
					.getString("information");
			if (infoUrl == null || infoUrl.equals("null")) {
				wvInfo.setVisibility(View.GONE);
				tvErr.setVisibility(View.VISIBLE);
				tvErr.setText(R.string.info_unsupported);
			} else if (infoUrl.startsWith("http")) {
				wvInfo.loadUrl(infoUrl);
			} else {
				wvInfo.loadUrl(app.getLibrary().getData().getString("baseurl")
						+ infoUrl);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			wvInfo.setVisibility(View.GONE);
			tvErr.setVisibility(View.VISIBLE);
			tvErr.setText(R.string.info_error);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_activity);

		load();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		SlidingMenu sm = getSlidingMenu();
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	@Override
	public void accountSelected() {
		super.accountSelected();
		load();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}
}
