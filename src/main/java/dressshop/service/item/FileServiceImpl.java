package dressshop.service.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(String itemImgLocation, String oriImgName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String ext = getFileExtension(oriImgName);
        String saveFileName = uuid.toString() + "." + ext;
        String fileUploadUrl = itemImgLocation + "/" + saveFileName;

        FileOutputStream fileOutputStream = new FileOutputStream(fileUploadUrl);
        fileOutputStream.write(fileData);
        fileOutputStream.close();

        return saveFileName;
    }

    private String getFileExtension(String oriImgName) {
        int dotIndex = oriImgName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < oriImgName.length() - 1) {
            return oriImgName.substring(dotIndex + 1);
        } else {
            throw new IllegalArgumentException("파일 이름에 확장자가 없습니다. 이미지 파일명:" + oriImgName);
        }
    }
}