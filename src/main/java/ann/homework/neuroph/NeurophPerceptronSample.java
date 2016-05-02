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

import sun.font.CreatedFontTracker;

import ann.homework.Worker;
import ann.homework.utils.WeightFile;

public class NeurophPerceptronSample extends NeurophWorker implements
		LearningEventListener {

	@Override
	protected NeuralNetwork<LearningRule> createNN(DataSet data) {

		NeuralNetwork<LearningRule> nn = new SimplePerceptron(
				data.getInputSize(), 1);
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
		nn.getLearningRule().addListener(this);
		return nn;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		printWeight();
	}
}
