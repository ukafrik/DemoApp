package com.demo;


import java.io.FileOutputStream;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/** * This class is used to modify an existing pdf file using iText jar. * @author javawithease */

public class PDFModifyExample { 
	public static void main(String args[]){ 
		int xloc = 0;
		int yloc = 20;

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
				
				pageContentByte.setFontAndSize(baseFont, 16); 
				pageContentByte.setTextMatrix(xloc, yloc+10);   
				pageContentByte.showText(xloc + ":" + (yloc+10) + "-New Sunday"); 
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

/*
    /*
     * Download a file from 
     *   - inside project, located in resources folder.
     *   - outside project, located in File system somewhere. 
     */
    @RequestMapping(value="/download/{type}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
     
        File file = null;
         
        if(type.equalsIgnoreCase("internal")){
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            file = new File(classloader.getResource(INTERNAL_FILE).getFile());
        }else{
            file = new File(EXTERNAL_FILE_PATH);
        }
         
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
 
}

*/
