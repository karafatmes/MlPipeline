package entities;

/**
 * @author sakes
 *
 */
public class Column {
	
	private String value;
	
	
	public Column(String value) {
		this.value=value;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
	
	public Column getByValue(String vallue) {
		if(this.value.equals(value)) {
			return this;
		}
		return null;
	}


}
