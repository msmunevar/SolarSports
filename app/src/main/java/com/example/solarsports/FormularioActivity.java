package com.example.solarsports;

import static android.net.VpnProfileState.STATE_FAILED;
import static android.print.PrintJobInfo.STATE_CANCELED;
import static android.print.PrintJobInfo.STATE_COMPLETED;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.solarsports.DB.DBmanager;
import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.moduleinstall.InstallStatusListener;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.common.moduleinstall.ModuleInstallStatusUpdate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Locale;

public class FormularioActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private String[] opciones;
    private String[] opciones2;
    private Cursor cursor;
    private TextInputEditText editText2;

    private static final String KEY_ALLOW_MANUAL_INPUT = "allow_manual_input";
    private static final String KEY_ENABLE_AUTO_ZOOM = "enable_auto_zoom";

    private boolean allowManualInput;
    private boolean enableAutoZoom;

    private TextView barcodeResultView;
    private LoadingPopup loadingPopup;

    private String descripcion;
    private String ahorro;
    private String consumo;
    private String generada;
    private String CO2;
    private String mes;
    private String ubicacion;
    private String mant;
    private ModuleInstallClient moduleInstallClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        loadingPopup = new LoadingPopup(this);
        loadingPopup.show();
        iniciarCodigoBarra(new BarcodeScanCallback() {
            @Override
            public void onScanSuccess(JSONObject mensaje) {
                try {
                    String displayValue = mensaje.getString("displayValue");
                    JSONObject Objeto = new JSONObject(displayValue);
                    // Obtiene el objeto "ahorroEnergia"
                    JSONObject ahorroEnergiaObjeto = Objeto.getJSONObject("ahorroEnergia");
                    JSONObject consumoEnergia = Objeto.getJSONObject("consumoEnergia");
                    JSONObject generadaEnergia = Objeto.getJSONObject("generadaEnergia");
                    JSONObject CO2red = Objeto.getJSONObject("CO2");

                    ubicacion = Objeto.getString("ubicacion");
                    ahorro = Integer.toString(ahorroEnergiaObjeto.getInt("valor"));
                    consumo = Integer.toString(consumoEnergia.getInt("valor"));
                    generada= Integer.toString(generadaEnergia.getInt("valor"));
                    CO2= Integer.toString(CO2red.getInt("valor"));
                    mant= Integer.toString(Objeto.getInt("diasmant"));
                    descripcion= Objeto.getString("Descripcion");
                    mes = Objeto.getString("mes");

                    System.out.println("Ubicación: " + ubicacion);
                    System.out.println("Ubicación: " + consumo);
                    System.out.println("Ubicación: " + ahorro);
                    System.out.println("Ubicación: " + generada);
                    System.out.println("Ubicación: " + CO2);
                    System.out.println("Ubicación: " + mes);

                    ((EditText) findViewById(R.id.editTextMes)).setText(mes);
                    ((EditText) findViewById(R.id.editTextRadiaciondia)).setText(generada);
                    ((EditText) findViewById(R.id.editTextConsumo)).setText(consumo);
                    ((EditText) findViewById(R.id.ediTextAhorro)).setText(ahorro);
                    ((EditText) findViewById(R.id.editTextDescripcion)).setText(descripcion);
                    ((EditText) findViewById(R.id.ediTextC02)).setText(CO2);
                    ((EditText) findViewById(R.id.ediTextMantenimiento)).setText(mant);
                } catch (JSONException e) {
                    e.printStackTrace();
                    finish();
                }
            }

            @Override
            public void onScanFailure(JSONObject errorMessage) {
                // Maneja el mensaje de error aquí
                System.err.println("Error al escanear: " + errorMessage);
            }
        });


        Intent intent = getIntent();
        int itemId = intent.getIntExtra("itemId", 0);
        int idUsuario = intent.getIntExtra("idUsuario", 0);
        String NombreElemento = intent.getStringExtra("NombreElemento");

        if (intent != null && intent.hasExtra("itemId")) {
            String message = "ID: " + itemId + ", idUsuario: " + idUsuario;
            Toast.makeText(FormularioActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        DBmanager dbmanager = new DBmanager(this);
        dbmanager.open();

        /*cursor = dbmanager.ObtenerItemsCatalogo(itemId);
        HashSet<String> valoresUnicos = new HashSet<>();
        if (cursor.moveToFirst()) {
            do {
                String nombreServicio = cursor.getString(cursor.getColumnIndexOrThrow("nombre_solicitud"));
                valoresUnicos.add(nombreServicio);
            } while (cursor.moveToNext());
        }

        opciones = valoresUnicos.toArray(new String[valoresUnicos.size()]);
        opciones2 = new String[]{"No se encontraron resultados"};*/

        opciones = new String[]{"Opcion1", "Opcion2"};
        opciones2= new String[]{"No se encotnraron ressultados"};


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(NombreElemento);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción a realizar al hacer clic en la flecha de retroceso
                onBackPressed(); // Llamada al método onBackPressed() para regresar atrás
            }
        });

        ScrollView scrollView = findViewById(R.id.ScrollView);  // Reemplaza 'R.id.scrollView' con el ID de tu ScrollView.
        WebView webView = findViewById(R.id.webView);
        Button buttonEnviar = findViewById(R.id.buttonEnviar);

        // Verifica el valor de NombreElemento y muestra el formulario o el iframe
        if ("Consulta Facturas".equals(NombreElemento)) {
            scrollView.setVisibility(View.GONE); // Oculta el ScrollView.
            webView.setVisibility(View.VISIBLE);
            buttonEnviar.setVisibility(View.GONE);//

            webView.getSettings().setJavaScriptEnabled(true); // Habilitar JavaScript si es necesario
            webView.setWebViewClient(new WebViewClient());

            // Cargar el contenido del iframe
            String iframeHtml = "<iframe title=\"Facturación (gestión)\" width=\"100%\" height=\"100%\" " +
                    "src=\"https://app.powerbi.com/view?r=eyJrIjoiYzEwNTlhYzctYjFjNi00NWFiLThiOGYtZWVlNDcwOGZiYmUyIiwidCI6IjQ2YmIyMmI4LTRjMmMtNDBmZi04MzYwLTdiNjMzNDgyMTI3OSIsImMiOjR9\" " +
                    "frameborder=\"0\" allowFullScreen=\"true\"></iframe>";

            webView.loadData(iframeHtml, "text/html", "UTF-8");

        } else {
            scrollView.setVisibility(View.VISIBLE); // Muestra el ScrollView.
            webView.setVisibility(View.GONE); // Oculta el WebView.
            buttonEnviar.setVisibility(View.VISIBLE);//
            //Es para capturar los datos y enviarlos
            buttonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mes = ((EditText) findViewById(R.id.editTextMes)).getText().toString();
                    generada = ((EditText) findViewById(R.id.editTextRadiaciondia)).getText().toString();
                    consumo = ((EditText) findViewById(R.id.editTextConsumo)).getText().toString();
                    ahorro= ((EditText) findViewById(R.id.ediTextAhorro)).getText().toString();
                    descripcion = ((EditText) findViewById(R.id.editTextDescripcion)).getText().toString();
                    CO2 = ((EditText) findViewById(R.id.ediTextC02)).getText().toString();
                    mant = ((EditText) findViewById(R.id.ediTextMantenimiento)).getText().toString();

                    ContentValues values = new ContentValues();
                    values.put("mes", mes);
                    values.put("radiacion_recibida", generada);
                    values.put("consumo",consumo);
                    values.put("ahorro",ahorro);
                    values.put("toneladasco2",CO2);
                    values.put("dias_mant",mant);
                    values.put("descripcion",descripcion);
                    values.put("usuario_id",idUsuario);
                    values.put("catalogo_id",itemId);
                    dbmanager.insert("formulario_registro",values);

                    // Guardar los datos en la tabla "registrosformularios"
                    //dbmanager.guardarRegistroFormulario(numeroContacto, correoElectronico, tipoSolicitud, tipoServicio, descripcion);

                    // Mostrar mensaje de éxito
                    Toast.makeText(FormularioActivity.this, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                }
            });

            //Para capturar si los campos están llenos para activar el botón de enviar.
            EditText editTextNumeroContacto = findViewById(R.id.editTextMes);
            EditText editTextCorreoElectronico = findViewById(R.id.editTextRadiaciondia);
            EditText editTextTipoSolicitud = findViewById(R.id.editTextConsumo);
            EditText editTextTipoServicio = findViewById(R.id.ediTextAhorro);
            EditText editTextDescripcion = findViewById(R.id.editTextDescripcion);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No se utiliza en este caso
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // No se utiliza en este caso
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Validar todos los campos y habilitar/deshabilitar el botón en consecuencia
                    String numeroContacto = editTextNumeroContacto.getText().toString();
                    String correoElectronico = editTextCorreoElectronico.getText().toString();
                    String tipoSolicitud = editTextTipoSolicitud.getText().toString();
                    String tipoServicio = editTextTipoServicio.getText().toString();
                    String descripcion = editTextDescripcion.getText().toString();

                    boolean isAllFieldsFilled = !numeroContacto.isEmpty() &&
                            !correoElectronico.isEmpty() &&
                            !descripcion.isEmpty();

                    buttonEnviar.setEnabled(isAllFieldsFilled);
                }
            };

            // Adjuntar el TextWatcher a cada campo de EditText
            editTextNumeroContacto.addTextChangedListener(textWatcher);
            editTextCorreoElectronico.addTextChangedListener(textWatcher);
            editTextTipoSolicitud.addTextChangedListener(textWatcher);
            editTextTipoServicio.addTextChangedListener(textWatcher);
            editTextDescripcion.addTextChangedListener(textWatcher);

            /*TextInputLayout textInputLayout1 = findViewById(R.id.textInputLayout);
            TextInputEditText editText1 = findViewById(R.id.editText);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opciones);
            configureEditText(editText1, adapter1);

            TextInputLayout textInputLayout2 = findViewById(R.id.textInputLayout2);
            editText2 = findViewById(R.id.editText2);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opciones2);
            configureEditText(editText2, adapter2);*/
        }
    }

    private void configureEditText(TextInputEditText editText, ArrayAdapter<String> adapter) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTypeface(null, Typeface.BOLD);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
        ListView bottomSheetListView = bottomSheetView.findViewById(R.id.bottom_sheet_listview);
        bottomSheetListView.setAdapter(adapter);

        bottomSheetDialog.setContentView(bottomSheetView);

        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            String selectedOption = adapter.getItem(position);
            editText.setText(selectedOption);
            bottomSheetDialog.dismiss();
            // Filtrar las opciones del segundo campo según la selección del primer campo
            filterOptions(selectedOption);
        };

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                bottomSheetListView.setOnItemClickListener(itemClickListener);
                bottomSheetDialog.show();
            } else {
                Log.d("Validación", "Usuario válido: ID: ");
            }
        });

        editText.setOnClickListener(v -> {
            Log.d("Validación", "Usuario válido: ID2: ");
            bottomSheetListView.setOnItemClickListener(itemClickListener);
            bottomSheetDialog.show();
        });

        bottomSheetDialog.setOnDismissListener(dialog -> {
            editText.clearFocus();
        });
    }

    private void filterOptions(String selectedOption) {

        HashSet<String> valoresUnicos = new HashSet<>();
        if (cursor.moveToFirst()) {
            do {
                String nombreSolicitud = cursor.getString(cursor.getColumnIndexOrThrow("nombre_solicitud"));
                String nombreServicio = cursor.getString(cursor.getColumnIndexOrThrow("nombre_servicio"));
                if (nombreSolicitud.equals(selectedOption)) {
                    valoresUnicos.add(nombreServicio);
                    System.out.println("Tipo de servicio - ID: " + nombreServicio);
                }
            } while (cursor.moveToNext());
        }
        opciones2 = valoresUnicos.toArray(new String[valoresUnicos.size()]);
        // Actualizar adaptador del editText2
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, opciones2);
        configureEditText(editText2, adapter2);
    }

    public interface BarcodeScanCallback {
        void onScanSuccess(JSONObject mensaje);
        void onScanFailure(JSONObject errorMessage);
    }

    private void iniciarCodigoBarra(BarcodeScanCallback callback) {

        GmsBarcodeScannerOptions.Builder optionsBuilder = new GmsBarcodeScannerOptions.Builder();
        if (allowManualInput) {
            optionsBuilder.allowManualInput();
        }
        if (enableAutoZoom) {
            optionsBuilder.enableAutoZoom();
        }

        moduleInstallClient = ModuleInstall.getClient(this);

        OptionalModuleApi optionalModuleApi = GmsBarcodeScanning.getClient(this);
        moduleInstallClient
                .areModulesAvailable(optionalModuleApi)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAvailable()) {
                                // Modules are present on the device...
                                GmsBarcodeScanner gmsBarcodeScanner = GmsBarcodeScanning.getClient(this, optionsBuilder.build());
                                gmsBarcodeScanner
                                        .startScan()
                                        .addOnSuccessListener(
                                                barcode -> {
                                                    loadingPopup.dismiss();
                                                    // Llama a la función para obtener el mensaje
                                                    callback.onScanSuccess(getSuccessfulMessage(barcode));
                                                    // Imprime el mensaje en el log
                                                    //Log.d("BarcodeScanner", "Mensaje del escaneo: " + mensaje);
                                                })
                                        .addOnFailureListener(
                                                e -> e.printStackTrace())
                                        .addOnCanceledListener(
                                                () -> finish());
                            } else {
                                // Modules are not present on the device...
                                moduleInstall();
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Handle failure…
                            barcodeResultView.setText("Handle failure…");
                        });
    }
    private JSONObject getSuccessfulMessage(Barcode barcode) {
        JSONObject jsonMensaje = new JSONObject();
        try {
            jsonMensaje.put("displayValue", barcode.getDisplayValue());
            jsonMensaje.put("rawValue", barcode.getRawValue());
            jsonMensaje.put("format", barcode.getFormat());
            jsonMensaje.put("valueType", barcode.getValueType());
            // Agrega más campos según sea necesario
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonMensaje;
    }



    public void onScanButtonClicked() {

        GmsBarcodeScannerOptions.Builder optionsBuilder = new GmsBarcodeScannerOptions.Builder();
        if (allowManualInput) {
            optionsBuilder.allowManualInput();
        }
        if (enableAutoZoom) {
            optionsBuilder.enableAutoZoom();
        }

        moduleInstallClient = ModuleInstall.getClient(this);

        OptionalModuleApi optionalModuleApi = GmsBarcodeScanning.getClient(this);
        moduleInstallClient
                .areModulesAvailable(optionalModuleApi)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAvailable()) {
                                // Modules are present on the device...
                                barcodeResultView.setText("Modules are present on the device");
                                GmsBarcodeScanner gmsBarcodeScanner = GmsBarcodeScanning.getClient(this, optionsBuilder.build());
                                gmsBarcodeScanner
                                        .startScan()
                                        .addOnFailureListener(
                                                e -> e.printStackTrace())
                                        .addOnCanceledListener(
                                                () -> System.out.println("Fallo"));
                            } else {
                                // Modules are not present on the device...
                                barcodeResultView.setText("Modules are not present on the device");
                                moduleInstall();
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Handle failure…
                            barcodeResultView.setText("Handle failure…");
                        });
    }

    final class ModuleInstallProgressListener implements InstallStatusListener {
        @Override
        public void onInstallStatusUpdated(ModuleInstallStatusUpdate update) {
            ModuleInstallStatusUpdate.ProgressInfo progressInfo = update.getProgressInfo();
            // Progress info is only set when modules are in the progress of downloading.
            if (progressInfo != null) {
                int progress =
                        (int) (progressInfo.getBytesDownloaded() * 100 / progressInfo.getTotalBytesToDownload());

            }
            // Handle failure status maybe…

            // Unregister listener when there are no more install status updates.
            if (isTerminateState(update.getInstallState())) {

                moduleInstallClient.unregisterListener(this);
            }
        }

        public boolean isTerminateState(@ModuleInstallStatusUpdate.InstallState int state) {
            return state == STATE_CANCELED || state == STATE_COMPLETED || state == STATE_FAILED;
        }
    }

    private void moduleInstall(){
        InstallStatusListener listener = new ModuleInstallProgressListener();

        OptionalModuleApi optionalModuleApi = GmsBarcodeScanning.getClient(this);
        ModuleInstallRequest moduleInstallRequest =
                ModuleInstallRequest.newBuilder()
                        .addApi(optionalModuleApi)
                        // Add more API if you would like to request multiple optional modules
                        //.addApi(...)
                        // Set the listener if you need to monitor the download progress
                        .setListener(listener)
                        .build();

        moduleInstallClient.installModules(moduleInstallRequest)
                .addOnSuccessListener(
                        response -> {
                            if (response.areModulesAlreadyInstalled()) {
                                // Modules are already installed when the request is sent.
                                barcodeResultView.setText("Modules are already installed when the request is sent.");
                            }
                        })
                .addOnFailureListener(
                        e -> {
                            // Handle failure...

                        });

    }

}
