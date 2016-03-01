package com.wq.freeze.kotlinweibo.ui.activity

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.wq.freeze.kotlinweibo.App
import com.wq.freeze.kotlinweibo.R
import com.wq.freeze.kotlinweibo.extension.aaaLogv
import com.wq.freeze.kotlinweibo.extension.lazyFind
import com.wq.freeze.kotlinweibo.extension.safelySubscribeWithLifecycle
import com.wq.freeze.kotlinweibo.extension.showAlert
import com.wq.freeze.kotlinweibo.model.config.AppPreference
import com.wq.freeze.kotlinweibo.model.net.ApiImpl
import com.wq.freeze.kotlinweibo.ui.adapter.MainPageAdapter
import org.jetbrains.anko.find
import kotlin.properties.Delegates

class MainActivity : RxAppCompatActivity(), TabLayout.OnTabSelectedListener {

    val toolBar by lazy { find<Toolbar>(R.id.tool_bar) }
    val drawer by lazyFind<DrawerLayout>(R.id.drawer)
    val navi by lazyFind<NavigationView>(R.id.navigation_view)
    val viewPage by lazyFind<ViewPager>(R.id.vp)
    val tabLayout by lazyFind<TabLayout>(R.id.tab_layout)
    lateinit var avatar: SimpleDraweeView
    lateinit var nick: TextView

    var tokenPref by AppPreference.anyPreference(App.appContext, "token", "")
    var uidPref by AppPreference.anyPreference(App.appContext, "uid", 0L)

//    var token by Delegates.notNull<String>()
//    var uid by Delegates.notNull<Long>()

    var mainPageAdapter by Delegates.notNull<MainPageAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar.title = getString(R.string.app_name)
        setSupportActionBar(toolBar)

        var mDrawerToggle = ActionBarDrawerToggle(
                this, drawer, toolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(mDrawerToggle)
        //        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        //        supportActionBar?.setHomeButtonEnabled(true);
        mDrawerToggle.syncState()

//        token = intent.extras.getString("token", "null")
//        uid = intent.extras.getLong("uid", 0L)
        initViewPage()
        initNavigationView()
    }

    private fun initNavigationView() {
        avatar = navi.getHeaderView(0).find<SimpleDraweeView>(R.id.avatar)
        nick = navi.getHeaderView(0).find<TextView>(R.id.nick)
        ApiImpl.instance.getUserInfo(tokenPref, uidPref)
                .safelySubscribeWithLifecycle(this, {
                    avatar.setImageURI(Uri.parse(it.profile_image_url), null)
                    nick.text = it.name
                }, {
                    it.printStackTrace()
                })
        navi.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.drawer_menu_logout -> {
                    logout()
                    false
                }
                else -> false
            }
        }
    }

    private fun logout() {
        showAlert("Are you sure?", "Logout"){
            tokenPref = ""
            uidPref = 0L
            finish()
        }
    }

    private fun initViewPage() {
        mainPageAdapter = MainPageAdapter(supportFragmentManager, this)
        viewPage.adapter = mainPageAdapter
        viewPage.offscreenPageLimit = 2
        tabLayout.setupWithViewPager(viewPage)
        tabLayout.setTabsFromPagerAdapter(mainPageAdapter)
        tabLayout.setOnTabSelectedListener(this)
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(p0: TabLayout.Tab?) {
        aaaLogv { "select ${p0?.position.toString()}" }
        viewPage.setCurrentItem(p0?.position!!, true)
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {
        aaaLogv { "reselected ${p0?.position.toString()}" }
    }

    //not work
//    fun handleViewPageLimit() {
//        val clazz = viewPage.javaClass
//        val declaredField = clazz.getDeclaredField("DEFAULT_OFFSCREEN_PAGES")
//        declaredField.isAccessible = true
//        declaredField.setInt(viewPage, 0)
//    }
}
