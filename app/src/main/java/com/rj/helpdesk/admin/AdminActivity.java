package com.rj.helpdesk.admin;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.rj.helpdesk.R;
import com.rj.helpdesk.databinding.AdminActivityBinding;
import com.rj.helpdesk.databinding.AdminDashboardContentBinding;

public class AdminActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AdminActivityBinding binding;
    private AdminDashboardContentBinding contentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_admin_dashboard);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.AdminDashboardFragment,
                    R.id.AdminUsersFragment
                    )
                    .setOpenableLayout(binding.adminDrawerLayout)
                    .build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.adminNavView, navController);
        }
        contentBinding = AdminDashboardContentBinding.bind(binding.content.getRoot());

        contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setOnClickListener(v -> {
            contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.GONE);
        });
    }

    public void showGlobalMessage(String title, String mssg, String close_name){
        if(contentBinding != null){
            contentBinding.commonGlobalMessage.textGlobalTitle.setText(title);
            contentBinding.commonGlobalMessage.textGlobalBody.setText(mssg);
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setText(close_name);
            contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_admin_dashboard);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }
}
