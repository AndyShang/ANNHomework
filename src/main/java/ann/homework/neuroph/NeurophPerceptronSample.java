package ann.homework.neuroph;

import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.exceptions.NeurophException;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuronFactory;
import org.neuroph.util.TransferFunctionType;

import ann.homework.Worker;
import ann.homework.utils.WeightFile;

public class NeurophPerceptronSample extends Worker implements
		LearningEventListener {

	DataSet trainning;
	NeuralNetwork<LMS> nn;

	@SuppressWarnings("serial")
	@Override
	public void setData(DataSet data) {
		super.setData(data);
		nn = new SimplePerceptron(data.getInputSize(), 1);
		nn.getLayerAt(0).addNeuron(new BiasNeuron());
		nn.setLearningRule(new LMS() {
			@Override
			public void updateNeuronWeights(Neuron neuron) {
				double err = neuron.getError();
				for (Connection c : neuron.getInputConnections()) {
					double input = c.getInput();
					double wc = err * input;
					Weight w = c.getWeight();
					w.weightChange = wc;
					w.value += wc;
				}
			}
		});
	}

	@Override
	public double[] run(double[] p) {
		nn.setInput(p);
		nn.calculate();
		return nn.getOutput();
	}

	public void load() {
		try {
			double[] weights = WeightFile.load();
			Connection[] inputConnections = nn.getOutputNeurons()[0]
					.getInputConnections();
			for (int i = 0; i < inputConnections.length; i++) {
				inputConnections[i].getWeight().setValue(weights[i]);
			}
		} catch (NeurophException e) {
		}
	}

	public void save() {
		nn.learn(trainning);
		Connection[] inputConnections = nn.getOutputNeurons()[0]
				.getInputConnections();
		double[] weights = new double[inputConnections.length];
		for (int i = 0; i < inputConnections.length; i++) {
			weights[i] = inputConnections[i].getWeight().getValue();
		}
		WeightFile.save(weights);
	}

	public void train(double p[], double[] exp) {
		if (trainning == null)
			trainning = new DataSet(p.length, 1);
		trainning.addRow(new DataSetRow(p, exp));
	}

	public float t(float value) {
		return value > 0 ? 1f : 0f;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		System.out.println(event.toString());
	}
}
