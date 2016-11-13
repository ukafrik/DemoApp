package com.demo;


import java.io.FileOutputStream;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/** * This class is used to modify an existing pdf file using iText jar. * @author javawithease */

public class PDFModifyExample { 
	public static void main(String args[]){ 
		int xloc = 300;
		int yloc = 650;

		try { 
			//Create PdfReader instance. 
			PdfReader pdfReader = new PdfReader("C:\\Users\\JonathanSolomon\\DevArea\\DataWarehouse\\iTextPDFDemos\\VerizonBillTestPDF.pdf");	  
			//Create PdfStamper instance. 
			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("C:\\Users\\JonathanSolomon\\DevArea\\DataWarehouse\\iTextPDFDemos\\ModifiedTestFile-" + xloc + "-" + yloc + ".pdf"));   
			//Create BaseFont instance. 
			BaseFont baseFont = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);   
			//Get the number of pages in pdf. 
			int pages = pdfReader.getNumberOfPages();   
			//Iterate the pdf through pages. 
			for(int i=1; i<=pages; i++) { 
				//Contain the pdf data. 
				PdfContentByte pageContentByte = pdfStamper.getOverContent(i);  
				pageContentByte.beginText(); 
				//Set text font and size. 
				pageContentByte.setFontAndSize(baseFont, 14); 
				pageContentByte.setTextMatrix(xloc, yloc);   
				//Write text 
				pageContentByte.showText(xloc + ":" + yloc + "-Happy Sunday"); 
				pageContentByte.endText(); 
			}   
			//Close the pdfStamper. 
			pdfStamper.close();	  
			System.out.println("PDF modified successfully."); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
}
