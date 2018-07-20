package org.tradebatch.flatfile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author Sasi Nataraja
 *
 * 
 */
public class TradeData implements Serializable {	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordCode;
	protected String clientType;
	protected int clientNumber;
	protected int accountNumber;
	protected int subaccountNumber;
	private String oppositePartyCode;
	protected String productGroupCode;
	protected String exchangeCode;
	protected String symbol;
	protected Date expirationDate;
	private String currencyCode;
	private String movementCode;
	private String buySellCode;
	private int  quantityLongSign;
	protected BigDecimal quantityLong;
	private int quantityShortSign;
	protected BigDecimal quantityShort;
	private BigDecimal exchangeBrokerFee;
	private String exchangeBrokerFee_DC;
	private String exchangeBrokerFee_CurrCode;
	private BigDecimal clearningFee;
	private String clearningFee_DC;
	private String clearningFee_CurrCode;
	private BigDecimal commission;
	private String commission_DC;
	private String commission_CurrCode;
	private Date transactionDate;
	private BigDecimal futureReference;
	private int ticketNumber;
	private BigDecimal externalNumber;
	private BigDecimal transactionPrice;
	private String traderInitials;
	private int oppositeTraderID;
	private String openCloseCode;
	private String filler;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public void setClientNumber(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setSubaccountNumber(int subaccountNumber) {
		this.subaccountNumber = subaccountNumber;
	}

	public void setOppositePartyCode(String oppositePartyCode) {
		this.oppositePartyCode = oppositePartyCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setMovementCode(String movementCode) {
		this.movementCode = movementCode;
	}

	public void setBuySellCode(String buySellCode) {
		this.buySellCode = buySellCode;
	}

	public void setQuantityLongSign(int quantityLongSign) {
		this.quantityLongSign = quantityLongSign;
	}

	public void setQuantityLong(BigDecimal quantityLong) {
		this.quantityLong = quantityLong;
	}

	public void setQuantityShortSign(int quantityShortSign) {
		this.quantityShortSign = quantityShortSign;
	}

	public void setQuantityShort(BigDecimal quantityShort) {
		this.quantityShort = quantityShort;
	}

	public void setExchangeBrokerFee(BigDecimal exchangeBrokerFee) {
		this.exchangeBrokerFee = exchangeBrokerFee;
	}

	public void setExchangeBrokerFee_DC(String exchangeBrokerFee_DC) {
		this.exchangeBrokerFee_DC = exchangeBrokerFee_DC;
	}

	public void setExchangeBrokerFee_CurrCode(String exchangeBrokerFee_CurrCode) {
		this.exchangeBrokerFee_CurrCode = exchangeBrokerFee_CurrCode;
	}

	public void setClearningFee(BigDecimal clearningFee) {
		this.clearningFee = clearningFee;
	}

	public void setClearningFee_DC(String clearningFee_DC) {
		this.clearningFee_DC = clearningFee_DC;
	}

	public void setClearningFee_CurrCode(String clearningFee_CurrCode) {
		this.clearningFee_CurrCode = clearningFee_CurrCode;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public void setCommission_DC(String commission_DC) {
		this.commission_DC = commission_DC;
	}

	public void setCommission_CurrCode(String commission_CurrCode) {
		this.commission_CurrCode = commission_CurrCode;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setFutureReference(BigDecimal futureReference) {
		this.futureReference = futureReference;
	}

	public void setTicketNumber(int ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public void setExternalNumber(BigDecimal externalNumber) {
		this.externalNumber = externalNumber;
	}

	public void setTransactionPrice(BigDecimal transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public void setTraderInitials(String traderInitials) {
		this.traderInitials = traderInitials;
	}

	public void setOppositeTraderID(int oppositeTraderID) {
		this.oppositeTraderID = oppositeTraderID;
	}

	public void setOpenCloseCode(String openCloseCode) {
		this.openCloseCode = openCloseCode;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public TradeData(String recordCode) {
		
	}
	
	public TradeData() {
		
	}

}
