package com.rj.helpdesk.auth.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.rj.helpdesk.R;
import com.rj.helpdesk.databinding.AuthLoginActivityBinding;
import com.rj.helpdesk.databinding.AuthLoginContentBinding;

public class LoginActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AuthLoginActivityBinding binding;
    private AuthLoginContentBinding contentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = AuthLoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        View contentRoot = findViewById(R.id.nav_host_fragment_content_login).getParent() instanceof View ?
                (View) findViewById(R.id.nav_host_fragment_content_login).getParent() : null;

        if(contentRoot != null){
            contentBinding = AuthLoginContentBinding.bind(contentRoot);
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        if(contentBinding!=null){
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setOnClickListener(v -> {
                contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.GONE);
            });
        }
    }
    public void showGlobalMessage(String title, String mssg, String close_name){
        if(contentBinding!=null){
            contentBinding.commonGlobalMessage.textGlobalTitle.setText(title);
            contentBinding.commonGlobalMessage.textGlobalBody.setText(mssg);
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setText(close_name);
            contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
