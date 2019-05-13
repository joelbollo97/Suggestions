import java.util.ArrayList;

public class Subcategory {
	private int id;
	private String name;
	private ArrayList<Content> contents = new ArrayList<Content>();
	
	public Subcategory() {
		name = "";
		id = 0;
	}
	public Subcategory(String sub,int nr) {
		name = sub;
		id = nr;
	}
	
	public void setId(int nr) {
		id = nr;
	}
	public int getId() {
		return id;
	}
	
	public void setName(String cat) {
		name = cat;
	}
	public String getName() {
		return name;
	}
	
	public void setContents(Content con) {
		contents.add(con);
	}
	public ArrayList<Content> getContents(){
		return contents;
	}
	
	public void removeContent(Content con) {
		contents.remove(con);
	}
	
	public void printContent() {
		for(int i=0;i<contents.size(); i++) System.out.println("["+contents.get(i).getId()+"] "+contents.get(i).getName()+" "+contents.get(i).getLikes());
	}
}
