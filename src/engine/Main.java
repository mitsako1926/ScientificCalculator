package engine;

import java.util.Random;

import gui.CalculatorFrame;
import gui.CalculatorScientificPanel;

public class Main {

	public static void main(String[] args) {
		new CalculatorFrame();
		
	
	}
}

//APLO TESTING
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



//SCIENTIFIC TESTING
//String[] scientificButtons = {  
//	    "π", "e","n!", "x^y","rand",
//	    "ln", "log", "10^x","(",")",
//	    "abs","sin", "cos", "tan", "cot",
//	    "deg","asin", "acos", "atan", "acot"
//	};
//
//	Random random = new Random();
//
//	int testRuns = 10_000;
//	int pressesPerRun = 100;
//	int crashes = 0;
//
//	for (int i = 1; i <= testRuns; i++) {
//
//	    CalculatorEngine engine = new CalculatorEngine();
//	    CalculatorScientificPanel panel = new CalculatorScientificPanel(engine);
//
//	    StringBuilder sequence = new StringBuilder();
//
//	    try {
//	        for (int j = 1; j <= pressesPerRun; j++) {
//
//	            String button = scientificButtons[random.nextInt(scientificButtons.length)];
//	            sequence.append("[SCI ").append(button).append("] ");
//
//	            panel.testPress(button);   // 🔥 ΤΩΡΑ ΠΕΡΝΑΕΙ ΑΠΟ press()
//
//	            if (engine.getDisplay() == null) {
//	                throw new IllegalStateException("Display is null");
//	            }
//	            if (engine.getHistoryUp() == null) {
//	                throw new IllegalStateException("HistoryUp is null");
//	            }
//	            if (engine.getHistoryDown() == null) {
//	                throw new IllegalStateException("HistoryDown is null");
//	            }
//	        }
//
//	    } catch (Exception e) {
//	        crashes++;
//
//	        System.out.println("======================================");
//	        System.out.println("CRASH FOUND");
//	        System.out.println("Test run: " + i);
//	        System.out.println("Sequence: " + sequence);
//	        System.out.println("Display: " + engine.getDisplay());
//	        System.out.println("HistoryUp: " + engine.getHistoryUp());
//	        System.out.println("HistoryDown: " + engine.getHistoryDown());
//	        System.out.println("======================================");
//
//	        e.printStackTrace();
//	        break;
//	    }
//
//	    if (i % 500 == 0) {
//	        System.out.println("Completed tests: " + i);
//	    }
//	}
//
//	if (crashes == 0) {
//	    System.out.println("Finished without crashes.");
//	}
//	
//	
//	Sto calculator scientific panel:
//		public void testPress(String buttonPressed) {
//	    press(buttonPressed);
//	}