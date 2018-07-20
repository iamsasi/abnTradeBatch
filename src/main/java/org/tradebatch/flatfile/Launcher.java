/**
 * 
 * @author Sasi Nataraja
 *
 */

package org.tradebatch.flatfile;
import static org.easybatch.core.job.JobBuilder.aNewJob;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;


import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.writer.FileRecordWriter;
public class Launcher {
	public Launcher() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IntrospectionException, IOException {
		File inputFile = new File("src/main/resources/input.txt");
	        File outputFile = new File("target/summaryReport.csv");
	        int[] fieldRange = {
	        		3,4,4,4,4,
  	 			6,2,4,6,8,
  	 			3,2,1,1,10,
  	 			1,10,12,1,3,
  	 		    12,1,3,12,1,
  	 		    3,8,6,6,6,
  	 		    15,6,7,1};
	        
	        String[] fields = {
	        		"recordCode","clientType","clientNumber","accountNumber","subaccountNumber",
      	 		  "oppositePartyCode","productGroupCode","exchangeCode","symbol","expirationDate",
    	 		  "currencyCode","movementCode","buySellCode","quantityLongSign","quantityLong",
    	 		  "quantityShortSign","quantityShort","exchangeBrokerFee","exchangeBrokerFee_DC","exchangeBrokerFee_CurrCode",
    	 		  "clearningFee","clearningFee_DC","clearningFee_CurrCode","commission","commission_DC",
    	 		  "commission_CurrCode","transactionDate","futureReference","ticketNumber","externalNumber",
    	 		  "transactionPrice","traderInitials","oppositeTraderID","openCloseCode","filler"};
	        String[] fieldsHeader = {"clientInfo","productInfo","totalTransactionAmount"};	        
	        
	        // Build a batch job
	        @SuppressWarnings({ "unchecked", "rawtypes" })
			Job job = aNewJob()
	                .reader(new TradeDataRecordReader(inputFile))
	                .mapper(new TradeDataRecordMapper<>(
	                		TradeReport.class,
	                		fieldRange , fields))
	                .marshaller(new TradeDataRecordMarshaller(TradeReport.class,fieldsHeader))
	                .writer(new FileRecordWriter(outputFile))
	                .build();
	        
	        // Execute the job
	        JobExecutor jobExecutor = new JobExecutor();
			@SuppressWarnings("unused")
			JobReport jobReport = jobExecutor.execute(job);
	        jobExecutor.shutdown();

}

}
