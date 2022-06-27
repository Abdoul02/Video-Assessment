package com.abdoul.videoassessment.presentation.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdoul.videoassessment.common.DateConversion.toFormatDate
import com.abdoul.videoassessment.common.loadImage
import com.abdoul.videoassessment.databinding.FragmentEventsBinding
import com.abdoul.videoassessment.databinding.VideoItemBinding
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.presentation.VideoListSate
import com.abdoul.videoassessment.presentation.adapter.BaseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : Fragment() {

    private val eventViewModel: EventViewModel by viewModels()
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    private val eventAdapter by lazy { BaseAdapter<EventItem>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeEvents()
        setUpRecyclerView()
        return root
    }

    private fun observeEvents() {
        lifecycleScope.launchWhenStarted {
            eventViewModel.events.collect {
                when (it) {
                    is VideoListSate.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is VideoListSate.Data -> {
                        if (it.videoItems.isNotEmpty()) {
                            binding.progressBar.isVisible = false
                            binding.eventRv.isVisible = true
                            eventAdapter.setData(it.videoItems as List<EventItem>)
                        }
                    }
                    is VideoListSate.Error -> {
                        Toast.makeText(requireContext(), it.errorMsg, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        eventAdapter.expressionViewHolderBinding = { eventItem, viewBinding ->
            val view = viewBinding as VideoItemBinding
            view.txtTitle.text = eventItem.title
            eventItem.subtitle?.let {
                view.txtSubtitle.isVisible = true
                view.txtSubtitle.text = it
            }
            view.txtDate.text = eventItem.date?.toFormatDate()
            eventItem.imageUrl?.let { view.imgThumbnail.loadImage(it) }
            view.root.setOnClickListener {
                it.findNavController().navigate(EventFragmentDirections.eventToPlayer(eventItem))
            }
        }

        eventAdapter.expressionOnCreateViewHolder = { viewGroup ->
            VideoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.eventRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
            itemAnimator = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}