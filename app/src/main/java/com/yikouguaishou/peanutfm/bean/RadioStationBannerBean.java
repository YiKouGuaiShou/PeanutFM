package com.yikouguaishou.peanutfm.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class RadioStationBannerBean {

    /**
     * userInfo : null
     * bannerList : [{"id":153,"url":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png?x-oss-process=image/resize,m_mfit,h_200,w_420","name":"深夜有心人","columnId":"1110","columnName":"深夜有心人","prividerCode":13010,"type":"3","linkType":"1","linkUrl":"","linkName":"","title":"","linkText":"","androidMin":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png","androidMinW":0,"androidMinH":0,"androidMedi":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png","androidMediW":0,"androidMediH":0,"androidMax":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png","androidMaxW":1080,"androidMaxH":468,"broadcasting_name":null,"broadcasting_logo":null,"broadcasting_address":null,"broadcasting_id":null,"broadcasting_frequency":null}]
     * cons : [{"id":217,"name":"俊情一点","status":1,"type":10,"showNumber":3,"layout":0,"sectionDetails":[{"id":0,"plateId":0,"resourceId":282,"type":6,"cover":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F17%2F11%2Fjpg2016111711197.jpg","linkUrl":null,"linkType":null,"createTime":null,"name":"radiofan","lookNumber":0,"collectNumber":0,"descriptions":null,"providerCode":null,"isCollect":0,"commentNumber":0,"imgList":null,"linkImg":null,"linkTitle":null,"anchorId":"282"}]},{"id":202,"name":"推荐","status":1,"type":6,"showNumber":3,"layout":1,"sectionDetails":[{"id":1546,"plateId":0,"resourceId":1110,"type":5,"cover":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F14%2Fpng2016110214321.png","linkUrl":null,"linkType":null,"createTime":"2016-12-19 17:21:23","name":"深夜有心人","lookNumber":712,"collectNumber":16,"descriptions":"播出频率：FM90.6、FM88.3\r\n节目简介：在黑暗中，请你闭上眼睛，跟我一起穿越声音的花园，一起寻找那些曾经温暖的故事，心动的回忆，电波与你一同倾听这个城市，深夜有心人，致爱声随行。\r\n播出频率，906和833\r\n\r\n","providerCode":"13010","isCollect":0,"commentNumber":0,"imgList":null,"linkImg":null,"linkTitle":null,"anchorId":null},{"id":1547,"plateId":0,"resourceId":1121,"type":5,"cover":"http://cdn.linker.cc/FSYT%2F201611%2F02%2F15%2Fjpg2016110215346.jpg","linkUrl":null,"linkType":null,"createTime":"2017-01-03 16:23:17","name":"俊情一点","lookNumber":442,"collectNumber":32,"descriptions":"主持人：黄俊、子皓、杜文阳\r\n播出频率：FM901/FM906\r\n节目简介：集音乐、娱乐、游戏为一体的强档综艺节目。成功培养出一群极具人气、水平极高的大众歌手，成就草根变歌手的梦想。\r\n","providerCode":"13010","isCollect":0,"commentNumber":0,"imgList":null,"linkImg":null,"linkTitle":null,"anchorId":null},{"id":1548,"plateId":0,"resourceId":14788473845857,"type":5,"cover":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F16%2F11%2FJPG2016111611118.JPG","linkUrl":null,"linkType":null,"createTime":"2016-12-29 16:04:59","name":"健康揸FIT人","lookNumber":107,"collectNumber":3,"descriptions":"节目频率：FM906\r\n节目主持：黄俊\r\n节目介绍：用轻松欢快的方式介绍生活健康信息。撇除传统的沉闷套路，用最潮的方法与你分享健康。","providerCode":"13010","isCollect":0,"commentNumber":0,"imgList":null,"linkImg":null,"linkTitle":null,"anchorId":null}]}]
     * rt : 1
     * des : 查询成功
     */

    private Object userInfo;
    private int rt;
    private String des;
    private List<BannerListBean> bannerList;
    private List<ConsBean> cons;

    public Object getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Object userInfo) {
        this.userInfo = userInfo;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<BannerListBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerListBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<ConsBean> getCons() {
        return cons;
    }

    public void setCons(List<ConsBean> cons) {
        this.cons = cons;
    }

    public static class BannerListBean {
        /**
         * id : 153
         * url : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png?x-oss-process=image/resize,m_mfit,h_200,w_420
         * name : 深夜有心人
         * columnId : 1110
         * columnName : 深夜有心人
         * prividerCode : 13010
         * type : 3
         * linkType : 1
         * linkUrl :
         * linkName :
         * title :
         * linkText :
         * androidMin : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png
         * androidMinW : 0
         * androidMinH : 0
         * androidMedi : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png
         * androidMediW : 0
         * androidMediH : 0
         * androidMax : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F18%2F16%2Fpng2016111816915.png
         * androidMaxW : 1080
         * androidMaxH : 468
         * broadcasting_name : null
         * broadcasting_logo : null
         * broadcasting_address : null
         * broadcasting_id : null
         * broadcasting_frequency : null
         */

        private int id;
        private String url;
        private String name;
        private String columnId;
        private String columnName;
        private int prividerCode;
        private String type;
        private String linkType;
        private String linkUrl;
        private String linkName;
        private String title;
        private String linkText;
        private String androidMin;
        private int androidMinW;
        private int androidMinH;
        private String androidMedi;
        private int androidMediW;
        private int androidMediH;
        private String androidMax;
        private int androidMaxW;
        private int androidMaxH;
        private Object broadcasting_name;
        private Object broadcasting_logo;
        private Object broadcasting_address;
        private Object broadcasting_id;
        private Object broadcasting_frequency;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColumnId() {
            return columnId;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getPrividerCode() {
            return prividerCode;
        }

        public void setPrividerCode(int prividerCode) {
            this.prividerCode = prividerCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLinkText() {
            return linkText;
        }

        public void setLinkText(String linkText) {
            this.linkText = linkText;
        }

        public String getAndroidMin() {
            return androidMin;
        }

        public void setAndroidMin(String androidMin) {
            this.androidMin = androidMin;
        }

        public int getAndroidMinW() {
            return androidMinW;
        }

        public void setAndroidMinW(int androidMinW) {
            this.androidMinW = androidMinW;
        }

        public int getAndroidMinH() {
            return androidMinH;
        }

        public void setAndroidMinH(int androidMinH) {
            this.androidMinH = androidMinH;
        }

        public String getAndroidMedi() {
            return androidMedi;
        }

        public void setAndroidMedi(String androidMedi) {
            this.androidMedi = androidMedi;
        }

        public int getAndroidMediW() {
            return androidMediW;
        }

        public void setAndroidMediW(int androidMediW) {
            this.androidMediW = androidMediW;
        }

        public int getAndroidMediH() {
            return androidMediH;
        }

        public void setAndroidMediH(int androidMediH) {
            this.androidMediH = androidMediH;
        }

        public String getAndroidMax() {
            return androidMax;
        }

        public void setAndroidMax(String androidMax) {
            this.androidMax = androidMax;
        }

        public int getAndroidMaxW() {
            return androidMaxW;
        }

        public void setAndroidMaxW(int androidMaxW) {
            this.androidMaxW = androidMaxW;
        }

        public int getAndroidMaxH() {
            return androidMaxH;
        }

        public void setAndroidMaxH(int androidMaxH) {
            this.androidMaxH = androidMaxH;
        }

        public Object getBroadcasting_name() {
            return broadcasting_name;
        }

        public void setBroadcasting_name(Object broadcasting_name) {
            this.broadcasting_name = broadcasting_name;
        }

        public Object getBroadcasting_logo() {
            return broadcasting_logo;
        }

        public void setBroadcasting_logo(Object broadcasting_logo) {
            this.broadcasting_logo = broadcasting_logo;
        }

        public Object getBroadcasting_address() {
            return broadcasting_address;
        }

        public void setBroadcasting_address(Object broadcasting_address) {
            this.broadcasting_address = broadcasting_address;
        }

        public Object getBroadcasting_id() {
            return broadcasting_id;
        }

        public void setBroadcasting_id(Object broadcasting_id) {
            this.broadcasting_id = broadcasting_id;
        }

        public Object getBroadcasting_frequency() {
            return broadcasting_frequency;
        }

        public void setBroadcasting_frequency(Object broadcasting_frequency) {
            this.broadcasting_frequency = broadcasting_frequency;
        }
    }

    public static class ConsBean {
        /**
         * id : 217
         * name : 俊情一点
         * status : 1
         * type : 10
         * showNumber : 3
         * layout : 0
         * sectionDetails : [{"id":0,"plateId":0,"resourceId":282,"type":6,"cover":"http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F17%2F11%2Fjpg2016111711197.jpg","linkUrl":null,"linkType":null,"createTime":null,"name":"radiofan","lookNumber":0,"collectNumber":0,"descriptions":null,"providerCode":null,"isCollect":0,"commentNumber":0,"imgList":null,"linkImg":null,"linkTitle":null,"anchorId":"282"}]
         */

        private int id;
        private String name;
        private int status;
        private int type;
        private int showNumber;
        private int layout;
        private List<SectionDetailsBean> sectionDetails;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getShowNumber() {
            return showNumber;
        }

        public void setShowNumber(int showNumber) {
            this.showNumber = showNumber;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }

        public List<SectionDetailsBean> getSectionDetails() {
            return sectionDetails;
        }

        public void setSectionDetails(List<SectionDetailsBean> sectionDetails) {
            this.sectionDetails = sectionDetails;
        }

        public static class SectionDetailsBean {
            /**
             * id : 0
             * plateId : 0
             * resourceId : 282
             * type : 6
             * cover : http://wxintest.oss-cn-hangzhou.aliyuncs.com/FSYT%2F201611%2F17%2F11%2Fjpg2016111711197.jpg
             * linkUrl : null
             * linkType : null
             * createTime : null
             * name : radiofan
             * lookNumber : 0
             * collectNumber : 0
             * descriptions : null
             * providerCode : null
             * isCollect : 0
             * commentNumber : 0
             * imgList : null
             * linkImg : null
             * linkTitle : null
             * anchorId : 282
             */

            private int id;
            private int plateId;
            private int resourceId;
            private int type;
            private String cover;
            private Object linkUrl;
            private Object linkType;
            private Object createTime;
            private String name;
            private int lookNumber;
            private int collectNumber;
            private Object descriptions;
            private Object providerCode;
            private int isCollect;
            private int commentNumber;
            private Object imgList;
            private Object linkImg;
            private Object linkTitle;
            private String anchorId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPlateId() {
                return plateId;
            }

            public void setPlateId(int plateId) {
                this.plateId = plateId;
            }

            public int getResourceId() {
                return resourceId;
            }

            public void setResourceId(int resourceId) {
                this.resourceId = resourceId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public Object getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(Object linkUrl) {
                this.linkUrl = linkUrl;
            }

            public Object getLinkType() {
                return linkType;
            }

            public void setLinkType(Object linkType) {
                this.linkType = linkType;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLookNumber() {
                return lookNumber;
            }

            public void setLookNumber(int lookNumber) {
                this.lookNumber = lookNumber;
            }

            public int getCollectNumber() {
                return collectNumber;
            }

            public void setCollectNumber(int collectNumber) {
                this.collectNumber = collectNumber;
            }

            public Object getDescriptions() {
                return descriptions;
            }

            public void setDescriptions(Object descriptions) {
                this.descriptions = descriptions;
            }

            public Object getProviderCode() {
                return providerCode;
            }

            public void setProviderCode(Object providerCode) {
                this.providerCode = providerCode;
            }

            public int getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(int isCollect) {
                this.isCollect = isCollect;
            }

            public int getCommentNumber() {
                return commentNumber;
            }

            public void setCommentNumber(int commentNumber) {
                this.commentNumber = commentNumber;
            }

            public Object getImgList() {
                return imgList;
            }

            public void setImgList(Object imgList) {
                this.imgList = imgList;
            }

            public Object getLinkImg() {
                return linkImg;
            }

            public void setLinkImg(Object linkImg) {
                this.linkImg = linkImg;
            }

            public Object getLinkTitle() {
                return linkTitle;
            }

            public void setLinkTitle(Object linkTitle) {
                this.linkTitle = linkTitle;
            }

            public String getAnchorId() {
                return anchorId;
            }

            public void setAnchorId(String anchorId) {
                this.anchorId = anchorId;
            }
        }
    }
}
