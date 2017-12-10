package com.lubo.learning.heroku.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {
    public String loadFile(String fileName) {
        InputStream in = getResourceAsStream(fileName);
                //ClassLoader.getSystemResourceAsStream(fileName);
                //getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String sCurrentLine;
        StringBuilder sb = new StringBuilder();

        try {
            while ((sCurrentLine = reader.readLine()) != null) {
                sb.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public List<String> getResourceFiles( String path ) {
        List<String> filenames = new ArrayList<>();

        try(
                InputStream in = getResourceAsStream( path );
                BufferedReader br = new BufferedReader( new InputStreamReader( in ) ) ) {
            String resource;

            while( (resource = br.readLine()) != null ) {
                filenames.add( path + "/" + resource );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filenames;
    }

    public InputStream getResourceAsStream( String resource ) {
        final InputStream in
                = this.getClass().getClassLoader().getResourceAsStream( resource );

        return in == null ? getClass().getResourceAsStream( resource ) : in;
    }

}
