package dressshop.service.item;

import java.io.IOException;

public interface FileService {

    String upload(String itemImgLocation, String oriImgName, byte[] fileData) throws IOException;
}
