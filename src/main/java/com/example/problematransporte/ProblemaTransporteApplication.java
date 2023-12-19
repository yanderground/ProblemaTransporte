package com.example.problematransporte;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class ProblemaTransporteApplication {
	public static void main(String[] args) {
		ArrayList<ArrayList<Double>> mainTable = new ArrayList<>();
		mainTable.add(new ArrayList<>(Arrays.asList(11.27, 16.33, 31.25, 24.33, 21.08, 9.74, 13.18, 41.86, 43.02, 31.8, 38.7, 20.26, 44.61, 34.71, 39.26, 14.89, 10.27, 50.9, 55.79, 42.73, 49.58, 33.51, 19.29, 22.99, 25.73, 999999D)));
		mainTable.add(new ArrayList<>(Arrays.asList(58.4, 11.55, 11.69, 29.98, 14.28, 28.57, 55.43, 37.28, 20.44, 33.49, 38.18, 24.97, 34.84, 40.53, 34.96, 44.99, 16.57, 6.92, 46.34, 34.32, 27.39, 33.08, 38.05, 53.46, 45.88, 999999D)));
		mainTable.add(new ArrayList<>(Arrays.asList(8.74, 51.25, 38.41, 8.75, 42.77, 18.68, 18.67, 31.99, 36.43, 49.5, 2.98, 30.53, 36.92, 15.59, 51.84, 34.23, 48.56, 31.88, 9.19, 34.74, 22.15, 17.19, 32.07, 27.48, 12.2, 999999D)));
		mainTable.add(new ArrayList<>(Arrays.asList(53.58, 54.46, 21.82, 58.13, 26.05, 16.43, 9.95, 41.04, 46.77, 23.83, 7.49, 0.88, 11.74, 8.62, 40.16, 31.66, 29.36, 49.68, 29.52, 4.67, 17.81, 25.13, 45.24, 42.37, 18.87, 999999D)));
		mainTable.add(new ArrayList<>(Arrays.asList(58.1, 28.39, 38.65, 2.1, 9.33, 17.28, 0.98, 3.52, 1.94, 4.25, 20.57, 38.09, 16.6, 38.27, 24.25, 15.59, 51.14, 38.2, 35.89, 21.09, 15.37, 20.17, 12.53, 11.52, 3.07, 999999D)));
		mainTable.add(new ArrayList<>(Arrays.asList(27.65, 16.57, 38.97, 19.14, 19.06, 25.09, 28.65, 4.71, 51.38, 35.79, 0.94, 51.28, 56.34, 40.41, 6.89, 0.39, 5.43, 38.73, 30.0, 5.48, 42.16, 16.77, 9.39, 36.7, 36.46, 999999D)));


		ArrayList<Double> supply = new ArrayList<>(Arrays.asList(5147D, 3053D, 5900D, 5044D, 6149D, 4312D));
		ArrayList<Double> demand = new ArrayList<>(Arrays.asList(1297D, 656D, 1124D, 837D, 1445D, 1495D, 1981D, 1440D, 1228D, 719D, 1023D, 763D, 221D, 628D, 188D, 1119D, 1556D, 1411D, 1017D, 1467D, 1424D, 2396D, 913D, 1303D, 976D, 978D));

		MetodoAproximacao metodoAproximacao = new MetodoAproximacao(mainTable, supply, demand);
		metodoAproximacao.calcular(false);

		metodoAproximacao.imprimirRespostas();
	}
}
