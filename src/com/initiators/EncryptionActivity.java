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
            String path = JOptionPane.showInputDialog("Please enter path of encrypted files (e.g. c:\\Temp\\Test Folder)");

            //Re-prompt user to enter a path
            while(path == null || path.isBlank() || path.isEmpty()){
                JOptionPane.showMessageDialog(optionPane, "File path cannot be empty!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog("Please enter path of encrypted files (e.g. c:\\Temp\\Test Folder)");

            }

            //Set file path and initialize object
            File fs = new File(path);

            //Check if path exists
            while(!fs.exists()){
                JOptionPane.showMessageDialog(optionPane, "File path does not exist!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog("Please enter path (e.g. c:\\Temp\\Test Folder)");
                fs = new File(path);
            }

            //Prompt for decryption key
            String key = JOptionPane.showInputDialog("Please enter secret key");

            //Re-prompt for decryption key if empty
            while(key == null || key.isEmpty() || key.isBlank()){
                JOptionPane.showMessageDialog(optionPane, "Key cannot be empty!", "Invalid key", JOptionPane.WARNING_MESSAGE);
                key = JOptionPane.showInputDialog("Please enter secret key");
            }

            //Decrypt files or output error if something goes wrong.
            for(File f : Objects.requireNonNull(fs.listFiles())){
                if(f.isFile() && f.getName().startsWith("enc")){
                    String inputFile = f.getAbsolutePath();
                    String outputFile = fs.getAbsolutePath() + "dec-" + LocalDateTime.now() + " - " + f.getName();
                    var r = encryptionService.decryptFile(inputFile, outputFile, key);
                    if(r)
                        System.out.printf("File %s decrypted!%n", f.getName());
                    else
                        System.out.printf("Failed to decrypt %s%n", f.getName());
                }else
                    System.out.printf("Failed to decrypt %s%n", f.getName());
            }
        }catch(Exception e){
            System.out.printf("Error -> %s", e.getMessage());
        }
    }

    public static void encryptFiles(IEncryptionService encryptionService, JOptionPane optionPane) {

        try{
            //Check if path is empty or blank
            String path = JOptionPane.showInputDialog("Please enter path (e.g. c:\\Temp\\Test Folder)");

            //Re-prompt user to enter a path
            while(path == null || path.isBlank() || path.isEmpty()){
                JOptionPane.showMessageDialog(optionPane, "File path cannot be empty!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog("Please enter path (e.g. c:\\Temp\\Test Folder)");

            }

            //Set file path and initialize object
            File fs = new File(path);

            //Check if path exists
            while(!fs.exists()){
                JOptionPane.showMessageDialog(optionPane, "File path does not exist!", "Invalid path", JOptionPane.WARNING_MESSAGE);
                path = JOptionPane.showInputDialog("Please enter path (e.g. c:\\Temp\\Test Folder)");
                fs = new File(path);
            }

            //Get files in directory
            File[] files = new File(fs.getAbsolutePath()).listFiles();

            //Prompt for encryption key
            String key = JOptionPane.showInputDialog("Please enter secret key");

            //Re-prompt for decryption key if empty
            while(key == null || key.isEmpty() || key.isBlank()){
                JOptionPane.showMessageDialog(optionPane, "Key cannot be empty!", "Invalid key", JOptionPane.WARNING_MESSAGE);
                key = JOptionPane.showInputDialog("Please enter secret key");
            }

            //Check if files collection is not null.
            assert files != null;

            //Encrypt files or output error if something goes wrong.
            for(File f : files){
                if(f.isFile()){
                    String inputFile = f.getAbsolutePath();
                    String outputFile = fs.getAbsolutePath() + "enc-" + LocalDateTime.now() + " - "+  f.getName();
                    var r = encryptionService.encryptFile(inputFile, outputFile, key);
                    if(r)
                        System.out.printf("File %s encrypted!%n", f.getName());
                    else
                        System.out.printf("Failed to encrypt %s%n", f.getName());
                }
                else
                    System.out.printf("Failed to encrypt %s%n", f.getName());
            }
        }catch(Exception e){
            System.out.printf("Error -> %s", e.getMessage());
        }
    }
}