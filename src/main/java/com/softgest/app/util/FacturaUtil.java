package com.softgest.app.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class FacturaUtil {

	@Bean(name="calculoLinea")
	public static CalculoLinea calculoLinea() {
		
		return new CalculoLinea();
	}
}

