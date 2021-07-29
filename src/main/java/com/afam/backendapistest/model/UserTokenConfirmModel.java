package com.afam.backendapistest.model;

public class UserTokenConfirmModel {
    private String verificationToken;

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    @Override
    public String toString() {
        return "UserTokenConfirmModel{" +
                "verificationToken='" + verificationToken + '\'' +
                '}';
    }

    //    private boolean verify(String verificationToken){
//        if(user == null || user.isEnable()){
//            return false;
//        }else {
//            user.setEnable(true);
//            return true;
//        }
//    }
}
