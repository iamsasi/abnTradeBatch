package org.tradebatch.flatfile;

import java.math.BigDecimal;
/**
 * 
 * @author Sasi Nataraja
 *
 */
public class TradeReport extends TradeData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientInfo;
	private String productInfo;
	private BigDecimal totalTransactionAmount;

	
	public TradeReport() {
	
	}
	
	public String getClientInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append(clientType);
		builder.append(clientNumber);
		builder.append(accountNumber);
		builder.append(subaccountNumber);
		this.clientInfo=builder.toString();
		return clientInfo;
	}


	public String getProductInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append(exchangeCode);
		builder.append(productGroupCode);
		builder.append(symbol);
		builder.append(expirationDate);
		this.productInfo=builder.toString();
		return productInfo;
	}


	public BigDecimal getTotalTransactionAmount() {
		this.totalTransactionAmount=quantityLong.subtract(quantityShort);
		return totalTransactionAmount;
	}


}