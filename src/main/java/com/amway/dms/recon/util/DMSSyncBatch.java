package com.amway.dms.recon.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class DMSSyncBatch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fileLocation = "./config.properties" ;
		Properties prop = new Properties();
		
		
		
		try {
		         if(args!=null && args.length>0) {
				  System.out.println("Input config file location1: " + args[0]); 
				  fileLocation =  ((args!=null && args.length>0) ? args[0] : fileLocation ) ;  
				  
                 }else{
				   System.out.println("Default config file location: " + fileLocation); 
                 }				 
      		      prop.load(new FileInputStream(fileLocation));
			       String pathToDataFile = prop.getProperty("pathToDataFile");
			       String delimeter = prop.getProperty("delimeter");
			       String serviceUrl = prop.getProperty("syncServiceUrl");
			       
			       List<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
			       BufferedReader dataBufferReader = new BufferedReader(new FileReader(pathToDataFile));
			       HashMap<String,String> hm = null;
			       String line = "";
					       
			       while((line = dataBufferReader.readLine()) != null) {  
			    	   hm = new HashMap<String,String>();
			           String[] fields = line.toString().split(delimeter);
			           hm.put("salesPlanAff", fields[0]);
			           hm.put("aboNum", fields[1]);
//			           hm.compute("processCd", remappingFunction);
//			           hm.out("sourceDetail", "xx")
			         dataList.add(hm);
			         System.out.println("Adding " + fields[0] + "|" + fields[1]);
			       }
			       
			        dataBufferReader.close();
			        
			        //get service connection 
			        URL url = new URL(serviceUrl);
			      	long st = new Date().getTime();
				    String fileName = "./errorFile_"+st+".txt";
				    System.out.println("error fileName = "+ fileName);
				    File file = new File(fileName);
				    if (!file.exists()) {
				      file.createNewFile();
				    }
				    long errorDataCount = 0 ; 
				    FileWriter fw = new FileWriter(file.getAbsoluteFile());
				    BufferedWriter bw = new BufferedWriter(fw);
				    bw.write("ERROR FILE DATA");
					bw.write("\n"+"==============");
				    
					//iterate the list and call WS 
			        if(dataList.size()>0){
			        	for (int i=0; i<dataList.size();i++ ){
			        		
			        		hm = (HashMap<String,String>) dataList.get(i);	
			        		
			        		  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			    		      conn.setDoOutput(true);
						      conn.setRequestMethod("POST");
						      conn.setRequestProperty("Content-Type", "application/json");
						      conn.setRequestProperty("Authorization", "Bearer RE1TOmZ2TmQ5c0FI");
			        		  OutputStream os = conn.getOutputStream();
					          os.write(getAboSyncJSON(hm.get("salesPlanAff"),hm.get("aboNum"), prop.getProperty("sourceDetail")).getBytes());
					          os.flush();
					         
					         if (conn.getResponseCode() != 200) {
							     bw.write("\n"+hm.get("salesPlanAff")+"|"+hm.get("aboNum"));
								errorDataCount++;
					           // throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
					         } else{
					          if (conn.getResponseCode() == 200) {
					        	try {
					        		System.out.println("successfully posted for syncing : salesPlanAff= "+hm.get("salesPlanAff") +",aboNum= "+hm.get("aboNum")+" and sourceDetail="+prop.getProperty("sourceDetail") );
			                        Thread.sleep(Long.parseLong(prop.getProperty("delayTimeInMilliSec")) );
			                     } catch (InterruptedException e) {
			                        // TODO Auto-generated catch block
			                       System.out.println("error during sleep" + e.getMessage());
			                    }
					           }
					        }
					         
					       conn.disconnect();					        
			        	}
						
						bw.close();
			        	
			        }
					
					System.out.println("Number of records sent successfully : "+ (dataList.size() -errorDataCount )  );
							System.out.println("Number of records could not send : "+ (errorDataCount )  );
			        
			        System.out.println("Mass Sync Complete!");
				            
		          
		        } catch (MalformedURLException e) {
		            e.printStackTrace();
		        } catch (FileNotFoundException e) {
			     // TODO Auto-generated catch block
			       e.printStackTrace();
		        } catch (IOException e) {
			      // TODO Auto-generated catch block
			     e.printStackTrace();
		       } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
              }

	}
	
	 public static String getAboSyncJSON(String salesPlanAff, String aboNum, String sourceDetail ) throws Exception {

//       	String jsonString = "{\"clientIpAddress\":\""
//            + InetAddress.getLocalHost().getHostAddress()
//            + "\",\"loggedInAccountId\":\""
//            + aboNum
//            + "\",\"loggedInPartyId\":\"\","
//            + "\"clientCntryCd\":\"BR\",\"clientApplicationId\":\"DMS\",\"loggedInCustomerServiceId\":\"\","
//            + "\"clientRoleList\":\"ROLE_TRUSTED_CLIENT\",\"salesPlanAff\":\"" + salesPlanAff + "\",\"aboNum\":\"" + aboNum
//            + "\",\"sourceDetail\":\""+ sourceDetail +"\"}" ;
		 
//		 String jsonString = "{\"clientIpAddress\":\""
//		            + InetAddress.getLocalHost().getHostAddress()
//		            + "\",\"loggedInAccountId\":\""
//		            + aboNum
//		            + "\",\"loggedInPartyId\":\"\","
//		            + "\"clientCntryCd\":\"BR\",\"clientApplicationId\":\"DMS\",\"loggedInCustomerServiceId\":\"\","
//		            + "\"clientRoleList\":\"ROLE_TRUSTED_CLIENT\",\"salesPlanAff\":\"" + salesPlanAff + "\",\"aboNum\":\"" + aboNum
//		            + "\",\"sourceDetail\":\""+ sourceDetail +"\",\"processCd\":\"RG\"}" ;
			
		 //Resignation
		 String jsonString = "{\"clientIpAddress\":\""
		            + InetAddress.getLocalHost().getHostAddress()
		            + "\",\"loggedInAccountId\":\""
		            + aboNum
		            + "\",\"loggedInPartyId\":\"\","
		            + "\"clientCntryCd\":\"BR\",\"clientApplicationId\":\"DMS\",\"loggedInCustomerServiceId\":\"\","
		            + "\"clientRoleList\":\"ROLE_TRUSTED_CLIENT\",\"salesPlanAff\":\"" + salesPlanAff + "\",\"aboNum\":\"" + aboNum
		            + "\",\"sourceDetail\":\""+ sourceDetail +"\",\"processCd\":\"RS\"}" ;
			
       	return jsonString;
			
    }

}