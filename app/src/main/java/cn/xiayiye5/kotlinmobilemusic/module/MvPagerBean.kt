package cn.xiayiye5.kotlinmobilemusic.module

data class MvPagerBean(var totalCount: Int, var videos: List<VideosBean>)
data class VideosBean(
        var id: Int, var title: String, var description: String, var artistName: String,
        var posterPic: String, var thumbnailPic: String, var albumImg: String, var regdate: String,
        var videoSourceTypeName: String, var totalViews: Int, var totalPcViews: Int,
        var totalMobileViews: Int, var totalComments: Int, var url: String,
        var hdUrl: String, var uhdUrl: String, var shdUrl: String, var videoSize: Int,
        var hdVideoSize: Int,var uhdVideoSize: Int, var shdVideoSize: Int, var duration: Int,
        var status: Int, var linkId: Int , var playListPic: String,var artists: List<ArtistsBean>)
data class ArtistsBean(var artistId: Int, var artistName: String)
//class MvPagerBean {
//    var totalCount: Int = 0
//    var videos: List<VideosBean>? = null
//
//    class VideosBean {
//
//        var id: Int = 0
//        var title: String? = null
//        var description: String? = null
//        var artistName: String? = null
//        var posterPic: String? = null
//        var thumbnailPic: String? = null
//        var albumImg: String? = null
//        var regdate: String? = null
//        var videoSourceTypeName: String? = null
//        var totalViews: Int = 0
//        var totalPcViews: Int = 0
//        var totalMobileViews: Int = 0
//        var totalComments: Int = 0
//        var url: String? = null
//        var hdUrl: String? = null
//        var uhdUrl: String? = null
//        var shdUrl: String? = null
//        var videoSize: Int = 0
//        var hdVideoSize: Int = 0
//        var uhdVideoSize: Int = 0
//        var shdVideoSize: Int = 0
//        var duration: Int = 0
//        var status: Int = 0
//        var linkId: Int = 0
//        var playListPic: String? = null
//        var artists: List<ArtistsBean>? = null
//
//        class ArtistsBean {
//            var artistId: Int = 0
//            var artistName: String? = null
//        }
//    }
//}

/*{
  "videos": [
    {
      "ArtistsBean": [
        {
          "artistId": 12,
          "artistName": "肺炎视频"
        },
        {
          "artistId": 13,
          "artistName": "肺炎视频2"
        }
      ],
      "artistName": "救治医生",
      "id": 212,
      "playListPic": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582303195157&di=d5ab60b072287785656842f8cf824a9e&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2018-5%2F15263513171586087.jpg",
      "title": "肺炎救治最可爱的人",
      "url": "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4"
    },
    {
      "ArtistsBean": [
        {
          "artistId": 12,
          "artistName": "肺炎视频"
        },
        {
          "artistId": 13,
          "artistName": "肺炎视频2"
        }
      ],
      "artistName": "肺炎下降",
      "id": 214,
      "playListPic": "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2422837038,1980360875&fm=26&gp=0.jpg",
      "title": "新冠肺炎",
      "url": "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218025702PSiVKDB5ap.mp4"
    },
    {
      "ArtistsBean": [
        {
          "artistId": 12,
          "artistName": "肺炎视频"
        },
        {
          "artistId": 13,
          "artistName": "肺炎视频2"
        }
      ],
      "artistName": "山东卫视",
      "id": 213,
      "playListPic": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1618093797,886872480&fm=26&gp=0.jpg",
      "title": "闪电视频",
      "url": "https://v-cdn.zjol.com.cn/280443.mp4"
    },
    {
      "ArtistsBean": [
        {
          "artistId": 12,
          "artistName": "高清资源"
        }
      ],
      "artistName": "高清视频",
      "id": 215,
      "playListPic": "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=74776306,2886946514&fm=26&gp=0.jpg",
      "title": "向日葵",
      "url": "https://v-cdn.zjol.com.cn/276982.mp4"
    },
    {
      "ArtistsBean": [
        {
          "artistId": 13,
          "artistName": "高清资源浙江在线"
        }
      ],
      "artistName": "高清视频浙江在线",
      "id": 216,
      "playListPic": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582302083648&di=7fa226cceebcf08190604a0e1a78f16b&imgtype=0&src=http%3A%2F%2Fwww.17qq.com%2Fimg_qqtouxiang%2F82016386.jpeg",
      "title": "浙江在线",
      "url": "https://v-cdn.zjol.com.cn/276984.mp4"
    },
    {
      "ArtistsBean": [
        {
          "artistId": 14,
          "artistName": "高清资源浙江在线"
        }
      ],
      "artistName": "高清为人民服务",
      "id": 217,
      "playListPic": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2775067587,3644432650&fm=26&gp=0.jpg",
      "title": "为人民服务",
      "url": "https://v-cdn.zjol.com.cn/276985.mp4"
    }
  ],
  "totalCount": 99
}*/
