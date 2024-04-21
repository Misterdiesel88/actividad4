package com.example.prueba6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prueba6.entity.User;
import com.example.prueba6.model.UserDao;
import com.example.prueba6.entity.User;
import com.example.prueba6.model.UserDao;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;
    private ListView ListUsers;
    SQLiteDatabase sqLiteDatabase;
    private int documento;
    private String usuario;
    private String nombres;
    private String apellidos;
    private String contra;
    private Context context;
    private Button btnSave;
    private Button btnListUsers;
    private Button btnBuscar;
    private Button btnLimpiar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        initObject();
        btnSave.setOnClickListener(this::createUser);
        btnListUsers.setOnClickListener(this::listUserShow);
        btnBuscar.setOnClickListener(this::buscarUsuarios);

    }

    //1 consulta de usuarios
    private void buscarUsuarios(View view) {
        UserDao dao1 = new UserDao(context, findViewById(R.id.btnBuscar));
        ArrayList<User> users1 = dao1.getUserList();
    }
    //para limpiar campos
    private void limpiarCampos() {
        etDocumento.getText().clear();
        etUsuario.getText().clear();
        etNombres.getText().clear();
        etApellidos.getText().clear();
        etContra.getText().clear();
    }

    private void listUserShow(View view) {
        UserDao dao = new UserDao(context,findViewById(R.id.lvLista));
        ArrayList<User> users = dao.getUserList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        ListUsers.setAdapter(adapter);

    }

    private void createUser(View view) {
        getData();
        User user = new User(documento,nombres,apellidos,usuario,contra);
        UserDao dao = new UserDao(context,view);
        dao.insertUsers(user);
        listUserShow(view);
    }

    private void getData(){
        documento = Integer.parseInt(etDocumento.getText().toString());
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        contra = etContra.getText().toString();
        //validacion de datos con expresiones regulares

    }
    private void initObject(){
        btnSave = findViewById(R.id.btnRegistrar);
        btnListUsers = findViewById(R.id.btnListar);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        ListUsers = findViewById(R.id.lvLista);
        etContra = findViewById(R.id.etContra);
        btnBuscar = findViewById(R.id.btnBuscar);


    }
}