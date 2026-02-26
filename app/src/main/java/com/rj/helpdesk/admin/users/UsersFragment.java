package com.rj.helpdesk.admin.users;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rj.helpdesk.R;
import com.rj.helpdesk.admin.AdminActivity;
import com.rj.helpdesk.admin.users.adapter.UsersAdapter;
import com.rj.helpdesk.common.models.domain.admin.Users;
import com.rj.helpdesk.common.network.admin.AdminConnectionManager;
import com.rj.helpdesk.common.utils.ui.SwipeToActionCallback;
import com.rj.helpdesk.databinding.AdminUsersFragmentBinding;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private AdminUsersFragmentBinding binding;
    private List<Users> listaUsuarios;
    private UsersAdapter adapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AdminUsersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Configurar RecyclerView
        binding.adminUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 2. Inicializar lista y adaptador
        listaUsuarios = new ArrayList<>();
        adapter = new UsersAdapter(listaUsuarios);
        binding.adminUsersRecyclerView.setAdapter(adapter);

        // 3. Cargar datos iniciales
        refrescarLista();

        // 4. Configurar el Swipe-to-Refresh
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler(Looper.getMainLooper()).postDelayed(this::refrescarLista, 1500);
        });

        // 5. Configurar el deslizamiento (Swipe) para acciones
        configurarSwipe();

        // 6. Configurar FAB para mostrar el Card de CRUD
        binding.fabIuUser.setOnClickListener(v -> {
            binding.formIu.adminUsersFormIuTitle.setText(R.string.admin_users_form_iu_title_text);
            binding.formIu.btnAdminUsersFormIuImport.setVisibility(View.VISIBLE);
            binding.formIu.adminUsersFormIuNameEditText.setText("");
            binding.formIu.adminUsersFormIuEmailEditText.setText("");
            binding.formIu.adminUsersCardFormIu.setVisibility(View.VISIBLE);
        });

        binding.formIu.btnCloseFormIu.setOnClickListener(v -> {
            binding.formIu.adminUsersCardFormIu.setVisibility(View.GONE);
        });

        binding.formIu.btnAdminUsersFormIuSave.setOnClickListener(v -> {
            binding.formIu.adminUsersCardFormIu.setVisibility(View.GONE);
            if (getActivity() instanceof AdminActivity) {
                ((AdminActivity) getActivity()).showGlobalMessage("Éxito", "Usuario procesado correctamente","Cerrar");
            }
        });
    }

    private void refrescarLista() {
        if (binding.swipeRefreshLayout.isRefreshing()) {
            binding.swipeRefreshLayout.setRefreshing(false);
        }
        AdminConnectionManager.ListAllUsers(getContext(), new AdminConnectionManager.UsersCallback() {
            @Override
            public void onSuccess(List<Users> UsersList) {
                listaUsuarios.clear();
                listaUsuarios.addAll(UsersList);
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String message) {
                binding.swipeRefreshLayout.setRefreshing(false);
                if (getActivity() instanceof AdminActivity) {
                    ((AdminActivity) getActivity()).showGlobalMessage("Error", message, "Entendido");
                }
            }
        });
    }

    private void configurarSwipe() {
        SwipeToActionCallback swipeCallback = new SwipeToActionCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Users usuario = listaUsuarios.get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    listaUsuarios.remove(position);
                    adapter.notifyItemRemoved(position);
                    if (getActivity() instanceof AdminActivity) {
                        ((AdminActivity) getActivity()).showGlobalMessage("Eliminado",  " ha sido borrado.","Cerrar");
                    }
                } else if (direction == ItemTouchHelper.RIGHT) {
                    adapter.notifyItemChanged(position);
                    binding.formIu.adminUsersFormIuTitle.setText(R.string.admin_users_form_iu_title_update_text);
                    binding.formIu.btnAdminUsersFormIuImport.setVisibility(View.GONE);
                    binding.formIu.adminUsersFormIuNameEditText.setText(usuario.getNombre());
                    binding.formIu.adminUsersFormIuEmailEditText.setText(usuario.getEmail());
                    binding.formIu.adminUsersCardFormIu.setVisibility(View.VISIBLE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(binding.adminUsersRecyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
