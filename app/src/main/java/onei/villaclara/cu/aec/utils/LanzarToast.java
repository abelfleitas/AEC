package onei.villaclara.cu.aec.utils;

/**
 * AEC
 * @author Abel Alejandro Fleitas Perdomo
 * @since  2019
 **/

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import onei.villaclara.cu.aec.R;

public class LanzarToast {

    public static void showToastOficial(Activity context, int stringId) {
        Toast toast = new Toast(context);
        LayoutInflater inflater =  context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, null);
        TextView mensaje_toast = (TextView) layout.findViewById(R.id.txtMensaje);
        mensaje_toast.setText(stringId);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
