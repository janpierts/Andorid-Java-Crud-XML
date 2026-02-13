package com.rj.helpdesk.auth.login;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.rj.helpdesk.R;
import com.rj.helpdesk.common.connections.auth.AuthConnectionManager;
import com.rj.helpdesk.common.utils.PreferenceUtils;
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

        contentBinding = AuthLoginContentBinding.bind(binding.content.getRoot());

        // Cargar API guardada usando Utils
        String savedApi = PreferenceUtils.getApiUrl(this);
        contentBinding.cardLoginSettings.editTextApi.setText(savedApi);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_login);
        
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // Listener para ocultar/mostrar la Toolbar según el destino
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.AdminDashboardFragment || destination.getId() == R.id.UserDashboardFragment) {
                    binding.toolbar.setVisibility(View.GONE);
                } else {
                    binding.toolbar.setVisibility(View.VISIBLE);
                }
            });
        }

        contentBinding.commonGlobalMessage.buttonCloseGlobalCard.setOnClickListener(v -> {
            contentBinding.commonGlobalMessage.cardGlobalMessage.setVisibility(View.GONE);
        });

        contentBinding.cardLoginSettings.buttonSaveLoginSettingsCard.setOnClickListener(v -> {
            String apiUrl = contentBinding.cardLoginSettings.editTextApi.getText().toString();
            PreferenceUtils.saveApiUrl(this, apiUrl);
            AuthConnectionManager.testConnection(this,isSuccess -> {
                if(isSuccess){
                    showGlobalMessage("Éxito", "Configuración guardada y API online", "Cerrar");
                    contentBinding.cardLoginSettings.cardLoginSettings.setVisibility(View.GONE);
                }else{
                    showGlobalMessage("Error", "API no disponible", "Cerrar");
                }
            });
        });
        
        if (contentBinding.cardLoginSettings.buttonCloseLoginSettingsCard != null) {
            contentBinding.cardLoginSettings.buttonCloseLoginSettingsCard.setOnClickListener(v -> {
                contentBinding.cardLoginSettings.cardLoginSettings.setVisibility(View.GONE);
            });
        }
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            contentBinding.cardLoginSettings.cardLoginSettings.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
