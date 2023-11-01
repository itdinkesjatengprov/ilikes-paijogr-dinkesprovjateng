package go.id.dinkesjatengprov.ilikes.ui.fragment.service

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.databinding.FragmentServiceListBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseFragment

private const val ARG_KEY = "KEY"

class ServiceListFragment : BaseFragment<FragmentServiceListBinding, ServiceViewModel>() {
    private var key: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getInt(ARG_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceListBinding.inflate(layoutInflater, container, false)

        binding?.fslTv?.text = when (key) {
            1 -> "Jadwal"
            2 -> "Riwayat"
            else -> "Tidak Diketahui"
        }

        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(key: Int) =
            ServiceListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_KEY, key)
                }
            }
    }
}