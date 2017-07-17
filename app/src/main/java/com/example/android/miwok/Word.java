package com.example.android.miwok;
/**
 * Created by USER on 15-Apr-17.
 * This class makes accessible, the default and miwok translations of words.
 */

public class Word{
    // The states of the class are defined here. Note they are private. This is for default word.
    private String mDefaultTranslation;

    // This is for miwok word.
    private String mMiwokTranslation;

    // This is for imageResource.
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    // Hhis provides a boolean logic
    private final static int NO_IMAGE_PROVIDED = -1;

    private int mMediaResourceId;
    /**
     * This is a constructor that initializes the class
     * It accepts two inputs. Texts for default and miwok words
     *
     * @param defaultTranslation is the default translation of the word
     * @param miwokTranslation   is the miwok translation of the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int mediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMediaResourceId = mediaResourceId;
    }

    /**
     * This is a constructor that initializes the class
     * It accepts three inputs. Texts for default word, miwok word and image word
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int mediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mMediaResourceId = mediaResourceId;
    }


    /**
     * These are methods that allow a caller to get outputs from the class
     * The first gets the default translation of the word
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * This gets the miwok translation of the word
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * This gets the image for each translation
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getMediaResourceId() { return mMediaResourceId; }
}
