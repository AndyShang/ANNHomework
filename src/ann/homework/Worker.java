package ann.homework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Worker {

	protected float[][] inputLines;
	protected float[] w;
	protected float b;
	private String output;

	protected void run() {
		for (int i = 0; i < inputLines.length; i++) {
			writeResult(inputLines[i], run(inputLines[i]));
		}
	}

	public void train() {
		for (int i = 0; i < inputLines.length; i++) {
			float[] p = new float[inputLines[i].length - 1];
			int j = 0;
			for (; j < p.length; j++) {
				p[j] = inputLines[i][j];
			}
			train(p, inputLines[i][j]);
		}
	}

	protected void train(float[] p, float f) {
	}

	private void writeResult(float[] fs, float result) {
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

	protected float run(float[] fs) {
		return 0f;
	}

	public void load() {
		float[] w = null;
		float b = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("w.txt"));
			String wStr = reader.readLine();
			String[] wArr = wStr.split(",");
			w = new float[wArr.length];
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

	private void load(float[] w, float b) {
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

	public void setInput(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		String line = reader.readLine();
		ArrayList<float[]> lines = new ArrayList<>();

		while (line != null) {
			line = line.trim();
			if (line.startsWith("#"))
				continue;
			String[] strArr = line.split(",");
			float[] numArr = new float[strArr.length];
			for (int i = 0; i < numArr.length; i++) {
				numArr[i] = Float.parseFloat(strArr[i]);
			}
			lines.add(numArr);
			line = reader.readLine();
		}
		inputLines = new float[0][];
		inputLines = lines.toArray(inputLines);
		reader.close();
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
