package br.com.lucaspaivac.rumoaotop500.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.lucaspaivac.rumoaotop500.model.Register;
import br.com.lucaspaivac.rumoaotop500.util.Constantes;
import br.com.lucaspaivac.rumoaotop500.util.ToolBox;

public class SqlHelper extends SQLiteOpenHelper {

    private static SqlHelper INSTANCE;
    private static SQLiteDatabase db;

    public static synchronized SqlHelper getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new SqlHelper(context);
        return INSTANCE;
    }

    public SqlHelper(@Nullable Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String sb = "CREATE TABLE IF NOT EXISTS [registros] (\n" +
                    " [id] INTEGER NOT NULL, \n" +
                    " [role] TEXT NOT NULL, \n" +
                    " [res_partida] TEXT NOT NULL, \n" +
                    " [sr] INTEGER NOT NULL, \n" +
                    " [calc_points] INTEGER NOT NULL, \n" +
                    " [created_date] DATETIME NOT NULL, \n" +
                    " CONSTRAINT [] PRIMARY KEY ([id]));";
            String[] comandos = sb.split(";");

            for (int i = 0; i < comandos.length; i++) {
                sqLiteDatabase.execSQL(comandos[i].toLowerCase());
            }
        }catch (Exception e){
            Log.e("Erro Banco", e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<Register> getRegisterBy (String role){
        List<Register> registers = new ArrayList<>();

        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM registros WHERE role = ?", new String[]{role});

        try{
            if (cursor.moveToFirst()){

                do {
                    Register register = new Register();
                    register.setResultado(cursor.getString(cursor.getColumnIndex(Constantes.RES_PARTIDA)));
                    register.setSr(cursor.getInt(cursor.getColumnIndex(Constantes.SR)));
                    register.setCalcPoints(cursor.getInt(cursor.getColumnIndex(Constantes.CALC_POINTS)));
                    register.setCreatedDate(cursor.getString(cursor.getColumnIndex(Constantes.CREATED_DATE)));

                    registers.add(register);
                } while (cursor.moveToNext());

                db.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e("ErroCursor", e.getMessage(), e);
        } finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return registers;
    }

    public long addItem (String role, String resPartida, int sr, int calcPoints){
        db = getWritableDatabase();
        db.beginTransaction();

        long regId = 0;

        try {
            ContentValues cv = new ContentValues();
            cv.put(Constantes.ROLE, role);
            cv.put(Constantes.RES_PARTIDA, resPartida);
            cv.put(Constantes.SR, sr);
            cv.put(Constantes.CALC_POINTS, calcPoints);
            cv.put(Constantes.CREATED_DATE, ToolBox.convertDateFormat());

            regId = db.insertOrThrow("registros", null, cv);
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e("addItemError", e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
        return regId;
    }

    public void clearBD(String role){
        db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("registros", " role = ? ", new String[]{role});
            db.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            db.endTransaction();
        }

    }
}
