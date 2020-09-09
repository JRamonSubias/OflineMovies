package com.esime.oflinemovies.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.esime.oflinemovies.R;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.app.SharedPreferenceManager;
import com.esime.oflinemovies.loginActivity.UI.LoginActivity;

import java.util.ArrayList;

public class Settings_Fragment extends Fragment {
    ListView listViewOpciones;

    public Settings_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_settings_, container, false);
        FindByView(view);
        ArrayList<String> listOpcion = new ArrayList<>();
        listOpcion.add("Gestionar Cuenta");
        listOpcion.add("Cerrar Sesión");
        listOpcion.add("Acerca de");
        listOpcion.add("Eliminar Cuenta");
        listOpcion.add("Version 1.0.0");

        ArrayAdapter<String> listAdapter = new CustomListAdapter(MyApp.getContext(),R.layout.list_adapter,listOpcion);

        listViewOpciones.setAdapter(listAdapter);
        listViewOpciones.setDivider(new ColorDrawable(getResources().getColor(R.color.white,null)));
        listViewOpciones.setDividerHeight(1);

        listViewOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: EditUser();
                        break;
                    case 1: alertDialogConfirmClose();
                        break;
                    case 2: DeleteSession();
                        break;
                    case 3:
                        break;
                }
            }
        });

       return view;
    }

    private void DeleteSession() {
    }

    private void alertDialogConfirmClose(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Seguro que quiere cerrar sesión")
                .setTitle("Cerrar Sesion")
                .setPositiveButton("Cerrar Sesión",((dialog, which) -> closeSession()))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button sButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            sButton.setTextColor(getResources().getColor(R.color.TextInputLayout,null));
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nButton.setTextColor(getResources().getColor(R.color.TextInputLayout,null));

    }

    private void closeSession() {
        SharedPreferenceManager.ClearPreference();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void EditUser() {
        GestionarCuenta gestionarCuenta = new GestionarCuenta();
        gestionarCuenta.show(getChildFragmentManager(),"GestionarCuenta");
    }

    private void FindByView(View view){
        listViewOpciones = view.findViewById(R.id.listOpcion);
    }
}
