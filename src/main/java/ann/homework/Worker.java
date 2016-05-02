package ann.homework;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class Worker {

	protected DataSet dataset;

	public void run() {
		DataSetRow row;
		for (int i = 0; i < dataset.getRows().size(); i++) {
			row = dataset.getRowAt(i);
			double[] inputs = row.getInput();
			double[] result = run(inputs);
			int j = 0;
			System.out.print("ÊäÈë:");
			for (; j < inputs.length - 1; j++) {
				System.out.print(inputs[j] + ",");
			}
			System.out.print(inputs[j] + "½á¹û:");
			for (j = 0; j < result.length - 1; j++) {
				System.out.print(result[j] + ",");
			}
			System.out.print(result[j] + "\n");
		}
	}

	protected double[] run(double[] input) {
		return null;
	}

	public void train() {
		DataSetRow row;
		for (int i = 0; i < dataset.getRows().size(); i++) {
			row = dataset.getRowAt(i);
			train(row.getInput(), row.getDesiredOutput());
		}
	}

	protected void train(double[] input, double[] output) {
	}

	public void load() {
	}

	public void save() {
	}

	public void setData(DataSet data) {
		dataset = data;
	}
}
