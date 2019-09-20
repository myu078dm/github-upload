package com.amway.dms.recon.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class DMSUtil {
	public static int THREAD_SLEEP_MS = 100;
	
	public static boolean massSync(String affNo, List<String> aboList, String sourceDetail, String endpoint) {
		for(int i=0;i<aboList.size();i++) {
			HashMap<String,String> hm = new HashMap<String,String>();
	        hm.put("salesPlanAff", affNo);
	        hm.put("aboNum", aboList.get(i));
	        try {
				int x = doMassSync(affNo, aboList.get(i), sourceDetail, endpoint);
				if(x!=200) {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static int doMassSync(String affNo, String aboNo, String sourceDetail, String endpoint) throws Exception {
		URL url = new URL(endpoint);
		long errorDataCount = 0 ;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestProperty("Authorization", "Bearer RE1TOmZ2TmQ5c0FI");
		OutputStream os = conn.getOutputStream();
        os.write(getAboSyncJSON(affNo,aboNo, sourceDetail).getBytes());
        os.flush();
       
       if (conn.getResponseCode() != 200) {
//		     bw.write("\n"+hm.get("salesPlanAff")+"|"+hm.get("aboNum"));
			errorDataCount++;
         // throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
       } else{
        if (conn.getResponseCode() == 200) {
      	try {
      		System.out.println("successfully posted for syncing : salesPlanAff= " + affNo +",aboNum= "+ aboNo+" and sourceDetail="+ sourceDetail );
            Thread.sleep(THREAD_SLEEP_MS);
        } catch (InterruptedException e) {
        	// TODO Auto-generated catch block
            System.out.println("error during sleep" + e.getMessage());
        }
      }
      }
      return conn.getResponseCode();
	}
	
	public static String getAboSyncJSON(String salesPlanAff, String aboNum, String sourceDetail ) throws Exception {

       	String jsonString = "{\"clientIpAddress\":\""
            + InetAddress.getLocalHost().getHostAddress()
            + "\",\"loggedInAccountId\":\""
            + aboNum
            + "\",\"loggedInPartyId\":\"\","
            + "\"clientCntryCd\":\"BR\",\"clientApplicationId\":\"DMS\",\"loggedInCustomerServiceId\":\"\","
            + "\"clientRoleList\":\"ROLE_TRUSTED_CLIENT\",\"salesPlanAff\":\"" + salesPlanAff + "\",\"aboNum\":\"" + aboNum
            + "\",\"sourceDetail\":\""+ sourceDetail +"\"}" ;
			
       	return jsonString;
			
    }
}
