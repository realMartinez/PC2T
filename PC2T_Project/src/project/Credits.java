package project;


public enum Credits {
	ACTOR("actor"),
	ANIMATOR("animator");

	private String type;
	
	Credits(String string) {
		type = string;
	}
	
	public String getType(){
		return type;
	}
	
	}
