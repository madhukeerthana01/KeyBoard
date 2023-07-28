package com.example.keyboardtwo;
import com.google.android.material.button.MaterialButton;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyKeyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    // keyboard keys (buttons)

    private MaterialButton mButton1;
    private MaterialButton mButton2;
    private MaterialButton mButton3;
    private MaterialButton mButton4;
    private MaterialButton mButton5;
    private MaterialButton mButton6;
    private MaterialButton mButton7;
    private MaterialButton mButton8;
    private MaterialButton mButton9;
    private MaterialButton mButton0;
    private Button mButtonDelete;
    private Button mButtonEnter;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;
    private InputMethodManager inputMethodManager;

        private void init(Context context, AttributeSet attrs) {
            LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
                mButton1 = findViewById(R.id.button_1);
                mButton2 = findViewById(R.id.button_2);
                mButton3 = findViewById(R.id.button_3);
                mButton4 = findViewById(R.id.button_4);
                mButton5 = findViewById(R.id.button_5);
                mButton6 = findViewById(R.id.button_6);
                mButton7 = findViewById(R.id.button_7);
                mButton8 = findViewById(R.id.button_8);
                mButton9 = findViewById(R.id.button_9);
                mButton0 = findViewById(R.id.button_0);
                mButtonDelete = findViewById(R.id.button_delete);
                mButtonEnter = findViewById(R.id.button_enter);

                // set button click listeners
                mButton1.setOnClickListener(this);
                mButton2.setOnClickListener(this);
                mButton3.setOnClickListener(this);
                mButton4.setOnClickListener(this);
                mButton5.setOnClickListener(this);
                mButton6.setOnClickListener(this);
                mButton7.setOnClickListener(this);
                mButton8.setOnClickListener(this);
                mButton9.setOnClickListener(this);
                mButton0.setOnClickListener(this);
                mButtonDelete.setOnClickListener(this);
                mButtonEnter.setOnClickListener(this);

                // map buttons IDs to input strings
                keyValues.put(R.id.button_1, "1");
                keyValues.put(R.id.button_2, "2");
                keyValues.put(R.id.button_3, "3");
                keyValues.put(R.id.button_4, "4");
                keyValues.put(R.id.button_5, "5");
                keyValues.put(R.id.button_6, "6");
                keyValues.put(R.id.button_7, "7");
                keyValues.put(R.id.button_8, "8");
                keyValues.put(R.id.button_9, "9");
                keyValues.put(R.id.button_0, "0");
                keyValues.put(R.id.button_enter, "\n");

                inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            }


    @Override
    public void onClick(View v) {
        // Do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;

        if (v.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // No selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // Delete the selection
                inputConnection.commitText("", 1);
            }
        } else if (v.getId() == R.id.button_enter) {
            String enteredText = inputConnection.getExtractedText(new ExtractedTextRequest(), 0).text.toString();

            if (TextUtils.isEmpty(enteredText)) {
                // No number entered
                Toast.makeText(getContext(), "Please enter a number.", Toast.LENGTH_SHORT).show();
            } else if (enteredText.length() != 6) {
                // Number length should be 6 digits
                Toast.makeText(getContext(), "Number should be exactly 6 digits.", Toast.LENGTH_SHORT).show();
            } else {
                // All validations passed, show success message
                Toast.makeText(getContext(), "Number entered correctly: " + enteredText, Toast.LENGTH_SHORT).show();
            }

            // Hide the keyboard
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }

    public void setInputConnection(InputConnection inputConnection) {
        this.inputConnection = inputConnection;
    }

    public InputConnection getInputConnection() {
        return inputConnection;
    }
}
