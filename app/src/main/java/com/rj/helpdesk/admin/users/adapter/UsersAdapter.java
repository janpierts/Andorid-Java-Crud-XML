package com.rj.helpdesk.admin.users.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rj.helpdesk.R;
import com.rj.helpdesk.common.models.domain.admin.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<Users> usuarios;
    public UsersAdapter(List<Users> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_users_items_register, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users usuario = usuarios.get(position);
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewEmail.setText(usuario.getEmail());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNombre;
        public TextView textViewEmail;

        public ViewHolder(View view) {
            super(view);
            textViewNombre = view.findViewById(R.id.admin_users_textViewNombre);
            textViewEmail = view.findViewById(R.id.admin_users_textViewEmail);
        }
    }
}