package pakiet;

//Pomocnicza klasa do tworzenia okna dialogu pomocnego przy tworzeniu i edycji instacji klasy Book
//program w ca³oœci utworzony przez: 
//Jan Venulet 235542
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4337009586584843973L;

	public Book currentBook, previousBook;
	JLabel titleLabel = new JLabel("Title:");
	JTextField titleField = new JTextField(10);
	JLabel authorLabel = new JLabel("Author:\t");
	JTextField authorField = new JTextField(10);
	JLabel yearLabel = new JLabel("Edition year:");
	JTextField yearField = new JTextField(10);
	JLabel typeLabel = new JLabel("Type:	");
	JTextField typeField = new JTextField(10);
	JComboBox<BookType> bookTypeCombo = new JComboBox<BookType>(BookType.values()); // w konstruktorze argumenty
																					// wyswietlane w liscie
	JButton saveButton = new JButton("Save");
	JButton cancelButton = new JButton("Cancel");

	public BookDialog(Window parent, Book parentBook) {
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL); // !!!!!!!!!!!!!!!!!
		this.currentBook = parentBook;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (parentBook == null) {
			this.setTitle("Create new book");
		} else {
			this.setTitle("Edit " + parentBook.getTitle());
			titleField.setText(parentBook.getTitle());
			authorField.setText(parentBook.getAuthor());
			yearField.setText(String.valueOf(parentBook.getYear()));
			bookTypeCombo.setSelectedItem(parentBook.getType());
			previousBook = parentBook;
		}
		this.setSize(200, 200);
		JPanel dialogPanel = new JPanel();
		dialogPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		dialogPanel.add(titleLabel);
		dialogPanel.add(titleField);
		dialogPanel.add(authorLabel);
		dialogPanel.add(authorField);
		dialogPanel.add(yearLabel);
		dialogPanel.add(yearField);
		dialogPanel.add(typeLabel);
		dialogPanel.add(bookTypeCombo);
		dialogPanel.add(saveButton);
		dialogPanel.add(cancelButton);
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		this.setResizable(false);

		this.setContentPane(dialogPanel);
		dialogPanel.setVisible(true);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == cancelButton) {
			this.dispose();
		}
		if (source == saveButton) {
			try {
				if (currentBook == null) {
					currentBook = new Book(titleField.getText(), authorField.getText());
					if (!yearField.getText().equals(""))
						currentBook.setYear(Integer.valueOf(yearField.getText()));
					currentBook.setType(String.valueOf(bookTypeCombo.getSelectedItem()));
				} else {
					currentBook.setTitle(titleField.getText());
					currentBook.setAuthor(authorField.getText());
					if (!yearField.getText().equals(""))
						currentBook.setYear(Integer.valueOf(yearField.getText()));
					currentBook.setType(String.valueOf(bookTypeCombo.getSelectedItem()));

				}
				this.dispose();
			} catch (BookException error) {
				JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public static Book returnBook(Window parent, Book parentBook) {
		BookDialog dialog = new BookDialog(parent, parentBook);
		return dialog.currentBook;
	}


}
