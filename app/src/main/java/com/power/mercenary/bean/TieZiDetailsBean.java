package com.power.mercenary.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class TieZiDetailsBean {


        /**
         * post_user_name : 大水杯
         * post_user_headimg : http://www.yongbing.com/Uploads/img/3/1530602439990.jpg
         * post_content : 任务完成的很棒
         * post_img : a.jpg,b.jpg
         * create_time : 1531185323
         * liuyan : [{"id":"1","liuyan_user_name":"大水杯","liuyan_user_headimg":"http://www.yongbing.com/Uploads/img/3/1530602439990.jpg","liuyan_content":"哈哈哈哈","create_time":"1531185323","huifu":[{"huifu_user_name":"用户","huifu_content":"回复回复"},{"huifu_user_name":"用户","huifu_content":"回复"},{"huifu_user_name":"12","huifu_content":"测试"},{"huifu_user_name":"12","huifu_content":"测试"}]},{"id":"2","liuyan_user_name":"用户","liuyan_user_headimg":"http://www.yongbing.com/Uploads/img/3/1530602439990.jpg","liuyan_content":"","create_time":"1531185328","huifu":[]},{"id":"3","liuyan_user_name":"12","liuyan_user_headimg":"./Uploads/img/5/1530840403388.jpg","liuyan_content":"测试","create_time":"1531241248","huifu":[]},{"id":"4","liuyan_user_name":"12","liuyan_user_headimg":"./Uploads/img/5/1530840403388.jpg","liuyan_content":"测试","create_time":"1531241350","huifu":[]},{"id":"5","liuyan_user_name":"12","liuyan_user_headimg":"./Uploads/img/5/1530840403388.jpg","liuyan_content":"测试","create_time":"1531242935","huifu":[]}]
         */

        private String post_user_name;
        private String post_user_headimg;
        private String post_content;
        private String post_img;
        private String create_time;
        private List<LiuyanBean> liuyan;

        public String getPost_user_name() {
            return post_user_name;
        }

        public void setPost_user_name(String post_user_name) {
            this.post_user_name = post_user_name;
        }

        public String getPost_user_headimg() {
            return post_user_headimg;
        }

        public void setPost_user_headimg(String post_user_headimg) {
            this.post_user_headimg = post_user_headimg;
        }

        public String getPost_content() {
            return post_content;
        }

        public void setPost_content(String post_content) {
            this.post_content = post_content;
        }

        public String getPost_img() {
            return post_img;
        }

        public void setPost_img(String post_img) {
            this.post_img = post_img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<LiuyanBean> getLiuyan() {
            return liuyan;
        }

        public void setLiuyan(List<LiuyanBean> liuyan) {
            this.liuyan = liuyan;
        }

        public static class LiuyanBean {
            /**
             * id : 1
             * liuyan_user_name : 大水杯
             * liuyan_user_headimg : http://www.yongbing.com/Uploads/img/3/1530602439990.jpg
             * liuyan_content : 哈哈哈哈
             * create_time : 1531185323
             * huifu : [{"huifu_user_name":"用户","huifu_content":"回复回复"},{"huifu_user_name":"用户","huifu_content":"回复"},{"huifu_user_name":"12","huifu_content":"测试"},{"huifu_user_name":"12","huifu_content":"测试"}]
             */

            private String id;
            private String liuyan_user_name;
            private String liuyan_user_headimg;
            private String liuyan_content;
            private String create_time;
            private List<HuifuBean> huifu;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLiuyan_user_name() {
                return liuyan_user_name;
            }

            public void setLiuyan_user_name(String liuyan_user_name) {
                this.liuyan_user_name = liuyan_user_name;
            }

            public String getLiuyan_user_headimg() {
                return liuyan_user_headimg;
            }

            public void setLiuyan_user_headimg(String liuyan_user_headimg) {
                this.liuyan_user_headimg = liuyan_user_headimg;
            }

            public String getLiuyan_content() {
                return liuyan_content;
            }

            public void setLiuyan_content(String liuyan_content) {
                this.liuyan_content = liuyan_content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public List<HuifuBean> getHuifu() {
                return huifu;
            }

            public void setHuifu(List<HuifuBean> huifu) {
                this.huifu = huifu;
            }

            public static class HuifuBean {
                /**
                 * huifu_user_name : 用户
                 * huifu_content : 回复回复
                 */

                private String huifu_user_name;
                private String huifu_content;

                public String getHuifu_user_name() {
                    return huifu_user_name;
                }

                public void setHuifu_user_name(String huifu_user_name) {
                    this.huifu_user_name = huifu_user_name;
                }

                public String getHuifu_content() {
                    return huifu_content;
                }

                public void setHuifu_content(String huifu_content) {
                    this.huifu_content = huifu_content;
                }
            }
    }
}
