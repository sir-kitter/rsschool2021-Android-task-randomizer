package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var open2nd: IOpen2nd? = null

    private var buttonGenerate: Button? = null
    private var textPreviousResult: TextView? = null
    private var editMin: EditText? = null
    private var editMax: EditText? = null

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        open2nd = context as IOpen2nd
    }

    override fun onStart() {
        super.onStart()
        //requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onStop() {
        //callback.remove()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textPreviousResult = view.findViewById(R.id.previous_result)
        buttonGenerate = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        textPreviousResult?.text = "Previous result: ${result.toString()}"

        editMin = view.findViewById(R.id.min_value);
        editMax = view.findViewById(R.id.max_value);

        buttonGenerate?.setOnClickListener {
            if(readyToGenerate()) {
                val min = editMin?.text.toString().toInt()
                val max = editMax?.text.toString().toInt()
                open2nd?.Open2nd(min, max)
            }
            else {
                showToast("Invalid input");
            }
        }
    }

    fun readyToGenerate(): Boolean {
        try {
            val min = editMin?.text.toString().toLong()
            val max = editMax?.text.toString().toLong()
            return editMin != null && editMax != null &&
                    editMin!!.text.toString().isNotEmpty() && editMax!!.text.toString()
                .isNotEmpty() &&
                    min >= 0 && max >= 0 && min <= max && min <= Int.MAX_VALUE && max <= Int.MAX_VALUE
        }
        catch(e: Throwable) {
            return false
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}