
public class HashObject<T> {

	private T object;
	private int frequency, probeDubInsert;
	private long key;
	private int dupCount = 1;
	

	public HashObject(T Object) {
		this.object = object;
		this.frequency = 0;
		key = GetHash(object);

	}

	public long GetHash(T Object) {
		long value = 0;
		if (Object instanceof String) {
			value = Math.abs(Object.hashCode());
		}
		if (Object instanceof Integer) {
			value = Math.abs(Object.hashCode());
		}
		if (Object instanceof Long) {
			value = Math.abs(Object.hashCode());
		}
		return value;

	}

	public long GetKey() {
		return key;
	}

	public void increaseFrequency() {
		this.frequency++;
	}

	public int GetFrequency() {
		return this.frequency;
	}

	public T GetObject() {
		return object;
	}

	public String toString() {
		return object.toString() + frequency;

	}
	
	public void increaseProbe() {
		this.probeDubInsert++;
	}
	
	public int getProbeCount() {
		return this.probeDubInsert;
	}
	
	public void duplicate() {
		this.dupCount++;
	}
	
	public int getDuplicate() {
		return this.dupCount;
	}

	public static boolean equals(Long key1, Long key2) {
		// TODO Auto-generated method stub
		if (key1.equals(key2)) {
			return true;
		} else {
			return false;
		}
	}

}
