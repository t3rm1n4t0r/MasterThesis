package game.net;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.alessandro.computergraphicsexample.RoomsActivity;

import java.util.ArrayList;

import login.interaction.FieldsNames;
import login.services.Login;

/**
 * Handler che permette la comunicazione tra il Thread di login attivato alla ricezione della risposta dal server e
 * l'Activity di login stessa ricevendo i risultati del Login
 */
public class LoginHandler extends Handler {

    private Context context;

    private ArrayList<LoginHandlerListener> loginHandlerListeners = new ArrayList<>();

    public LoginHandler(Context context) {
        super();
        this.context = context;
    }

    public void addLoginHandlerListeners(LoginHandlerListener l) {
        loginHandlerListeners.add(l);
    }

    public void removeLoginHandlerListeners(LoginHandlerListener l) {
        loginHandlerListeners.remove(l);
    }

    @Override
    public void handleMessage(Message msg) {
        Login.LoginResult result = (Login.LoginResult) msg.obj;
        if (result.isOk()) {
            for (LoginHandlerListener l : loginHandlerListeners)
                l.onLoginComplete(true);

            Intent intent = new Intent(context, RoomsActivity.class);
            intent.putExtra(FieldsNames.USERNAME, result.getUsername());
            intent.putExtra(FieldsNames.HASHCODE, result.getHashcode());
            context.startActivity(intent);
        } else {
            for (LoginHandlerListener l : loginHandlerListeners)
                l.onLoginComplete(false);
        }
        Log.d("RESULT", String.valueOf(result.isOk()));
    }

}