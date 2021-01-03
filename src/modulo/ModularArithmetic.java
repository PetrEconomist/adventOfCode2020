package modulo;


public class ModularArithmetic {

	public static void main(String[] args) {
		getMultiplicativeInverseTest();
		ChineseRemainderTheoremTest();
	}
	
	private static void getMultiplicativeInverseTest() {
		int x, mod;
		x = 241133;
		mod = 7;
		System.out.printf("multiplicative inverse for %d in mod %d is %d\n", x, mod, getMultiplicativeInverse(x, mod));
		x = 1;
		mod = 7;
		System.out.printf("multiplicative inverse for %d in mod %d is  %d\n", x, mod, getMultiplicativeInverse(x, mod));
		x = 5;
		mod = 12;
		System.out.printf("multiplicative inverse for %d in mod %d is  %d\n", x, mod, getMultiplicativeInverse(x, mod));
		x = 163;
		mod = 527;
		System.out.printf("multiplicative inverse for %d in mod %d is  %d\n", x, mod, getMultiplicativeInverse(x, mod));
		x = 2;
		mod = 6;
		System.out.printf("multiplicative inverse for %d in mod %d is  %d\n", x, mod, getMultiplicativeInverse(x, mod));
	}

	/**
	 * Calculate multiplicative inverse, that is number a in the formula below
	 * ax=1 mod(n), 
	 * https://en.wikipedia.org/wiki/Modular_multiplicative_inverse
	 * @param x number of which inverse is calculated
	 * @param mod modus in which we calculate inverse
	 * @return multiplicative inverse, if does not exists, returns 0;
	 */
	public static long getMultiplicativeInverse(long x, long mod) {
		x = x % mod;
		long solution =  getMultiplicativeInverse(x, mod, 1, 0);
		return getBasicNumber(solution, mod);
	}
	
	//https://isibalo.com/matematika/modularni-aritmetika/vypocet-inverzniho-prvku
	//https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
	private static long getMultiplicativeInverse(long remainderIMinsuOne, long remainderIMinsuTwo, long tIMinusOne, long tIMinusTwo) {
		if(remainderIMinsuOne==0) return 0;
		if(remainderIMinsuOne==1) return 1;
		long remainderI = remainderIMinsuTwo % remainderIMinsuOne;
		long tI = tIMinusTwo - (tIMinusOne * (remainderIMinsuTwo / remainderIMinsuOne));
		if (remainderI > 1) {
			return getMultiplicativeInverse(remainderI, remainderIMinsuOne, tI, tIMinusOne);
		}else if (remainderI == 1){
			return tI;
		}else{
			return 0;
		}
	}
	
	/**
	 * Gets basic number in given modulo
	 * Examples:
	 * -2 im mod 6 = 4
	 * 11 in mod 6 = 5
	 * 17 in mod 6 = 5
	 * @param x number to be converted in basic number
	 * @param mod modulo in which we calculate
	 * @return basic number (range zero to modulo minus one)
	 */
	public static long getBasicNumber(long x, long mod) {
		if(x<0) return (x - ((x / mod - 1) * mod));
		return (x - ((x / mod) * mod));
	}
	

	/**
	 *	https://en.wikipedia.org/wiki/Chinese_remainder_theorem
	 *	Return first solution of equations given by argument
	 *	@param equations values from equations, inner array is in format {value, modulo} 
	 *	http://voho.eu/wiki/cinska-veta-o-zbytcich/
	 */
	public static long ChineseRemainderTheorem(int equations[][]){
		int valueIndex = 0;
		int moduloIndex = 1;
		long mod = 1;
		for(int i=0;i<equations.length;i++) {
			mod *= equations[i][moduloIndex];
		}
		long solution = 0;
		for(int i=0;i<equations.length;i++) {
			long valueI = equations[i][valueIndex];
			if(valueI==0) continue;
			long moduloI = equations[i][moduloIndex];
			long sI = mod / moduloI;
			long tI = getMultiplicativeInverse(sI, moduloI);
			long qI = sI * tI;
			System.out.printf("Chinese remainder theorem val%d: %d\n", i, valueI);
			System.out.printf("Chinese remainder theorem mod%d: %d\n", i, moduloI);
			System.out.printf("Chinese remainder theorem s%d: %d\n", i, sI);
			System.out.printf("Chinese remainder theorem t%d: %d\n", i, tI);
			System.out.printf("Chinese remainder theorem q%d: %d\n", i, qI);
			solution += valueI * qI;
		}
		System.out.printf("ChineseRemainderTheorem Solution  %s in modulo %d\n",  getBasicNumber(solution, mod), mod);
		return getBasicNumber(solution, mod);
	}
	
	private static void ChineseRemainderTheoremTest(){
		int[][] arr = {{1,2}, {2,3}, {4,5}, {0,7}};
		System.out.printf("For input %s result %d\n",  arr.toString(), ChineseRemainderTheorem(arr));
		int[][] arr2 = {{2,3}, {1,8}, {7,13}};
		System.out.printf("For input %s result %d\n",  arr2.toString(), ChineseRemainderTheorem(arr2));
	}
}
