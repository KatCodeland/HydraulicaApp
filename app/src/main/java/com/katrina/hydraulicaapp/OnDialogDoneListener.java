package com.katrina.hydraulicaapp;

/**
 * Created by Ashley on 14/11/2017.
 */

public interface OnDialogDoneListener {

    //when click on done or ok, this method gets called
    public void onDialogDone(String tag, boolean cancelled, CharSequence message);
    //character sequence can be processed a buffer, you could use string here if you wanted to
    //when click on done or ok, this mehtod gets called
}
