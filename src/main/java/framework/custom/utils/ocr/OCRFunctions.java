package framework.custom.utils.ocr;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import info.debatty.java.stringsimilarity.JaroWinkler;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;



public class OCRFunctions {
	
	public final int CHAR_ALFABETICO = 0;
	public final int CHAR_NUMERICO = 1;
	public final int CHAR_ALFANUMERICO =2;
	
	public String getTextFromPrintscreen(int x, int y, int width, int height, int TYPE_CHAR) throws Exception {
		
		try {
			String charWhiteList="";
			
			if(TYPE_CHAR==this.CHAR_ALFABETICO) {
				charWhiteList = "aábcdeéfghiíjklmnñoópqrstuúvwxyzAÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ .,";
			}
			else if(TYPE_CHAR==this.CHAR_NUMERICO) {
				charWhiteList = "0123456789,.";
			}
			else {
				charWhiteList = "aábcdeéfghiíjklmnñoópqrstuúvwxyzAÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ0123456789 .,";
			}
			
			Robot robot = new Robot();
			Graphics2D g2d;
			
			Rectangle pantalla = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage captura = robot.createScreenCapture(pantalla);
			
//			ImageIO.write(captura, "jpg", new File("C:/poc/captura.jpg"));
			
			BufferedImage capturaRecortada = new BufferedImage(width, height, captura.getType());
			
			g2d = capturaRecortada.createGraphics();
			g2d.drawImage(captura, 0, 0, width, height, x, y, x + width, y + height, null);
			g2d.dispose();
			
//			ImageIO.write(capturaRecortada, "jpg", new File("C:/poc/capturaRecortada.jpg"));
			
			ITesseract tess = new Tesseract();
			System.setProperty("TESSERACT_DEBUG_LEVEL", "0");
				
			try {
				tess.setTessVariable("tessedit_char_whitelist", charWhiteList);
//				tess.setTessVariable("tessedit_write_images", "false");
				tess.setTessVariable("debug_file", "NUL");
				tess.setDatapath("./tessdata");
				tess.setLanguage("spa");
				String imageText = tess.doOCR(capturaRecortada);
				return imageText;
			}
			catch(Exception ex) {
				throw ex;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean compararTextos(String texto1, String texto2, double accuracyPercent) {
		
		try {
			
			accuracyPercent = accuracyPercent/100;
			
			String[] objetCleanText = texto1.split("\n");
	        String[] textWished = texto2.split("\n");
	        ArrayList<Double> resultSim = new ArrayList<>();
	            for (String s : textWished) {
	                for (String line : objetCleanText) {
	                    String cleanText = line.replaceAll("[^A-Za-z0-9 .áéíóúÁÉÍÓÚ]", "");
	                    double result = JaroWinkler.Similarity(cleanText, s);
	                    if (result >= accuracyPercent) {
	                        System.out.println("obtenido: " + cleanText);
	                        System.out.println("deseado: " + s);
	                        System.out.println("simulitud:" + result + "\n");
	                        resultSim.add(result);
	                        break;
	                    }else{
//	                        System.out.println("NOT FOUND");
	                    }
	                }
	            }
	            double sum = resultSim.stream().mapToDouble(a -> a).sum();
	            double avg = sum/textWished.length;
	            System.out.println("El promedio obtenido es: " + avg);
	            
	            if(avg>= accuracyPercent) {
	            	return true;
	            }
	            else {
	            	return false;
	            }
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
public void clickInText(String textoBuscado, int orden) throws Exception {
		
		Robot robot = new Robot();
		
		Rectangle pantalla = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage captura = robot.createScreenCapture(pantalla);
		
		ITesseract tess = new Tesseract();
		System.setProperty("TESSERACT_DEBUG_LEVEL", "0");
		
		try {

			tess.setTessVariable("debug_file", "NUL");
			tess.setDatapath("./tessdata");
			tess.setLanguage("spa");
			
			Thread.sleep(1000);
			// Reconocer el texto en la imagen
            List<Word> words = tess.getWords(captura, ITessAPI.TessPageIteratorLevel.RIL_WORD);
            
            // Buscar el texto específico y obtener su posición
            int x = -1;
            int y = -1;
            int contador = 1;
            
            for (Word word : words) {
                String texto = word.getText();
                if (texto.contains(textoBuscado)) {
                	if(contador == orden) {
                		Rectangle rect = word.getBoundingBox();
                        x = (rect.x + rect.width/2);
                        y = (rect.y + rect.height/2);
                        System.out.println("El texto '" + textoBuscado + "' se encuentra en las coordenadas: (" + x + ", " + y + ")");
                        
                        robot.mouseMove(x, y);
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        
                        break; // Terminar el bucle una vez que se haya encontrado el texto
                	}
                    contador++;
                }
                
            }
            if (x == -1 && y == -1) {
                throw new Exception("El texto "+ textoBuscado +" no se encontró en la imagen.");
            }
		}
		
		catch(Exception ex) {
			throw ex;
		}
	}

}
