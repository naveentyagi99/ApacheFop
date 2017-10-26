package com.fopconsole;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class FOPPdfDemo {

    public static void main(String[] args) {
        FOPPdfDemo fOPPdfDemo = new FOPPdfDemo();
        try {
            fOPPdfDemo.convertToPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertToPDF()  throws IOException, FOPException, TransformerException {

        File xsltFile = new File("C:\\Users\\Naveen Tyagi\\Desktop\\Paginasition\\project\\ApacheFop\\src\\main\\java\\com\\fopconsole\\template.xsl");
        StreamSource xmlSource = new StreamSource(new File("C:\\Users\\Naveen Tyagi\\Desktop\\Paginasition\\project\\ApacheFop\\src\\main\\java\\com\\fopconsole\\Employees.xml"));
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out;
        out = new java.io.FileOutputStream("C:\\Users\\Naveen Tyagi\\Desktop\\test.pdf");
        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }
}
