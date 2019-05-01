package com.example.gitasoft.login1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuUtama extends AppCompatActivity {
    SessionHandler session;


    ImageView logout,infoRumah,socialMedia,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        logout = (ImageView) findViewById(R.id.logout);
        session = new SessionHandler(getApplicationContext());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(MenuUtama.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        infoRumah = (ImageView) findViewById(R.id.btnInfoRumah);
        infoRumah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtama.this, InfoProduct.class);
                startActivity(intent);
            }
        });
        socialMedia = (ImageView) findViewById(R.id.btnSocialMedia);
        socialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/girl.shop5");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/girl.shop5")));
                }
            }
        });
        about = (ImageView) findViewById(R.id.btnabout);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtama.this, About.class);
                startActivity(intent);
            }
        });

    }
}
