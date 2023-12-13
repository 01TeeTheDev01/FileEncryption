package com.main;

import com.initiators.EncryptionPrompt;
import com.services.EncryptionService;
import com.services.IEncryptionService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //Init service
        IEncryptionService encryptionService = new EncryptionService();

        //Init pane
        JOptionPane jOpt = new JOptionPane();

        //Execute
        EncryptionPrompt.encryptionPromptActivity(encryptionService, jOpt);
    }
}