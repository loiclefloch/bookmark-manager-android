package bm.bookmark_manager.common.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import bm.bookmark_manager.R;

public class BaseAppCompatActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    protected android.support.v7.app.ActionBar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getText(R.string.loading));
        progressDialog.setCancelable(false);
    }

    /**
     * Setup the toolbar
     * @param title the title of the view.
     */
    protected void initToolbar(String title) {
        toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(title);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Remove the back icon on the toolbar. <br>
     * To disable back button, override onBackPressed function to do nothing.
     */
    protected void removeBackIconOnToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void initView(int id) {
        setContentView(id);
    }

}
