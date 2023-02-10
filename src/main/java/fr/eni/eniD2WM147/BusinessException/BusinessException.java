package fr.eni.eniD2WM147.BusinessException;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
    private List<String> listeMessage;


    public  BusinessException() {
        this.listeMessage = new ArrayList<>();
    }


    public List<String> getListeMessage() {
        return listeMessage;
    }


    public void addMessage(String message){
        this.listeMessage.add(message);
    }



}
