package PHI;

import java.util.Date;
import java.util.ArrayList;

public class CustomHealthData extends HealthData{
	private ArrayList<String> notes;

    public CustomHealthData(String name, Date date) {
        super(name, date);
        notes = new ArrayList<>();
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    @Override
    public String getMetric() {
        return "Notes";
    }

    @Override
    public String getData() {
        StringBuilder builder = new StringBuilder();
        for (String note : notes) {
            builder.append(note).append("\n");
        }
        return builder.toString();
    }
}
