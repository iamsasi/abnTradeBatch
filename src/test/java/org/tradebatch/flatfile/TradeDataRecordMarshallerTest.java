package org.tradebatch.flatfile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.easybatch.core.record.Header;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TradeDataRecordMarshallerTest {
	 @Mock
	    private Header header;
	    @Mock
	    private Bean payload;
	    @Mock
	    private Record<Bean> record;

	    private TradeDataRecordMarshaller<Bean> marshaller;

	    @Before
	    public void setUp() throws Exception {
	        when(record.getHeader()).thenReturn(header);
	        when(record.getPayload()).thenReturn(payload);
	        when(payload.getField1()).thenReturn("aaa");
	        when(payload.getField2()).thenReturn("bb");
	        when(payload.getField3()).thenReturn("cccc");
	        marshaller = new TradeDataRecordMarshaller<>(Bean.class, "field1", "field2", "field3");
	    }

	    @Test
	    public void marshal() throws Exception {
	        String expectedPayload = "\"aaa\",\"bb\",\"cccc\"";
	        StringRecord actual = marshaller.processRecord(record);
	        System.out.println(actual.getPayload()); 
	        assertThat(actual.getHeader()).isEqualTo(header);
	        assertThat(actual.getPayload()).isEqualTo(expectedPayload);
	    }
	}