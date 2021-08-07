package ua.tabarkevych.postspublicapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.tabarkevych.postspublicapi.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}