package com.services;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

public class EncryptionService implements IEncryptionService {

    private static final String ALGORITHM = "AES";
    private FileInputStream inputStream;
    private FileOutputStream outputStream;
    private static final String MESSAGE = "Error -> %s";


    /*
     * The following code is a method that generates a secret key for encryption using a given key string. The generated key is of type SecretKeySpec and is based on the SHA-256 hash algorithm.
     *
     * Here is a step-by-step explanation of the code:
     * 1. The method takes a string key as input.
     * 2. It converts the key string into a byte array using the UTF-8 character encoding.
     * 3. It creates an instance of the MessageDigest class with the "SHA-256" algorithm.
     * 4. The keyBytes are then passed to the MessageDigest's digest method, which calculates the SHA-256 hash of the keyBytes.
     * 5. The resulting hash is then truncated to 16 bytes (128 bits) using the Arrays.copyOf method. This is done because the SecretKeySpec constructor requires a key of length 16.
     * 6. Finally, a new SecretKeySpec object is created using the truncated keyBytes and a constant value ALGORITHM (which is not provided in the code snippet).
     * 7. If any exception occurs during the execution of the code, it will be caught in the catch block and the stack trace will be printed.
     * 8. If no exception occurs, the generated SecretKeySpec object is returned.
     * 9. If an exception occurs, null is returned.
     */
    private SecretKeySpec generateSecretKey(String key) {
        try{
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            keyBytes = sha.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);
            return new SecretKeySpec(keyBytes, ALGORITHM);
        }catch(Exception e){
            System.out.printf(MESSAGE, e.getMessage());
        }
        return null;
    }


    /*
     * The given code is a method called "encryptFile" that takes three parameters: inputFile (the path of the file to be encrypted), outputFile (the path where the encrypted file will be saved), and key (the encryption key).
     *
     * Here is a step-by-step explanation of the code:
     * 1. The method starts by generating a secret key using the "generateSecretKey" method (not shown in the code snippet). The key is passed as a parameter to this method.
     * 2. It then creates a Cipher object using the "Cipher.getInstance" method, with the ALGORITHM specified (which is not shown in the code snippet).
     * 3. The cipher is initialized in encryption mode using the "init" method, with the secret key.
     * 4. It opens an input stream to read the contents of the inputFile using the FileInputStream.
     * 5. It opens an output stream to write the encrypted data to the outputFile using the FileOutputStream.
     * 6. A buffer of size 1024 bytes is created to read data from the input stream and write encrypted data to the output stream.
     * 7. Inside a while loop, it reads data from the input stream into the buffer and stores the number of bytes read in the "bytesRead" variable.
     * 8. It then calls the "update" method of the cipher to encrypt the data in the buffer, starting from index 0 and up to the number of bytes read.
     * 9. The encrypted bytes are written to the output stream using the "write" method.
     * 10. The loop continues until there is no more data to read from the input stream (bytesRead is -1).
     * 11. After the loop ends, the "doFinal" method of the cipher is called to encrypt any remaining data and obtain the final encrypted bytes.
     * 12. The final encrypted bytes are written to the output stream.
     * 13. Finally, the input and output streams are closed.
     * 14. If any exception occurs during the encryption process, it is caught and the stack trace is printed.
     *
     * In summary, the given code encrypts the contents of a file using a provided key and saves the encrypted data to a new file.
     */
    @Override
    public boolean encryptFile(String inputFile, String outputFile, String key) {
        if(inputFile.isBlank() || inputFile.isEmpty() ||
                outputFile.isBlank() || outputFile.isEmpty() ||
                key.isBlank() || key.isEmpty())
            return false;

        try {
            SecretKeySpec secretKey = generateSecretKey(key);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] encryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(encryptedBytes);
            }

            byte[] encryptedBytes = cipher.doFinal();
            outputStream.write(encryptedBytes);

            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            System.out.printf(MESSAGE, e.getMessage());
            return false;
        }
    }


    /*
     * The code is a method that decrypts a file using a given key.
     * 1. It first generates a secret key using the provided key.
     * 2. It initializes a cipher using the specified encryption algorithm.
     * 3. It opens an input stream to read from the input file and an output stream to write to the output file.
     * 4. It creates a buffer to store chunks of data read from the input file.
     * 5. It enters a loop that reads data from the input file into the buffer and decrypts it using the cipher. The decrypted data is then written to the output file.
     * 6. After the loop finishes, any remaining decrypted data is written to the output file.
     * 7. Finally, it closes the input and output streams.
     *
     * Step by step explanation:
     * - The method takes three parameters: inputFile (the path to the input file), outputFile (the path to the output file), and key (the encryption key).
     * - It tries to execute the code inside the try block, and if an exception occurs, it prints the stack trace.
     * - It generates a secret key using the provided key by calling the generateSecretKey method.
     * - It initializes a cipher using the ALGORITHM specified.
     * - It opens an input stream to read from the input file and an output stream to write to the output file.
     * - It creates a buffer of size 1024 bytes to store chunks of data read from the input file.
     * - It enters a while loop that reads data from the input file into the buffer. The bytesRead variable stores the number of bytes read.
     * - Inside the loop, it calls the cipher's update method to decrypt the data in the buffer. The decryptedBytes variable stores the decrypted data.
     * - It writes the decrypted data to the output file using the output stream's write method.
     * - After the loop finishes, it calls the cipher's doFinal method to decrypt any remaining data.
     * - It writes the remaining decrypted data to the output file.
     * - It closes the input and output streams.
     */
    @Override
    public boolean decryptFile(String inputFile, String outputFile, String key) {
        if(inputFile.isBlank() || inputFile.isEmpty() ||
                outputFile.isBlank() || outputFile.isEmpty() ||
                key.isBlank() || key.isEmpty())
            return false;

        try {
            SecretKeySpec secretKey = generateSecretKey(key);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(decryptedBytes);
            }

            byte[] decryptedBytes = cipher.doFinal();
            outputStream.write(decryptedBytes);

            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            System.out.printf(MESSAGE, e.getMessage());
            return false;
        }
    }
}
