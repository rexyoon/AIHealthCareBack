//package com.aihealthcare.aihealthcare.utils;
//
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;
//
//import java.io.File;
//
//public class ImageToText {
//    public static String extractTextFromImage(String imagePath) {
//        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("src/main/resources/tessdata"); // Tesseract 데이터 경로 설정
//        tesseract.setLanguage("eng"); // 언어 설정
//
//        try {
//            return tesseract.doOCR(new File(imagePath));
//        } catch (TesseractException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
