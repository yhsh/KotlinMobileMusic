package cn.xiayiye5.kotlinmobilemusic.module

/**
 * ClassName:HomeItemBean
 * Description:home界面每个条目的bean
 */
data class HomeItemBeans(
    val `data`: List<HomeItemBean>
)

data class HomeItemBean(
    val clickUrl: String,
    val description: String,
    val hdUrl: String,
    val hdVideoSize: Int,
    val id: Int,
    val posterPic: String,
    val status: Int,
    val thumbnailPic: String,
    val title: String,
    val type: String,
    val uhdUrl: String,
    val uhdVideoSize: Int,
    val url: String,
    val videoSize: Int
)