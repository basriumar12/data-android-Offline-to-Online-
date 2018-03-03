package info.blogbasbas.fromoflinetoonline.pojo.getdata;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import info.blogbasbas.fromoflinetoonline.pojo.Nama;


public class ResponseGetData{

	@SerializedName("data")
	private List<Nama> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setData(List<Nama> data){
		this.data = data;
	}

	public List<Nama> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetData{" + 
			"data = '" + data + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}