package com.ayhanunal.movies.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.CreditAdapter
import com.ayhanunal.movies.databinding.FragmentPersonDetailsBinding
import com.ayhanunal.movies.viewmodel.PersonDetailsViewModel

class PersonDetailsFragment : Fragment() {

    private lateinit var dataBinding: FragmentPersonDetailsBinding
    private lateinit var viewModel: PersonDetailsViewModel

    private var creditAdapter = CreditAdapter(arrayListOf())

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

        observeLiveData(view)
    }

    private fun observeLiveData(view: View) {
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            dataBinding.actorDetail = it

        })

        viewModel.credits.observe(viewLifecycleOwner, Observer {
            it?.let {
                creditAdapter = CreditAdapter(it)

                view.findViewById<RecyclerView>(R.id.person_details_actor_credits_recycler_view).layoutManager = GridLayoutManager(context, 3)
                view.findViewById<RecyclerView>(R.id.person_details_actor_credits_recycler_view).adapter = creditAdapter
            }
        })
    }
}