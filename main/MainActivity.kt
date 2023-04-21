package com.example.Bustonks


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.Bustonks.fragments.Buss
import com.example.Bustonks.fragments.FirstFragment
import com.example.Bustonks.fragments.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import java.util.*


class MainActivity : AppCompatActivity(), FirstFragment.OnFragmentSendDataListener {

    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayFragments: ArrayList<Fragment> = arrayListOf<Fragment>(
            Buss(),
            FirstFragment(),
            SecondFragment()
        )
        val adapter = ViewPagerAdapter(supportFragmentManager, arrayFragments)
        vp.adapter = adapter
        vp.setCurrentItem(1)
        vp.setOffscreenPageLimit(2)
        vp.setOnPageChangeListener(myOnPageChangeListener)

    }

    var myOnPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            if(state == 1)
                manageKeyboard(findViewById(R.id.ggwp), 1)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float, positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {

            //Скрыть/показать клавиатуру на фрагментах

            if(vp.currentItem == 1)
                manageKeyboard(fir_frag, 0)
        }
    }



    //Убрать клавиатуру
    fun Context.manageKeyboard(view: View, ind: Int){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Показать
        if(ind == 0)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        //Убрать
        if(ind == 1)
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        manageKeyboard(fir_frag, 1)
    }


    override fun onRefresh() {
        sec_frag.Spisok.adapter!!.notifyDataSetChanged()
    }
}