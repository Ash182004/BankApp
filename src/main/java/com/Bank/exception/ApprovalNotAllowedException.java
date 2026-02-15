package com.Bank.exception;

public class ApprovalNotAllowedException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApprovalNotAllowedException(String message) {
        super(message);
    }
}
