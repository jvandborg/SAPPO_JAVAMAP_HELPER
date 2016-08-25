package dk.firesquad.aii.config;

public class Driver {
	private String name;
	private String type;

	public Driver() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Driver [name=" + name + ", type=" + type + "]";
	}

}
