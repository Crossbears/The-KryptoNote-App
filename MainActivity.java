package com.example.kryptonote;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;



public class MainActivity extends AppCompatActivity {

    private String key;
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


        public String makePad(String note)
        {
            key= ((EditText) findViewById(R.id.key)).getText().toString();
        String pad = this.key;
        for (; pad.length() < note.length(); )
        {
            pad += this.key;
        }
        return pad;
    }

    public String encrypt(String note)
    {
        String pad = makePad(note);
        String result="";
        for (int i = 0; i < note.length(); i++)
        {
            String c = note.substring(i, i + 1);
            int position = ALPHABET.indexOf(c);
            int shift = Integer.parseInt(pad.substring(i, i + 1));
            int newPosition = (position + shift) % ALPHABET.length();
            result = result + ALPHABET.substring(newPosition, newPosition + 1);
        }
        return result;
    }

    public String decrypt(String note)
    {
        String pad = makePad(note);
        String result="";
        for (int i = 0; i < note.length(); i++) {
            String c = note.substring(i, i + 1);
            int position = ALPHABET.indexOf(c);
            int shift = Integer.parseInt(pad.substring(i, i + 1));
            int newPosition = (position - shift) % ALPHABET.length();
            if (newPosition < 0) {
                newPosition = newPosition + ALPHABET.length();
            }
            result=result + ALPHABET.substring(newPosition, newPosition + 1);
        }
        return result;
    }



    public void onEncrypt(View v)
    {
        try {
            EditText noteView = findViewById(R.id.data);
            String note = noteView.getText().toString();
            String result = encrypt(note);
            ((EditText) findViewById(R.id.data)).setText(result);
        }

        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public void onDecrypt(View v)
    {
        try {
            EditText noteView = findViewById(R.id.data);
            String note = noteView.getText().toString();
            String result = decrypt(note);
            ((TextView) findViewById(R.id.data)).setText(result);
        }

        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public void onSave(View v)
    {
        try {
            String name = ((EditText) findViewById(R.id.file)).getText().toString();
            File dir = this.getFilesDir();
            File file = new File(dir, name);
            FileWriter fw = new FileWriter(file);
            fw.write(((EditText) findViewById(R.id.data)).getText().toString());
            fw.close();
            Toast label=Toast.makeText(this, "Note Saved.", Toast.LENGTH_LONG);
            label.show();
        }
        catch (Exception e)
        {
            Toast label= Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }
    }

    public void onLoad(View v)
    {
        try {
            String name = ((EditText) findViewById(R.id.file)).getText().toString();
            File dir = this.getFilesDir();
            File file = new File(dir, name);
            FileReader fr = new FileReader(file);
            String show = "";
            for (int c = fr.read(); c != -1; c = fr.read())
            {
                show +=(char)c;
            }
            fr.close();
            ((EditText) findViewById(R.id.data)).setText(show);
            Toast label=Toast.makeText(this, "Note Loaded.", Toast.LENGTH_LONG);
            label.show();
        }

        catch (Exception e)
        {
            Toast label= Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
            label.show();
        }
    }


}