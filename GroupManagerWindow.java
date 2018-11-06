package pakiet;
//Program obiektowy s³u¿¹cy do obs³ugi wielu obiektów klasy GroupOfBooks
//Spe³niane s¹ wszystkie wymagane funkcje poza zapisem i odczytem dla plików binarnych
//Jan Venulet 235542 Listopad
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GroupManagerWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Vector<GroupofBooks> groups = new Vector();
	// GroupofBooks[] groups;
	// int recordCount;
	static Object[] columns = { "Group name", "Size", "Type" };
	static Object[][] tableData = null;
	DefaultTableModel defaultTableModel = new DefaultTableModel(columns, 0);
	JTable table = new JTable(defaultTableModel);
	JScrollPane scrollPane = new JScrollPane(table);
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("File");
	JMenuItem newItem = new JMenuItem("New group");
	JMenuItem deleteItem = new JMenuItem("Delete selected group");
	JMenuItem editItem = new JMenuItem("Edit selected group");
	JMenuItem saveItem = new JMenuItem("Save selected group");
	JMenuItem openItem = new JMenuItem("Open group from file");
	JMenu menuAbout = new JMenu("About");
	JMenuItem aboutItem = new JMenuItem("About");
	JMenuItem exitItem = new JMenuItem("Exit");
	JButton createButton = new JButton("Create group");
	JButton deleteButton = new JButton("Delete selected group");
	JButton editButton = new JButton("Modify selected group");
	JButton readButton = new JButton("Read group from file");
	JButton writeButton = new JButton("Save selected group to file");
	JButton exitButton = new JButton("Exit");

	public GroupManagerWindow() {
		super("Group Manager by Venulet Jan 235542");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 500);
		this.setResizable(false);
		//TestDataSetGenerate(); // To mo¿na zakomentowaæ -- tylko wprowadza puste grupy
		// recordCount=0;
		menu.add(newItem);
		menu.add(deleteItem);
		menu.add(editItem);
		menu.add(saveItem);
		menu.add(openItem);
		menuAbout.add(aboutItem);
		menu.add(exitItem);
		menuBar.add(menu);
		menuBar.add(menuAbout);
		newItem.addActionListener(this);
		deleteItem.addActionListener(this);
		editItem.addActionListener(this);
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		aboutItem.addActionListener(this);
		exitItem.addActionListener(this);
		createButton.addActionListener(this);
		deleteButton.addActionListener(this);
		editButton.addActionListener(this);
		readButton.addActionListener(this);
		writeButton.addActionListener(this);
		exitButton.addActionListener(this);
		scrollPane.setPreferredSize(new Dimension(350, 280));
		scrollPane.setBorder(BorderFactory.createTitledBorder("Groups of books"));
		this.setJMenuBar(menuBar);
		updateTable();
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		panel.add(createButton);
		panel.add(deleteButton);
		panel.add(editButton);
		panel.add(readButton);
		panel.add(writeButton);
		panel.add(exitButton);
		this.setContentPane(panel);
		panel.setVisible(true);
		this.setVisible(true);
	}

	public void updateTable() {
		this.defaultTableModel.setRowCount(0);
		if (this.groups == null) {
			return;
		}
		for (GroupofBooks tmpGroup : this.groups) {
			if (tmpGroup != null) {
				String[] tmp = { tmpGroup.getName(), Integer.toString(tmpGroup.size()), tmpGroup.getType().toString() };
				this.defaultTableModel.addRow(tmp);
			}
		}
	}

	int getTableSelect() {
		int tmp = table.getSelectedRow();
		if (tmp < 0) {
			JOptionPane.showMessageDialog(this, "There is no selected group!", "Error", 0);
			return tmp;
		} else
			return tmp;
	}

	public static void main(String[] args) {
		GroupManagerWindow groupManagerWindow;
		groupManagerWindow = new GroupManagerWindow();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Object source = e.getSource();
			if (source == aboutItem) {
				JOptionPane.showMessageDialog(this,
						"GroupOfBooksManager(c)\nDeveloped by:Jan Maksymilain Venulet\nNovember 2018", "About",
						JOptionPane.INFORMATION_MESSAGE);
				if (false)
					throw new BookException("blad");
			}
			if (source == this.exitItem || source == this.exitButton) {
				this.dispose();
			}
			if (source == this.createButton || source == this.newItem) {
				String name = JOptionPane.showInputDialog(this, "Enter new group name:", "Create group");
				String type = (JOptionPane.showInputDialog(this, "Enter new group name:", "Create group",
						JOptionPane.QUESTION_MESSAGE, null, GroupType.values(), GroupType.VECTOR)).toString();
				// String name2 ="aba";
				// GroupType type2 = GroupType.ARRAY_LIST;
				GroupofBooks tmp = new GroupofBooks(name, type);
				GroupOfBooksDialog newBookDialog = new GroupOfBooksDialog(tmp, this);
				groups.add(tmp);
				// this.recordCount++;
			}
			if (source == this.editButton || source == this.editItem) {
				int tmp = getTableSelect();
				if (tmp >= 0) {
					Iterator<GroupofBooks> iterator;
					iterator = this.groups.iterator();
					while (tmp > 0) {
						iterator.next();
						tmp--;
					}
					GroupOfBooksDialog newBookDialog = new GroupOfBooksDialog(iterator.next(), this);
				}
			}
			if (source == this.deleteButton || source == this.deleteButton) {
				int tmp = getTableSelect();
				if (tmp >= 0) {
					Iterator<GroupofBooks> iterator;
					iterator = this.groups.iterator();
					while (tmp >= 0) {
						iterator.next();
						tmp--;
					}
					iterator.remove();
				}
			}
			if (source == this.writeButton || source == this.saveItem) {
				int tmp = this.getTableSelect();
				if (tmp >= 0) {
					java.util.Iterator<GroupofBooks> iterator = this.groups.iterator();
					while (tmp > 0) {
						iterator.next();
						tmp--;
					}
					String fileName = JOptionPane.showInputDialog(this,
							"Enter file name you want to save selected group: ", "Save book to file");
					GroupofBooks book = (GroupofBooks) iterator.next();
					book.writeToFile(fileName);
				}
			}
			if (source ==this.openItem || source ==this.readButton)
			{
				String fileName = JOptionPane.showInputDialog(this,
						"Enter file name to open: ", "Read book from file");
				GroupofBooks tmp = new GroupofBooks(fileName);
				this.groups.add(tmp);
			}
		} catch (BookException error) {
			JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			;
		}
		updateTable();

	}

	public void TestDataSetGenerate() {
		String name2 = "Biblioteka im. Mickiewicza";
		String name1 = "Biblioteka im. Króla Augusta";
		String name3 = "Biblioteka im. Króla ¯eromskiego";
		String name4 = "Biblioteka im. Narutowicza";
		String name5 = "LO V";
		String name6 = "Szko³a podstawowa nr. 14";
		GroupType type2 = GroupType.ARRAY_LIST;
		GroupType type1 = GroupType.VECTOR;
		GroupType type3 = GroupType.TREE_SET;
		GroupofBooks tmp;
		try {
			tmp = new GroupofBooks(name2, type3);
			groups.add(tmp);
			tmp = new GroupofBooks(name5, type2);
			groups.add(tmp);
			tmp = new GroupofBooks(name3, type3);
			groups.add(tmp);
			tmp = new GroupofBooks(name6, type2);
			groups.add(tmp);
			tmp = new GroupofBooks(name1, type1);
			groups.add(tmp);
			tmp = new GroupofBooks(name4, type2);
			groups.add(tmp);
		} catch (BookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
