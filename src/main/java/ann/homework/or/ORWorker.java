package ann.homework.or;

public class ORWorker extends CustomNNWorker {

	public ORWorker() {
	}

	public double[] run(double[] p) {
		double sum = 0f;
		double[] a = new double[p.length];
		double out;
		for (int i = 0; i < p.length; i++) {
			a[i] = w[i] * p[i] + b;
			sum += a[i];
		}
		out = t(sum);
		return new double[] { out };
	}

	public void train(double p[], double[] exp) {
		double e = exp[0] - run(p)[0];
		for (int i = 0; i < p.length; i++) {
			w[i] = w[i] + e * p[i];
			b += e;
		}
	}

	public float t(double value) {
		return value > 0 ? 1f : 0f;
	}
}
