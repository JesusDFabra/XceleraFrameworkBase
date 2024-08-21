package framework.helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import framework.data.entities.ActionData;
import framework.data.entities.ExecutionData;
import framework.data.entities.Prints;
import framework.enums.EnumPrintStatus;
import framework.testtools.ITestToolFunctions;

public class ScreenshotHelper {

	private ITestToolFunctions _testToolFunctions;
	private ExecutionData _testToExecute;
	private ActionData _actionData;
	private Prints _prints;

	private boolean ConvertedImage = true;
	private boolean PrintSaved;

	public ScreenshotHelper(ITestToolFunctions testToolFunctions, ExecutionData testToExecute, ActionData actionData,
			Prints prints) {
		_testToolFunctions = testToolFunctions;
		_testToExecute = testToExecute;
		_actionData = actionData;
		_prints = prints;
	}

	private boolean isConvertedImage() {
		return ConvertedImage;
	}

	private void setConvertedImage(boolean convertImage) {
		ConvertedImage = convertImage;
	}

	private boolean isPrintSaved() {
		return PrintSaved;
	}

	private void setPrintSaved(boolean printSaved) {
		PrintSaved = printSaved;
	}

	public void takeScreenshot() throws Exception {
		waitScreenshotThreadToFinish();
		setConvertedImage(false);
		String printPath = GeneralHelper.getPrintPath(_testToExecute);

		BufferedImage screenshot = _testToolFunctions.takeScreenshot(printPath);

		new Thread() {
			public void run() {
				try {
					BufferedImage result = new BufferedImage(screenshot.getWidth(), screenshot.getHeight(),
							BufferedImage.TYPE_INT_RGB);
					result.createGraphics().drawImage(screenshot, 0, 0, Color.WHITE, null);// passo necessário, pois
																							// quando altera a
																							// compressão o fundo fica
																							// pink este passo resolve
					ImageIO.write(result, "JPG", new File(printPath));// alterado a compressao para JPG afim de diminuir
																		// o tamanho da imagem
					setPrintSaved(true);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

		setValueToPrintAction(screenshot);
	}

	private void setValueToPrintAction(BufferedImage image) throws Exception {
		new Thread() {
			public void run() {
				try {
					_prints.Print.add(GeneralHelper.convertImageInByte(image));
					_actionData.PrintStatus = EnumPrintStatus.HasPrint;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					setConvertedImage(true);
				}
			}
		}.start();
	}

	public void waitScreenshotThreadToFinish() throws Exception {
		int loops = 0;
		while (!isConvertedImage()) {
			Thread.sleep(250);
			if (loops == 4)
				System.out.print("Take screenshot: ...");
			if (loops > 4)
				System.out.print(".");
			if(loops >50)
				break;
			loops++;
		}
		if (loops > 4)
			System.out.println();
	}
}
