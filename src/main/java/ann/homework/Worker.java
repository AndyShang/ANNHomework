package ann.homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Worker {

	protected double[][] inputLines;
	private String output;

	public void run() {
		for (int i = 0; i < inputLines.length; i++) {
			writeResult(inputLines[i], run(inputLines[i]));
		}
	}

	protected double run(double[] fs) {
		return 0f;
	}

	public void train() {
		for (int i = 0; i < inputLines.length; i++) {
			double[] p = new double[inputLines[i].length - 1];
			int j = 0;
			for (; j < p.length; j++) {
				p[j] = inputLines[i][j];
			}
			train(p, inputLines[i][j]);
		}
	}

	protected void train(double[] p, double f) {
	}

	private void writeResult(double[] fs, double result) {
		String str = "";
		int i = 0;
		for (; i < fs.length - 1; i++) {
			str += fs[i] + ",";
		}
		str += fs[i] + ":" + result;
		if (output != null) {

		} else {
			System.out.println(str);
		}
	}

	public void load() {
	}

	public void save() {
	}

	public void setInput(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line = reader.readLine();
		ArrayList<double[]> lines = new ArrayList<double[]>();

		while (line != null) {
			line = line.trim();
			if (line.startsWith("#"))
				continue;
			String[] strArr = line.split(",");
			double[] numArr = new double[strArr.length];
			for (int i = 0; i < numArr.length; i++) {
				numArr[i] = Float.parseFloat(strArr[i]);
			}
			lines.add(numArr);
			line = reader.readLine();
		}
		inputLines = new double[0][];
		inputLines = lines.toArray(inputLines);
		reader.close();
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
