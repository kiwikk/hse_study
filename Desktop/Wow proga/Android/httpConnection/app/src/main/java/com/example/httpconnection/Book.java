package com.example.httpconnection;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Book {
    public String id;
    public String rootDir;
    public String bootSectionId;
    public String description;
    public String bookName;
    public String cover;
    public String author;
    public String genre;
    public String[] is;
    public boolean isFinished = false;


    private static final String INFO_FILE_NAME = "book-info.xml";
    private static final String BOOK_ID_TAG = "bookId";
    private static final String BOOT_SECTION_ID_TAG = "bootSectionId";
    private static final String DESCRIPTION_TAG = "description";
    private static final String BOOK_NAME_TAG = "bookName";
    private static final String COVER_TAG = "cover";
    private static final String AUTHOR_TAG = "author";
    private static final String GENRE_TAG = "genre";

    private static final String BOOK_ID_ATTR_VALUE = "value";
    private static final String BOOT_SECTION_ID_ATTR_VALUE = "value";
    private static final String BOOK_NAME_ATTR_VALUE = "value";
    private static final String COVER_ATTR_VALUE = "value";
    private static final String AUTHOR_ATTR_VALUE = "value";
    private static final String GENRE_ATTR_VALUE = "value";

    public void getInputStream(String book_name) {
        String url = "https://kiwikk.pythonanywhere.com/name?name=" + book_name;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        is = new String[1];
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Log.d("OkHttpClient", "Book 61");
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code" + response);
                    else {
                        is[0] = response.body().string();
                        isFinished = true;
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Exception Book 49", e.getMessage());
            e.printStackTrace();
        }

    }


    public void parseDescription(String is) {
        try {
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParser.setInput(new StringReader(is));

            for (int evType = xmlPullParser.next(); evType != XmlPullParser.END_DOCUMENT; ) {
                if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
                    switch (xmlPullParser.getName()) {
                        case BOOK_NAME_TAG:
                            this.bookName = xmlPullParser.getAttributeValue(null, BOOK_NAME_ATTR_VALUE);
                            break;
                        case AUTHOR_TAG:
                            this.author = xmlPullParser.getAttributeValue(null, AUTHOR_ATTR_VALUE);
                            break;
                        case GENRE_TAG:
                            this.genre = xmlPullParser.getAttributeValue(null, GENRE_ATTR_VALUE);
                            break;
                        case DESCRIPTION_TAG:
                            evType = xmlPullParser.next();
                            if (evType == XmlPullParser.TEXT) {
                                String description = xmlPullParser.getText();
                                description = replaceLeadingLineBreaks(description);
                                description = replaceLeadingLineBreaks(new StringBuilder(description).reverse().toString());
                                this.description = new StringBuilder(description).reverse().toString();
                                break;
                            }
                    }
                }
                evType = xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e("Book 78 XMLPullParser", e.getMessage());
            e.printStackTrace();
        }
    }


    private static String replaceLeadingLineBreaks(String str) {
        int leadingLineBreaks = 0;
        for (; leadingLineBreaks < str.length(); ) {
            char ch = str.charAt(leadingLineBreaks);
            if (ch != '\n' && ch != '\t' && ch != ' ') break;
            ++leadingLineBreaks;
        }
        return str.substring(leadingLineBreaks);
    }
}
