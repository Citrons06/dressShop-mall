package dressshop.service.itemImg;

import java.io.IOException;

public interface FileService {

    String upload(String itemImgLocation, String oriImgName, byte[] fileData) throws IOException;
}
