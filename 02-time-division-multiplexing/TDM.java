import java.util.Scanner;

public class TDM {
	public static void main (String args[]) {
		int numOfChannels = args.length;

		// Create scanner
		Scanner scanner = new Scanner(System.in);

		// For waiting
		String wait;

		if (numOfChannels < 2) {
			System.out.println("Please enter at least two channels.");
			return;
		}

		int totalLength = 0;

		// Show data
		for (int i = 0; i < numOfChannels; i++) {
			System.out.println("Data of Channel " + (i + 1) + ": " + args[i]);
			totalLength += args[i].length();
		}

		System.out.println("totalLength: " + totalLength);

		System.out.println("--- SYNCHRONOUS ---\n");

		// Count number of frames
		int maxLength = -1;
		for (int i = 0; i < numOfChannels; i++) {
			if (args[i].length() > maxLength) maxLength = args[i].length();
		}
		System.out.println("We will be using " + maxLength + " frames.");

		// Build frames
		char[] frame = new char[numOfChannels];

		// Data that will be received
		String[] received = new String[numOfChannels];
		for (int i = 0; i < numOfChannels; i++) {
			received[i] = "";
		}

		// Send and recieve frames
		for (int iterator = 0; iterator < maxLength; iterator++) {

			for (int i = 0; i < numOfChannels; i++) {
				
				if (args[i].length() > iterator) {
					frame[i] = args[i].charAt(iterator);
				} else {
					frame[i] = '\0';
				}

			}

			// Reverse the frame
			char[] tmp = new char[frame.length];
			for (int i = 0, j = (frame.length - 1); i < frame.length; i++, j--) {
				tmp[j] = frame[i];
			}
			for (int i = 0; i < frame.length; i++) {
				frame[i] = tmp[i];
			}

			System.out.print("Frame " + (iterator + 1) + ": ");
			for (int i = 0; i < frame.length; i++) {
				System.out.print(frame[i] + "\t");
			}

			// WAIT
			wait = scanner.nextLine();

			// Reverse the frame
			tmp = new char[frame.length];
			for (int i = 0, j = (frame.length - 1); i < frame.length; i++, j--) {
				tmp[j] = frame[i];
			}
			for (int i = 0; i < frame.length; i++) {
				frame[i] = tmp[i];
			}

			for (int i = 0; i < frame.length; i++) {
				received[i] = received[i] + frame[i];
			}		

			// Show data
			for (int i = 0; i < numOfChannels; i++) {
				System.out.println("Received Data of Channel " + (i + 1) + ": " + received[i]);
			}
			System.out.print("\n");

		}

		System.out.println("\n--- ASYNCHRONOUS ---\n");
		System.out.print("Number of slots: ");
		int numberOfSlots = Integer.parseInt(scanner.nextLine());

		int numOfFrames = (int) Math.ceil((double) totalLength / (double) numberOfSlots);

		System.out.println("We will be using " + numOfFrames + " frames.");

		// Data that will be received
		received = new String[numOfChannels];
		for (int i = 0; i < numOfChannels; i++) {
			received[i] = "";
		}

		// Send and recieve frames
		int stringSelector = 0;
		for (int iterator = 0; iterator < numOfFrames; iterator++) {

			// Build frames
			frame = new char[numberOfSlots];
			// Build metadata
			int currentFrameElemCounter = 0;
			int[] channelMetaData = new int[numberOfSlots];
			for (int i = 0; i < numberOfSlots; i++) {
				channelMetaData[i] = -1;
			}

			do {
				int current = (stringSelector++) % args.length;

				// If all strings are blank, break
				boolean allBlank = true;
				for (int i = 0; i < args.length; i++) {
					if (args[i].length() > 0) {
						allBlank = false;
						break;
					}
				}
				if (allBlank) break;

				if (args[current].length() > 0) {
					channelMetaData[currentFrameElemCounter] = current;
					frame[currentFrameElemCounter++] = args[current].charAt(0);
					if (args[current].length() > 1)
						args[current] = args[current].substring(1);
					else
						args[current] = "";
				}
			} while (currentFrameElemCounter < numberOfSlots);


			// Reverse the frame
			char[] tmp = new char[frame.length];
			for (int i = 0, j = (frame.length - 1); i < frame.length; i++, j--) {
				tmp[j] = frame[i];
			}
			for (int i = 0; i < frame.length; i++) {
				frame[i] = tmp[i];
			}

			System.out.print("Frame " + (iterator + 1) + ": ");
			for (int i = 0; i < frame.length; i++) {
				System.out.print(frame[i] + "\t");
			}

			// WAIT
			wait = scanner.nextLine();

			// Reverse the frame
			tmp = new char[frame.length];
			for (int i = 0, j = (frame.length - 1); i < frame.length; i++, j--) {
				tmp[j] = frame[i];
			}
			for (int i = 0; i < frame.length; i++) {
				frame[i] = tmp[i];
			}

			for (int i = 0; i < channelMetaData.length; i++) {
				if (channelMetaData[i] < 0) continue;
				received[channelMetaData[i]] += frame[i];
			}		

			// Show data
			for (int i = 0; i < numOfChannels; i++) {
				System.out.println("Received Data of Channel " + (i + 1) + ": " + received[i]);
			}
			System.out.println("\n");
		}

	}
}