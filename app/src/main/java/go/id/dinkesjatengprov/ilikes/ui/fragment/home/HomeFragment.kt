package go.id.dinkesjatengprov.ilikes.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.R
import go.id.dinkesjatengprov.ilikes.adapter.MenuAdapter
import go.id.dinkesjatengprov.ilikes.data.menuDashboard1
import go.id.dinkesjatengprov.ilikes.data.remote.RetrofitConfig
import go.id.dinkesjatengprov.ilikes.data.remote.model.StatusRequest
import go.id.dinkesjatengprov.ilikes.databinding.FragmentHomeBinding
import go.id.dinkesjatengprov.ilikes.databinding.ItemCarouselBinding
import go.id.dinkesjatengprov.ilikes.helper.listCarouselDummy
import go.id.dinkesjatengprov.ilikes.ui.activity.home.HomeActivity
import go.id.dinkesjatengprov.ilikes.ui.activity.webview.ZoomImageActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseFragment
import go.id.dinkesjatengprov.ilikes.ui.dialog.DialogViewBanner
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        (requireActivity() as HomeActivity).title = ""
//        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        setupObserver()
        viewModel?.getBanner()

        val adapter = MenuAdapter(menuDashboard1)
        binding?.homeRvMenu?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.homeRvMenu?.adapter = adapter

        return binding?.root
    }

    private fun setupObserver() {
        viewModel?.banner?.observe(viewLifecycleOwner){
            when(it.statusRequest){
                StatusRequest.SUCCESS -> {
                    val carouselItem : ArrayList<CarouselItem> = arrayListOf()
                    for (i in it.data?: arrayListOf()){
                        carouselItem.add(
                            CarouselItem(imageUrl = RetrofitConfig.BASE_URL+"v1/management/banner/"+i.link)
                        )
                    }
                    setCarousel(carouselItem)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    private fun setCarousel(banner: ArrayList<CarouselItem>) {
        binding?.homeCarousel?.registerLifecycle(viewLifecycleOwner)
        binding?.homeCarousel?.carouselListener = object : CarouselListener{
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding {
                return ItemCarouselBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                item: CarouselItem,
                position: Int
            ) {
                Log.d("1MAG3", "onBindViewHolder: ${item.imageUrl}")
                val currentBinding = binding as ItemCarouselBinding
                currentBinding.imageView.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setImage(item, R.drawable.banner_dummy1)
                }
                binding.imageView.setOnClickListener {
                    DialogViewBanner(requireContext())
                        .setImage(item.imageUrl!!)
                        .show()

//                    val intent = Intent(requireContext(), ZoomImageActivity::class.java)
//                    intent.putExtra("url", item.imageUrl)
//                    startActivity(intent)
                }
            }
        }
        binding?.homeCarousel?.setData(banner)
    }

}