package org.tradebatch.flatfile;

import org.easybatch.core.reader.AbstractFileRecordReader;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Header;
import org.easybatch.core.record.StringRecord;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Date;
import java.util.Scanner;


public class TradeDataRecordReader extends AbstractFileRecordReader {

    private Scanner scanner;
    private long currentRecordNumber;

    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param fileName to read records from
     */
    public TradeDataRecordReader(final String fileName) {
        this(fileName, Charset.defaultCharset().name());
    }

    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param fileName    to read records from
     * @param charsetName of the input file
     */
    public TradeDataRecordReader(final String fileName, final String charsetName) {
        this(new File(fileName), charsetName);
    }

    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param input to read records from
     */
    public TradeDataRecordReader(final File input) {
        this(input, Charset.defaultCharset().name());
    }

    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param input       to read records from
     * @param charsetName of the input file
     */
    public TradeDataRecordReader(final File input, final String charsetName) {
        super(input, Charset.forName(charsetName));
    }


    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param path of the file to read records from
     */
    public TradeDataRecordReader(final Path path) {
        this(path, Charset.defaultCharset().name());
    }


    /**
     * Create a new {@link TradeDataRecordReader}.
     *
     * @param path of the file to read records from
     * @param charsetName of the input file
     */
    public TradeDataRecordReader(final Path path, final String charsetName) {
        super(path.toFile(), Charset.forName(charsetName));
    }

    @Override
    public StringRecord readRecord() {
        Header header = new Header(++currentRecordNumber, getDataSourceName(), new Date());
        if (scanner.hasNextLine()) {
            return new StringRecord(header, scanner.nextLine());
        } else {
            return null;
        }
    }

    @Override
    public void open() throws Exception {
        currentRecordNumber = 0;
        scanner = new Scanner(file, charset.name());
    }

    @Override
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }

    private String getDataSourceName() {
        return file.getAbsolutePath();
    }

}
