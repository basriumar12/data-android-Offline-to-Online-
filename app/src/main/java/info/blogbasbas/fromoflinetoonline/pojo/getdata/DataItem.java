package info.blogbasbas.fromoflinetoonline.pojo.getdata;


import com.google.gson.annotations.SerializedName;


public class DataItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}