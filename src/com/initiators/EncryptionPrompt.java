package com.initiators;

import com.enums.EncryptionActivityType;
import com.services.IEncryptionService;

import javax.swing.*;

public class EncryptionPrompt {

    public static void encryptionPromptActivity(IEncryptionService encryptionService, JOptionPane jOpt) {
        //Define message options
        var choices = new Object[]{"Encrypt", "Decrypt", "Quit"};

        //Get option from user
        int option = JOptionPane.showOptionDialog(null,"What would you like to do today?","Prompt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,choices,null);

        //Execute actions
        if (option == EncryptionActivityType.ENCRYPT.ordinal())
            EncryptionActivity.encryptFiles(encryptionService, jOpt);
        else if(option == EncryptionActivityType.DECRYPT.ordinal())
            EncryptionActivity.decryptFiles(encryptionService, jOpt);
        else
            JOptionPane.showMessageDialog(null,"Application will now exit. Goodbye...","Exiting application",JOptionPane.INFORMATION_MESSAGE);
    }
}