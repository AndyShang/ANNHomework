package ann.homework.neuroph;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.LayerFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

public class NumberRecognition extends NeurophWorker implements
		LearningEventListener {

	private int count = 0;

	public NumberRecognition() {
		inputRequired = false;
	}

	@Override
	public void run() {
		dataset = Numbers.getTrainData();
		DataSetRow row;
		for (int i = 0; i < dataset.getRows().size(); i++) {
			row = dataset.getRowAt(i);
			double[] inputs = row.getInput();
			double[] result = run(inputs);
			System.out.println("ÊäÈë:");
			Numbers.print(i);
			int j = 0;
			System.out.print("½á¹û:");
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
		train(Numbers.getTrainDataRow(0), new double[] { 0, 0, 0, 0 });
		train(Numbers.getTrainDataRow(1), new double[] { 0, 0, 0, 1 });
		train(Numbers.getTrainDataRow(2), new double[] { 0, 0, 1, 0 });
		train(Numbers.getTrainDataRow(3), new double[] { 0, 0, 1, 1 });
		train(Numbers.getTrainDataRow(4), new double[] { 0, 1, 0, 0 });
		train(Numbers.getTrainDataRow(5), new double[] { 0, 1, 0, 1 });
		train(Numbers.getTrainDataRow(6), new double[] { 0, 1, 1, 0 });
		train(Numbers.getTrainDataRow(7), new double[] { 0, 1, 1, 1 });
		train(Numbers.getTrainDataRow(8), new double[] { 1, 0, 0, 0 });
		train(Numbers.getTrainDataRow(9), new double[] { 1, 0, 0, 1 });
	}

	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	@Override
	public NeuralNetwork<LearningRule> createNN(DataSet data) {
		NeuralNetwork nn = createNetwork(35, 4);
		nn.setLearningRule(new LMS() {
			@Override
			public void updateNeuronWeights(Neuron neuron) {
				double err = neuron.getError();
				for (Connection c : neuron.getInputConnections()) {
					double input = c.getInput();
					double wc = learningRate * err * input;
					Weight w = c.getWeight();
					w.weightChange = wc;
					w.value += wc;
				}
			}
		});
		nn.getLearningRule().addListener(this);
		((IterativeLearning) nn.getLearningRule()).setLearningRate(0.01d);
		setNetwork(nn);
		return nn;
	}

	@SuppressWarnings("rawtypes")
	private NeuralNetwork createNetwork(int inputSize, int outputSize) {
		NeuralNetwork nn = new NeuralNetwork();
		// type seems not used at all
		// nn.setNetworkType(NeuralNetworkType.ADALINE);

		NeuronProperties inProp = new NeuronProperties();
		inProp.setProperty("transferFunction", TransferFunctionType.LINEAR);
		Layer inputLayer = LayerFactory.createLayer(inputSize, inProp);
		inputLayer.addNeuron(new BiasNeuron());

		NeuronProperties outProp = new NeuronProperties();
		outProp.setProperty("transferFunction", TransferFunctionType.RAMP);
		outProp.setProperty("transferFunction.slope", 1d);
		outProp.setProperty("transferFunction.yHigh", 1d);
		outProp.setProperty("transferFunction.xHigh", 1d);
		outProp.setProperty("transferFunction.yLow", -1d);
		outProp.setProperty("transferFunction.xLow", -1d);
		Layer outputLayer = LayerFactory.createLayer(outputSize, outProp);

		nn.addLayer(inputLayer);
		nn.addLayer(outputLayer);
		ConnectionFactory.fullConnect(inputLayer, outputLayer);

		NeuralNetworkFactory.setDefaultIO(nn);
		return nn;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		System.out.println("iterated for " + count++);
		printWeight();
	}
}