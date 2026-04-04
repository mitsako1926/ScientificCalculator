package engine;

import gui.CalculatorFrame;

public class Main {

	public static void main(String[] args) {
		new CalculatorFrame();
		
	}

}


//String[] buttons = {
//        "0","1","2","3","4","5","6","7","8","9",
//        "+","-","×","÷","%","=","C","del","±",".","√","x²","1/"
//};
//
//Random random = new Random();
//
//int testRuns = 10_000;          // πόσα διαφορετικά sessions
//int pressesPerRun = 100;        // πόσα πατήματα σε κάθε session
//int crashes = 0;
//
//for (int i = 1; i <= testRuns; i++) {
//    CalculatorEngine engine = new CalculatorEngine();
//    StringBuilder sequence = new StringBuilder();
//
//    try {
//        for (int j = 1; j <= pressesPerRun; j++) {
//            String button = buttons[random.nextInt(buttons.length)];
//            sequence.append(button).append(" ");
//
//            engine.press(button);
//
//            if (engine.getDisplay() == null) {
//                throw new IllegalStateException("Display is null");
//            }
//            if (engine.getHistoryUp() == null) {
//                throw new IllegalStateException("HistoryUp is null");
//            }
//            if (engine.getHistoryDown() == null) {
//                throw new IllegalStateException("HistoryDown is null");
//            }
//        }
//    } catch (Exception e) {
//        crashes++;
//
//        System.out.println("======================================");
//        System.out.println("CRASH FOUND");
//        System.out.println("Test run: " + i);
//        System.out.println("Sequence: " + sequence);
//        System.out.println("Display: " + engine.getDisplay());
//        System.out.println("HistoryUp: " + engine.getHistoryUp());
//        System.out.println("HistoryDown: " + engine.getHistoryDown());
//        System.out.println("======================================");
//        e.printStackTrace();
//
//        break; // σταματά στο πρώτο crash
//    }
//
//    if (i % 500 == 0) {
//        System.out.println("Completed tests: " + i);
//    }
//}
//
//if (crashes == 0) {
//    System.out.println("Finished without crashes.");
//}