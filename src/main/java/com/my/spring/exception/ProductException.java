package com.my.spring.exception;

public class ProductException extends Exception
{
	public ProductException(String message)
	{
		super("ProductException-"+ message);
	}
	
	public ProductException(String message, Throwable cause)
	{
		super("ProducttException-"+ message,cause);
	}
}