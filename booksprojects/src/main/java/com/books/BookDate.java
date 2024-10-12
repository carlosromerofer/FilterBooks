package com.books;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class BookDate {

	private Integer id;
	private String title;
	private Integer pages;
	private String summary;
	private String publicationTimestamp;
	private Author author;
	private Date publicationDate;

	public BookDate() {
		super();
	}

	public BookDate(Integer id, String title, Integer pages, String summary, String publicationTimestamp, Author author,
			Date publicationDate) {
		super();
		this.id = id;
		this.title = title;
		this.pages = pages;
		this.summary = summary;
		this.publicationTimestamp = publicationTimestamp;
		this.author = author;
		this.publicationDate = publicationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPublicationTimestamp() {
		return publicationTimestamp;
	}

	public void setPublicationTimestamp(String publicationTimestamp) {
		this.publicationTimestamp = publicationTimestamp;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Override
	public String toString() {
		return "BookDate [id=" + id + ", title=" + title + ", pages=" + pages + ", summary=" + summary
				+ ", publicationTimestamp=" + publicationTimestamp + ", author=" + author + ", publicationDate="
				+ publicationDate + "]";
	}

	private Optional<BookDate> filter(String filter, List<BookDate> books)
			throws InvocationTargetException, ParseException {

		// List to order books
		List<BookDate> bookListOrder = new ArrayList<BookDate>();
		BookDate bookCompare = new BookDate();
		Author authorCompare = new Author();

		for (BookDate book : books) {

			// Escriba por pantalla los libros que no tengan fecha de publicación
			// No hay order by date porque no tienen fechas
			if (filter == "") {

				if (book.getPublicationTimestamp() == null || book.getPublicationTimestamp().isEmpty()) {
					System.out.println("ID: " + book.getId());
					System.out.println("Title: " + book.getTitle());
					System.out.println("Publication Date: - ");
					System.out.println("Pages: " + book.getPages());
					System.out.println("Summary: " + book.getSummary());
					System.out.println("Author Name: " + book.getAuthor().getName());
					System.out.println("Author Surname: " + book.getAuthor().getFirstSurname());
					System.out.println("Author Bio: " + book.getAuthor().getBio());
					System.out.println(
							"----------------------------------------------------------------------------------------------------");

				}

				// Si el usuario añade caracteres para buscar
			} else if (!filter.trim().isEmpty()) {
				// Si no tiene fecha no lo añado a la lista a mostrar
				if (book.getPublicationTimestamp() != null) {
					// Si nombre del libro, resumen o biografía tienen ese carácter lo añado a la
					// lista a mostrar
					if (book.getTitle().contains(filter) || book.getSummary().contains(filter)
							|| book.getAuthor().getBio().contains(filter)) {

						// Checkeo si es la primera pasada que tenga fecha y mi nuevo Objecto
						// bookDateCompare está sin
						// datos
						if (bookCompare.getId() == null) {

							// no tiene datos, es primera pasada y seteo sus valores. también seteo el campo
							// nuevo
							String bookCompareDateStr;
							bookCompareDateStr = epochtoStrDate(book.getPublicationTimestamp());
							Date bookDate = strDatetoDate(bookCompareDateStr);

							bookCompare.setPublicationDate(bookDate);
							bookCompare.setId(book.getId());
							bookCompare.setTitle(book.getTitle());
							bookCompare.setPublicationTimestamp(book.getPublicationTimestamp());
							bookCompare.setPages(book.getPages());
							bookCompare.setSummary(book.getSummary());
							authorCompare.setName(book.getAuthor().getName());
							authorCompare.setFirstSurname(book.getAuthor().getFirstSurname());
							authorCompare.setBio(book.getAuthor().getBio());
							bookCompare.setAuthor(authorCompare);

							bookListOrder.add(bookCompare);
						} else {
							// tiene datos con lo cual no es primera pasada
							// comparo fechas y me quedo con la fecha más reciente

							// Transformo la fecha a Str legible del objeto inicial. Y lo paso a Date para
							// comparación
							String bookDateStr;
							bookDateStr = epochtoStrDate(book.getPublicationTimestamp());
							Date bookDate = strDatetoDate(bookDateStr);

							// transformo fecha de epoch a fecha Str legible del objecto nuevo.
							String bookDateCompareStr;
							bookDateCompareStr = epochtoStrDate(bookCompare.getPublicationTimestamp());
							Date bookCompareDate = strDatetoDate(bookDateCompareStr);

							// Seteo la publicationDate() en el objeto nuevo que va a a lista a mostrar
							bookCompare.setPublicationDate(bookCompareDate);

							// Si la fecha del objeto temporal es menor que la del objeto inicial seteo el
							// objeto temporal con la info del inicial
							if (bookCompareDate.before(bookDate)) {
								// Reinicio la lista pues solo puedo mostrar uno
								bookListOrder.remove(0);

								bookCompare.setId(book.getId());
								bookCompare.setTitle(book.getTitle());
								bookCompare.setPublicationTimestamp(book.getPublicationTimestamp());
								bookCompare.setPublicationDate(bookDate);
								bookCompare.setPages(book.getPages());
								bookCompare.setSummary(book.getSummary());
								authorCompare.setName(book.getAuthor().getName());
								authorCompare.setFirstSurname(book.getAuthor().getFirstSurname());
								authorCompare.setBio(book.getAuthor().getBio());
								bookCompare.setAuthor(authorCompare);

								bookListOrder.add(book);

							}

						}

					}

				}

			}
		}
		Collections.reverse(bookListOrder);

		for (BookDate bookDateTemp : bookListOrder) {

			System.out.println("ID: " + bookDateTemp.getId());
			System.out.println("Title: " + bookDateTemp.getTitle());
			// transformo fecha para mostrarla bonito
			String bookDateStr;
			bookDateStr = epochtoStrDate(bookDateTemp.getPublicationTimestamp());

			System.out.println("Publication Date: " + bookDateStr);
			System.out.println("Pages: " + bookDateTemp.getPages());
			System.out.println("Summary: " + bookDateTemp.getSummary());
			System.out.println("Author Name: " + bookDateTemp.getAuthor().getName());
			System.out.println("Author Surname: " + bookDateTemp.getAuthor().getFirstSurname());
			System.out.println("Author Bio: " + bookDateTemp.getAuthor().getBio());
			System.out.println(
					"----------------------------------------------------------------------------------------------------");

		}
		
		return Optional.empty();

	}

	public static String epochtoStrDate(String dateStr) {
		long dateLong = Long.parseLong(dateStr);
		Date date = new Date(dateLong * 1000L);
		DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);

		return formatted;
	}

	public static Date strDatetoDate(String dateStr) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new java.sql.Date(df.parse(dateStr).getTime());

		return date;
	}
}
