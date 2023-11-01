package go.id.dinkesjatengprov.ilikes.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import go.id.dinkesjatengprov.ilikes.ui.fragment.service.ServiceListFragment

class ServiceViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ServiceListFragment()
        fragment.arguments = Bundle().apply {
            putInt("KEY", position + 1)
        }
        return fragment
    }

}