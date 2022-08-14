package com.example.myapplication.domain;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SecureFileHandle<T extends SecureFile> {

    private final Context context;

    public T getFile() {
        return data;
    }

    private T data;

    public SecureFileHandle(Context context, T data) {
        this.context = context;
        this.data = data;
        this.loadFile();
    }

    public void saveFile() {
        File file = new File(context.getFilesDir(), data.getFileName());
        if (file.exists()) {
            boolean dontCare = file.delete();
        }
        try {
            Gson gson = new Gson();
            String json = gson.toJson(data);
            boolean fileCreatedSuccessDontCare = file.createNewFile();
            try (FileOutputStream fos = context.openFileOutput(data.getFileName(), Context.MODE_PRIVATE)) {
                fos.write(json.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        File file = new File(context.getFilesDir(), data.getFileName());
        if (!file.exists()) {
            return ;
        }
        String content = readStringFromFile(file);

        //if(content ==null|| content.length() ==0) return;
        this.data = new Gson().fromJson(content,data.getType());
        if (this.data != null && this.data.isValid()) {
            return;
        }
        file.delete();
        return;
    }

    public  void reload(){
        this.loadFile();
    }

    private String readStringFromFile(File file) {
        StringBuilder text = new StringBuilder();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            }
        } catch (IOException e) {
            //You'll need to add proper error handling here
            return "";
        }
        return text.toString();
    }

}
