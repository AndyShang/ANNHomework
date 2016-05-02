package ann.homework.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WeightFile {

	private ArrayList<double[]> list;
	
	public WeightFile() {
		list = new ArrayList<>();
	}

	public double[] getWeights(int neuron) {
		return list.get(neuron);
	}

	public void addWeights(double[] weights) {
		list.add(weights);
	}

	public static WeightFile load() {
		WeightFile result = new WeightFile();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("w.txt"));
			String wStr = reader.readLine();
			while (wStr != null) {
				result.parseLine(wStr);
				wStr = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void parseLine(String wStr) {
		String[] wArr = wStr.split(",");
		double[] weights = new double[wArr.length];
		for (int i = 0; i < wArr.length; i++) {
			weights[i] = Double.parseDouble(wArr[i]);
		}
		addWeights(weights);
	}

	public void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("w.txt"));
			System.out.println("ÑµÁ·½á¹ûÊÇ:");
			for (int i = 0; i < list.size(); i++) {
				double[] w = list.get(i);
				for (int j = 0; j < w.length - 1; j++) {
					writer.write(w[j] + ",");
					System.out.print(w[j] + ",");
				}
				writer.write(w[w.length - 1] + "\n");
				System.out.println(w[w.length - 1]);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
