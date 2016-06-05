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

	private NeuralNetwork nn;
	DataSet trainning;
	private WeightFile weightFile;

	@SuppressWarnings("serial")
	@Override
	public void setData(DataSet data) {
		super.setData(data);
	}

	public NeuralNetwork getNetwork() {
		return nn;
	}

	public void setNetwork(NeuralNetwork nn) {
		this.nn = nn;
	}

	public NeuralNetwork<LearningRule> createNN(DataSet data) {
		return null;
	}

	@Override
	public double[] run(double[] p) {
		NeuralNetwork nn = getNetwork();
		nn.setInput(p);
		nn.calculate();
		return nn.getOutput();
	}

	public void load() {
		weightFile = WeightFile.load();
		NeuralNetwork nn = getNetwork();
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
		NeuralNetwork nn = getNetwork();
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
			trainning = new DataSet(p.length, exp.length);
		trainning.addRow(new DataSetRow(p, exp));
	}
	
	public void train(int size, DataSetRow row) {
		if (trainning == null)
			trainning = new DataSet(size);
		trainning.addRow(row);
	}

	public void printWeight() {
		NeuralNetwork nn = getNetwork();
		Neuron[] neurons = nn.getOutputNeurons();
		for (int i = 0; i < neurons.length; i++) {
			Connection[] inputConnections = neurons[i].getInputConnections();
			double[] weights = new double[inputConnections.length];
			System.out.print("µÚ" + (i + 1) + "×éw:\n");
			for (int j = 0; j < inputConnections.length; j++) {
				weights[j] = inputConnections[j].getWeight().getValue();
				System.out.print(weights[j]);
				if (j < inputConnections.length - 1) {
					System.out.print(", ");
				}
			}
			System.out.print("\n");
		}
		System.out.print("=============================== \n");
	}
}
