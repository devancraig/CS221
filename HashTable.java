import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HashTable<T> {
	private int probeCheck;
	private int probe1, probe2, numItemsLinear, numItemsDouble, hashRequest = 0;
	private int dup1, dup2 = 0;
	private int size, debugLevel;
	private double load;
	private HashObject<T> table[];
	static HashTest primeG = new HashTest();

	HashTable(double load, int debugLevel) {
		load = this.load;
		debugLevel = this.debugLevel;
		size = primeG.getPrimes(95500, 96000);
		table = new HashObject[size];

	}

	public void linearInsert(HashObject<T> object) {
		long i = 0;
		long key = object.GetKey();
		long index = linearHashing(key);
		System.out.println(this.size);
		while (index < this.size) {
			int j = (int) linearHashing(index + i);
			if (table[j] == null) {
				table[j] = object;
			} else {
				i++;
				index %= this.size;
			}
		}

	}

	public double getLoad() {
		return (double) numItemsLinear / size;
	}

	public long linearSearch(HashObject<T> T) {
		long i = 0;
		long key = T.GetKey();
		int index = (int) ((linearHashing(key) + i) % this.size);
		hashRequest++;
		int tprobe = probe1;
		probe1++;
		while (i < this.size) {
			i++;
			 if (table[index] != null && table[index].equals(T)) {
				table[index].increaseFrequency();
				table[index].increaseProbe();
				table[index].duplicate();
				hashRequest--;
				probe1 = tprobe;
				dup1++;
				return index;
			} else if (table[index] == null) {
				table[index] = T;
				numItemsLinear++;
				return index;
			}
			probe1++;
			index = (int) ((linearHashing(key) + i) % this.size);
		}

		return index;

	}

	public double avgProbelinear() {
		return (double) probe1/numItemsLinear;
	}

	public double avgProbeDouble() {
		return (double) probe2/numItemsDouble;
	}

	public void doubleHash(HashObject<T> T) {
		long key = T.GetKey();
		long step = 1 + (key % (size - 2));
		long index = key % size;

		while (table[(int) index] != null) {
			if (table[(int) index] == T) {
				dup2++;
				return;
			} else {
				probe2++;
				index += step;
				index %= size;
			}
		}
		if (table[(int) index] == null) {
			table[(int) index] = T;
			numItemsDouble++;
		}
	}
	
	public int getHashRequest() {
		return this.hashRequest;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int numProbe1() {
		return this.probe1;
	}
	
	public int numProbe2() {
		return this.probe2;
	}
	
	public int getDup1() {
		return this.dup1;
	}
	
	public int getDup2() {
		return this.dup2;
	}
	
	public int getNumItemsDouble() {
		return this.numItemsDouble;
	}
	
	public int getNumItemsLinear() {
		return this.numItemsLinear;
	}
//	private int hashIndex(Long key, int i) {
//		if (probeCheck == linearHashing(key)) {
//			return ((int) ((key % size) + i) % size);
//		} else {
//			return ((int) (((key % size) + i * (1 + (key % (size - 2)))) % size));
//		}
//
//	}

	public long linearHashing(Long k) {
		return (k % this.size);
	}

	public long doubleHashing(Long k) {
		return 1 + (k % size / 2);
	}

	public void viewTable() throws IOException {
		FileWriter fW = new FileWriter("linear-dump");
		PrintWriter pW = new PrintWriter(fW);
		for (int i = 0; i < table.length; i++) {
			HashObject<?> val = table[i];
			if (table[i] != null) {
				pW.println("table[" + i + "] =" + table[i].toString() + " "
						+ val.getProbeCount() + " " + (table[i].getDuplicate() - (table[i].getDuplicate() - 1)));
			}
		}
			pW.close();
	}

}
