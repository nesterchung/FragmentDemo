package com.nexterchung.fragmentdemo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {
    private val fragments: MutableMap<String, Class<out Fragment>> = mutableMapOf()

    init {
        fragments["one"] = FragmentOne::class.java
        fragments["two"] = FragmentTwo::class.java

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info { "onCreate" }
        setContentView(R.layout.activity_main)

        switchToFragment("one")

        findViewById<RadioGroup>(R.id.radioGroup).setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_one -> switchToFragment("one")
                R.id.radio_two -> switchToFragment("two")
                else -> Unit
            }
        }
    }

    private fun switchToFragment(tag: String) {
        info { "switchToFragment $tag" }
        val beginTransaction = supportFragmentManager.beginTransaction()
        fragments.keys.filterNot {
            it == tag
        }.mapNotNull {
            supportFragmentManager.findFragmentByTag(it)
        }.forEach {
            beginTransaction.hide(it)
        }

        supportFragmentManager.findFragmentByTag(tag).let {
            if (it == null) {
                beginTransaction.add(R.id.content_fragment, fragments[tag]?.newInstance(), tag).commit()
            } else {
                beginTransaction.show(it).commit()
            }
        }
    }
}


abstract class BaseFragment : Fragment(), AnkoLogger {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        info { "onAttach" }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        info { "onActivityCreated" }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        info { "onCreateView" }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info { "onViewCreated" }
    }

    override fun onDestroy() {
        info { "onDestroy" }
        super.onDestroy()
    }

    override fun onDestroyView() {
        info { "onDestroyView" }
        super.onDestroyView()
    }

    override fun onDetach() {
        info { "onDetach" }
        super.onDetach()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        info { "onViewStateRestore" }
    }

    override fun onPause() {
        super.onPause()
        info { "onPause" }
    }

    override fun onStart() {
        super.onStart()
        info { "onStart" }
    }

    override fun onResume() {
        super.onResume()
        info { "onResume" }
    }

    override fun onStop() {
        super.onStop()
        info { "onStop" }
    }


}

class FragmentOne : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        info { "onCreateView" }
        return TextView(context).apply {
            text = "FragmentOne"
        }
    }
}

class FragmentTwo : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        info { "onCreateView" }
        return TextView(context).apply {
            text = "FragmentTwo"
        }
    }
}
