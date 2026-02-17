package com.rj.helpdesk.admin.dashboard;

import static androidx.constraintlayout.widget.ConstraintSet.VISIBLE;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.rj.helpdesk.R;
import com.rj.helpdesk.databinding.AdminDashboardActivityBinding;
import com.rj.helpdesk.databinding.AdminDashboardContentBinding;

public class DashboardActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private AdminDashboardActivityBinding binding;
    private AdminDashboardContentBinding contentBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminDashboardActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        contentBinding = AdminDashboardContentBinding.bind(binding.content.getRoot());
        setupNavigation();
        setupGlobalUI();

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

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_admin_dashboard);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
    }
    private void setupGlobalUI() {
        if (contentBinding != null) {
            // Mostrar el botón inmediatamente
            contentBinding.commonGlobalButton.getRoot().setVisibility(View.VISIBLE);

            contentBinding.commonGlobalButton.getRoot().bringToFront();
            // Listener para el mensaje global
            contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setOnClickListener(v -> {
                contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.GONE);
            });

            // Configurar el click del botón global aquí mismo
            contentBinding.commonGlobalButton.getRoot().setOnClickListener(v -> {
                // Lógica para cambiar de módulos
                showGlobalMessage("Módulos", "Aquí puedes cambiar de sección", "Cerrar");
            });
        }
    }
}
