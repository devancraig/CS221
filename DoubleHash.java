import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DoubleHash <T> {
	private int dup, numItems = 0;
	private int debugLevel;
	public double load;
	private int hash, probe, size;
	private HashTest primeG = new HashTest();
	private HashObject<T> dub[];
	
	DoubleHash(double load, int debugLevel) {
		load = this.load;
		debugLevel = this.debugLevel;
		size = primeG.getPrimes(95500, 96000);
		dub = new HashObject[size];

	}
	
	public void DoubleSearch(HashObject<T> T) {
		long i = 0;
		long key = T.GetKey();
		int step = (int) (1 +(key %(size -2)));
		int index = (int) (key % size);
		hash++;
		int tprobe = probe;
		probe++;
		while (i < size) {
			i++;
			if (dub[index] == null) {
				dub[index] = T;
				numItems++;
				return;
			} else if (dub[index] != null && dub[index].equals(T)) {
				dub[index].increaseFrequency();
				dub[index].increaseProbe();
				dub[index].duplicate();
				hash--;
				probe = tprobe;
				dup++;
				return;
			}
			probe++;
			index = (int) (index + step) % size;
		}

		return;

	}
	
	public int getHash() {
		return this.hash;
	}
	
	public int getItems() {
		return this.numItems;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getDup() {
		return this.dup;
	}
	
	public double getLoad() {
		return (double) probe/numItems;
	}
	
	public int getProbe() {
		return this.probe;
	}
	
	public double getAverage() {
		return (double) probe/numItems;
	}
	
	public long doubleHashing(Long k) {
		return 1 + (k % size / 2);
	}
	
	public void viewTable() throws IOException {
		FileWriter fW = new FileWriter("double-dump");
		PrintWriter pW = new PrintWriter(fW);
		for (int i = 0; i < dub.length; i++) {
			HashObject<?> val = dub[i];
			if (dub[i] != null) {
				pW.println("table[" + i + "] =" + dub[i].toString() + " "
						+ val.getProbeCount() + " " + (dub[i].getDuplicate() - (dub[i].getDuplicate() - 1)));
			}
		}
			pW.close();
	}
}
