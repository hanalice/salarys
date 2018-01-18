package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * Title: ExcelDeal.java
 *
 * Description: 读写excel
 *
 * Company: wutron
 *
 * @author Alice
 *
 * @date 2015年8月6日
 *
 */
public class ExcelDeal {

	public static void main(String[] args) {
		readExcel();
	}
	
	public static void readExcel(){
		    Sheet sheet;
	        Workbook book;
	        Cell cell;
	        try { 
	            //t.xls为要读取的excel文件名,xlsx格式文件解析不了，必须是xls格式
	        	WorkbookSettings settings = new WorkbookSettings();
	        	settings.setEncoding("GBK");
	            book= Workbook.getWorkbook(new File("D:\\myfiles\\zhaoshiLog\\excelDeal\\工资表.xls"), settings);
	            List<String[]> result = new ArrayList<>(); 
	            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
	            for (int i = 0; i < book.getSheets().length; i++) {
	            	 sheet=book.getSheet(i); 
	            		 for (int j = 0; j <sheet.getRows() ; j++) {
	 						String[] str = new String[sheet.getColumns()];
	 						for (int k = 0; k < sheet.getColumns(); k++) {
	 							cell = sheet.getCell(k, j);//（列，行）
	 							str[k] = cell.getContents();
	 						}
	 						result.add(str);
	 					}
				}
	            for(int i=0;i<result.size();i++){
	                String[] str = (String[])result.get(i);
	                for(int j=0;j<str.length;j++){
	                 System.out.println(str[j]);
	                }
	               }
	            book.close(); 
	        }
	        catch(Exception e)  { 
	        	System.out.println(e.getMessage());
	        	e.printStackTrace();
	        } 
	}
	
	/*public static void writeExcel(){
		String[] title = {"编号","产品名称","产品价格","产品数量","生产日期","产地","是否出口"}; 
		try {
			//获取开始时间
			long start = System.currentTimeMillis();
			//输出的excel的路径
			String outPath = "d:\\testDealExcel.xls";
			//创建excel工作簿
			WritableWorkbook wwb;
			//新建立一个jxl文件，即在D盘下生成testDealExcel.xls
			OutputStream os = new FileOutputStream(outPath);
			wwb = Workbook.createWorkbook(os);
			//天剑第一个工作表并设置第一个sheet的名字
			WritableSheet sheet = wwb.createSheet("sheet", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				//Label(x,y,z)代表单元格的第x+1列，第y+1行，内容z
				//在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				label = new Label(i, 0, title[i], getHeader());
				//将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			//下面是填充数据
			*//**
			 * 保存数字到单元格，需要使用jxl.write.Number
			 * 必须使用期完整路径，否则会出现错误
			 *//*
			// 填充产品编号   
            jxl.write.Number number = new jxl.write.Number(0,1,20071001);   
            sheet.addCell(number);   
            // 填充产品名称   
            label = new Label(1,1,"金鸽瓜子");   
            sheet.addCell(label);   
	        *//**
	          * 定义对于显示金额的公共格式 
	          * jxl会自动实现四舍五入 
	          * 例如 2.456会被格式化为2.46,2.454会被格式化为2.45 
	          *//* 
            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###.00");   
            jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf);   
            // 填充产品价格   
            jxl.write.Number nb = new jxl.write.Number(2,1,200000.45,wcf);   
            sheet.addCell(nb);   
            // 填充产品数量   
            jxl.write.Number numb = new jxl.write.Number(3,1,200);   
            sheet.addCell(numb);   
            *//**
              *定义显示日期的公共格式 
              * 如:yyyy-MM-dd hh:mm 
              * 
              *//* 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
            String newdate = sdf.format(new Date());   
            // 填充出产日期   
            label = new Label(4,1,newdate);   
            sheet.addCell(label);   
            // 填充产地   
             label = new Label(5,1,"陕西西安");   
            sheet.addCell(label);   
            *//** 
             * 显示布尔值 
             *//* 
            jxl.write.Boolean bool = new jxl.write.Boolean(6,1,true);   
            sheet.addCell(bool);   
            *//** 
              * 合并单元格 
              * 通过writablesheet.mergeCells(int x,int y,int m,int n);来实现的 
              * 表示将从第x+1列，y+1行到m+1列，n+1行合并 
              *   
              *//* 
            sheet.mergeCells(0,3,2,3);   
            label = new Label(0,3,"合并了三个单元格");   
            sheet.addCell(label);   
            *//** 
              * 定义公共字体格式 
              * 通过获取一个字体的样式来作为模板 
              * 首先通过web.getSheet(0)获得第一个sheet 
              * 然后取得第一个sheet的第二列，第一行也就是"产品名称"的字体   
              *//* 
            CellFormat cf = (CellFormat) wwb.getSheet(0).getCell(1, 0).getCellFormat();   
            WritableCellFormat wc = new WritableCellFormat();   
            // 设置居中   
            wc.setAlignment(Alignment.CENTRE);   
             // 设置边框线   
            wc.setBorder(Border.ALL, BorderLineStyle.THIN);   
            // 设置单元格的背景颜色   
            wc.setBackground(jxl.format.Colour.RED);   
            label = new Label(1,5,"字体",wc);   
            sheet.addCell(label);   
 
            // 设置字体   
            jxl.write.WritableFont wfont = new jxl.write.WritableFont(WritableFont.createFont("隶书"),20);   
            WritableCellFormat font = new WritableCellFormat(wfont);   
            label = new Label(2,6,"隶书",font);   
            sheet.addCell(label);   
               
            // 写入数据   
            wwb.write();   
            // 关闭文件   
            wwb.close();   
            long end = System.currentTimeMillis();   
            System.out.println("----完成该操作共用的时间是:"+(end-start)/1000);   
        } catch (Exception e) {   
            System.out.println("---出现异常---");   
            e.printStackTrace();  
		} 
	}
	
	public static WritableCellFormat getHeader(){
		WritableFont font = new WritableFont(WritableFont.TIMES,10,WritableFont.BOLD);
		try {
			font.setColour(Colour.BLUE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		WritableCellFormat format = new WritableCellFormat(font);
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			format.setBackground(Colour.YELLOW);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return format;
	}*/
}
