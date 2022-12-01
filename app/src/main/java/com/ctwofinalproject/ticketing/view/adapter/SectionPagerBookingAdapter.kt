package com.ctwofinalproject.ticketing.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionPagerBookingAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var fragmentList: List<Fragment> = ArrayList()
    private var titleList: List<String> = ArrayList()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {

        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }

    fun addFragment(fragment: Fragment): List<Fragment> {
        fragmentList = addFragment(fragment)
        return fragmentList
    }
    fun addTitle(title: String): List<String> {
        titleList = addTitle(title)
        return titleList
    }
}
