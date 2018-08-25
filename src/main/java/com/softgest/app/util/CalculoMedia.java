package com.softgest.app.util;

import java.util.List;
import com.softgest.app.models.entity.Valoracion;


public class CalculoMedia {	
	
	public static  Double calculoMedia(List<Valoracion> valoraciones) {
		
			List<Valoracion> val = valoraciones;
			if(val != null) {
				int puntos = 0;
				if(val.size() > 0 ) {
					for(Valoracion v : val) {
						puntos += v.getPuntos();
					}
					double media = puntos / val.size()+1;
					return media;
				}else {
					return 0.0;
				}
			}
			
		
		return 0.0;
	}
}
