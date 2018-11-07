package id.alif.footbalmatchschedule.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {


    override fun getPageTitle(position: Int): CharSequence? {
      return when (position) {
           0 -> "Last Matches"
          else -> {
              return "Next Matches"
          }

       }
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                LastMatchFragment()
            }
            else -> {
                return NextMatchFragment()
            }
        }
    }

    override fun getCount(): Int {
       return 2
    }


}