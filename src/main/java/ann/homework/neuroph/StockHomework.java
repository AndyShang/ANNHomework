package ann.homework.neuroph;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.core.transfer.Linear;
import org.neuroph.core.transfer.TransferFunction;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;
import org.neuroph.nnet.learning.SigmoidDeltaRule;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.NguyenWidrowRandomizer;

public class StockHomework extends NeurophWorker implements
		LearningEventListener {

	private int count = 0;

	public StockHomework() {
		inputRequired = false;
	}

	@Override
	public void run() {
		dataset = Numbers.getTrainData();
		for (int i = 30; i < 40; i++) {
			double[] result = run(new double[] { i + 0d });
			System.out.println("输入:");
			Numbers.print(i);
			int j = 0;
			System.out.print("结果:");
			int num = 0;
			for (j = 0; j < result.length; j++) {
				if (result[j] > 0.5d)
					num += 1 << (3 - j);
			}
			System.out.println(num + "");
		}
	}

	@Override
	public void train() {
		try {
			InputStream in = new FileInputStream("closings.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new DataInputStream(in)));
			String line = null;
			double d = 0d;
			while ((line = br.readLine()) != null) {
				train(new double[] { d++ },
						new double[] { Double.parseDouble(line) });
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	@Override
	public NeuralNetwork<LearningRule> createNN(DataSet data) {
		SigmoidDeltaRule rule = new SigmoidDeltaRule() {
			@Override
			protected void updateNetworkWeights(double[] outputError) {
				super.updateNetworkWeights(outputError);
				calculateErrorAndUpdateHiddenNeurons();
			}

			protected void calculateErrorAndUpdateHiddenNeurons() {
				Layer[] layers = neuralNetwork.getLayers();
				for (int layerIdx = layers.length - 2; layerIdx > 0; layerIdx--) {
					for (Neuron neuron : layers[layerIdx].getNeurons()) {
						double neuronError = this
								.calculateHiddenNeuronError(neuron);
						neuron.setError(neuronError);
						updateNeuronWeights(neuron);
					}
				}
			}

			protected double calculateHiddenNeuronError(Neuron neuron) {
				double deltaSum = 0d;
				for (Connection connection : neuron.getOutConnections()) {
					// 根据后一层神经元的敏感性计算当前层神经元的敏感性
					double delta = connection.getToNeuron().getError()
							* connection.getWeight().value;
					deltaSum += delta;
				}

				TransferFunction transferFunction = neuron
						.getTransferFunction();
				double netInput = neuron.getNetInput();
				double f1 = transferFunction.getDerivative(netInput);
				double neuronError = f1 * deltaSum;
				return neuronError;
			}
		};
		NeuralNetwork nn = createNetwork(1, 12, 1);
		nn.setLearningRule(rule);
		rule.addListener(this);
		rule.setLearningRate(0.01d);
		rule.setMaxError(0.001d);
		setNetwork(nn);
		return nn;
	}

	@SuppressWarnings("rawtypes")
	private NeuralNetwork createNetwork(int... neuronsInLayers) {

		List<Integer> neuronsInLayersVector = new ArrayList<>();
		for (int i = 0; i < neuronsInLayers.length; i++) {
			neuronsInLayersVector.add(new Integer(neuronsInLayers[i]));
		}

		NeuralNetwork nn = new NeuralNetwork();
		NeuronProperties neuronProperties = new NeuronProperties();
		neuronProperties.setProperty("transferFunction",
				TransferFunctionType.SIGMOID);
		// create input layer
		NeuronProperties inputNeuronProperties = new NeuronProperties(
				InputNeuron.class, Linear.class);
		Layer layer = LayerFactory.createLayer(neuronsInLayersVector.get(0),
				inputNeuronProperties);
		layer.addNeuron(new BiasNeuron());
		nn.addLayer(layer);

		// create layers
		Layer prevLayer = layer;

		int layerIdx = 1;
		for (layerIdx = 1; layerIdx < neuronsInLayersVector.size() - 1; layerIdx++) {
			Integer neuronsNum = neuronsInLayersVector.get(layerIdx);
			// createLayer layer
			layer = LayerFactory.createLayer(neuronsNum, neuronProperties);
			layer.addNeuron(new BiasNeuron());
			// add created layer to network
			nn.addLayer(layer);
			// createLayer full connectivity between previous and this layer
			if (prevLayer != null) {
				ConnectionFactory.fullConnect(prevLayer, layer);
			}

			prevLayer = layer;
		}

		Integer neuronsNum = neuronsInLayersVector.get(layerIdx);
		NeuronProperties outProperties = new NeuronProperties();
		outProperties.put("transferFunction", Linear.class);
		layer = LayerFactory.createLayer(neuronsNum, outProperties);
		nn.addLayer(layer);
		ConnectionFactory.fullConnect(prevLayer, layer);
		prevLayer = layer;

		// set input and output cells for network
		NeuralNetworkFactory.setDefaultIO(nn);
		nn.randomizeWeights(new NguyenWidrowRandomizer(-0.7, 0.7));
		return nn;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		System.out.println("iterated for " + count++);
		printWeight();
	}
}