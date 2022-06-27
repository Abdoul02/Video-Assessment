package com.abdoul.videoassessment.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventItem(
    override val id: String?,
    override val title: String?,
    override val subtitle: String?,
    override val date: Long?,
    override val imageUrl: String?,
    val videoUrl: String?
) : BaseVideoItem,Parcelable
