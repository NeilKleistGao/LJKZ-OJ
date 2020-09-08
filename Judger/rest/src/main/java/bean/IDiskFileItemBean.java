package bean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Local
public interface IDiskFileItemBean {
    List<FileItem> getFileItems(HttpServletRequest request) throws FileUploadException;
}
