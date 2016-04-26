package ann.homework.neuroph;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.LearningRule;

import ann.homework.Worker;

public class NeurophPerceptronSample extends Worker {

	DataSet trainning;
	NeuralNetwork<LearningRule> nn;

	public NeurophPerceptronSample() {
	}

	public double run(double[] p) {
		nn.setInput(p);
		nn.calculate();
		double[] output = nn.getOutput();
		return output[0];
	}

	public void load() {
		nn.load("w.txt");
	}

	public void save() {
		nn.learn(trainning);
		nn.save("w.txt");
	}

	public void train(double p[], double exp) {
		if (trainning == null)
			trainning = new DataSet(p.length, 1);
		trainning.addRow(new DataSetRow((double[]) p, new double[] { exp }));
	}

	public float t(float value) {
		return value > 0 ? 1f : 0f;
	}
}
