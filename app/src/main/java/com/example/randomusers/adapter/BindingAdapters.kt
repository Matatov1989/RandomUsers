package com.example.randomusers.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.randomusers.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide
            .with(view.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_android)
            .into(view)
    }

    @BindingAdapter("date")
    @JvmStatic
    fun calculateDaysNextBirthday(view: TextView, date: String) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dob = LocalDate.parse(date, formatter)
        val today = LocalDate.now()
        val nextBirthday = dob.withYear(today.year)
        val finalNextBirthday = if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
            nextBirthday.plusYears(1)
        } else {
            nextBirthday
        }
        val days = ChronoUnit.DAYS.between(today, finalNextBirthday)

        view.text = view.context.getString(R.string.daysToNextBirthday, days)
    }
}