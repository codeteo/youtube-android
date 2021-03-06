package com.github.mrbean355.android.navigation.navdrawer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mrbean355.android.navigation.R
import kotlinx.android.synthetic.main.fragment_demo.*

class ShareFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        demo_text.text = "Share fragment (click me)"
        demo_text.setOnClickListener {
            findNavController().navigate(R.id.action_nav_share_to_nav_details)
        }
    }
}