package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/*
*   Name: Manraj Singh
*   Banner Id: B00877934
*   email: manraj.singh@dal.ca
*
*       Task1: Corrected the build issue by changing the version of gradle and made the build successful to start with
*       Task2: Made changes in the NotesFragment fragment and swapped the action of left and right swipe
*       Task3: Made changes in the SwipeNotesCallBack to change the background color, animation and image on the left and right swipe action
*       Task4: Formatted all files
*       Task5: Added comments
*
* */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}