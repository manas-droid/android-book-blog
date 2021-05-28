package com.example.bookblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bookblog.Graphql.ApolloInstance;
import com.example.bookblog.Graphql.Books.GetBooks;
import com.example.bookblog.Graphql.Callbacks.GetBookResponse;
import com.example.bookblog.UI.Adapter.HomeAdapter;
import com.example.bookblog.UI.Fragment.HomeFragment;
import com.example.bookblog.UI.Fragment.YouFragment;
import com.example.bookblog.UtilUser.User;
import com.example.bookblog.UtilUser.UserProfile;
import com.example.bookblog.identity.AuthenticationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import apolloSchema.GetAllPostResultsQuery;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        User user = UserProfile.getUserInfo(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
         Menu menu = bottomNavigationView.getMenu();
         MenuItem menuItem = menu.findItem(R.id.you);
         menuItem.setTitle(user.getName());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        ApolloInstance.setToken(this);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()){
            case R.id.home : selectedFragment = new HomeFragment();
                            break;
            case R.id.you : selectedFragment = new YouFragment();
            default: break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
        return true;
    };

}