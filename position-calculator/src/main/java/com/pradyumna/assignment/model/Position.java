/**
 * 
 */
package com.pradyumna.assignment.model;

/**
 * @author pradyumna
 *
 */
public class Position {

	private String instrument;
	private Integer account;
	private String accountType;
	private Integer quantity;
	private Integer delta;
	/**
	 * @return the instrument
	 */
	public String getInstrument() {
		return instrument;
	}
	/**
	 * @param instrument
	 * @param account
	 * @param accountType
	 * @param quantity
	 */
	public Position(String instrument, Integer account, String accountType, Integer quantity) {
		super();
		this.instrument = instrument;
		this.account = account;
		this.accountType = accountType;
		this.quantity = quantity;
	}
	/**
	 * @return the account
	 */
	public Integer getAccount() {
		return account;
	}
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param instrument the instrument to set
	 */
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(Integer account) {
		this.account = account;
	}
	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * @return the delta
	 */
	public Integer getDelta() {
		return delta;
	}
	/**
	 * @param delta the delta to set
	 */
	public void setDelta(Integer delta) {
		this.delta = delta;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [instrument=" + instrument + ", account=" + account + ", accountType=" + accountType
				+ ", quantity=" + quantity + ", delta=" + delta + "]";
	}
	
	
}
