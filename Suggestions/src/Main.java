import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException {
		DatabaseManager db = new DatabaseManager();
		db.connect();
		Connection conn = db.getConnection();
		Statement stat = conn.createStatement();
		
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Admin> admins = new ArrayList<Admin>();
		//ArrayList<Category> categories = new ArrayList<Category>();
		ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
		ArrayList<Content> contents = new ArrayList<Content>();
		
		int sub_index = 0;
		//int cat_index = 0;
		int con_index = 0;
		
		Scanner in = new Scanner(System.in);
		
		boolean userLogin = false;
		User currentUser = new User();
		
		boolean adminLogin = false;
		Admin currentAdmin = new Admin();
		
		boolean running = true;
		
		while(running == true) {
			
			System.out.println("-----WELCOME TO THE SUGGESTIONS APP-----");
			System.out.println("[1] User");
			System.out.println("[2] Admin");
			System.out.println("[3] Exit");
			Login login = new Login();
			String sql = new String();
			
			
			String privileges = in.nextLine();
			
			switch(privileges) {
			case "1":
				userLogin = true;
				currentUser = login.loginOrRegisterUser(users);
				
				/*sql = "insert into user " + " (user_name, password)"
	                    + " values ('"+currentUser.getUsername()+"', '"+currentUser.getPassword()+"')";
				db.newStatement(sql);*/
				break;
			case "2":
				adminLogin = true;
				currentAdmin = login.LoginOrRegisterAdmin(admins);
				/*sql = "insert into admin " + " (admin_name, password)"
	                    + " values ('"+currentAdmin.getUsername()+"', '"+currentAdmin.getPassword()+"')";
				db.newStatement(sql);*/
				break;
			case "3":
				running = false;
				in.close();
				break;
			}
			
			
			
			
			
			//ADMIN INTERACTION
			while(adminLogin == true) {
				
				System.out.println(currentAdmin.getUsername()+" ADMIN MENU");
				System.out.println("[1] Create categories/subcategories");
				System.out.println("[2] Add new content");
				System.out.println("[3] View requests");
				System.out.println("[4] Add subcategories");
				System.out.println("[5] Remove categories/subcategories");
				System.out.println("[6] Show all");
				System.out.println("[7] Logout");
				
				String interaction = in.nextLine();

				switch(interaction) {
				case "1":
					System.out.print("Create a new category:");
					Category cat = new Category();
					cat.setName(in.nextLine());
					
					sql = "insert into categories " + " (category_name)"
		                    + " values ('"+cat.getName()+"')";
					db.newStatement(sql);
					
					System.out.println("Adding subcategories...");
					boolean addSubcategories = true;
					while(addSubcategories == true) {
						System.out.print("Subcategory name:");
						String name = in.nextLine();
						Subcategory sub = new Subcategory();
						sub.setName(name);
						//sub.setId(sub_index);
						//sub_index++;
						//cat.setSubcategories(sub);
						//subcategories.add(sub);
						String selectsql = "select category_id from categories where category_name = '"+cat.getName()+"'";
						ResultSet rs = stat.executeQuery(selectsql);
						int selected = 0;
						while(rs.next()) {
							selected = rs.getInt("category_id");
						}
						
						sql = "insert into subcategories " + " (subcategory_name, category_id)"
			                    + " values ('"+name+"', '"+selected+"' )";
						db.newStatement(sql);
						
						System.out.println("Subcategory "+sub.getName()+" has been added to "+cat.getName());
						
						System.out.println("Add another subcategory Y/N?");
						if(in.nextLine().equals("n")) addSubcategories=false;
					}
					break;
					
				case "2":
					boolean addContent = true;
					while(addContent == true) {
						System.out.println("Choose a category:");
						Category c = new Category();
						currentAdmin.printCategories();
						int selected_cat = in.nextInt();
						in.nextLine();
						c = currentAdmin.searchCategories(selected_cat);
						
						System.out.println("Choose a subcategory");
						int s = 0;
						currentAdmin.searchCategories(selected_cat).printSubcategories(selected_cat);
						int selected_sub = in.nextInt();
						in.nextLine();
						s = c.searchSubcategories(selected_sub);
						
						System.out.println("Write content:");
						String name = in.nextLine();
						Content con = new Content();
						sql = "insert into content " + " (content_name, likes, subcategory_id)"
			                    + " values ('"+name+"', "+0+", "+selected_sub+")";
						db.newStatement(sql);
						con.setName(name);
						//con.setId(con_index);
						//con.setLikes(0);
						//con_index++;
						//s.setContents(con);
						//contents.add(con);
						System.out.println(name+" was added");
						
						System.out.println("Add more content Y/N?");
						if(in.nextLine().equals("n")) addContent = false;
					}
					break;
					
				case "3":
					boolean checkRequest = true;
					while(checkRequest==true) {
						String s = currentAdmin.handleRequest();
						System.out.println(s);
						
						System.out.println("No more requests");
						checkRequest = false;
					}
					break;
				case "4":
					boolean chosen = true;
					while(chosen == true) {
						System.out.println("Choose a category to edit:");
						currentAdmin.printCategories();
						Category c = new Category();
						
						int position=0;
						int selected_cat = in.nextInt();
						in.nextLine();
						currentAdmin.searchCategories(selected_cat);
						position = c.getId(selected_cat);
						
						boolean addSub = true;
						while(addSub == true) {
							System.out.print("Write new subcategory: ");
							String name = in.nextLine();
							Subcategory s = new Subcategory();
							sql = "insert into subcategories " + " (subcategory_name, category_id)"
				                    + " values ('"+name+"', '"+position+"' )";
							db.newStatement(sql);
							s.setName(name);
							//subcategories.add(s);
							c.setSubcategories(s);
							//System.out.println(s.getName()+" was added to "+c.getName());
							
							System.out.println("Add another subcategory Y/N?");
							if(in.nextLine().equals("n")) addSub = false;
						}
						System.out.println("Edit another category Y/N?");
						if(in.nextLine().equals("n")) chosen = false;
					}
					break;
					
				case "5":
					boolean deleting = true;
					while(deleting == true) {
						System.out.println("Delete [1] Category or [2] Subcategory?");
						if(in.nextLine().equals("1")) {
							System.out.println("Select category to delete");
							currentAdmin.printCategories();	
							int selected_cat = in.nextInt();
							in.nextLine();
							currentAdmin.removeCategories(selected_cat);
						}
						else {
							System.out.println("Choose a category");
							Category c = new Category();
							currentAdmin.printCategories();
							int selected_cat = in.nextInt();
							in.nextLine();
							c = currentAdmin.searchCategories(selected_cat);
							
							boolean chooseSub = true;
							while(chooseSub == true) {
								System.out.println("Delete a subcategory:");
								c.printSubcategories(selected_cat);
								int selected_sub = in.nextInt();
								in.nextLine();
								c.removeSubcategories(selected_sub);
								System.out.println("Remove another subcategory Y/N?");
								if(in.nextLine().equals("n")) chooseSub = false;
							}
						}
						System.out.println("Delete more Y/N?");
						if(in.nextLine().equals("n")) deleting = false;
					}

					break;
				case "6": 
					currentAdmin.printCategories();
					currentAdmin.printSubCategories();
					currentAdmin.printAllContent();
					break;
				case "7":
					adminLogin = false;
					currentAdmin = null;
					break;
				}
				
				
			}
			
			//USER INTERACTION
			while(userLogin == true) {
				boolean found = false;
				String check_user = "select user_id from requests";
				int user_id = currentUser.getUserId(currentUser.getUsername());
				ResultSet rs = stat.executeQuery(check_user);
				String check_request = new String();
				while(rs.next()) {
					int user_req = rs.getInt("user_id");
					if(user_req==user_id) {
						found =true;
					}
				}
				
				if(found==true) {
					check_request = "select request_name,is_approved from requests where user_id="+user_id;
					ResultSet rs2 = stat.executeQuery(check_request);
					while(rs2.next()) {
						boolean is_approved = rs2.getBoolean("is_approved");
						System.out.println(rs2.getString("request_name")+" is "+is_approved);
					}
				}
				
				
				
				System.out.println(currentUser.getUsername()+" USER MENU");
				System.out.println("[1] Choose favorites");
				System.out.println("[2] Remove favorites");
				System.out.println("[3] View favorites");
				System.out.println("[4] View content");
				System.out.println("[5] Send request");
				System.out.println("[6] Logout");
				
				
				String interaction = in.nextLine();
				
				switch(interaction) {
				case "1":
					boolean favorite_cat = true;
					while(favorite_cat == true) {
						System.out.println("Choose your favorite category from: ");
						Category c = new Category();
						currentAdmin.printCategories();
						//currentUser.printFavoriteCategories(user_id);
						
						int position=0;
						int selected_cat = in.nextInt();
						in.nextLine();
						currentUser.setFavoriteCategories(user_id, selected_cat);
						
						//System.out.println(c.getName()+" was added to your favorite categories");
						
						boolean favorite_sub = true;
						while(favorite_sub == true) {
							System.out.println("Choose your favorite subcategory from: ");
							int s = 0;
							c.printSubcategories(selected_cat);
							int selected_sub = in.nextInt();
							in.nextLine();
							s = c.searchSubcategories(selected_sub);
							currentUser.setFavoriteSubcategories(user_id,s);
							//System.out.println(s.getName()+" was added to your favorite subcategories");
							
							System.out.println("Add more favorite subcategories Y/N?");
							if(in.nextLine().equals("n")) favorite_sub = false;
						}
						
						System.out.println("Add more favorite categories Y/N?");
						if(in.nextLine().equals("n")) favorite_cat = false;
					}
					break;
				case "2":
					boolean remove_cat = true;
					while(remove_cat == true) {
						System.out.println("Choose a category to remove from: ");
						Category c = new Category();
						currentUser.printFavoriteCategories(user_id);
						int selected_cat = in.nextInt();
						in.nextLine();
						int nr = currentUser.searchFavoriteCategories(user_id,selected_cat);

						boolean remove_sub = true;
						while(remove_sub == true) {
							System.out.println("Choose to remove subcategory from: ");
							int s = 0;
							c.printSubcategories(nr);
							int selected_sub = in.nextInt();
							in.nextLine();
							s = c.searchSubcategories(selected_sub);
							currentUser.removeFavoriteSubcategories(selected_sub);
							
							System.out.println("Remove more subcategories Y/N?");
							if(in.nextLine().equals("n")) remove_sub = false;
						}
						
						System.out.println("Remove "+c.getName()+" from favorite categories Y/N?");
						if(in.nextLine().equals("y")) {
							currentUser.removeFavoriteCategories(nr);
							System.out.println(c.getName()+" was removed from your favorite categories");
						}
						System.out.println("Remove more Y/N?");
						if(in.nextLine().equals("n")) remove_cat = false;
					}
					break;
				case "3":
					System.out.println("Your favorite categories: ");
					currentUser.printFavoriteCategories(user_id);
					System.out.println("Your favorite subcategories: ");
					currentUser.printFavoriteSubcategories(user_id);
					break;
				case "4":
					boolean likeContent = true;
					while(likeContent == true) {
						System.out.println("ALL CONTENT");
						currentUser.printAllContent();
					
						System.out.println("Select the content you like:");
						int liked = 0;
						int selected_content = in.nextInt();
						in.nextLine();
						liked = currentUser.searchContent(selected_content);
						currentUser.setLikedContent(liked);
						
						System.out.println("Like more content Y/N?");
						if(in.nextLine().equals("n")) {
							likeContent = false;
							System.out.println("Your liked content:");
							currentUser.printAllContent();
						}
					}
					break;
				case "5":
					boolean addRequest = true;
					while(addRequest == true) {
						System.out.println("Write a category request to the admin");
						String request = in.nextLine();
						
						sql = "insert into requests " + " (request_name, user_id)"
			                    + " values ('"+request+"', "+user_id+" )";
						int nr = stat.executeUpdate(sql);
						System.out.println(nr);
						System.out.println("Do you want to send another request Y/N?");
						if(in.nextLine().equals("n")) addRequest = false;
					}
					break;
					
				case "6":
					userLogin = false;
					currentUser = null;
					break;
				}
			}
	
		}
		
	}

}
