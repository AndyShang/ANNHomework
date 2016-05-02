package ann.homework.neuroph;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.ThresholdNeuron;
import org.neuroph.nnet.learning.BinaryDeltaRule;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuralNetworkType;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

@SuppressWarnings("serial")
public class SimplePerceptron extends NeuralNetwork<LMS> {
	public SimplePerceptron(int inputNeuronsCount, int outputNeuronsCount) {
		createNetwork(inputNeuronsCount, outputNeuronsCount);
	}

	private void createNetwork(int inputNeuronsCount, int outputNeuronsCount) {
		this.setNetworkType(NeuralNetworkType.PERCEPTRON);

		// init neuron settings for input layer
		NeuronProperties inputNeuronProperties = new NeuronProperties();
		inputNeuronProperties.setProperty("transferFunction",
				TransferFunctionType.LINEAR);

		// create input layer
		Layer inputLayer = LayerFactory.createLayer(inputNeuronsCount,
				inputNeuronProperties);
		inputLayer.addNeuron(new BiasNeuron());
		this.addLayer(inputLayer);

		NeuronProperties outputNeuronProperties = new NeuronProperties();
		outputNeuronProperties.setProperty("neuronType", ThresholdNeuron.class);
		outputNeuronProperties.setProperty("thresh",
				new Double(Math.abs(Math.random())));
		outputNeuronProperties.setProperty("transferFunction",
				TransferFunctionType.STEP);
		// for sigmoid and tanh transfer functions set slope propery
		outputNeuronProperties.setProperty("transferFunction.slope",
				new Double(1));

		// createLayer output layer
		Layer outputLayer = LayerFactory.createLayer(outputNeuronsCount,
				outputNeuronProperties);
		this.addLayer(outputLayer);

		// create full conectivity between input and output layer
		ConnectionFactory.fullConnect(inputLayer, outputLayer);

		// set input and output cells for this network
		NeuralNetworkFactory.setDefaultIO(this);

		this.setLearningRule(new BinaryDeltaRule());
	}
}
