package servlet;

import bean.IDiskFileItemBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utils.Zip;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ReceiverServlet extends HttpServlet {
    @EJB
    IDiskFileItemBean diskFileItemBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.getWriter().println("fail");
        }

        File path = new File("/home/neilkleistgao/ljkz_data");
        if (!path.exists()) {
            path.mkdir();
        }

        try {
            List<FileItem> items = diskFileItemBean.getFileItems(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String filename = new File(item.getName()).getName();
                    File target = new File("/home/neilkleistgao/ljkz_data/" + filename);
                    item.write(target);
                    Zip.unzip(target.getAbsolutePath(), req.getParameter("pid"));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        resp.getWriter().println("success");
    }
}
