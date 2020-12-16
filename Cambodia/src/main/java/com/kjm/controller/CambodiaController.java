package com.kjm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kjm.service.BoardService;
import com.kjm.vo.CambodiaVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CambodiaController {
	
	@Inject
	private BoardService service;

	@RequestMapping(value = "/cam.do")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		return mv;
	}
	
	@RequestMapping(value="/cambodia.do")	
	public String cambodia()throws Exception{
		Workbook wb;
		Sheet sheet;
		Row row;
		Cell cell;
		int rowNo;
		List<CambodiaVO> list;
		
		int count;
		int num = 0;
		System.out.println(num);
		//for(int i = 2000;i<=2000;i++) {
		for(int i = 2000;i<=2018;i++) {
			CambodiaVO vo = new CambodiaVO();
			count = 1;

			vo.setYear(Integer.toString(i));
			vo.setIo("ex");

			
			
			
			list = service.getCambodiaYear(vo);
			
			wb = new HSSFWorkbook();
			sheet = wb.createSheet(Integer.toString(i) + "년도 국가 별 수출 내역 DB");
			row = null;
			cell = null;
			rowNo = 0;
		    row = sheet.createRow(rowNo++);

		    cell = row.createCell(0);
		    cell.setCellValue("Number");
		    cell = row.createCell(1);
		    cell.setCellValue("Year");
		    cell = row.createCell(2);
		    cell.setCellValue("HS4 ID");
		    cell = row.createCell(3);
		    cell.setCellValue("Product Name");    
		    cell = row.createCell(4);
		    cell.setCellValue("Country");	    
		    cell = row.createCell(5);
		    cell.setCellValue("Value");
		    
			//for(int j=0;j<10;j++) {
			for(int j=0;j<list.size();j++) {
				
				CambodiaVO camvo = list.get(j);
				

				
				try{
					String urlStrEx = "https://oec.world/olap-proxy/data?cube=trade_i_baci_a_92&Exporter%20Country=askhm&HS4=" + camvo.getId() + "&Year="+camvo.getYear()+"&drilldowns=Importer%20Country&locale=en&measures=Trade%20Value&parents=true&sparse=false&properties=Importer%20Country%20ISO%203&q=Trade%20Value";
					//String urlStrIm = "https://oec.world/olap-proxy/data?cube=trade_i_baci_a_92&Importer%20Country=askhm&HS4=" + camvo.getId() + "&Year="+camvo.getYear()+"&drilldowns=Exporter+Country&measures=Trade+Value&parents=true&sparse=false&properties=Exporter+Country+ISO+3&locale=en";
					URL url = new URL(urlStrEx);
					URLConnection uc = url.openConnection();
					uc.setRequestProperty("User-Agent", "Chrome/86.0.4240.111");
					BufferedReader bf; 
					String line = "";
					String result="";
					bf = new BufferedReader(new InputStreamReader(uc.getInputStream()));
					while((line=bf.readLine())!=null)
					{ result=result.concat(line); 
					}

					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(result);
					JSONArray parse_data = (JSONArray) obj.get("data");
					for(int k =0; k < parse_data.size();k++) {
						JSONObject data = (JSONObject) parse_data.get(k);
						row = sheet.createRow(rowNo++);
						cell=row.createCell(0);
						cell.setCellValue(count);
						
						cell=row.createCell(1);
						cell.setCellValue(camvo.getYear());

						cell=row.createCell(2);
						cell.setCellValue(camvo.getId());
						
						cell=row.createCell(3);
						cell.setCellValue(camvo.getProduct());
						
						
						
						cell=row.createCell(4);
						cell.setCellValue((String)data.get("Country"));
						
						Object temp = data.get("Trade Value");
						Double Ktemp = Double.parseDouble(String.valueOf(temp));
						
						cell=row.createCell(5);
						cell.setCellValue(String.format("%f", Ktemp));
						count++;
						System.out.println(rowNo);
					}

					bf.close();
				}catch(Exception e){ 
					System.out.println(e.getMessage()); 
				}
			}			
	    	String path = "C:\\Temp\\Cambodia\\"; //경로
	    	String fileName = Integer.toString(i)+ "수출.xls"; //파일명		ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ
	        try {

	            File xlsFile = new File(path+fileName); //저장경로 설정
	            FileOutputStream fileOut = new FileOutputStream(xlsFile);
	            wb.write(fileOut);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }			
	        wb.close();	
			

			
		}
		
	
		
		
		
		

		return "redirect:/cam.do";
		

	}
	@RequestMapping(value="/getListAddExcelCambodia.do")	
	public String getListAddExcelCambodia(MultipartHttpServletRequest request) throws Exception{
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if(iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        List<CambodiaVO> list = new ArrayList<CambodiaVO>();
  
        try {
        	Workbook workbook = WorkbookFactory.create(file.getInputStream());

            // 첫번째 시트 불러오기
            Sheet sheet = workbook.getSheetAt(3);
            
            for(int i=0; i<=sheet.getLastRowNum(); i++) {
            	CambodiaVO vo = new CambodiaVO();
                Row row = sheet.getRow(i);
                
                // 행이 존재하기 않으면 패스
                if(null == row) {
                    continue;
                }
                
                Cell cell = row.getCell(2);
                try {
                	vo.setYear(cell.getStringCellValue());
                }catch(IllegalStateException e) {
                	vo.setYear(Integer.toString((int)cell.getNumericCellValue()));
                }
                
                cell = row.getCell(4);
                try {
                	vo.setId(cell.getStringCellValue());
                }catch(IllegalStateException e) {
                	vo.setId(Integer.toString((int)cell.getNumericCellValue()));
                }
                cell = row.getCell(5);
                try {
                	vo.setProduct(cell.getStringCellValue());
                }catch(IllegalStateException e) {
                	vo.setProduct(Integer.toString((int)cell.getNumericCellValue()));
                }
                
                vo.setIo("ex");

                list.add(i, vo);
                
                //service.registCambodia(vo);
                //service.updateCam(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.registCambodiaList(list);

        return "redirect:/cam.do";
	
	}
	
}
