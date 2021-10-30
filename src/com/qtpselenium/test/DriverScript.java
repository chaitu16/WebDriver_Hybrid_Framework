package com.qtpselenium.test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qtpselenium.xls.read.Excel_Reader;

public class DriverScript {
	
	public Excel_Reader suiteXLS;
	
	public static Logger APP_LOGS;
	
	public String current_Suite_Xls;
	public Excel_Reader currentXLS;
	public int currentTCID;
	public String currentTestcaseName;
	public int currentTestcaseID;
	public String currentKeyword;
	public Keywords keywords;
	public Method method[];
	public String keyword_execution_result;
	public ArrayList<String> resultSet;
	public int currentTestDataSetID;
	public String data;
	public String object;
	
	public static Properties config;
	public static Properties or;
	public static WebDriver driver;
	
	
	public DriverScript() {
		keywords = new Keywords();
		 method = keywords.getClass().getMethods();
	}
	
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\config\\config.properties");
		config =new Properties();
		config.load(fis);
	
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\config\\or.properties");
		or =new Properties();
		or.load(fis);
		
		
		
		DriverScript test = new DriverScript();
		test.start();
	
		
	}
		
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		
		APP_LOGS.debug("Start Logs");
		
		APP_LOGS.debug("Initialize Suite Xlsx");	
		
		APP_LOGS.debug("Initializing object data keywords starting loading browser");
		
		suiteXLS = new Excel_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\Suite.xlsx");		
		
		for(Constants.Sheet_Rnum =2;Constants.Sheet_Rnum<=suiteXLS.getRowCount(Constants.Suite_Name);Constants.Sheet_Rnum++) {
			
			APP_LOGS.debug(suiteXLS.getCellData(Constants.Suite_Name, Constants.Suite_TSID, Constants.Sheet_Rnum));
			APP_LOGS.debug(suiteXLS.getCellData(Constants.Suite_Name, Constants.Suite_Runmode, Constants.Sheet_Rnum));
			
			if(suiteXLS.getCellData(Constants.Suite_Name, Constants.Suite_Runmode, Constants.Sheet_Rnum).equals(Constants.Runmode_Value)) {
				// Execute individual Test Suite
				APP_LOGS.debug("Execute test Suite" +"\n");
				
				current_Suite_Xls = suiteXLS.getCellData(Constants.Suite_Name, Constants.Suite_TSID, Constants.Sheet_Rnum)+".xlsx";
				//System.out.println("Current Suite is : "+ current_Suite_Xls);
				
				currentXLS = new Excel_Reader(System.getProperty("user.dir")+"\\src\\com\\qtpselenium\\xls\\"+current_Suite_Xls);
				System.out.println("Current Suite is : "+ current_Suite_Xls+"*******************************Current Suite");
				APP_LOGS.debug("Current Suite is : "+ current_Suite_Xls+"*******************************Current Suite");
			
				for(currentTCID=2; currentTCID<=currentXLS.getRowCount(Constants.Suite_TC_Name);currentTCID++ ) {	
					
					currentTestcaseName = currentXLS.getCellData(Constants.Suite_TC_Name, Constants.Suite_TCID, currentTCID);
					
					System.out.println("current test case is :" +currentTestcaseName+"---------------------Current Test Case");
					
					APP_LOGS.debug("current test case is :" +currentTestcaseName+"---------------------Current Test Case");
					
					
					if(currentXLS.getCellData(Constants.Suite_TC_Name, Constants.Suite_Runmode,currentTCID).equals(Constants.Runmode_Value)){						
		//			System.out.println(currentXLS.getCellData(Constants.Suite_TC_Name, Constants.Suite_TCID, currentTCID)+"---------???");	
		//			}			
						
						if(currentXLS.isSheetExist(currentTestcaseName)) {
							
						for(currentTestDataSetID =2;currentTestDataSetID<=currentXLS.getRowCount(currentTestcaseName);currentTestDataSetID++) {	
							resultSet =new ArrayList<String>();	
						if(currentXLS.getCellData(currentTestcaseName, Constants.Suite_Runmode, currentTestDataSetID).equals(Constants.Runmode_Value)) {							
							
					//	  System.out.println(currentXLS.getCellData(currentTestcaseName, 0, i));
						//  APP_LOGS.debug(currentXLS.getCellData(currentTestcaseName, 0, currentTestDataSetID));						  
						  executeKeywords();					 						  					  	
					}
						createXLSReport();
				}										 			
				}else {
					resultSet= new ArrayList<String>();
					executeKeywords();	
					createXLSReport();					 					
				}					
					}			
					
				}
			}
		}
	}
	
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		for(currentTestcaseID=2;currentTestcaseID<=currentXLS.getRowCount(Constants.TestSuite_TestSteps);currentTestcaseID++) {
			data = currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.DATA, currentTestcaseID);
			if(data.startsWith(Constants.DATA_Start_Col)) {
				data = currentXLS.getCellData(currentTestcaseName, data.split(Constants.DATA_Split)[1], currentTestDataSetID);
			}
			
			
			object = currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.OBJECT, currentTestcaseID);
		//	if(currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.Suite_TCID, currentTestcaseID).equals(currentTestcaseName))	
				if(currentTestcaseName.equals(currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.Suite_TCID, currentTestcaseID))) {	
			
				currentKeyword = currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.TestSteps_Keyword, currentTestcaseID);
				//  System.out.println(currentKeyword);	
				APP_LOGS.debug(currentKeyword);	
				
			
				for(int i =0; i<method.length;i++) {
					if(method[i].getName().equals(currentKeyword)) {	
						
							keyword_execution_result= (String) method[i].invoke(keywords,object,data);
							APP_LOGS.debug(keyword_execution_result);
							resultSet.add(keyword_execution_result);							
					}
				}
			}
		}
		
	}
	
		 public void createXLSReport(){

	        String colName=Constants.RESULT +(currentTestDataSetID-1);
	        boolean isColExist=false;

	        for(int c=0;c<currentXLS.getColumnCount(Constants.TestSuite_TestSteps);c++){
	        
	            if(currentXLS.getCellData(Constants.TestSuite_TestSteps,c , 1).equals(colName)){
	                isColExist=true;
	                break;
	            }
	        }
	        if(!isColExist)
	        	currentXLS.addColumn(Constants.TestSuite_TestSteps, colName);
	        int index=0;
	        for(int i=2;i<=currentXLS.getRowCount(Constants.TestSuite_TestSteps);i++){

	            if(currentTestcaseName.equals(currentXLS.getCellData(Constants.TestSuite_TestSteps, Constants.Suite_TCID, i))){
	                if(resultSet.size()==0)
	                	currentXLS.setCellData(Constants.TestSuite_TestSteps, colName, i, Constants.KEYWORD_SKIP);
	                else
	                	currentXLS.setCellData(Constants.TestSuite_TestSteps, colName, i, resultSet.get(index));
	                index++;
	            
	            }
	           }
	           

	        if(resultSet.size()==0){
	            // skip
	        	currentXLS.setCellData(currentTestcaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
	            return;
	        }else{
	            for(int i=0;i<resultSet.size();i++){
	                if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
	                //	currentXLS.setCellData(currentTestcaseName, Constants.RESULT, currentTCID, resultSet.get(i));
	                	currentXLS.setCellData(currentTestcaseName, Constants.RESULT, currentTestDataSetID,Constants.KEYWORD_FAIL);
	                    return;
	                }
	            }
	        }
	        currentXLS.setCellData(currentTestcaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);
	        //	if(!currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode",currentTestDataSetID).equals("Y")){}
	    }

	}
	