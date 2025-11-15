package teliang.orm;

public class PrivatePojo {
	@PrimaryKey
	private Integer code;
	private String address;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "PrivatePojo [code=" + code + ", address=" + address + "]";
	}

}
