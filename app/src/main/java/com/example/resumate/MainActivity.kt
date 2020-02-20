package com.example.resumate
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.resumate.ui.main.MainFragment
import com.example.resumate.ui.main.OCROutputView
import com.example.resumate.ui.main.SharedViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frameTop, MainFragment.newInstance())
                .add(R.id.frameBottom, OCROutputView.newInstance())
                .commitNow()

        }
    }

    override fun onResume() {
        super.onResume()
        print("STUFF")
    }
}
