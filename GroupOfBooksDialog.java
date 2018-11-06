package pakiet;
//Klasa GroupofBooksDialog s³u¿y do modyfikowania grupy ksi¹¿ek, która jest kolekjc¹ o podanym typie. Posiada równie¿ nazwê. 
//W tym oknie dialogowym mo¿na edytowaæ, tworzyæ i zapisywaæ wybrane obiekty typu Book do pliku o wybranej, przez siebie nazwie.
//Jan Venulet 235542 Listopad
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.HTMLDocument.Iterator;


public class GroupOfBooksDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	GroupofBooks currentGroup;
	static Object[] columns = { "Title", "Author", "Year", "Type" };
	static Object[][] tableData = null;
	JLabel groupNameLabel = new JLabel("Group name: ");
	JTextField groupNameField = new JTextField(10);
	JLabel typeLabel = new JLabel("Group type: ");
	JTextField typeField = new JTextField(10);
	DefaultTableModel defaultTableModel = new DefaultTableModel(columns, 0);
	JTable table = new JTable(defaultTableModel);
	JScrollPane scrollPane = new JScrollPane(table);
	JButton createButton = new JButton("Create new book");
	JButton deleteButton = new JButton("Delete current book");
	JButton editButton = new JButton("Modify current book data");
	JButton readButton = new JButton("Read book data from file");
	JButton writeButton = new JButton("Write book data to file");

	JMenuBar menuBar = new JMenuBar();

	JMenu menu = new JMenu("File");
	JMenu menuSort = new JMenu("Sort");
	JMenu menuGroup = new JMenu("Group preferences");

	JMenuItem newItem = new JMenuItem("New book");
	JMenuItem deleteItem = new JMenuItem("Delete book");
	JMenuItem editItem = new JMenuItem("Edit book");
	JMenuItem saveItem = new JMenuItem("Save book to file");
	JMenuItem openItem = new JMenuItem("Open book from file");
	JMenuItem aboutItem = new JMenuItem("About");
	JMenuItem exitItem = new JMenuItem("Exit");
	JMenuItem sortTitle = new JMenuItem("Sort by title");
	JMenuItem sortAuthor = new JMenuItem("Sort by author");
	JMenuItem sortType = new JMenuItem("Sort by type");
	JMenuItem sortYear = new JMenuItem("Sort by year");
	JMenuItem groupName = new JMenuItem("Change group name");
	JMenuItem groupType = new JMenuItem("Change group type");

	GroupOfBooksDialog(GroupofBooks currentGroup, Window parent) {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		//setTitle("Modification of group " + currentGroup.getName());
		setTitle("Group Manager Window");
		this.currentGroup = currentGroup;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 500);
		this.setResizable(false);
		menu.add(newItem);
		menu.add(deleteItem);
		menu.add(editItem);
		menu.add(saveItem);
		menu.add(openItem);
		menu.add(aboutItem);
		menu.add(exitItem);
		menuSort.add(sortTitle);
		menuSort.add(sortAuthor);
		menuSort.add(sortType);
		menuSort.add(sortYear);
		menuBar.add(menu);
		menuBar.add(menuSort);
		menuBar.add(menuGroup);
		menuGroup.add(groupName);
		menuGroup.add(groupType);
		newItem.addActionListener(this);
		deleteItem.addActionListener(this);
		editItem.addActionListener(this);
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		sortTitle.addActionListener(this);
		sortAuthor.addActionListener(this);
		sortType.addActionListener(this);
		sortYear.addActionListener(this);
		groupType.addActionListener(this);
		groupName.addActionListener(this);
		this.setJMenuBar(menuBar);
		updateTable();
		JPanel panel = new JPanel();
		panel.add(groupNameLabel);
		groupNameField.setEditable(false);
		typeField.setEditable(false);
		scrollPane.setPreferredSize(new Dimension(350, 280)); // dlaczego setSize nie dzia³a!!!!
		scrollPane.setBorder(BorderFactory.createTitledBorder("Lista osób:"));
		createButton.addActionListener(this);
		deleteButton.addActionListener(this);
		editButton.addActionListener(this);
		readButton.addActionListener(this);
		writeButton.addActionListener(this);
		panel.add(groupNameField);
		panel.add(typeLabel);
		panel.add(typeField);
		panel.add(scrollPane);
		panel.add(createButton);
		panel.add(deleteButton);
		panel.add(editButton);
		panel.add(readButton);
		panel.add(writeButton);
		this.showCurrentGroup();
		this.setContentPane(panel);
		panel.setVisible(true);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			GroupofBooks currentGroup = new GroupofBooks("Hello", GroupType.VECTOR);
			currentGroup.add(new Book("ABC-niemieckiego", "Rokita" , "science" ,2009 ));
			currentGroup.add(new Book("Poligon", "Si Xan", 357));
			currentGroup.add(new Book("Vicoria", "Paulsen", "romance" ,1913));
			currentGroup.add(new Book("Matrix", "Wachowski", "fantasy", 1999));
			currentGroup.add(new Book("Vesuvio", "Paulsen", "romance" ,1918));
			currentGroup.add(new Book("Vigaro", "Paulsen", "romance", 1920));
			currentGroup.add(new Book("Vigaro", "Abatiz", "romance", 1962));
			currentGroup.add(new Book("Vigaro", "Wood", "romance", 1999));
			currentGroup.add(new Book("White House", "Underwood", 1999));
			JFrame frame = new JFrame("231");
			GroupOfBooksDialog dialog = new GroupOfBooksDialog(currentGroup, frame);
		} catch (BookException e) {
			e.getMessage();
		}
	}

	public void updateTable() {
		this.defaultTableModel.setRowCount(0);
		for (Book book : this.currentGroup) {
			String[] tmp = { book.getTitle(), book.getAuthor(), Integer.toString(book.getYear()),
					book.getType().toString() };
			this.defaultTableModel.addRow(tmp);
		}
	}

	public void showCurrentGroup() {
		this.groupNameField.setText(currentGroup.getName());
		this.typeField.setText(currentGroup.getType().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		try {
			if (source == this.exitItem) {
				this.dispose();
			}

			if ((source == this.deleteItem) || (source == this.deleteButton)) {
				int tmp = this.getTableSelect();
				if (tmp >= 0) {
					java.util.Iterator<Book> iterator = this.currentGroup.iterator();
					while (tmp >= 0) {
						iterator.next();
						tmp--;
					}
					iterator.remove();
				}
			}
			if ((source == this.editItem) || (source == this.editButton)) {
				int tmp = this.getTableSelect();
				if (tmp >= 0) {
					java.util.Iterator<Book> iterator = this.currentGroup.iterator();
					while (tmp > 0) {
						iterator.next();
						tmp--;
					}
					BookDialog.returnBook(this, (Book) iterator.next());
					tmp--;
				}
			}
			if (source == this.createButton || source == this.newItem) {
				Book tmp = BookDialog.returnBook(this, null);
				currentGroup.add(tmp);
				System.out.println("chyba ok" + tmp.getTitle());
			}
			if (source == aboutItem) {
				JOptionPane.showMessageDialog(this,
						"GroupOfBooksManager(c)\nDeveloped by:Jan Maksymilain Venulet\nOctober 2018", "About",
						JOptionPane.INFORMATION_MESSAGE);

			}
			if (source == this.writeButton || source == this.saveItem) {
				int tmp = this.getTableSelect();
				if (tmp >= 0) {
					java.util.Iterator<Book> iterator = this.currentGroup.iterator();
					while (tmp > 0) {
						iterator.next();
						tmp--;
					}
					String fileName = JOptionPane.showInputDialog(this,
							"Enter file name you want to save current book: ", "Save book");
					Book book = (Book) iterator.next();
					book.writeToFile(fileName);
				}
			}
			if (source == this.readButton || source == this.openItem) {
				String fileName = JOptionPane.showInputDialog(this, "Enter file name you want to load book from: ",
						"Load book");
				Book book = new Book("default", "default");
				Book.readFromFile(fileName);
				System.out.println(fileName + book.getAuthor() + " " + book.getTitle());
				currentGroup.add(Book.readFromFile(fileName));
			}
			
			if (source == this.sortAuthor)
			{
				this.currentGroup.sortByAuthor();
			}
			
			if (source == this.sortTitle)
			{
				this.currentGroup.sortByTitle();
			}
			if (source == this.sortYear)
			{
				this.currentGroup.sortByYear();
			}
			if (source == this.sortType)
			{
				this.currentGroup.sortByType();
			}
			if (source == this.groupType)
			{
				String newType = JOptionPane.showInputDialog(this, "Enter new group type: " , "Change type of group", JOptionPane.INFORMATION_MESSAGE);
				this.currentGroup.setType(newType);
			}
			if (source == this.groupName)
			{
				String newName = JOptionPane.showInputDialog(this, "Enter new group name: " , "Change group name", JOptionPane.INFORMATION_MESSAGE);
				System.out.println(newName);
				this.currentGroup.setName(newName);
			}
		} catch (BookException error) {
			JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);;
		}
		updateTable();
		showCurrentGroupOfBooks();
		
	}

	public void showCurrentGroupOfBooks()
	{
		if (this.currentGroup != null)
		{
			this.groupNameField.setText(this.currentGroup.getName());
			this.typeField.setText(this.currentGroup.getType().toString());
		} else {
			this.groupNameField.setText("");
			this.typeField.setText("");
		}
	}
	int getTableSelect() {
		int tmp = table.getSelectedRow();
		if (tmp < 0) {
			JOptionPane.showMessageDialog(this, "There is no selected book!", "Error", 0);
			return tmp;
		} else
			return tmp;
	}

}
