package com.example.projokes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projokes.R
import com.example.projokes.databinding.FragmentDetailBinding
import com.example.projokes.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding
    private var jokeUuid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            jokeUuid = DetailFragmentArgs.fromBundle(it).jokeUuid
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(jokeUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.jokeLiveData.observe(viewLifecycleOwner, Observer { joke ->
            joke?.let {
                dataBinding.joke = joke
            }
        })
    }
}