package PHI;

import java.util.Date;

public abstract class HealthData {
	 private String name;
	    private Date date;

	    public HealthData(String name, Date date) {
	        this.name = name;
	        this.date = date;
	    }

	    public String getName() {
	        return name;
	    }

	    public Date getDate() {
	        return date;
	    }

	    public abstract String getMetric();

	    public abstract String getData();
}
