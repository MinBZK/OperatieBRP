package org.jboss.soa.esb.samples.quickstart.hibernateaction;

public class GatewayRecord {
	private Long id;	
	private String data;
	private String status;

	public GatewayRecord() { }
	
	public GatewayRecord(String f_data, String f_status) {
		id = new Long(0);
		this.data = f_data;
		this.status = f_status;	
	}
	
	public Long getId() {
		return id;
	}
		
	public void setId(Long f_id) {
		this.id = f_id;
	}

	public String getData() {
		return data;
	}

	public void setData(String f_data) {
		this.data = f_data;
	}		

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String f_status) {
		this.status = f_status;
	}
	
	public String toString() {
		return "id [" + id + "]"
			+ "data [" + data + "]  " 
			+ "status [" + status + "] ";
	}
}

