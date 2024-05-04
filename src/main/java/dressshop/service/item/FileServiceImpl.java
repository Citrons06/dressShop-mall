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
    public String upload(String uploadPath, String oriImgName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String ext = oriImgName.substring(oriImgName.lastIndexOf("."));
        String saveFileName = uuid.toString() + "." + ext;
        String fileUploadUrl = uploadPath + "/" + saveFileName;

        FileOutputStream fileOutputStream = new FileOutputStream(fileUploadUrl);
        fileOutputStream.write(fileData);
        fileOutputStream.close();

        return saveFileName;
    }

    @Override
    public void delete(String filePath) {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}