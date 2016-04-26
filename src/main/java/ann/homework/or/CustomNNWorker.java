package ann.homework.or;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ann.homework.Worker;

public class CustomNNWorker extends Worker {

	protected double[] w;
	protected double b;

	public void load() {
		double[] w = null;
		double b = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("w.txt"));
			String wStr = reader.readLine();
			String[] wArr = wStr.split(",");
			w = new double[wArr.length];
			for (int i = 0; i < wArr.length; i++) {
				w[i] = Float.parseFloat(wArr[i]);
			}
			b = Float.parseFloat(reader.readLine());
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		load(w, b);
	}

	private void load(double[] w, double b) {
		this.w = w;
		this.b = b;
	}

	public void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("w.txt"));
			System.out.println("ÑµÁ·½á¹ûÊÇ:");
			for (int i = 0; i < w.length - 1; i++) {
				writer.write(w[i] + ",");
				System.out.print("w:" + w[i] + ",");
			}
			writer.write(w[w.length - 1] + "\n");
			System.out.println(w[w.length - 1]);
			writer.write(b + "");
			System.out.println("b:" + b);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
