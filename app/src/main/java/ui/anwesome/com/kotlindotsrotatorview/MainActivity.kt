package ui.anwesome.com.kotlindotsrotatorview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.dotsrotatorview.DotsRotatorView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DotsRotatorView.create(this)
    }
}
