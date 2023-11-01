package go.id.dinkesjatengprov.ilikes.ui.activity.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import go.id.dinkesjatengprov.ilikes.databinding.ActivityWebviewBinding
import go.id.dinkesjatengprov.ilikes.ui.base.BaseActivity
import go.id.dinkesjatengprov.ilikes.ui.base.BaseViewModel

class WebviewActivity : BaseActivity<ActivityWebviewBinding, WebviewViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.awToolbar)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WebviewViewModel::class.java)

        val url = intent.getStringExtra("URL")
        val urlTitle = intent.getStringExtra("URL_TITLE")

        binding?.awTvTitle?.text = urlTitle
        binding?.awTvUrl?.text = url

        binding?.awWebview?.settings?.javaScriptEnabled = true
        binding?.awWebview?.settings?.domStorageEnabled = true
        binding?.awWebview?.webViewClient = WebViewClient()
        binding?.awWebview?.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                consoleMessage?.apply {
                    Log.d("MyApplication", "${message()} -- From line ${lineNumber()} of ${sourceId()}")
                }
                return true
            }
        }
        binding?.awWebview?.loadUrl(url ?: "http://google.com")

        binding?.awIvClose?.setOnClickListener { finish() }
        binding?.awIvRefresh?.setOnClickListener {
            binding?.awWebview?.loadUrl(
                url ?: "http://google.com"
            )
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && binding?.awWebview?.canGoBack() == true) {
            binding?.awWebview?.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }
}