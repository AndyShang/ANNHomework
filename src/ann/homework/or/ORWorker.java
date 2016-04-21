package ann.homework.or;

import ann.homework.Worker;

public class ORWorker extends Worker {

	public float run(float[] p) {
		float sum = 0f;
		float[] a = new float[p.length];
		float out;
		for (int i = 0; i < p.length; i++) {
			a[i] = w[i] * p[i] + b;
			sum += a[i];
		}
		out = t(sum);
		return out;
	}

	public void train(float p[], float exp) {
		float e = exp - run(p);
		for (int i = 0; i < p.length; i++) {
			w[i] = w[i] + e * p[i];
			b += e;
		}
	}

	public float t(float value) {
		return value > 0 ? 1f : 0f;
	}
}
