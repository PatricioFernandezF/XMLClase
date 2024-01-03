package Simulacion;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.xpath.XPathConstants;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.*;


public class Principal {

	public static void main(String[] args) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException, TransformerException {
		
		// Cargar el archivo XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("biblioteca.xml");
		
		
		
		//insertarLibro(doc);
		
        System.out.println("----------------Titulos Antiguos----------------");
		imprimirTitulos(doc);
		editarTitulo(doc,"Drácula Condemor","Drácula Me da Miedo");
		System.out.println("----------------Titulos Nuevos----------------");
		imprimirTitulos(doc);
		guardarCambios(doc, "biblioteca.xml");
		
	}
	
	
	
	private static void insertarLibro(Document doc) {
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el título del libro: ");
		String titulo = scanner.nextLine();
		
		System.out.print("Ingrese el autor del libro: ");
		String autor = scanner.nextLine();
		
		System.out.print("Ingrese el año de publicación del libro: ");
		String anio = scanner.nextLine();
		Element biblioteca = doc.getDocumentElement();
		
		// Crear elementos para el nuevo libro
		Element libro = doc.createElement("libro");
		Element tituloElement = doc.createElement("titulo");
		Element autorElement = doc.createElement("autor");
		Element anioElement = doc.createElement("año");
		
		// Establecer el contenido de los elementos
		tituloElement.appendChild(doc.createTextNode(titulo));
		autorElement.appendChild(doc.createTextNode(autor));
		anioElement.appendChild(doc.createTextNode(anio));
		
		// Agregar los elementos al libro
		libro.appendChild(tituloElement);
		libro.appendChild(autorElement);
		libro.appendChild(anioElement);
		
		// Agregar el libro a la biblioteca
		biblioteca.appendChild(libro);
		}
	
	
	

	public static void guardarCambios(Document doc, String nombreArchivo) throws IOException, TransformerException {
		// Escribir el contenido del documento en el archivo
		try (FileWriter writer = new FileWriter(nombreArchivo)) {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
		}
	}
	

	public static void imprimirTitulos(Document doc) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		


        // Crear objeto XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        // Definir la expresión XPath para seleccionar todos los títulos de los libros
        String expression = "/biblioteca/libro/titulo";

        // Compilar y ejecutar la expresión
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getTextContent());
        }
		
	}
	
	
	public static void editarTitulo(Document doc,String titulo,String tituloNuevo) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		


        // Crear objeto XPath
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        // Definir la expresión XPath para seleccionar todos los títulos de los libros
        String expression = "/biblioteca/libro/titulo";

        // Compilar y ejecutar la expresión
        XPathExpression expr = xpath.compile(expression);
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        
        
        for (int i = 0; i < nodes.getLength(); i++) {
        	
        	if (nodes.item(i).getTextContent().equals(titulo)) {
        		nodes.item(i).setTextContent(tituloNuevo);
        	}
           
        }
		
	}

	


}
