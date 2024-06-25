/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.document.service;

import com.argusoft.imtecho.document.dto.DocumentDto;
import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author jay
 */
public interface DocumentService {

    DocumentDto uploadFile(MultipartFile file, String moduleName, boolean isTemporary);

    DocumentDto uploadFile(byte[] bytes, String moduleName, boolean isTemporary, String filename, String contentType);

    File getFile(Long id) throws FileNotFoundException;

    File getThumbnail(Long id) throws FileNotFoundException;

    void removeDocument(Long id);

    void cronForRemoveTemporaryDocument();
    
    DocumentDto retrieveDocumentById(Long id);

//    Long base64ToDocument(String base64File, String fileName, String moduleName);

}
