package pakiet;
//Klasa GroupofBooks reprezentuje i przetrzymuje zbior obiektow typu Book. Takie zbiory maja nazwe oraz typ z trybu wy
//liczeniowego GroupType. Klasa spe³nia wszystkie wymagane za³o¿enia, poza zapisem do i odczytem z plików binarnych
//Jan Venulet 235542 Listopad
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Vector;
import java.util.List;

enum GroupType {
	VECTOR("vektor"), HASH_SET("hash set"), ARRAY_LIST("array list"), TREE_SET("tree set"), LINKED_LIST("linked list");

	String type;

	GroupType(String arg0) {
		this.type = arg0;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public String toString() {
		return type;
	}

	static public GroupType find(String arg0) {
		for (GroupType type : GroupType.values()) {
			if (arg0.equals(type.toString()))
				return type;
		}
		return null;
	}

	public Collection<Book> CreateCollection() throws BookException {
		switch (this) {
		case VECTOR:
			return new Vector<Book>();
		case ARRAY_LIST:
			return new ArrayList<Book>();
		case HASH_SET:
			return new HashSet<Book>();
		case LINKED_LIST:
			return new LinkedList<Book>();
		case TREE_SET:
			return new TreeSet<Book>();
		default:
			throw new BookException("You have entered wrong collection type!");
		}
	}

}

public class GroupofBooks implements Iterable<Book>, Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private GroupType collectionType;
	private Collection<Book> collection;

	@Override
	public String toString() {
		return this.name + " type : " + this.collectionType;
	}

	public void setName(String arg0) throws BookException {
		if ((arg0.equals("") || arg0 == null))
			throw new BookException("Group name cannot be void!");
		this.name = arg0;
	}

	public String getName() {
		return this.name;
	}

	public void setType(String arg0) throws BookException {
		GroupType tmp;
		tmp = GroupType.find(arg0);
		if (tmp == null)
			throw new BookException("Invalid collection type!");
		else
			setType(tmp);
	}

	public void setType(GroupType arg0) throws BookException {
		if (arg0 == null)
			throw new BookException("Invalid collection type!");
		if (this.collectionType == arg0)
			return;
		else {
			Collection<Book> tmp = this.collection;
			this.collection = collectionType.CreateCollection(); // tworzenie nowej kolekcji
			this.collectionType = arg0;
			for (Book book : tmp)
				collection.add(book);
		}
	}

	public GroupType getType() {
		return this.collectionType;
	}

	public GroupofBooks(String name, String type) throws BookException {
		this.setName(name);
		GroupType tmp;
		tmp = GroupType.find(type);
		this.collectionType = tmp;
		collection = this.collectionType.CreateCollection();
	}

	public GroupofBooks(String name, GroupType type) throws BookException {
		this.setName(name);
		this.collectionType = type;
		collection = this.collectionType.CreateCollection();
	}

	public GroupofBooks(String filepath) throws BookException {
		try {
			this.readFromFile(filepath);
		} catch (BookException e) {
			throw new BookException("File couldn't be found");
		}
	}

	@Override
	public Iterator<Book> iterator() {
		return collection.iterator();
	}

	public boolean add(Book arg0) {
		return this.collection.add(arg0);
	}

	public int size() {
		return collection.size();
	}

	public void sortByTitle() throws BookException {
		if (this.collectionType == GroupType.HASH_SET || this.collectionType == GroupType.TREE_SET) {
			throw new BookException("Sets cannot be sorted! Change your collection type to list");
		} else
			Collections.sort((List<Book>) collection);
	}

	public void sortByAuthor() throws BookException {
		if (this.collectionType == GroupType.HASH_SET || this.collectionType == GroupType.TREE_SET) {
			throw new BookException("Sets cannot be sorted! Change your collection type to list");
		} else
			Collections.sort((List<Book>) collection, new Comparator<Book>() {
				@Override
				public int compare(Book arg0, Book arg1) {
					return arg0.getAuthor().compareTo(arg1.getAuthor());
				}

			});
	}

	public void sortByYear() throws BookException {
		if (this.collectionType == GroupType.HASH_SET || this.collectionType == GroupType.TREE_SET) {
			throw new BookException("Sets cannot be sorted! Change your collection type to list");
		} else
			Collections.sort((List<Book>) collection, new Comparator<Book>() {
				@Override
				public int compare(Book arg0, Book arg1) {
					if (arg0.getYear() > arg1.getYear())
						return 1;
					else if (arg0.getYear() < arg1.getYear())
						return -1; // arg1 mniejsze od arg1
					return 0; // rowne
				}

			});
	}

	public void sortByType() throws BookException {
		if (this.collectionType == GroupType.HASH_SET || this.collectionType == GroupType.TREE_SET) {
			throw new BookException("Sets cannot be sorted! Change your collection type to list");
		} else
			Collections.sort((List<Book>) collection, new Comparator<Book>() {
				@Override
				public int compare(Book arg0, Book arg1) {
					return arg0.getType().toString().compareTo(arg1.getType().toString());
				}
			});
	}

	public void writeToFile(String filePath) throws BookException { // tu mo¿e trzeba zrobiæ tak jak w przykladowym
																	// programie !!!
		try (PrintWriter file = new PrintWriter(filePath);) {
			file.println(this.name);
			file.println(this.collectionType);
			file.println(this.size());
			for (Book book : this.collection) {
				book.writeToFile(filePath, file);
				///System.out.println("okejS\n");
			}
		} catch (FileNotFoundException e) {
			throw new BookException("File hasn't been found!");
		}
	}

	public GroupofBooks readFromFile(String filePath) throws BookException {
		try (FileReader file = new FileReader(filePath); BufferedReader bufferedReader = new BufferedReader(file)) {
			String groupName = bufferedReader.readLine();
			String groupType = bufferedReader.readLine();
			String size = bufferedReader.readLine();
			GroupofBooks group = new GroupofBooks(groupName, groupType);
			this.setName(groupName);
			GroupType tmp = GroupType.find(groupType);
			this.collectionType = tmp;
			collection = this.collectionType.CreateCollection();
			int i = Integer.parseInt(size);
			for (; i > 0; i--) {
				Book tmpBook = Book.readFromFile(filePath, 3 + 4 * this.size()); // zagmatwana konstrukcja, readLine()
																					// pomija od 3 co 4(bo tyle ma
																					// rekord Book)
				this.collection.add(tmpBook);
			}
			;
			return group;
		} catch (FileNotFoundException e) {
			throw new BookException("File hasn't been found!");
		} catch (IOException e) {
			throw new BookException("Input/Output error!");
		}
		// return null;
	}
}