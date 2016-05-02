package ann.homework.neuroph;

import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.exceptions.NeurophException;
import org.neuroph.core.learning.LearningRule;

import ann.homework.Worker;
import ann.homework.utils.WeightFile;

public class NeurophWorker extends Worker {

	DataSet trainning;
	NeuralNetwork<LearningRule> nn;
	private WeightFile weightFile;

	@SuppressWarnings("serial")
	@Override
	public void setData(DataSet data) {
		super.setData(data);
		nn = createNN(data);
	}

	protected NeuralNetwork<LearningRule> createNN(DataSet data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] run(double[] p) {
		nn.setInput(p);
		nn.calculate();
		return nn.getOutput();
	}

	public void load() {
		weightFile = WeightFile.load();
		Neuron[] neurons = nn.getOutputNeurons();
		for (int i = 0; i < neurons.length; i++) {
			double[] weights = weightFile.getWeights(i);
			Connection[] inputConnections = neurons[i].getInputConnections();
			for (int j = 0; j < inputConnections.length; j++) {
				inputConnections[j].getWeight().setValue(weights[j]);
			}
		}
	}

	public void save() {
		nn.learn(trainning);
		//
		weightFile = new WeightFile();
		Neuron[] neurons = nn.getOutputNeurons();
		for (int i = 0; i < neurons.length; i++) {
			Connection[] inputConnections = neurons[i].getInputConnections();
			double[] weights = new double[inputConnections.length];
			for (int j = 0; j < inputConnections.length; j++) {
				weights[j] = inputConnections[j].getWeight().getValue();
			}
			weightFile.addWeights(weights);
		}
		weightFile.save();
	}

	public void train(double p[], double[] exp) {
		if (trainning == null)
			trainning = new DataSet(p.length, 1);
		trainning.addRow(new DataSetRow(p, exp));
	}

	public float t(float value) {
		return value > 0 ? 1f : 0f;
	}
}
