
public class Content {
	private int id;
	private String name;
	private int likes;
	
	public Content() {
		name = "";
		likes = 0;
		id = 0;
	}
	public Content (String con, int nr, int new_id) {
		name = con;
		likes = nr;
		id = new_id;
	}
	
	public void setId(int nr) {
		id = nr;
	}
	public int getId() {
		return id;
	}
	
	public void setName(String con) {
		name = con;
	}
	public String getName() {
		return name;
	}
	
	public void setLikes(int nr) {
		likes = nr;
	}
	public int getLikes() {
		return likes;
	}
}
