package com.samuscosta.contatudo.utilidade;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tempo {

	private static final String TAG = Tempo.class.getSimpleName();

	public static final String DDMM_BARRA = "dd/MM";
	public static final String DDMMYY_BARRA = "dd/MM/yy";
	public static final String DDMMYYYY_BARRA = "dd/MM/yyyy";
	public static final String HH_DOIS_PONTOS = "HH";
	public static final String HHMM_DOIS_PONTOS = "HH:mm";
	public static final String HHMMSS_DOIS_PONTOS = "HH:mm:ss";

	public static String retornarDataHoraAtual() {
		return retornaDataHoje() + " " + retornaHora();
	}

	public static String retornaHora() {
		return retornaHora(HHMMSS_DOIS_PONTOS); 
	}

	public static String retornaHora(Long data, String formato) {
		String hora = "";
		Date agora = new Date(data);
		SimpleDateFormat formata = new SimpleDateFormat(formato, Locale.getDefault());

		if (formato.equals(HH_DOIS_PONTOS)) {
			hora = formata.format(agora) + ":00:00";
		}
		if (formato.equals(HHMM_DOIS_PONTOS)) {
			hora = formata.format(agora) + ":00";
		}
		if (formato.equals(HHMMSS_DOIS_PONTOS)) {
			hora = formata.format(agora);
		}

		return hora;
	}

	public static String retornaHora(String formato) {
		String hora = "";
		Date agora = new Date();
		SimpleDateFormat formata = new SimpleDateFormat(formato, Locale.getDefault());

		if (formato.equals(HHMM_DOIS_PONTOS)) {
			hora = formata.format(agora) + ":00:00";
		}
		if (formato.equals(HHMM_DOIS_PONTOS)) {
			hora = formata.format(agora) + ":00";
		}
		if (formato.equals(HHMMSS_DOIS_PONTOS)) {
			hora = formata.format(agora);
		}

		return hora;
	}

	public static String retornaDataHoje() {
		return retornaData(0, DDMMYYYY_BARRA);
	}

	public static String retornaDataOntem() {
		return retornaData(-1, DDMMYYYY_BARRA);
	}

	public static String retornaData(int dias, String formato) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DATE, dias);
		SimpleDateFormat formata = new SimpleDateFormat(formato, Locale.getDefault());

		return formata.format(calendario.getTime());
	}

	public static long diferencaTempo(String UnidadeTempo, String MenorData, String MaiorData) {
		//Ajustar código de acordo com o necessário
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		try {
			Date date1 = sdf.parse(MenorData);
			Date date2 = sdf.parse(MaiorData);
			long differenceMilliSeconds = date2.getTime() - date1.getTime();

			if (UnidadeTempo.equals("minutos")) {
				return differenceMilliSeconds/1000/60;
			}else if (UnidadeTempo.equals("horas")) {
				return differenceMilliSeconds/1000/60/60;
			}else if (UnidadeTempo.equals("dias")) {
				return differenceMilliSeconds/1000/60/60/24;
			}

		} catch (ParseException e) {
			Log.e(TAG, "DiferenciaTempo " + e.toString());
			e.printStackTrace();
		}
		return Long.parseLong("0");
	}

}
