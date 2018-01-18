/**
 * 
 */
package com.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
						}else if("bonus".equals(type)){
							if (StringUtils.isNotEmpty(createBonus(str))) {
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
	 * 查看制定用户，制定年月是否已经上传过工资单,如果之前存在记录，则全部删除掉
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
	 * 查看制定用户，制定年月是否已经上传过年终,如果之前存在记录，则全部删除掉
	 * @param userName
	 * @param year
	 * @param month
	 */
	/*private void checkBonusRepeat(String userName, String year, String month){
		List<Bonus> bonus = bonusDAO.getByUserName(userName, year, month);
		if (StringUtil.isNotEmpty(bonus)) {
			for (Bonus temp : bonus) {
				bonusDAO.delete(temp);
			}
		}
	}*/
	
	private String createSalary(String[] salaryStr){
		checkSalaryRepeat(salaryStr[0], salaryStr[1], salaryStr[2]);
		Salary salary = new Salary();
		salary.setName(salaryStr[0]);
		salary.setYear(salaryStr[1]);
		salary.setMonth(salaryStr[2]);
		salary.setGangwei(StringUtil.isNotBlank(salaryStr[3]) ? Float.valueOf(salaryStr[3]) : 0);
		salary.setXinji(StringUtil.isNotBlank(salaryStr[4]) ? Float.valueOf(salaryStr[4]) : 0);
		salary.setJiaotong(StringUtil.isNotBlank(salaryStr[5]) ? Float.valueOf(salaryStr[5]) : 0);
		salary.setOne_child(StringUtil.isNotBlank(salaryStr[6]) ? Float.valueOf(salaryStr[6]) : 0);
		salary.setLiangyou(StringUtil.isNotBlank(salaryStr[7]) ? Float.valueOf(salaryStr[7]) : 0);
		salary.setJiaxiang(StringUtil.isNotBlank(salaryStr[8]) ? Float.valueOf(salaryStr[8]) : 0);                   
		salary.setIncrease(StringUtil.isNotBlank(salaryStr[9]) ? Float.valueOf(salaryStr[9]) : 0);
		
		salary.setAnnual(StringUtil.isNotBlank(salaryStr[10]) ? Float.valueOf(salaryStr[10]) : 0);
		salary.setYanglao(StringUtil.isNotBlank(salaryStr[11]) ? Float.valueOf(salaryStr[11]) : 0);
		salary.setGongjijin(StringUtil.isNotBlank(salaryStr[12]) ? Float.valueOf(salaryStr[12]) : 0);
		salary.setYiliao(StringUtil.isNotBlank(salaryStr[13]) ? Float.valueOf(salaryStr[13]) : 0);
		salary.setShiye(StringUtil.isNotBlank(salaryStr[14]) ? Float.valueOf(salaryStr[14]) : 0);
		
		salary.setPersonal_income_tax(StringUtil.isNotBlank(salaryStr[15]) ? Float.valueOf(salaryStr[15]) : 0);
		salary.setGonghui(StringUtil.isNotBlank(salaryStr[16]) ? Float.valueOf(salaryStr[16]) : 0);
		salary.setKouxiang(StringUtil.isNotBlank(salaryStr[17]) ? Float.valueOf(salaryStr[17]) : 0);
		salary.setDecrease(StringUtil.isNotBlank(salaryStr[18]) ? Float.valueOf(salaryStr[18]) : 0);
		salary.setTotal(StringUtil.isNotBlank(salaryStr[19]) ? Float.valueOf(salaryStr[19]) : 0);
		
		//2th edition customer need to delete the field
		//salary.setRemark(salaryStr[20]);
		return salaryDAO.save(salary);
	}
	
	private String createSubsidy(String[] subsidayStr){
		checkSubsidyRepeat(subsidayStr[0], subsidayStr[1], subsidayStr[2]);
		SalarySubsidy subsidy = new SalarySubsidy();
		subsidy.setName(subsidayStr[0]);
		subsidy.setYear(subsidayStr[1]);
		subsidy.setMonth(subsidayStr[2]);
		subsidy.setGangwei(StringUtil.isNotBlank(subsidayStr[3]) ? Float.valueOf(subsidayStr[3]) : 0);
		subsidy.setGongzuoliang(StringUtil.isNotBlank(subsidayStr[4]) ? Float.valueOf(subsidayStr[4]) : 0);
		subsidy.setJiaxiang1(StringUtil.isNotBlank(subsidayStr[5]) ? Float.valueOf(subsidayStr[5]) : 0);
		subsidy.setJiaxiang2(StringUtil.isNotBlank(subsidayStr[6]) ? Float.valueOf(subsidayStr[6]) : 0);
		subsidy.setIncrease(StringUtil.isNotBlank(subsidayStr[7]) ? Float.valueOf(subsidayStr[7]) : 0);
		subsidy.setPersonal_income_tax(StringUtil.isNotBlank(subsidayStr[8]) ? Float.valueOf(subsidayStr[8]) : 0);
		subsidy.setKouxiang1(StringUtil.isNotBlank(subsidayStr[9]) ? Float.valueOf(subsidayStr[9]) : 0);
		subsidy.setKouxiang2(StringUtil.isNotBlank(subsidayStr[10]) ? Float.valueOf(subsidayStr[10]) : 0);
		subsidy.setDecrease(StringUtil.isNotBlank(subsidayStr[11]) ? Float.valueOf(subsidayStr[11]) : 0);
		subsidy.setTotal(StringUtil.isNotBlank(subsidayStr[12]) ? Float.valueOf(subsidayStr[12]) : 0);
		subsidy.setRemark(subsidayStr[13]);
		return salarySubsidyDAO.save(subsidy);
	}
	
	private String createBonus(String[] bonusStr){
		/*checkBonusRepeat(bonusStr[0], bonusStr[1], bonusStr[2]);
		Bonus temp = new Bonus();
		temp.setName(bonusStr[0]);
		temp.setYear(bonusStr[1]);
		temp.setMonth(bonusStr[2]);
		temp.setBonus(StringUtil.isNotBlank(bonusStr[3]) ? Float.valueOf(bonusStr[3]) : 0);
		temp.setAdd1(StringUtil.isNotBlank(bonusStr[4]) ? Float.valueOf(bonusStr[4]) : 0);
		temp.setAdd2(StringUtil.isNotBlank(bonusStr[5]) ? Float.valueOf(bonusStr[5]) : 0);
		temp.setRemark(bonusStr[6]);
		return bonusDAO.save(temp);*/
		
		checkSalaryRepeat(bonusStr[0], bonusStr[1], "13");
		Float total = StringUtil.isNotBlank(bonusStr[3]) ? Float.valueOf(bonusStr[3]) : 0;
		Float add1 = StringUtil.isNotBlank(bonusStr[4]) ? Float.valueOf(bonusStr[4]) : 0;
		Float add2 = StringUtil.isNotBlank(bonusStr[5]) ? Float.valueOf(bonusStr[5]) : 0;
		String remark = bonusStr[6];
		Salary temp = new Salary(bonusStr[0], bonusStr[1], "13", total, remark, add1, add2);
		return salaryDAO.save(temp);
		
	}
	
}
