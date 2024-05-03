package dressshop.service.item;

import java.io.IOException;

public interface FileService {

    String upload(String uploadPath, String oriImgName, byte[] fileData) throws IOException;
    void delete(String filePath);
}
