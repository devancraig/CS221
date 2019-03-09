import java.util.Random;

/**
 * @author Devan Craig 
 * 	   This program uses Karatsuba algorithm along with Gauss's
 *         trick to multiply two numbers together.
 */
public class Karatsuba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int num = rand.nextInt(100);
		int num2 = rand.nextInt(60);

		long ans = Karatsuba(num, num2);
		System.out.println(ans);

		long ans2 = Karatsuba(17, 45);
		System.out.println(ans2);

		long ans3 = Karatsuba(55462, 8624);
		System.out.println(ans3);

	}

	/**
	 * @param one
	 * @param two
	 * @return Runs two long numbers through the Karatsuba algorithm and returns the
	 *         value of the two long numbers multiplied.
	 */
	public static long Karatsuba(long one, long two) {
		// x = one y = two
		long max = Math.max(one, two);

		if (max < 2) {
			return one * two;
		}

		max = (max / 2) + (max % 2);

		// x = a *10^n/2 + b y = c * 10^n/2 + d

		long b = one >> max;
		long a = one - (b << max);
		long d = two >> max;
		long c = two - (d << max);

		long ac = a * c;
		long bd = b * d;
		long abcd = (a + b) * (c + d);

		// have to use the 3 - 1 - 2 Gauss's trick
		return ac + (abcd - ac - bd) + bd;
	}

}
