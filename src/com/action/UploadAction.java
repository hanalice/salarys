package com.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.constants.Globals;
import com.dao.beans.Salary;
import com.dao.beans.SalarySubsidy;
import com.dao.interfaces.SalaryDAO;
import com.dao.interfaces.SalarySubsidyDAO;
import com.util.StringUtil;
import com.util.TestReflet;

@Controller
public class UploadAction extends BaseAction{
	
	@Autowired
	private SalaryDAO salaryDAO;
	@Autowired
	private SalarySubsidyDAO salarySubsidyDAO;
	
	@SuppressWarnings("unused")
	private File saveFileFromInputStream(InputStream stream, String path, String filename) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException{
		File file = new File(path);
	    if(!file.exists()){
	    	file.mkdirs();
	    }
	    FileOutputStream fs = new FileOutputStream(file + "/" + filename);  
	    byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
	    int byteread = 0;  
	    while ((byteread = stream.read(buffer)) != -1) {  
	        bytesum += byteread;  
	        fs.write(buffer, 0, byteread);  
	        fs.flush();  
	    }  
	    fs.close();  
	    stream.close();
	    return file;  
	}  
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/uploadAction", params = "commonUpload")
	public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		String uploadFileUrl = "";
		String fileName = "";
		String salaryType = getParameter("salaryType", request);
		for (Iterator<?> it = request.getFileNames(); it.hasNext();) {
	        String key = (String) it.next();  
	        MultipartFile imgFile = request.getFile(key);  
	        if (imgFile.getOriginalFilename().length() > 0) {
	        	fileName = imgFile.getOriginalFilename(); 
	            Date now = new Date();
	            String nowDate = StringUtil.dateParseStringYMD(now);
				uploadFileUrl = getRealPath() + "\\excels\\"+nowDate;
                try {
					File _apkFile = saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}  
	        }
	    }
		writeJSONObjectToClient(readExcel(uploadFileUrl+"\\"+fileName, salaryType), response);
	}
	
	public String readExcel(String path, String type){
		String flag = Globals.failJsonStr ;
	    Sheet sheet;
        Workbook book;
        Cell cell;
        try { 
            //t.xls为要读取的excel文件名,xlsx格式文件解析不了，必须是xls格式
        	WorkbookSettings settings = new WorkbookSettings();
        	settings.setEncoding("GBK");
            book= Workbook.getWorkbook(new File(path), settings);
            List<String[]> result = new ArrayList<>(); 
            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            for (int i = 0; i < book.getSheets().length; i++) {
            	 sheet=book.getSheet(i); 
            		 for (int j = 1; j <sheet.getRows() ; j++) {
 						String[] str = new String[sheet.getColumns()];
 						for (int k = 0; k < sheet.getColumns(); k++) {
 							cell = sheet.getCell(k, j);//（列，行）
 							str[k] = cell.getContents();
 						}
 						result.add(str);
 					}
			}
            for(int i = 0 ;i < result.size(); i++){
                String[] str = (String[])result.get(i);
                if (str != null && StringUtil.isNotEmpty(str)) {
                	if (StringUtils.isNotEmpty(type)) {
						if ("salary".equals(type)) {
							if (StringUtils.isNotEmpty(createSalary(str))) {
								flag = Globals.successJsonStr;
							}
						}else if ("subsidy".equals(type)) {
							if (StringUtils.isNotEmpty(createSubsidy(str))) {
								flag = Globals.successJsonStr;
							}
						}
					}
				}
               /* for(int j=0;j<str.length;j++){
                 System.out.println(str[j]);
                }*/
               }
            book.close(); 
        }
        catch(Exception e)  { 
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        } 
        return flag;
	}
	
	/**
	 * 查看指定用户，指定年月是否已经上传过工资单,如果之前存在记录，则全部删除掉
	 * @param userName
	 * @param year
	 * @param month
	 * @return
	 */
	private void checkSalaryRepeat(String userName, String year, String month){
		List<Salary> salarys = salaryDAO.getByUserName(userName, year, month);
		if(StringUtil.isNotEmpty(salarys)){
			for (Salary temp : salarys) {
				salaryDAO.delete(temp);
			}
		}
	}
	
	/**
	 * 查看制定用户，制定年月是否已经上传过津贴单,如果之前存在记录，则全部删除掉
	 * @param userName
	 * @param year
	 * @param month
	 * @return
	 */
	private void checkSubsidyRepeat(String userName, String year, String month){
		List<SalarySubsidy> subsidys = salarySubsidyDAO.getByUserName(userName, year, month);
		if (StringUtil.isNotEmpty(subsidys)) {
			for (SalarySubsidy temp : subsidys) {
				salarySubsidyDAO.delete(temp);
			}
		}
	}
	
	/**
	 * @param salaryStr
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NumberFormatException 
	 * @updated at 2018/1/28 23:59 add colume poison before jiaxiang colume
	 */
	private String createSalary(String[] salaryStr) throws ClassNotFoundException, NumberFormatException, IllegalArgumentException, IllegalAccessException{
		if (StringUtil.isNotEmpty(salaryStr[0]) && StringUtil.isNotEmpty(salaryStr[1]) && StringUtil.isNotEmpty(salaryStr[2])) {
			checkSalaryRepeat(salaryStr[0], salaryStr[1], salaryStr[2]);
			String[] salary_columes = Globals.SALARY_COLUMES;
			
			System.out.println("alice craeteSalary");
			for(int i=0; i<salaryStr.length; i++) {
				System.out.println(salaryStr[i]);
			}
			
			Salary salary = new Salary();
	        Class clas= Class.forName(salary.getClass().getName());
			if (!(clas.isInstance(salary))) {  
	            System.out.println("传入的java实例与配置的java对象类型不符！");  
	            return null;  
	        }  
	        Field[] fields = clas.getDeclaredFields();  
	        ArrayList<String> fieldsNameList = new ArrayList<String>();  
	        for (Field field : fields) {  
	            fieldsNameList.add(field.getName());  
	        }  
	        for (int i = 0; i< salary_columes.length; i++) {
	        	TestReflet.setBeanValue(salary, fields, fieldsNameList, salary_columes[i], salaryStr[i]);  
	        }
			return salaryDAO.save(salary);
		}else {
			return Globals.UPLOAD_ERROR;
		}
	}
	
	/**
	 * @param salaryStr, already do the empty check
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NumberFormatException 
	 * @updated at 2018/1/28 23:59 add colume holiday before increase colume
	 */
	private String createSubsidy(String[] subsidayStr) throws ClassNotFoundException, NumberFormatException, IllegalArgumentException, IllegalAccessException{
		if (StringUtil.isNotEmpty(subsidayStr[0]) && StringUtil.isNotEmpty(subsidayStr[1]) && StringUtil.isNotEmpty(subsidayStr[2])) {
			checkSubsidyRepeat(subsidayStr[0], subsidayStr[1], subsidayStr[2]);
			String[] subsidy_columes = Globals.SUBSIDY_COLUMES;
			
			System.out.println("alice craeteSubside");
			for (int i=0; i<subsidayStr.length; i++) {
				System.out.println(subsidayStr[i]);
			}
			
			SalarySubsidy subsidy = new SalarySubsidy();
	        Class clas = Class.forName(subsidy.getClass().getName());
			if (!(clas.isInstance(subsidy))) {  
	            System.out.println("传入的java实例与配置的java对象类型不符！");  
	            return null;  
	        }  
	        Field[] fields = clas.getDeclaredFields();  
	        ArrayList<String> fieldsNameList = new ArrayList<String>();  
	        for (Field field : fields) {  
	            fieldsNameList.add(field.getName());  
	        }  
	        for (int i = 0; i< subsidy_columes.length; i++) {
	        	TestReflet.setBeanValue(subsidy, fields, fieldsNameList, subsidy_columes[i], subsidayStr[i]);  
	        }
			
			return salarySubsidyDAO.save(subsidy);
		}else {
			return Globals.UPLOAD_ERROR;
		}
	}
}
