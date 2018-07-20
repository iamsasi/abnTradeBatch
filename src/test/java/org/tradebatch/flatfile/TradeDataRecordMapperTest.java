package org.tradebatch.flatfile;

import static org.junit.Assert.*;
import org.easybatch.core.record.StringRecord;
import org.easybatch.flatfile.FixedLengthRecordMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class TradeDataRecordMapperTest {

	 private TradeDataRecordMapper<Bean> tradeDataRecordMapper;

	    @Mock
	    private StringRecord record;

	    @Before
	    public void setUp() throws Exception {
	    	tradeDataRecordMapper = new TradeDataRecordMapper<>(Bean.class,
	                new int[]{4, 2, 3},
	                new String[]{"field1", "field2", "field3"});
	        when(record.getPayload()).thenReturn("aaaabbccc");
	    }

	    @Test(expected = Exception.class)
	    public void testIllFormedRecord() throws Exception {
	        when(record.getPayload()).thenReturn("aaaabbcccd"); // unexpected record size
	        tradeDataRecordMapper.parseRecord(record);
	    }

	    @Test
	    public void testRecordParsing() throws Exception {
	        List<Field> fields = tradeDataRecordMapper.parseRecord(record);
	        assertThat(fields).hasSize(3);
	        assertThat(fields).extracting("rawContent")
	            .containsExactly("aaaa", "bb", "ccc");
	    }

	    @Test
	    public void testRecordParsingWithTrimmedWhitespaces() throws Exception {
	    	tradeDataRecordMapper.setTrimWhitespaces(true);
	        when(record.getPayload()).thenReturn(" aa bbcc ");
	        List<Field> fields = tradeDataRecordMapper.parseRecord(record);
	        assertThat(fields).hasSize(3);
	        assertThat(fields).extracting("rawContent")
	            .containsExactly("aa", "bb", "cc");
	    }

}
