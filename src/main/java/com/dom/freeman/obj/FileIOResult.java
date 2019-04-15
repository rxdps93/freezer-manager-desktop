package com.dom.freeman.obj;

public class FileIOResult {

	private final FileIOStatus status;
	private final String message;
	private Exception cause;
	
	public FileIOResult(FileIOStatus status) {
		this.status = status;
		this.message = status.getDefaultMessage();
	}
	
	public FileIOResult(FileIOStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public FileIOStatus getStatus() {
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
		return this.status.equals(FileIOStatus.OPERATION_SUCCESS);
	}
}
