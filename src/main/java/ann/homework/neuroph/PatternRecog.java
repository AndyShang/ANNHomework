package ann.homework.neuroph;

import java.util.Arrays;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.Hopfield;
import org.neuroph.nnet.comp.neuron.InputOutputNeuron;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

public class PatternRecog extends NeurophWorker implements
		LearningEventListener {

	private int count = 0;

	public PatternRecog() {
		inputRequired = false;
	}

	@Override
	public void run() {
		recallWords(getDataSetRow(1, 1, -1.5, -1), getNetwork());
		recallWords(getDataSetRow(1, 0, -1, -1), getNetwork());
		recallWords(getDataSetRow(1, -1, 0.5, -1), getNetwork());
	}

	private void recallWords(double[] v, NeuralNetwork network) {
		network.setInput(v);

		double[] networkOutput = null;
		double[] preNetworkOutput = null;
		while (true) {
			network.calculate();
			networkOutput = network.getOutput();
			if (preNetworkOutput == null) {
				preNetworkOutput = networkOutput;
				continue;
			}
			if (Arrays.equals(networkOutput, preNetworkOutput)) {
				break;
			}
			preNetworkOutput = networkOutput;
		}
		System.out.println("Input: " + Arrays.toString(v));
		System.out.println("Output: " + Arrays.toString(networkOutput));
	}

	@Override
	public void train() {
		train(4, new DataSetRow(getDataSetRow(1, 1, -1, -1)));
		train(4, new DataSetRow(getDataSetRow(1, -1, 1, -1)));
	}

	private double[] getDataSetRow(double... value) {
		return value;
	}

	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	@Override
	public NeuralNetwork<LearningRule> createNN(DataSet data) {
		LearningRule rule = new LearningRule() {
			@Override
			public void learn(DataSet trainingSet) {
				int M = trainingSet.size();
				int N = neuralNetwork.getLayerAt(0).getNeuronsCount();
				Layer hopfieldLayer = neuralNetwork.getLayerAt(0);

				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (j == i)
							continue;
						Neuron ni = hopfieldLayer.getNeuronAt(i);
						Neuron nj = hopfieldLayer.getNeuronAt(j);
						Connection cij = nj.getConnectionFrom(ni);
						Connection cji = ni.getConnectionFrom(nj);

						double wij = 0;
						for (int k = 0; k < M; k++) {
							DataSetRow row = trainingSet.getRowAt(k);
							double[] inputs = row.getInput();
							wij += inputs[i] * inputs[j];
						}
						cij.getWeight().setValue(wij);
						cji.getWeight().setValue(wij);
					}
				}
			}
		};
		NeuralNetwork nn = createNetwork();
		nn.setLearningRule(rule);
		rule.addListener(this);
		setNetwork(nn);
		return nn;
	}

	@SuppressWarnings("rawtypes")
	private NeuralNetwork createNetwork() {
		NeuronProperties neuronProperties = new NeuronProperties();
		neuronProperties.setProperty("neuronType", InputOutputNeuron.class);
		neuronProperties.setProperty("bias", new Double(0.0D));
		neuronProperties.setProperty("transferFunction",
				TransferFunctionType.STEP);
		neuronProperties
				.setProperty("transferFunction.yHigh", new Double(1.0D));
		neuronProperties
				.setProperty("transferFunction.yLow", new Double(-1.0D));

		NeuralNetwork nn = new Hopfield(4, neuronProperties);
		return nn;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		System.out.println("iterated for " + count++);
		printWeight();
	}
}