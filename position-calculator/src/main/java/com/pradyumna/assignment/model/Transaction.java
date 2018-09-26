/**
 * 
 */
package com.pradyumna.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pradyumna
 *
 */
public class Transaction {
	@JsonProperty("TransactionId")
	private Integer transactionId;
	@JsonProperty("Instrument")
    private String instrument;
	@JsonProperty("TransactionType")
    private String transactionType;
	@JsonProperty("TransactionQuantity")
    private Integer transactionQuantity;
	/**
	 * @return the transactionId
	 */
	public Integer getTransactionId() {
		return transactionId;
	}
	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @return the transactionQuantity
	 */
	public Integer getTransactionQuantity() {
		return transactionQuantity;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @param transactionQuantity the transactionQuantity to set
	 */
	public void setTransactionQuantity(Integer transactionQuantity) {
		this.transactionQuantity = transactionQuantity;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", instrument=" + instrument + ", transactionType="
				+ transactionType + ", transactionQuantity=" + transactionQuantity + "]";
	}
	
}
