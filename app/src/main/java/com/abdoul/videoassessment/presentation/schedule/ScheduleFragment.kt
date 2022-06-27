package com.abdoul.videoassessment.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdoul.videoassessment.common.DateConversion.isTomorrow
import com.abdoul.videoassessment.common.DateConversion.toFormatDate
import com.abdoul.videoassessment.common.loadImage
import com.abdoul.videoassessment.databinding.FragmentScheduleBinding
import com.abdoul.videoassessment.databinding.VideoItemBinding
import com.abdoul.videoassessment.domain.model.ScheduleItem
import com.abdoul.videoassessment.presentation.VideoListSate
import com.abdoul.videoassessment.presentation.adapter.BaseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val scheduleAdapter by lazy { BaseAdapter<ScheduleItem>() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeSchedule()
        setUpRecyclerView()

        return root
    }

    private fun observeSchedule() {
        lifecycleScope.launchWhenStarted {
            scheduleViewModel.schedule.collect {
                when (it) {
                    is VideoListSate.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is VideoListSate.Data -> {
                        if (it.videoItems.isNotEmpty()) {
                            binding.progressBar.isVisible = false
                            binding.scheduleRv.isVisible = true
                            scheduleAdapter.setData(
                                it.videoItems.filter { item -> item.date?.isTomorrow() ?: true } as List<ScheduleItem>)
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
        scheduleAdapter.expressionViewHolderBinding = { scheduleItem, viewBinding ->
            val view = viewBinding as VideoItemBinding
            view.txtTitle.text = scheduleItem.title
            scheduleItem.subtitle?.let {
                view.txtSubtitle.isVisible = true
                view.txtSubtitle.text = it
            }
            view.txtDate.text = scheduleItem.date?.toFormatDate()
            scheduleItem.imageUrl?.let { view.imgThumbnail.loadImage(it) }
        }

        scheduleAdapter.expressionOnCreateViewHolder = { viewGroup ->
            VideoItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.scheduleRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
            itemAnimator = null
        }
    }

    private fun startRefresh(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}