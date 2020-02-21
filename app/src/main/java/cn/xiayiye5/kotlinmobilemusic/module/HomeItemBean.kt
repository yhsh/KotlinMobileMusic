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
/*{
  "data": [
    {
      "description": "微信主流头像",
      "posterPic": "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3896767983,959806183&fm=26&gp=0.jpg",
      "title": "微信头像"
    },
    {
      "description": "QQ主流头像",
      "posterPic": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582286361383&di=156b13daa6333b9cc38c5eb3a41d6064&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20181113%2F13%2F1542086037-pjSqfITMOP.jpg",
      "title": "QQ头像"
    },
    {
      "description": "向日葵风景图",
      "posterPic": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582286486405&di=d791caf27b3d725ddc23cdc01137d091&imgtype=0&src=http%3A%2F%2Fwww.17qq.com%2Fimg_qqtouxiang%2F81516641.jpeg",
      "title": "向日葵"
    },
    {
      "description": "鲜花送给你",
      "posterPic": "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=227290577,1607606749&fm=26&gp=0.jpg",
      "title": "鲜花"
    },
    {
      "description": "唯美气球",
      "posterPic": "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2920793283,2292775170&fm=26&gp=0.jpg",
      "title": "气球"
    }
  ]
}*/
)