package com.main;

import com.initiators.EncryptionActivity;
import com.services.EncryptionService;
import com.services.IEncryptionService;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //Init service
        IEncryptionService encryptionService = new EncryptionService();

        //Init pane
        JOptionPane jOpt = new JOptionPane();

        //Execute actions
        EncryptionActivity.encryptFiles(encryptionService, jOpt);
        EncryptionActivity.decryptFiles(encryptionService, jOpt);
    }
}