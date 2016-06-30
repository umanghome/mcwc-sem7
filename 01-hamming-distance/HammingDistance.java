public class HammingDistance {
	public static void main (String args[]) {

		int minArgs = 3;

		if (args.length != minArgs) {
			System.out.println("Usage: java HammingDistance <number 1> <number 2> <number 3>");
			return;
		}

		// Get binary values
		int maxLength = -1;
		String[] binary = new String[minArgs];
		for (int i = 0; i < minArgs; i++) {
			Integer temp = Integer.parseInt(args[i]);
			binary[i] = Integer.toBinaryString(temp.intValue());

			if (binary[i].length() > maxLength) maxLength = binary[i].length();
		}

		// Add padding
		for (int i = 0; i < minArgs; i++) {
			int diff = maxLength - binary[i].length();
			if (diff > 0) {
				for (int j = 0; j < diff; j++) {
					binary[i] = "0" + binary[i];
				}
			}

			System.out.println(binary[i]);
		}

		// Get minimum hamming distance
		int minDistance = maxLength + 1;
		int firstIndex = -1;
		int secondIndex = -1;
		for (int i = 0; i < minArgs; i++) {
			for (int j = i + 1; j < minArgs; j++) {
				int thisDistance = 0;
				for (int k = 0; k < maxLength; k++) {
					if (binary[i].charAt(k) != binary[j].charAt(k)) thisDistance++;
				}

				if (thisDistance < minDistance) {
					minDistance = thisDistance;
					firstIndex = i;
					secondIndex = j;
				}

			}
		}

		System.out.print("Minimum hamming distance is: " + minDistance);
		System.out.println(", and it is between " + args[firstIndex] + " and " + args[secondIndex] + ".");

	}
}