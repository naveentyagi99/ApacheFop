package com.fop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.servlet.FopServlet;


public class FopPrintServlet extends FopServlet {

    private static final long serialVersionUID = 1645706757391617935L;

    protected void render(Source src, Transformer transformer, HttpServletResponse response)
            throws FOPException, TransformerException, IOException {

        FOUserAgent foUserAgent = getFOUserAgent();

        //Setup FOP
        Fop fop = fopFactory.newFop(MimeConstants.MIME_FOP_PRINT, foUserAgent);

        //Make sure the XSL transformation's result is piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        //Start the transformation and rendering process
        transformer.transform(src, res);

        //Return the result
        reportOK(response);
    }

    // private helper, tell (browser) user that file printed
    private void reportOK(HttpServletResponse response) throws IOException {
        String sMsg = "<html><title>Success</title>\n"
                + "<body><h1>FopPrintServlet: </h1>"
                + "<h3>The requested data was printed to the default printer.</h3></body></html>";

        response.setContentType("text/html");
        response.setContentLength(sMsg.length());

        PrintWriter out = response.getWriter();
        out.println(sMsg);
        out.flush();
    }

}
