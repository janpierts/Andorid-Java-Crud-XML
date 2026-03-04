package com.rj.helpdesk.ui.admin;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.rj.helpdesk.R;
import com.rj.helpdesk.databinding.AdminActivityBinding;
import com.rj.helpdesk.databinding.AdminDashboardContentBinding;
import com.rj.helpdesk.ui.auth.AuthActivity;

public class AdminActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AdminActivityBinding binding;
    private AdminDashboardContentBinding contentBinding;
    private boolean optionalbtn;

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
            this.optionalbtn=false;
            contentBinding.commonGlobalMessage.containerGlobalMessage.setVisibility(View.GONE);
        });
        contentBinding.commonGlobalMessage.buttonOptionGlobalCard.setOnClickListener(v -> {
            this.optionalbtn=true;
            contentBinding.commonGlobalMessage.containerGlobalMessage.setVisibility(View.GONE);
        });
    }

    public interface OnMessageClickListener {
        void onConfirm();
    }
    public void showGlobalMessage(String title, String mssg, String close_name, boolean option, String option_name, String image, AuthActivity.OnMessageClickListener listener){
        if(contentBinding != null){
            int resId = getResources().getIdentifier(image, "drawable", getPackageName());
            if (resId != 0) {
                contentBinding.commonGlobalMessage.imageView.setImageResource(resId);
            } else {
                contentBinding.commonGlobalMessage.imageView.setImageResource(R.drawable.ic_error);
            }
            contentBinding.commonGlobalMessage.textGlobalTitle.setText(title);
            contentBinding.commonGlobalMessage.textGlobalBody.setText(mssg);
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setText(close_name);
            contentBinding.commonGlobalMessage.buttonOptionGlobalCard.setText(option?option_name:"");
            contentBinding.commonGlobalMessage.buttonOptionGlobalCard.setVisibility(option?View.VISIBLE:View.GONE);
            if(option){
                contentBinding.commonGlobalMessage.buttonOptionGlobalCard.setOnClickListener(v -> {
                    contentBinding.commonGlobalMessage.containerGlobalMessage.setVisibility(View.GONE);
                    if(listener != null) listener.onConfirm();
                });
            }
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setOnClickListener(v -> {
                contentBinding.commonGlobalMessage.containerGlobalMessage.setVisibility(View.GONE);
            });
            contentBinding.commonGlobalMessage.containerGlobalMessage.setVisibility(View.VISIBLE);
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
