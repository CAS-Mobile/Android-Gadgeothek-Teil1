package ch.hsr.mge.gadgeothek.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ch.hsr.mge.gadgeothek.R;
import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

public class GadgeothekActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadgeothek);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryService.logout(new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean success) {
                        SharedPreferences preferences = getSharedPreferences(AbstractAuthenticationActivity.PREFERENCES, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        // disable auto-login for the next time
                        editor.putBoolean(AbstractAuthenticationActivity.LAST_AUTOLOGIN_FAILED, true);
                        editor.apply();
                        Intent intent = new Intent(GadgeothekActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right);
                        finish();
                    }
                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });
    }
}
