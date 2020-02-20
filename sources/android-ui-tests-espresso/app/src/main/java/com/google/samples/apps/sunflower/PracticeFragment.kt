package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_practice.*

class PracticeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_practice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            entered_name_text.text = savedInstanceState.getCharSequence("name")
        }

        enter_name_button.setOnClickListener {

            if(name_edit_text.text.isNullOrEmpty()) {
                name_input_layout.error = "名前が空です"
                return@setOnClickListener
            }

            entered_name_text.text = name_edit_text.text
        }

        clear_button.setOnClickListener {
            entered_name_text.text = ""
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence("name", entered_name_text.text);
    }

    fun clearText() {
        entered_name_text.text = ""
    }
}
