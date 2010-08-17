package ua.com.myjava.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.ws.WebFault;

/**
 * User: abogoley
 * Date: 17.08.2010
 */
@WebFault(name = "Cannot store article")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleException extends RuntimeException {
    public ArticleException(String s) {
        super(s);
    }
}
