package co.edu.uniminuto;

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

import java.util.ArrayList;

import co.edu.uniminuto.bdsqlite.R;
import co.edu.uniminuto.entity.User;
import co.edu.uniminuto.model.UserDao;

import android.widget.Spinner;
import android.widget.AdapterView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Validate";
    private Context context;
    private EditText etDocumento;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etUsuario;
    private EditText etContra;
    private ListView listUsers;
    private int documento;
    String usuario;
    String nombres;
    String apellidos;
    String contra;
    SQLiteDatabase baseDatos;
    private Button btnSave;
    private Button btnListUsers;
    private Button btnUpdate;
    private Button btnBuscar;
    private View view;
    private Button btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        context = this;
        initObjet();

        btnSave.setOnClickListener(this::createUser);
        btnListUsers.setOnClickListener(this::listUserShow);
        btnUpdate.setOnClickListener(this::updateUser);
        btnBuscar.setOnClickListener(view -> buscarUsuario());
        btnLimpiar.setOnClickListener(view -> limpiarCampos());
    }

    //Limpieza de datos
    private void limpiarCampos() {
        etDocumento.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etUsuario.setText("");
        etContra.setText("");
    }

    //realizar la búsqueda en la base de datos y actualizar los campos de texto con los datos del usuario encontrado
    private void buscarUsuario() {
        int documento = Integer.parseInt(etDocumento.getText().toString());
        UserDao dao = new UserDao(context, view);
        User user = dao.getUserByDocument(documento);
        if (user != null) {
            // Si se encuentra el usuario, actualizar los campos de texto
            etNombres.setText(user.getNames());
            etApellidos.setText(user.getLastName());
            etUsuario.setText(user.getUser());
            etContra.setText(user.getPassword());
        } else {
            // Si no se encuentra el usuario, limpiar los campos de texto
            limpiarCampos();
            Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_SHORT).show();
        }
    }

    //llamamos al método cuando se requiera actualizar un usuario en la actividad principal.
    private void updateUser(View view) {
        getData();
        User user = new User(documento, nombres, apellidos, usuario, contra);
        UserDao dao = new UserDao(context, view);
        dao.updateUser(user);
        listUserShow(view);
    }

    private void listUserShow(View view) {
        UserDao dao = new UserDao(context,findViewById(R.id.lvLista));
        ArrayList<User> users = dao.getUserList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        listUsers.setAdapter(adapter);
    }

    private void createUser(View view) {
        getData();
        User user = new User(documento,nombres,apellidos,usuario,contra);
        UserDao dao = new UserDao(context,view);
        dao. insertUser(user);
        listUserShow(view);
    }

    //Objeto para la captura de datos
    private void getData(){
        documento = Integer.parseInt(etDocumento.getText().toString());
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        contra = etContra.getText().toString();
    }
    private void initObjet(){
        //Enlace de objetos

        //Botones
        btnSave = findViewById(R.id.btnRegistrar);
        btnListUsers = findViewById(R.id.btnListar);
        btnUpdate = findViewById(R.id.btnActualizar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnLimpiar = findViewById(R.id.btnLimpiar);

        //Edit Text
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        etContra = findViewById(R.id.etContra);

        //List
        listUsers = findViewById(R.id.lvLista);
    }
}