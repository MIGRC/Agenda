package br.edu.ucpel.agenda.util;

import android.app.Activity;
import android.widget.Toast;

/**
 *
 * @author Miguel Aguiar Barbosa
 */
public class Mensagem {
	
	public static void Msg(Activity activity, String msg){
		Toast.makeText(activity, msg,Toast.LENGTH_LONG).show();
	}
}
