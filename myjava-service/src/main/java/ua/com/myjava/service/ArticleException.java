package ua.com.myjava.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.WebFault;

/**
 * User: abogoley
 * Date: 17.08.2010
 */
@WebFault(name = "Cannot store article")
@XmlAccessorType(XmlAccessType.NONE)
public class ArticleException extends RuntimeException {
    @XmlElement
    private String message;

    public ArticleException(String s) {
        super(s);
        this.message = s;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
