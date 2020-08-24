package com.jet2traveltech.article.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jet2traveltech.article.databinding.FragmentAboutUsBinding

/**
 * Fragment to show about us screen simply.
 * Implemented to user demonstrate navigation fragment.
 */
class AboutUsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentAboutUsBinding.inflate(inflater, container, false).root
}