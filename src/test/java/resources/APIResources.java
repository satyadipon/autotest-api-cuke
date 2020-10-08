package resources;
//enum is special class in java which has collection of constants or  methods
public enum APIResources {
	
	/* all endpoints */
	latestAPI("/api/latest"),
	pastDateAPI("/api/pastDate");
	private String resource;
	
	APIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
	
	public String getResource(String pastDate)
	{
		return resource.replaceAll("pastDate", pastDate);
	}
	

}
