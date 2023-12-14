package com.initiators;

import com.services.IEncryptionService;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Objects;

public class EncryptionActivity {
    public static void decryptFiles(IEncryptionService encryptionService, JOptionPane optionPane) {
        try{
            //Check if path is empty or blank
            String path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);

            //Re-prompt user to enter a path
            while(path == null || path.isBlank() || path.isEmpty()){
                JOptionPane.showMessageDialog(optionPane, "File path cannot be empty!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);

            }

            //Set file path and initialize object
            File fs = new File(path);

            //Check if path exists
            while(!fs.exists()){
                JOptionPane.showMessageDialog(optionPane, "File path does not exist!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);
                fs = new File(path);
            }

            //Prompt for decryption key
            String key = JOptionPane.showInputDialog(null,"Please enter key", "Decryption key", JOptionPane.QUESTION_MESSAGE);

            //Re-prompt for decryption key if empty
            while(key == null || key.isEmpty() || key.isBlank()){
                JOptionPane.showMessageDialog(optionPane, "Key cannot be empty!", "Invalid key", JOptionPane.WARNING_MESSAGE);
                key = JOptionPane.showInputDialog(null,"Please enter key", "Decryption key", JOptionPane.QUESTION_MESSAGE);
            }

            int fileCount = 0;

            //Decrypt files or output error if something goes wrong.
            for(File f : Objects.requireNonNull(fs.listFiles())){
                if(f.isFile() && f.getName().startsWith("Encrypted")){
                    String inputFile = f.getAbsolutePath();
                    String outputFile = "Decrypted - " + LocalDateTime.now() + " - " + f.getName();
                    var r = encryptionService.decryptFile(inputFile, outputFile, key);
                    if(r)
                        fileCount++;
                    else
                        JOptionPane.showMessageDialog(null, String.format("Failed to decrypt or failed to delete %s after decryption.", f.getName()),"Decryption", JOptionPane.WARNING_MESSAGE);
                }
            }

            JOptionPane.showMessageDialog(null, String.format("Files decrypted: %d", fileCount),"Decryption", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void encryptFiles(IEncryptionService encryptionService, JOptionPane optionPane) {
        try{
            //Check if path is empty or blank
            String path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);

            //Re-prompt user to enter a path
            while(path == null || path.isBlank() || path.isEmpty()){
                JOptionPane.showMessageDialog(optionPane, "File path cannot be empty!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);

            }

            //Set file path and initialize object
            File fs = new File(path);

            //Check if path exists
            while(!fs.exists()){
                JOptionPane.showMessageDialog(optionPane, "File path does not exist!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog(null,"Please enter path", "Folder path", JOptionPane.QUESTION_MESSAGE);
                fs = new File(path);
            }

            //Get files in directory
            File[] files = new File(fs.getAbsolutePath()).listFiles();

            //Prompt for encryption key
            String key = JOptionPane.showInputDialog(null,"Please enter key", "Encryption key", JOptionPane.QUESTION_MESSAGE);

            //Re-prompt for decryption key if empty
            while(key == null || key.isEmpty() || key.isBlank()){
                JOptionPane.showMessageDialog(optionPane, "Key cannot be empty!", "Invalid key", JOptionPane.WARNING_MESSAGE);
                key = JOptionPane.showInputDialog(null,"Please enter key", "Encryption key", JOptionPane.QUESTION_MESSAGE);
            }

            //Check if files collection is not null.
            assert files != null;

            int fileCount = 0;

            //Encrypt files or output error if something goes wrong.
            for(File f : files){
                if(f.isFile() && !f.getName().startsWith(".DS_Store")){
                    String inputFile = f.getAbsolutePath();
                    String outputFile = "Encrypted - " + LocalDateTime.now() + " - " +  f.getName();
                    var r = encryptionService.encryptFile(inputFile, outputFile, key);
                    if(r)
                        fileCount++;
                    else
                        JOptionPane.showMessageDialog(null, String.format("Failed to encrypt or failed to delete %s after encryption.", f.getName()),"Error", JOptionPane.WARNING_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(null, String.format("Failed to encrypt %s%n", f.getName()),"Error", JOptionPane.WARNING_MESSAGE);
            }

            JOptionPane.showMessageDialog(null, String.format("Files encrypted: %d", fileCount),"Encryption", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}