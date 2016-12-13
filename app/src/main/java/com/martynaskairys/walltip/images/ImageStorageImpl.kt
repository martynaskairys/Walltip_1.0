package com.martynaskairys.walltip.images

import android.content.Context
import java.util.*

/** Store image Urls */
internal class ImageStorageImpl(val context: Context) : ImageStorage {

    override fun saveAllUrls(imageUrls: Array<String>) {
        val imagesList = toList(imageUrls)
        saveInternal(ALL_CHOSEN_FOLDER_URLS, imagesList)
    }

    private fun toList(imageUrls: Array<String>): List<String> {
        val arrayList = ArrayList<String>()
        for (url in imageUrls) {
            arrayList.add(url)
        }
        return arrayList
    }

    override fun saveShownUrls(imageUrls: List<String>) {
        saveInternal(SHOWN_URLS, imageUrls)
    }

    private fun saveInternal(key: String, imageUrls: List<String>) {
        val urlsSet = HashSet<String>(imageUrls)

        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val edit = preferences.edit()
        edit.putStringSet(key, urlsSet)
        edit.apply()
    }

    /** Returns all urls from chosen folder */
    override fun getAllUrls() : ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(ALL_CHOSEN_FOLDER_URLS, Collections.emptySet())
        return ArrayList(stringSet)
    }

    // todo have every method return List

    /** TODO Update comment Returns urls from chosen folder that have not been shown yet */
    override fun getShownUrls() : ArrayList<String> {
        val preferences = context.getSharedPreferences(STANDARD, Context.MODE_PRIVATE)
        val stringSet = preferences.getStringSet(SHOWN_URLS, Collections.emptySet())
        return ArrayList(stringSet)
    }

    companion object {
        val ALL_CHOSEN_FOLDER_URLS = "chosen_folder_urls"
        val SHOWN_URLS = "shown_urls"
        val STANDARD = "standard"
    }

}

interface ImageStorage {
    /** Saves full list image urls chose by user */
    fun saveAllUrls(imageUrls: Array<String>)
    fun saveShownUrls(imageUrls: List<String>)
    /** Returns all urls from chosen folder */
    fun getAllUrls() : ArrayList<String>
    /** Returns urls from chosen folder that have been shown already */
    fun getShownUrls() : ArrayList<String>
}
