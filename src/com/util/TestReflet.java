package com.util;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;  
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;  

import com.dao.beans.Salary;
  
public class TestReflet {  
  
    public static void main(String[] args) {  
        try {  
            Salary bean = new Salary();  
            Class clas = Class.forName(bean.getClass().getName());  
  
            if (!(clas.isInstance(bean))) {  
                System.out.println("传入的java实例与配置的java对象类型不符！");  
                return;  
            }  
            Field[] fields = clas.getDeclaredFields(); 
            ArrayList<String> fieldsNameList = new ArrayList<String>();  
            int i = 0;  
            for (Field field : fields) {  
                fieldsNameList.add(field.getName());
            }  
              
            setBeanValue(bean, fields, fieldsNameList, "name", "Shane");  
            setBeanValue(bean, fields, fieldsNameList, "year", "2017"); 
            setBeanValue(bean, fields, fieldsNameList, "month", "11");  
            setBeanValue(bean, fields, fieldsNameList, "gangwei", "320");
  
            System.out.println("Name:" + bean.getName() + "\n" + "Year:"  
                    + bean.getYear() + "\n" + "Month" + bean.getMonth() + "\n");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void setBeanValue(Object destObj, Field[] fields,  
            ArrayList<String> fieldsNameList, String strKey, Object objValue)  
            throws IllegalArgumentException, IllegalAccessException {  
        int intIndex = fieldsNameList.indexOf(strKey);  
        String type = fields[intIndex].getType().getName();
        if (intIndex >= 0) {  
        	fields[intIndex].setAccessible(true);
        	if (type != "java.lang.String") {
        		fields[intIndex].set(destObj, StringUtil.obj2SpecificType(objValue, type));
        	}else {
        		fields[intIndex].set(destObj, objValue);
        	}
        }  
    }  
    
//    public static void main(String[] args) {
//        Class<Salary> classobj = Salary.class;
//
//        String parentName = classobj.getSuperclass().getName();
//        String modifier = Modifier.toString(classobj.getModifiers());
//        Class [] interfaces = classobj.getInterfaces();
//        StringBuffer interfacesBuf = new StringBuffer();
//        if(interfaces.length != 0){
//            interfacesBuf.append("implements ");
//            for(int i = 0; i <= interfaces.length - 1; i++){
//                if(i != interfaces.length - 1){
//                    interfacesBuf.append(interfaces[i].getName() + ",");
//                }else{
//                    interfacesBuf.append(interfaces[i].getName());
//                }
//            }
//        }
//        String Header = modifier + " class " + classobj.getName() + " extends " + parentName + interfacesBuf.toString() + "{";
//        System.out.println(Header + "\n");
//
//        printFields(classobj);
//        printMethods(classobj);
//        printConstructors(classobj);
//
//        System.out.println("}");
//    }
    
    public static void printFields(Class<?> classobj){
        System.out.println("    //Fileds");
        String modifier = "";
        String type = "";
        String name = "";
        Field [] fields = classobj.getDeclaredFields();
        for(int i = 0; i <= fields.length - 1; i++){
            modifier = Modifier.toString(fields[i].getModifiers());
            type = fields[i].getType().getName();
            name = fields[i].getName();
            System.out.println("    " + modifier + " " + type + " " + name + ";");
        }
        System.out.println();
    }
    
    public static void printMethods(Class<?> classobj){
        System.out.println("    //Methods");
        String modifier = "";
        String returnType = "";
        String name = "";
        Class [] paraClasses = null;
        StringBuffer paraType = new StringBuffer();
        Method [] methods = classobj.getDeclaredMethods();
        for(int i = 0; i <= methods.length - 1; i++){
            modifier = Modifier.toString(methods[i].getModifiers());
            returnType = methods[i].getReturnType().getName();
            name = methods[i].getName();
            paraClasses = methods[i].getParameterTypes();
            for(int j = 0; j <= paraClasses.length - 1; j++){
                if(j != paraClasses.length - 1){
                    paraType.append(paraClasses[j].getName() + ", ");
                }
                else{
                    paraType.append(paraClasses[j].getName());
                }
            }
            System.out.println("    " + modifier + " " + returnType + " " + name + "(" + paraType.toString() + ")");
        }
        System.out.println();
    }
    
    public static void printConstructors(Class<?> classobj){
        System.out.println("    //Constructors");
        Constructor [] constructors = classobj.getConstructors();
        String modifier = "";
        String name = "";
        Class [] paraClasses = null;
        StringBuffer paraType = new StringBuffer();
        for(int i = 0; i <= constructors.length - 1; i++){
            modifier = Modifier.toString(constructors[i].getModifiers());
            name = constructors[i].getName();
            paraClasses = constructors[i].getParameterTypes();
            for(int j = 0; j <= paraClasses.length - 1; j++){
                if(j != paraClasses.length - 1){
                    paraType.append(paraClasses[j].getName() + ", ");
                }
                else{
                    paraType.append(paraClasses[j].getName());
                }
            }
            System.out.println("    " + modifier + " " + name + "(" + paraType.toString() + ")");
        }
        System.out.println();
    }
}  