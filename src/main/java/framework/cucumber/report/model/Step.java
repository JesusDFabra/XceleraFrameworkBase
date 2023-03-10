package framework.cucumber.report.model;

public class Step {
	private boolean isNewPage;
	private String sub;
	private byte[] image;
	
	public Step(String sub, byte[] image, boolean isNewPage) {
		this.sub = sub;
		this.image = image;
		this.isNewPage = isNewPage;
	}
	
	public String getSub() {
		return this.sub;
	}
	
	public byte[] getImage() {
		return this.image;
	}
	
	public boolean getIsNewPage() {
		return this.isNewPage;
	}
}
