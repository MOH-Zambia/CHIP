
package com.argusoft.imtecho.common.service;

/**
 *
 * <p>
 *     Defines Service for generating Digital sign with create visible sign on PDF 
 * </p>
 * @author ashish
 * @since 22/09/2020 18:22
 *
 */
public interface CreateSignatureService {
    /**
     * Create Digital signature on Given Document
     * @param p12FilePassword A .p12 File Password (Security File)
     * @param p12FilePath A .p12 File Path (Generated By system )
     * @param dsImageFile A Digital Signature Sign Image File path
     * @param inFilePath A File Path which is expected to Signed Digitally
     * @param outFilePath  A File Path where output will be stored 
     */
    void  createDigitalSignature(String p12FilePassword,String p12FilePath,String dsImageFile,String inFilePath, String outFilePath);
}