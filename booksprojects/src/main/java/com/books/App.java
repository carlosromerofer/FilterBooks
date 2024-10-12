package com.books;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.lang.reflect.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App {

	private static List<BookDate> bookList;
	private List<Author> authorList;
	private static File file;
	private static FileReader fileReader;
	private static Type type;

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		try {
			file = new File("C:\\Users\\Carlos\\eclipse-workspace\\booksprojects\\src\\main\\resources\\books.json");
			Gson gson = new Gson();
			fileReader = new FileReader(file);
			type = new TypeToken<List<BookDate>>() {
			}.getType();

			List<BookDate> bookList = gson.fromJson(fileReader, type);
			BookDate bd = new BookDate();

			Method method = bd.getClass().getDeclaredMethod("filter", String.class, List.class);
			method.setAccessible(true);
			method.invoke(bd, new Object[] { menuOptions(), bookList });

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static String menuOptions() {
		// TODO: mejorar a do-while para volver a mostrar elecciones y pasar comentarios a inglés
		try {
			System.out.println("Elija la información que desea obtener:");
			System.out.println("1 - Libros que no tengan fecha de publicación");
			System.out.println(
					"2 - Libros que contengan la cadena de caracteres en el nombre en el resumen y en la biografia del autor del libro");
			char ch = (char) System.in.read();
			
			switch (ch) {

			case '1': {
				System.out.println("Libros que no tengan fecha de publicación:");
				System.out.println("-----------------------------------------------------------");
				return "";
			}
			case '2': {
				System.out.println("Introduzca los caracteres para los que quiere buscar en nombre, resumen y biografía");
				System.out.println("En caso de encontrar más de un libro la lista devolverá el libro más reciente publicado");
				Scanner scanner = new Scanner(System.in);
				String textSearch = scanner.next();
				System.out.println("-----------------------------------------------------------");
				return textSearch;
			}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "";
	}

}
