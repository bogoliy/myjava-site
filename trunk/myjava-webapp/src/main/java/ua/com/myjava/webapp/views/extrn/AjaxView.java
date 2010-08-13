package ua.com.myjava.webapp.views.extrn;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import flexjson.JSONSerializer;

/**
 * @author abogoley
 */
public class AjaxView extends AbstractView {
    private static final Log log = LogFactory.getLog(AjaxView.class);

    /* (non-Javadoc)
    * @see org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */

    @Override
    protected void renderMergedOutputModel(Map map, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.info("Resolving ajax request view -" + map);

        JSONSerializer serializer = new JSONSerializer();
        String jsonString = serializer.serialize(map);
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(jsonString.getBytes("utf-8"));

    }

}