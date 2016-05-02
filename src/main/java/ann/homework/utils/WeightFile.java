package ann.homework.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WeightFile {
	public static double[] load() {
		ArrayList<Double> d = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("w.txt"));
			String wStr = reader.readLine();
			while (wStr != null) {
				d.add(Double.parseDouble(wStr));
				wStr = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		double[] res = new double[d.size()];
		for (int i = 0; i < d.size(); i++) {
			res[i] = d.get(i).doubleValue();
		}
		return res;
	}

	public static void save(double[] w) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("w.txt"));
			System.out.println("ÑµÁ·½á¹ûÊÇ:");
			for (int i = 0; i < w.length - 1; i++) {
				writer.write(w[i] + "\n");
				System.out.print(w[i] + ",");
			}
			writer.write(w[w.length - 1] + "");
			System.out.println(w[w.length - 1]);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
