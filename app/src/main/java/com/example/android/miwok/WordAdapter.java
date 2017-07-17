package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

/*
* {@link WordAdapter} is an {@link WordAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link WordAdapter} objects.
* */
public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int ColorResourceId) {
        super(context, 0, words);
        mColorResourceId = ColorResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link miwok} object located at this position in the list
        Word currentWord = getItem(position);
        // Find the TextView in the list_item.xml layout with the miwok translation
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_word);
        // Get the miwok word from the current Word object and
        // set this text on the miwok TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the english translation
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_word);
        // Get the english word from the current Word object and
        // set this text on the english TextView
        englishTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the imageResourceId
        ImageView imageResource = (ImageView) listItemView.findViewById(R.id.image);
        // Checks if the Word object has an imageResourceId defined
        if (currentWord.hasImage()) {
            // Get the image from the current Word object and
            // set this image on the ImageView
            imageResource.setImageResource(currentWord.getImageResourceId());
            // If there is an imageResourceId, it is set to visible
            imageResource.setVisibility(View.VISIBLE);}

        // If there is no imageResourceId, the space for image on the listView is removed.
        else { imageResource.setVisibility(View.GONE); }

        // Finds the LinearLayout with the english and miwok translation textViews.
        RelativeLayout linear = (RelativeLayout) listItemView.findViewById(R.id.words);
        // Sets a background color for this LinearLayout found above
        linear.setBackgroundColor(ContextCompat.getColor(getContext(), mColorResourceId));

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}