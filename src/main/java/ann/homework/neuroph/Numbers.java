package ann.homework.neuroph;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class Numbers {

	public static DataSet getTrainData() {
		DataSet ds = new DataSet(35, 1);
		for (int i = 0; i < DIGITS.length; i++) {
			DataSetRow row = new DataSetRow(toDouble(DIGITS[i]),
					new double[] { i });
			ds.addRow(row);
		}
		return ds;
	}

	public static double[] getTrainDataRow(int i) {
		return toDouble(DIGITS[i]);
	}

	private static double[] toDouble(String str) {
		// TODO Auto-generated method stub
		double[] result = new double[35];
		for (int i = 0; i < result.length; i++) {
			result[i] = str.charAt(i) == '0' ? 1 : -1;
		}
		return result;
	}

	public final static String[] DIGITS = {//
	" 000 " + //
			"0   0" + //
			"0   0" + //
			"0   0" + //
			"0   0" + //
			"0   0" + //
			" 000 ",

	"  0  " + //
			" 00  " + //
			"0 0  " + //
			"  0  " + //
			"  0  " + //
			"  0  " + //
			"  0  ",

	" 000 " + //
			"0   0" + //
			"    0" + //
			"   0 " + //
			"  0  " + //
			" 0   " + //
			"00000",

	" 000 " + //
			"0   0" + //
			"    0" + //
			" 000 " + //
			"    0" + //
			"0   0" + //
			" 000 ",

	"   0 " + //
			"  00 " + //
			" 0 0 " + //
			"0  0 " + //
			"00000" + //
			"   0 " + //
			"   0 ",

	"00000" + //
			"0    " + //
			"0    " + //
			"0000 " + //
			"    0" + //
			"0   0" + //
			" 000 ",

	" 000 " + //
			"0   0" + //
			"0    " + //
			"0000 " + //
			"0   0" + //
			"0   0" + //
			" 000 ",

	"00000" + //
			"    0" + //
			"    0" + //
			"   0 " + //
			"  0  " + //
			" 0   " + //
			"0    ",

	" 000 " + //
			"0   0" + //
			"0   0" + //
			" 000 " + //
			"0   0" + //
			"0   0" + //
			" 000 ",

	" 000 " + //
			"0   0" + //
			"0   0" + //
			" 0000" + //
			"    0" + //
			"0   0" + //
			" 000 " };

	public static void print(int i) {
		String str = DIGITS[i];
		for (int j = 0; j < 7; j++) {
			System.out.println(str.substring(j * 5, (j + 1) * 5));
		}
	}
}