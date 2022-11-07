package com.example.moviereview.controller;

import com.example.moviereview.model.ResponseObject;
import com.example.moviereview.model.User;
import com.example.moviereview.service.FileService;
import com.example.moviereview.service.FirebaseStorageStrategy;
import com.example.moviereview.service.UserService;
import com.google.api.client.http.HttpStatusCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ImageController {
    private final FileService fileService;
    private final UserService userService;
    private final FirebaseStorageStrategy firebaseStorageStrategy;

    /*@PostMapping("/profile/pic")
    public Object upload(@RequestParam("file") MultipartFile multipartFile){
        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        return fileService.upload(multipartFile);
    }*/

    /*@PostMapping("/upload/image")
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());
        return firebaseStorageStrategy.uploadFile(multipartFile);
    }*/

    String prefixURL = "https://firebasestorage.googleapis.com/v0/b/viewie-api.appspot.com/o/";
    String suffixURL = "?alt=media";

    @PostMapping("/user/image")
    public ResponseEntity<Object> updateImage(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        //String IMAGE_URL = firebaseStorageStrategy.uploadFile(multipartFile);
        User user = userService.getUserById(userId);
        if (user.getAvatar() != null || !user.getAvatar().equals("")) {
            String oldImageName = user.getAvatar().substring(prefixURL.length(), user.getAvatar().length() - suffixURL.length());
            boolean isDelete = fileService.deleteFile(oldImageName);
            if (isDelete) {
                log.info("Delete file success");
            }
        }
        String IMAGE_URL = fileService.upload(multipartFile);
        HashMap<String, String> imageUser = new HashMap<>();
        imageUser.put("avatar", IMAGE_URL);
        userService.updateImageById(userId, imageUser);
        log.info("HIT -/upload | File Name : {}", multipartFile.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatusCodes.STATUS_CODE_OK, "Upload successfully", IMAGE_URL));
    }

    /*@PostMapping("/profile/pic/{fileName}")
    public Object download(@PathVariable String fileName) throws IOException {
        log.info("HIT -/download | File Name : {}", fileName);
        return fileService.download(fileName);
    }*/
}
