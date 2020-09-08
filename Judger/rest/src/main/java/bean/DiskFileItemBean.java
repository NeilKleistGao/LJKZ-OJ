package bean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.ejb.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Singleton
public class DiskFileItemBean implements IDiskFileItemBean {
    private ServletFileUpload uploader;

    public DiskFileItemBean() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        uploader = new ServletFileUpload(factory);
    }

    @Override
    public List<FileItem> getFileItems(HttpServletRequest request) throws FileUploadException {
        return uploader.parseRequest(request);
    }
}
