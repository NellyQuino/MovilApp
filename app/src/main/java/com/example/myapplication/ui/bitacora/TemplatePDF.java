package com.example.myapplication.ui.bitacora;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;


import com.example.myapplication.BuildConfig;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Documented;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TemplatePDF {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private Font fSubTitleTable = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD, BaseColor.WHITE);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15,Font.BOLD, BaseColor.RED);


    public TemplatePDF(Context context){
        this.context = context;
    }

    public void openDocument(){
        createFile();
        try {
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(pdfFile));
            document.open();

        } catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    private void createFile(){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if(!folder.exists())
            folder.mkdir();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Mibitácora_" +date+ ".pdf");
    }

    //Cerrar el documento
    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title, String subTitle){
        try {
            paragraph = new Paragraph();
            addChild(new Paragraph(title,fTitle));
            addChild(new Paragraph(subTitle,fSubTitle));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addTitles",e.toString());
        }
    }

    private void addChild(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text,fText);
            paragraph.setSpacingBefore(5);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addParagraph",e.toString());
        }
    }

    public void createTable(String[] headers, ArrayList<String[]> data){

        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(headers.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC =0;
            while (indexC < headers.length){
                pdfPCell = new PdfPCell(new Phrase( headers[indexC++], fSubTitleTable));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(new BaseColor(102,137,116));
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < data.size(); indexR++){
                String[] row = data.get(indexR);
                for (indexC=0;indexC< headers.length;indexC++){
                    pdfPCell= new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable",e.toString());
        }
    }
}


