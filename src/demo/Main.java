package demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
 
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Main {
 
	private static final String reportFileTemplate = "jasper/JasperDesign.jrxml"; // created a template file - Jaspersoft Studio
	
	 
   public static void main(String[] args) {
	 
	   Main main = new Main();
	  try {
		main.createPdf();
	  } catch (IOException e) { 
		System.out.println("Pdf report failed... !" + e.getCause());
	}
	  
    }
   
	
	public void createPdf() throws IOException {
		 
		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(fileInputStream(reportFileTemplate));
			Map<String , Object> params= new HashMap();
		//	params.put("title", "Template Jasper Report");  
			// parametre eklenmek istenirse title gibi bu kısımdan eklenebilir.
		
			
			 List<Student> lsStudents = new ArrayList<>(); 
			 Student s = new Student("lorem", "ipsum", "15");
			 lsStudents.add(s);
			 
			 JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lsStudents);
			 
			 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
			 byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			 System.out.println(Base64.getEncoder().encodeToString(pdfBytes));
	 
			 
			
		} catch (JRException e) { 
			e.printStackTrace();
		}
	 
	}


	private InputStream  fileInputStream(String res) throws IOException {
		Resource reportResource = new ClassPathResource(res);
		return reportResource.getInputStream();
   }
}
