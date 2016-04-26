package ann.homework;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ann.homework.neuroph.NeurophPerceptronSample;
import ann.homework.or.ORWorker;

public class Homework {

	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("c", "chapter", true, "The chapter");
		options.addOption("t", "train", false,
				"the input file is training data");
		options.addOption("o", "output", true, "the output file path");
		options.addOption("i", "input", true, "the input file path");
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
		String output = null;
		String chapter = null;
		Worker worker = null;

		if (line.hasOption('h')) {
			help();
			return;
		}

		if (line.hasOption('c'))
			chapter = line.getOptionValue('c');
		if (line.hasOption('i'))
			input = line.getOptionValue('i');
		if (line.hasOption('o'))
			output = line.getOptionValue('o');

		if (chapter == null) {
			help();
			return;
		}
		worker = getWorker(chapter);
		try {
			worker.setInput(input);
			worker.setOutput(output);
		} catch (IOException e) {
			help();
			return;
		}
		worker.load();
		if (line.hasOption('t')) {
			worker.train();
			worker.save();
		} else
			worker.run();
	}

	private Worker getWorker(String chapter) {
		if (chapter.equals("01"))
			return new ORWorker();
		if(chapter.equals("03"))
			return new NeurophPerceptronSample();
		return null;
	}
}
