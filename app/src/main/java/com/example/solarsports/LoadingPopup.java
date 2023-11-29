package com.example.solarsports;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.view.WindowManager;

public class LoadingPopup {
    private Dialog dialog;
    private Activity activity;

    public LoadingPopup(Activity activity) {
        this.activity = activity;
    }

    public void show() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_loading); // Reemplaza 'popup_loading' con el nombre de tu archivo XML de diseño

        // Añade esta línea para ajustar el ancho del cuadro de diálogo emergente
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Configura el ancho deseado
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

