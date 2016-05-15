package ann.homework.neuroph;

import org.neuroph.core.Connection;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.Weight;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.LMS;

public class ClassifyQuadrant extends NeurophWorker implements
		LearningEventListener {

	@Override
	public NeuralNetwork<LearningRule> createNN(DataSet data) {

		NeuralNetwork<LearningRule> nn = new SimplePerceptron(
				data.getInputSize(), 2);
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
		setNetwork(nn);
		return nn;
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		printWeight();
	}
}
