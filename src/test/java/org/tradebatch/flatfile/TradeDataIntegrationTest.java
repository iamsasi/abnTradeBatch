
package org.tradebatch.flatfile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.easybatch.core.record.PayloadExtractor.extractPayloads;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.job.JobStatus;
import org.easybatch.core.processor.RecordCollector;
import org.easybatch.core.record.Record;
import org.junit.Test;

public class TradeDataIntegrationTest {


	  /*
     * Test fixed-length record processing
     */

    @Test
    public void testFlrProcessing() throws Exception {

        File dataSource = new File(getFileUri("/tradedata.txt"));

        RecordCollector<TradeData> recordCollector = new RecordCollector<>();
        Job job = JobBuilder.aNewJob()
                .reader(new TradeDataRecordReader(dataSource))
                .mapper(new TradeDataRecordMapper<>(TradeData.class, new int[]{3,4,4,4},
                        new String[]{"recordCode","clientType","clientNumber","accountNumber"}))
                .processor(recordCollector)
                .build();

        JobReport jobReport = new JobExecutor().execute(job);

        assertThat(jobReport).isNotNull();
        assertThat(jobReport.getMetrics().getErrorCount()).isEqualTo(0);
        assertThat(jobReport.getMetrics().getFilterCount()).isEqualTo(0);
        assertThat(jobReport.getMetrics().getWriteCount()).isEqualTo(1);
        assertThat(jobReport.getStatus()).isEqualTo(JobStatus.COMPLETED);
        assertThat(jobReport.getMetrics().getReadCount()).isEqualTo(1);

        List<Record<TradeData>> records = recordCollector.getRecords();
        List<TradeData> persons = extractPayloads(records);

        assertThat(persons).hasSize(1);
    }
    private URI getFileUri(String fileName) throws URISyntaxException {
        return this.getClass().getResource(fileName).toURI();
    }

}
