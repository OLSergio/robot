package com.bbva.mx.controlles;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bbva.mx.pojos.Commands;
import com.bbva.mx.pojos.Testeo;
import com.bbva.mx.pojos.Tests;
import com.google.gson.Gson;

@Controller
public class ControllerInicio {
	String value = "";

	@GetMapping("/")
	public String inicio() {
		return "index";
	}

	@PostMapping("/subir")
	public String subirFile(@RequestParam("scriptr") MultipartFile multipart, HttpServletRequest request) {
		if (!multipart.isEmpty()) {
			System.out.println("entre");
			String nombreFile = subeFile(multipart, request);
			System.out.println(nombreFile);
		}

		return "subio";
	}

	@PostMapping("/scripts")
	public String scripts(HttpServletRequest request, Model model) {

		String rutaScripts = request.getServletContext().getRealPath("/resources/scripsr/");
		File f = new File(rutaScripts);

		File[] lf = f.listFiles();
		List<String> listaScripts = new ArrayList<String>();
		for (File file : lf) {

			// System.out.println();
			listaScripts.add(file.getName());
		}
		model.addAttribute("listFile", listaScripts);
		return "scripts";
	}

	@PostMapping("/ejecuta")
	public String executa(@RequestParam("file") String file, HttpServletRequest request, Model model) {

		// variables
		Long fin;
		Long inicio;
		List<String[]> durationProcess = new ArrayList<String[]>();
		boolean error = false;

		System.setProperty("webdriver.gecko.driver", "C:/geckodriver.exe");
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,
				"C:\\Users\\xmy6588\\Documents\\logsselenium.txt");
		value = "iniciando Ejecucion";
		WebDriver driver = null;
		Gson gson = null;
		String filePaht = request.getServletContext().getRealPath("/resources/scripsr/") + file;

		try {
			//esperarSegundos(driver, 15);
			driver = new FirefoxDriver();
			//esperarSegundos(driver, 15);
			gson = new Gson();
			Reader reader = new FileReader(filePaht);
			Testeo tests = gson.fromJson(reader, Testeo.class);
			driver.get(tests.getUrl());

			Iterator<Tests> it = tests.getTests().iterator();
			while (it.hasNext()) {
				esperarSegundos(driver, 30);
				inicio = System.currentTimeMillis();
				driver.switchTo().defaultContent();
				Tests t = (Tests) it.next();
				value = t.getName();
				Iterator<Commands> itCom = t.getCommands().iterator();
				
				while (itCom.hasNext()) {
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.MILLISECONDS);
					driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					Commands com = (Commands) itCom.next();
					System.out.println(">>>COMANDO>>>> " + com.getCommand());

					switch (com.getCommand()) {
					case "open":
						System.out.println("URL:" + tests.getUrl() + com.getTarget());
						driver.navigate().to(tests.getUrl() + com.getTarget());
						break;

					case "clickAt":
						// esperarSegundos(driver,Integer.parseInt(com.getComment())+1);
						WebDriverWait wait = (new WebDriverWait(driver, 30));
						WebElement element = null;
						if (com.getTarget().contains("name")) {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.name(com.getTarget().replace(com.getTarget().split("=")[0] + "=", ""))));
						} else if (com.getTarget().contains("css")) {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.cssSelector(com.getTarget().replace(com.getTarget().split("=")[0] + "=", ""))));
						} else if (com.getTarget().contains("id")) {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.id(com.getTarget().replace(com.getTarget().split("=")[0] + "=", ""))));
						} else if (com.getTarget().contains("xpath")) {
							element = wait.until(ExpectedConditions.elementToBeClickable(
									By.xpath(com.getTarget().replace(com.getTarget().split("=")[0] + "=", ""))));
						}
						element.click();
						break;

					case "type":
						// WebElement element =
						// wait.until(ExpectedConditions.elementToBeClickable(By.name(com.getTarget().split("=")[1])));
						driver.findElement(By.name(com.getTarget().split("=")[1])).sendKeys(com.getValue());
						break;

					case "selectWindow":

						for (String winHandle : driver.getWindowHandles()) {
							driver.switchTo().window(winHandle);
						}
						break;

					case "selectFrame":
						if (com.getTarget().contains("index")) {
							driver.switchTo().frame(Integer.parseInt(com.getTarget().split("=")[1]));

						} else if (com.getTarget().contains("name")) {

							driver.switchTo().frame(driver.findElement(By.name(com.getTarget().split("=")[1])));

						}

						break;
					case "clickAtHover":
						WebDriverWait waitJs = (new WebDriverWait(driver, 30));
						//WebElement hoverElement = driver.findElement(By.id("busquedaMini0"));
						WebElement hoverElement = driver.findElement(By.xpath(com.getParent().replace(com.getParent().split("=")[0]+"=","")));
						Actions builder = new Actions(driver);
						builder.moveToElement(hoverElement).perform();
						WebElement clickElement = waitJs.until(ExpectedConditions.visibilityOfElementLocated(
								By.cssSelector(com.getTarget().replace(com.getTarget().split("=")[0] + "=", ""))));
						builder.moveToElement(clickElement).click().perform();
						break;
					}
				}
				fin = System.currentTimeMillis();
				long duration = fin - inicio;
				String processduration[] = new String[2];
				processduration[0] = t.getName();
				processduration[1] = String.valueOf(duration);
				durationProcess.add(processduration);
				model.addAttribute("listDur", durationProcess);
			}
			System.out.println("*******************************************");
			System.out.println("************CIRCUITO TERMINADO*************");
			System.out.println("*******************************************");

		} catch (Exception e) {
			e.printStackTrace();
			error = true;
			model.addAttribute("errortxt", e.getCause());
		} finally {

			for (String[] durations : durationProcess) {
				System.out.println("test:" + durations[0] + " ---duracion:" + durations[1]);

			}
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				driver.close();
			}
		}
		model.addAttribute("error", error);
		return "processduration";
	}

	/*******************************************************************************************************************************************/
	/*******************************************************************************************************************************************/
	/*******************************************************************************************************************************************/

	public static void esperarSegundos(WebDriver driver, int segundos) {

		synchronized (driver) {
			try {
				driver.wait(segundos * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String subeFile(MultipartFile multipart, HttpServletRequest request) {
		System.out.println("entre2");
		String nameOriginal = multipart.getOriginalFilename();
		String rutaFinal = request.getServletContext().getRealPath("/resources/scripsr/");
		System.out.println(rutaFinal);
		try {
			File imageFile = new File(rutaFinal + nameOriginal);
			System.out.println("entre5");
			System.out.println(imageFile.getAbsolutePath());
			multipart.transferTo(imageFile);
			return nameOriginal;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
} 