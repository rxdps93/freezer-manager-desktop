package com.dom.freeman.obj;

public enum FileIOStatus {

	OPERATION_SUCCESS("The operation completed successfully"),
	OPERATION_FAILURE("The operation could not be completed"),
	OPERATION_NOT_PERMITTED("The current user is not permitted to perform that operation");
	
	private String message;
	
	private FileIOStatus(String defaultMsg) {
		this.message = defaultMsg;
	}
	
	public String getDefaultMessage() {
		return this.message;
	}
}
