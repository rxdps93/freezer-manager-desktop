package com.dom.freeman.obj;

public class OperationResult {

	private final OperationStatus status;
	private final String message;
	private Exception cause;
	
	public OperationResult(OperationStatus status) {
		this.status = status;
		this.message = status.getDefaultMessage();
	}
	
	public OperationResult(OperationStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public OperationStatus getStatus() {
		return this.status;
	}
	
	public Exception getCause() {
		return this.cause;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setCause(Exception cause) {
		this.cause = cause;
	}
	
	public boolean isSuccess() {
		return this.status.equals(OperationStatus.OPERATION_SUCCESS) ||
				this.status.equals(OperationStatus.OPERATION_PERMITTED);
	}
}
