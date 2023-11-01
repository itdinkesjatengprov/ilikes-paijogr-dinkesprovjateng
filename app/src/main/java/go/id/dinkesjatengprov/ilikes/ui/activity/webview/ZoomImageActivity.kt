package go.id.dinkesjatengprov.ilikes.ui.activity.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import go.id.dinkesjatengprov.ilikes.databinding.ActivityZoomImageBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity

class ZoomImageActivity : BaseActivity<ActivityZoomImageBinding, WebviewViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomImageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val url = intent.getStringExtra("url")
        Glide.with(this)
            .load(url)
            .into(binding?.imageView as ImageView)
    }
}