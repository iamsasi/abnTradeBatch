package org.tradebatch.flatfile;

import org.easybatch.core.record.StringRecord;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class TradeDataRecordReaderTest {

	 private TradeDataRecordReader tradeDataRecordReader;

	    private Path fileSource;
	    private Path nonExistingFileSource;
	    private Path emptyFileSource;
	    
	    @Before
	    public void setUp() throws Exception {
	    	fileSource = Paths.get("src/test/resources/Input.txt");
	    	nonExistingFileSource=Paths.get("src/test/resources/unknown.txt");
	    	emptyFileSource = Paths.get("src/test/resources/empty-file.txt");
	    }

	    @Test
	    public void whenInputFileExistsAndIsNotEmpty_thenReadRecordShouldReturnNextRecords() throws Exception {
	    	tradeDataRecordReader = new TradeDataRecordReader(fileSource);
	    	tradeDataRecordReader.open();
	        StringRecord record = tradeDataRecordReader.readRecord();
	        assertThat(record.getHeader().getNumber()).isEqualTo(1L);
	        assertThat(record.getPayload()).isEqualTo("a,b,c");

	        record = tradeDataRecordReader.readRecord();
	        assertThat(record.getHeader().getNumber()).isEqualTo(2L);
	        assertThat(record.getPayload()).isEqualTo("1,2,3");

	        record = tradeDataRecordReader.readRecord();
	        assertThat(record.getHeader().getNumber()).isEqualTo(3L);
	        assertThat(record.getPayload()).isEqualTo("a1,b2,c3");

	        record = tradeDataRecordReader.readRecord();
	        assertThat(record).isNull();
	    }

	    @Test(expected = FileNotFoundException.class)
	    public void whenInputFileDoesNotExist_thenOpeningTheReaderShouldThrowFileNotFoundException() throws Exception {
	    	tradeDataRecordReader = new TradeDataRecordReader(nonExistingFileSource);
	    	tradeDataRecordReader.open();
	    }

	    @Test
	    public void whenInputFileIsEmpty_thenReadRecordShouldReturnNull() throws Exception {
	    	tradeDataRecordReader = new TradeDataRecordReader(emptyFileSource);
	    	tradeDataRecordReader.open();
	        assertThat(tradeDataRecordReader.readRecord()).isNull();
	    }

	    @After
	    public void tearDown() throws Exception {
	    	tradeDataRecordReader.close();
	    }


}
