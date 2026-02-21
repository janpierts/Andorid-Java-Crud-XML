package com.rj.helpdesk.admin.users;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rj.helpdesk.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private List<Usuario> usuarios;

    public static class Usuario {
        public String nombre;
        public String email;
        public Usuario(String nombre, String email) {
            this.nombre = nombre;
            this.email = email;
        }
    }

    public UsuarioAdapter(List<Usuario> usuarios) {
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
        Usuario usuario = usuarios.get(position);
        holder.textViewNombre.setText(usuario.nombre);
        holder.textViewEmail.setText(usuario.email);
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