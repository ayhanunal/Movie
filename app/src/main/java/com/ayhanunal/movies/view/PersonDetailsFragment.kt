package com.ayhanunal.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.CreditAdapter
import com.ayhanunal.movies.databinding.FragmentMovieDetailsBinding
import com.ayhanunal.movies.databinding.FragmentPersonDetailsBinding
import com.ayhanunal.movies.util.downloadFromUrl
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.viewmodel.PersonDetailsViewModel
import kotlinx.android.synthetic.main.fragment_person_details.*

class PersonDetailsFragment : Fragment() {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w780"

    private lateinit var dataBinding: FragmentPersonDetailsBinding
    private lateinit var viewModel: PersonDetailsViewModel

    private var adapter = CreditAdapter(arrayListOf())

    private var actorId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_person_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            actorId = PersonDetailsFragmentArgs.fromBundle(it).actorID
        }

        viewModel = ViewModelProviders.of(this).get(PersonDetailsViewModel::class.java)
        viewModel.refreshData(actorId)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            dataBinding.actorDetail = it

            context?.let {context ->
                person_details_actor_detail_image.downloadFromUrl(IMAGE_PATH + it.profile_path, placeholderProgressBar(context))
            }
        })

        viewModel.credits.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter = CreditAdapter(it)
                person_details_actor_credits_recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                person_details_actor_credits_recycler_view.adapter = adapter
            }
        })
    }
}