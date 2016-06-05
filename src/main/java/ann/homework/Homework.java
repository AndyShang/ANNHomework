package ann.homework;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.neuroph.core.data.DataSet;
import org.neuroph.util.TrainingSetImport;

import ann.homework.neuroph.ClassifyQuadrant;
import ann.homework.neuroph.NeurophPerceptronSample;
import ann.homework.neuroph.NeurophWorker;
import ann.homework.neuroph.NumberRecognition;
import ann.homework.neuroph.PatternRecog;
import ann.homework.neuroph.StockHomework;
import ann.homework.or.ORWorker;

public class Homework {

	public static int defaultInputCount = 2;
	public static int defaultOutputCount = 1;

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", "chapter", true, "The chapter");
		options.addOption("t", "train", false,
				"the input file is training data");
		options.addOption("o", "output", true, "the output file path");
		options.addOption("i", "input", true, "the input file path");
		options.addOption("ic", "inputcount", true, "the count of input");
		options.addOption("oc", "outputcount", true, "the count of output");
		try {
			new Homework().execute(new DefaultParser().parse(options, args));
		} catch (ParseException e) {
			new Homework().help();
		}
	}

	private void help() {
		System.out.println("栗子-c 01 -t -i train.txt -o w.txt");
		System.out.println("请用-c 指定课程");
		System.out.println("01 记忆逻辑OR运算");
		System.exit(0);
	}

	private void execute(CommandLine line) {
		String input = null;
		String chapter = null;
		Worker worker = null;
		int inputsCount = defaultInputCount;
		int outputsCount = defaultOutputCount;
		DataSet data = null;

		if (line.hasOption('h')) {
			help();
			return;
		}

		if (line.hasOption('c'))
			chapter = line.getOptionValue('c');
		if (line.hasOption('i'))
			input = line.getOptionValue('i');
		if (line.hasOption("ic"))
			inputsCount = Integer.parseInt(line.getOptionValue("ic"));
		if (line.hasOption("oc"))
			outputsCount = Integer.parseInt(line.getOptionValue("oc"));
		//
		if (!line.hasOption('t'))
			outputsCount = 0;
		if (chapter == null) {
			help();
			return;
		}
		worker = getWorker(chapter);
		if (worker.inputRequired) {
			try {
				data = TrainingSetImport.importFromFile(input, inputsCount,
						outputsCount, ",");
			} catch (NumberFormatException | IOException e1) {
				help();
				return;
			}
			worker.setData(data);
		}
		if (worker instanceof NeurophWorker) {
			((NeurophWorker) worker).createNN(data);
		}
		if (line.hasOption('t')) {
			worker.train();
			worker.save();
		} else {
			worker.load();
			worker.run();
		}
	}

	private Worker getWorker(String chapter) {
		if (chapter.equals("01"))
			return new ORWorker();
		if (chapter.equals("03"))
			return new NeurophPerceptronSample();
		if (chapter.equals("04"))
			return new ClassifyQuadrant();
		if (chapter.equals("05"))
			return new NumberRecognition();
		if (chapter.equals("07"))
			return new StockHomework();
		if (chapter.equals("08"))
			return new PatternRecog();
		return null;
	}
}
