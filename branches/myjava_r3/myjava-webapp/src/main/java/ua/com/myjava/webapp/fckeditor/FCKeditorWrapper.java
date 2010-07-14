package ua.com.myjava.webapp.fckeditor;

import javax.servlet.http.HttpServletRequest;

import net.fckeditor.FCKeditor;

public class FCKeditorWrapper {
	private HttpServletRequest request;

	public FCKeditorWrapper(final HttpServletRequest request) {
		this.request = request;
	}

	public String get(final String instanceName, final String value) {
		FCKeditor editor = new FCKeditor(request, instanceName);
		editor.setValue(value);
		return editor.createHtml();
	}
}