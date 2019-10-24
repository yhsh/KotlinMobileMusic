package cn.xiayiye5.kotlinmobilemusic.ui.activity

import androidx.appcompat.widget.Toolbar
import cn.xiayiye5.kotlinmobilemusic.R
import cn.xiayiye5.kotlinmobilemusic.base.BaseActivity
import cn.xiayiye5.kotlinmobilemusic.util.FragmentUtil
import cn.xiayiye5.kotlinmobilemusic.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_index.*
import org.jetbrains.anko.find

class IndexActivity : BaseActivity(), ToolBarManager {
    override val toolBar by lazy { find<Toolbar>(R.id.tb_tittle) }

    override fun getLayoutId(): Int {
        return R.layout.activity_index
    }

    override fun initData() {
        super.initData()
        initIndexToolBar()
    }

    override fun initListener() {
        super.initListener()
        bottomBar.setOnTabSelectListener {
            supportFragmentManager.beginTransaction().replace(
                R.id.index_replace,
                FragmentUtil.fragmentUtil.getFragment(it)!!, it.toString())
                .commit()
        }
    }
}
