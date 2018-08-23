package com.softgest.app.util;

public class CalculoLinea {

	public static Double total = 0.0;
	
	public static Double calculoLinea(int cantidad, double precio) {
		total += Math.round((cantidad * precio)*100.0)/100.0;
		return cantidad * precio;
	}
	
	public static void resetTotal() {
		total = 0.0;
	}
}
