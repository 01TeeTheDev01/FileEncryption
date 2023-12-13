package com.services;

public interface IEncryptionService {
    boolean encryptFile(String inputFile, String outputFile, String key);
    boolean decryptFile(String inputFile, String outputFile, String key);
}
