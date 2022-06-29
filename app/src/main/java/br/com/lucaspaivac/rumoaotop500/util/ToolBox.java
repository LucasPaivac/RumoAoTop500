package br.com.lucaspaivac.rumoaotop500.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.style.StrikethroughSpan;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.lucaspaivac.rumoaotop500.R;
import br.com.lucaspaivac.rumoaotop500.bd.SqlHelper;
import br.com.lucaspaivac.rumoaotop500.view.ListActivity;

public class ToolBox {

    public static void nav (Context context, Class aClass){
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    public static boolean validate (EditText editText){
        if (editText.getText().toString().isEmpty() || convertValueI(editText.getText().toString())< 1000){
            return false;
        }else{
            return true;
        }
    }

    public static boolean validateTwo (String resPartida, int lastSr, int sr){
        if (resPartida == "Vitória" && sr <= lastSr)
        {
            return false;
        }
        else if (resPartida == "Derrota" && sr >= lastSr)
        {
            return false;
        }
        else if (resPartida == "Empate" && sr != lastSr)
        {
            return false;
        }else {
            return true;
        }
    }

    public static int convertValueI (String value){
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return -1;
        }
    }

    public static void toast(Context context, int message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void alertDialogCreator(Context context, int title, String message, int icon,
                                          String positiveText, DialogInterface.OnClickListener positiveAction,
                                          String negativeText, DialogInterface.OnClickListener negativeAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setIcon(icon);
            builder.setCancelable(false);
            builder.setPositiveButton(positiveText, positiveAction);
            builder.setNegativeButton(negativeText, negativeAction);
            builder.show();

    }

    public static void alertDialogCreator(Context context, int title, String message, int icon,
                                          String positiveText, DialogInterface.OnClickListener positiveAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(icon);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveText, positiveAction);
        builder.show();

    }

    public static int iconResponse (Spinner spinner){
        int selectedItemPosition = spinner.getSelectedItemPosition();
        try {
            switch (selectedItemPosition){
                case 0: return R.drawable.baseline_trending_up_black_24dp;
                case 1: return R.drawable.baseline_trending_down_black_24dp;
                case 2: return R.drawable.baseline_remove_black_24dp;
                default: return R.drawable.ic_launcher_background;
            }
        }catch (Exception e){
            return R.string.error_icon_response;
        }
    }

    public static int titlereponse (Spinner spinner){
        int selectedItemPosition = spinner.getSelectedItemPosition();
        try {
            switch (selectedItemPosition){
                case 0: return R.string.victory;
                case 1: return R.string.defeat;
                case 2: return R.string.draw;
                default: return R.string.blank;
            }
        }catch (Exception e){
            return R.string.error_title_response;
        }
    }

    public static String resPartidaResponse (Spinner spinner){
        int selectedItemPosition = spinner.getSelectedItemPosition();
        try {
            switch (selectedItemPosition){
                case 0: return "Vitória";
                case 1: return "Derrota";
                case 2: return "Empate";
                default: return " ";
            }
        }catch (Exception e){
            return "Erro resPartidaResponse";
        }
    }

    public static int messageResponse (Spinner spinner){
        int selectedItemPosition = spinner.getSelectedItemPosition();

        try {
            switch (selectedItemPosition){
                case 0: return R.string.earned_points_message;
                case 1: return R.string.lost_points_message;
                case 2: return R.string.draw_message;
                default: return 0;
            }
        }catch (Exception e){
            return R.string.error_message_response;
        }
    }

    public static String  convertDateFormat (){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY", new Locale("pt", "BR"));
        String dateFinal = format.format(Calendar.getInstance().getTime());
        return dateFinal;
    }

    public static void openListActivity(Context context, String role){
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(Constantes.ROLE, role);
        context.startActivity(intent);
    }

    public static void limparBancoRole(Context context, String role){
        SqlHelper helper = SqlHelper.getInstance(context);
        helper.clearBD(role);
    }
}
