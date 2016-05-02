package ann.homework.or;

import ann.homework.Worker;
import ann.homework.utils.WeightFile;

public class CustomNNWorker extends Worker {

	protected double[] w;
	protected double b;

	public void load() {
		double[] weight = WeightFile.load();
		w = new double[(weight.length - 1)];
		int i = 0;
		for (; i < w.length; i++) {
			w[i] = weight[i];
		}
		b = weight[i];
	}

	public void save() {
		double[] weight = new double[(w.length + 1)];
		int i = 0;
		for (; i < w.length; i++) {
			weight[i] = w[i];
		}
		weight[i] = b;
		WeightFile.save(weight);
	}
}
