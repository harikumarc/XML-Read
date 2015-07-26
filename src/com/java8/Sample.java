package com.java8;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Sample {
	
	
	
		 
		 public static void main(String argv[]) {
			 
			  try {
			    Map<String,String> dataMap=new HashMap<String,String>();
				File fXmlFile = new File("C:\\Users\\Hari\\WebAPIMethods.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				doc.getDocumentElement().normalize();
				XPathFactory xpathFactory = XPathFactory.newInstance();
				// XPath to find empty text nodes.
				XPathExpression xpathExp = xpathFactory.newXPath().compile(
				    	"//text()[normalize-space(.) = '']");  
				NodeList emptyTextNodes = (NodeList) 
				        xpathExp.evaluate(doc, XPathConstants.NODESET);

				// Remove each empty text node from document.
				for (int i = 0; i < emptyTextNodes.getLength(); i++) {
				    Node emptyTextNode = emptyTextNodes.item(i);
				    emptyTextNode.getParentNode().removeChild(emptyTextNode);
				}
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 
				NodeList nList = doc.getElementsByTagName("WebAPIApp");
			 
				System.out.println("----------------------------");
			 
				for (int temp = 0; temp < nList.getLength(); temp++) {
					
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						NodeList textEnvList = eElement.getChildNodes();
						for (int i = 0; i < textEnvList.getLength(); i++) {
							Node nNode1 = textEnvList.item(i);
							Element parElement = (Element) nNode1;
							String saveResponse= parElement.getAttribute("SaveResponse");
							NodeList propList = parElement.getElementsByTagName("param");
							String key=nNode1.getNodeName();
							String dbString="";
							for (int k = 0; k < propList.getLength(); k++) {
								Element dbElement = (Element)propList.item(k);
								NodeList textdbList = dbElement.getChildNodes();
								if(dbString!=""){
										dbString= dbString+"|"+((Node)textdbList.item(0)).getNodeValue().trim();
								}else{
									dbString= ((Node)textdbList.item(0)).getNodeValue().trim();
								}
							}
							dbString=dbString+";"+saveResponse;
							dataMap.put(key, dbString);
							
						}
					}
				}
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
				    System.out.println("Key = " + entry.getKey());
				    System.out.println("value = " + entry.getValue());
				}
				
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			  }
}











 
 
 

