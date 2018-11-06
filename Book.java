package pakiet;
//Klasa Book, czyli ksiazka zawiera w sobie 4 pola prywatne z getterami i setterami

//z odpowiednimi wymaganiami co do nich. Jest tu rowniez zaimplementowany tryb 
//wyliczeniowy enum BookType do okeslania rodzaju ksiazki
//Ca³oœæ zosta³a napisana przez: 
//Jan Venulet 235542

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

enum BookType {
	UNDEFINED("NA"), CRIMINAL("criminal"), COMEDY("comedy"), ROMANCE("romance"), HORROR("horror"), FANTASY("fantasy"),
	SCIENCE("science");

	private String bookType;

	private BookType(String type) {
		this.bookType = type;
	}

	@Override
	public String toString() {
		return this.bookType;
	}
}

class BookException extends Exception {
	private static final long serialVersionUID = 14L;

	public BookException(String message) {
		super(message);

	}
}

public class Book implements Comparable {

	private String title;
	private String author;
	private BookType type;
	private int editionYear;

	public Book(String newTitle, String newAuthor) throws BookException {
		this.setTitle(newTitle);
		this.setAuthor(newAuthor);
		this.setYear(0000);
		this.setType("NA");
	}

	public Book(String NewTitle, String Newauthor, int NewEditionYear) throws BookException {
		this(NewTitle, Newauthor);
		this.setYear(NewEditionYear);
	}

	public Book(String NewTitle, String NewAuthor, BookType NewType) throws BookException {
		this(NewTitle, NewAuthor);
		this.setType(NewType.toString());
	}

	public Book(String NewTitle, String NewAuthor, String NewType) throws BookException {
		this(NewTitle, NewAuthor);
		this.setType(NewType);
	}

	public Book(String NewTitle, String NewAuthor, BookType NewType, int NewEditionYear) throws BookException {
		this(NewTitle, NewAuthor, NewEditionYear);
		this.setType(NewType.toString());
	}

	public Book(String NewTitle, String NewAuthor, String NewType, int NewEditionYear) throws BookException {
		this(NewTitle, NewAuthor, NewEditionYear);
		this.setType(NewType);
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String NewAuthor) throws BookException {
		if (NewAuthor.equals("") || NewAuthor.length() > 40) {
			throw new BookException("Author name is too long or empty!");
		}
		this.author = NewAuthor;
		return;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String NewTitle) throws BookException {
		if (NewTitle.equals("") || NewTitle.length() > 100) {
			throw new BookException("Title is too long or empty!");
		}
		this.title = NewTitle;
		return;
	}

	public int getYear() {
		return this.editionYear;
	}

	public void setYear(int NewYear) throws BookException {
		if (NewYear > 2018) {
			throw new BookException("Year is incorrect(2018 is latest year possible)!");
		}
		this.editionYear = NewYear;
	}

	public BookType getType() {
		return this.type;
	}

	public void setType(String NewType) {
		for (BookType type : BookType.values()) {
			if (NewType.equals(type.toString())) {
				this.type = type;
				return;
			}
		}
		this.type = BookType.UNDEFINED;
	}

	@Override
	public String toString() {
		return "\"" + this.title + "\" " + this.author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + editionYear;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (editionYear != other.editionYear)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void writeToFile(String fileName) {
		File file = new File(fileName); // to tylko tak na prawde sciezka do pliku
		try (PrintWriter printWriter = new PrintWriter(file)) {
			printWriter.println(this.title);
			printWriter.println(this.author);
			printWriter.println(this.editionYear);
			printWriter.println(this.type.toString());
		} catch (FileNotFoundException e) {
			System.out.println("File error occured - file not found");
		}
	};

	public void writeToFile(String fileName, PrintWriter printWriter) { // to ten dziwny zapis dla GroupManagerWindow
		// File file = new File(fileName); // to tylko tak na prawde sciezka do pliku
		// try {//(PrintWriter printWriter = new PrintWriter(file)) {
		printWriter.println(this.title);

		//System.out.println(this.title + " goszlo\n");
		printWriter.println(this.author);
		//System.out.println(this.author + " goszlo\n");
		printWriter.println(Integer.toString(this.editionYear));
		//System.out.println(this.editionYear + " goszlo\n");
		printWriter.println(this.type.toString());
		//System.out.println(this.type + " goszlo\n");
		// } catch (FileNotFoundException e) {
		// System.out.println("File error occured - file not found");

	};

	public static Book readFromFile(String fileName) throws BookException {
		try (FileReader file = new FileReader(fileName); BufferedReader bufferedReader = new BufferedReader(file);) {
			// if (bufferedReader.ready())
			{
				String readTitle = bufferedReader.readLine();
				String readAuthor = bufferedReader.readLine();
				int readYear = Integer.parseInt(bufferedReader.readLine());
				String readType = bufferedReader.readLine();
				Book readBook = new Book(readTitle, readAuthor, readType, readYear);
				file.close();
				return readBook;
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		throw new BookException("Book hasn't been found in file");
	}

	public static Book readFromFile(String fileName, int fromWhere) throws BookException { // to ten dziwny odczyt dla
																							// GroupManagerWindow
		try (FileReader file = new FileReader(fileName); BufferedReader bufferedReader = new BufferedReader(file);) {
			// if (bufferedReader.ready())
			{
				for (int i = 0; i < fromWhere; i++) {
					String stupidString = bufferedReader.readLine(); // bezu¿yteczny string
				}
				String readTitle = bufferedReader.readLine();
				//System.out.println(readTitle + " poszlo\n");
				String readAuthor = bufferedReader.readLine();
				//System.out.println(readAuthor + " poszlo\n");
				String readYear = (bufferedReader.readLine());
				//System.out.println(readYear + " poszlo\n");
				String readType = bufferedReader.readLine();
				//System.out.println(readType + " poszlo\n");
				Book readBook = new Book(readTitle, readAuthor, readType, Integer.valueOf(readYear));
				file.close();
				return readBook;
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
		throw new BookException("Book hasn't been found in file");
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 == null)
			return 1; // przyjmuje, ze wieksze od null-a
		Book tmpBook = (Book) arg0;
		boolean tmp = this.getTitle().equals((tmpBook.getTitle()));
		if (tmp == true) {
			tmp = this.getAuthor().equals((tmpBook.getAuthor()));
			if (tmp == true) {
				tmp = this.getYear() > (tmpBook.getYear());
				if (tmp == true)
					return 1;
				tmp = this.getYear() == (tmpBook.getYear());
				if (tmp == true)
					return 0;
				return -1;

			}
			return this.getAuthor().compareTo(tmpBook.getAuthor());
		}
		return this.getTitle().compareTo(tmpBook.getTitle());
	}

	public void writeToBinaryFile(String filename) throws BookException {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));) {
			outputStream.writeObject(this);
			outputStream.close();
		} catch (FileNotFoundException e) {
			throw new BookException("Book hasn't been found in file");
		} catch (IOException e) {
			throw new BookException("Book hasn't been found in file");
		}
	}

}
