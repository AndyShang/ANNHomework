package ann.homework.or;

import org.neuroph.core.data.DataSet;

import ann.homework.Worker;
import ann.homework.utils.WeightFile;

public class CustomNNWorker extends Worker {

	protected double[] w;
	protected double b;
	private WeightFile weightFile;

	@Override
	public void setData(DataSet data) {
		super.setData(data);
		w = new double[data.getInputSize()];
		for (int i = 0; i < w.length; i++) {
			w[i] = Math.random();
		}
	}

	@Override
	public void load() {
		weightFile = WeightFile.load();
		double[] weight = weightFile.getWeights(0);
		w = new double[(weight.length - 1)];
		int i = 0;
		for (; i < w.length; i++) {
			w[i] = weight[i];
		}
		b = weight[i];
	}

	@Override
	public void save() {
		double[] weight = new double[(w.length + 1)];
		int i = 0;
		for (; i < w.length; i++) {
			weight[i] = w[i];
		}
		weight[i] = b;
		weightFile = new WeightFile();
		weightFile.addWeights(weight);
		weightFile.save();
	}
}
