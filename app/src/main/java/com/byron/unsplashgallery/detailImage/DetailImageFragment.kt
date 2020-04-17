package com.byron.unsplashgallery.detailImage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.byron.unsplashgallery.R
import com.byron.unsplashgallery.databinding.FragmentDetailImageBinding
import com.byron.unsplashgallery.detailImage.DetailImageFragmentArgs.Companion.fromBundle


class DetailImageFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentDetailImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_image, container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.image = fromBundle(requireArguments()).image
    }

}
