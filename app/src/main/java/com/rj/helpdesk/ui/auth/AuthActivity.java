package com.rj.helpdesk.ui.auth;

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
import com.rj.helpdesk.common.network.auth.AuthConnectionManager;
import com.rj.helpdesk.common.utils.PreferenceUtils;
import com.rj.helpdesk.databinding.AuthLoginActivityBinding;
import com.rj.helpdesk.databinding.AuthLoginContentBinding;

public class AuthActivity extends AppCompatActivity {
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
        contentBinding.containerApiSettings.editTextApi.setText(savedApi);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_login);
        
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }

        contentBinding.containerApiSettings.buttonSaveLoginSettingsCard.setOnClickListener(v -> {
            String apiUrl = contentBinding.containerApiSettings.editTextApi.getText().toString();
            if(!PreferenceUtils.isValidUrl(apiUrl)) {
                showGlobalMessage("Error", "URL no válida", "Cerrar",false,"","ic_error",null);
                return;
            }
            showGlobalMessage("Guardar API", "Desea guardar la configuración?", "Cerrar",true,"Entendido","ic_warn", () -> {
                PreferenceUtils.saveApiUrl(this, apiUrl);
                AuthConnectionManager.testConnection(this, isSuccess -> {
                    if (isSuccess) {
                        showGlobalMessage("Éxito", "Configuración guardada y API online", "Entendido", false, "", "ic_renew",null);
                        contentBinding.containerApiSettings.containerApiSettings.setVisibility(View.GONE);
                    } else {
                        showGlobalMessage("Error", "API no disponible", "Cerrar", false, "", "ic_error",null);
                    }
                });
            });
        });
        contentBinding.containerApiSettings.buttonCloseLoginSettingsCard.setOnClickListener(v -> {
            contentBinding.containerApiSettings.containerApiSettings.setVisibility(View.GONE);
        });
    }
    public interface OnMessageClickListener {
        void onConfirm();
    }
    public void showGlobalMessage(String title, String mssg, String close_name, boolean option, String option_name, String image, OnMessageClickListener listener){
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            contentBinding.containerApiSettings.containerApiSettings.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
