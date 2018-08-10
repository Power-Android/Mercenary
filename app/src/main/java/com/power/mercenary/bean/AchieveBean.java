package com.power.mercenary.bean;

import java.util.List;

/**
 * Created by lenovo on 2018/8/10.
 */

public class AchieveBean {

        private List<XunzhangBean> xunzhang;
        private List<DoneAchieveBean> done_achieve;
        private List<NotAchieveBean> not_achieve;

        public List<XunzhangBean> getXunzhang() {
            return xunzhang;
        }

        public void setXunzhang(List<XunzhangBean> xunzhang) {
            this.xunzhang = xunzhang;
        }

        public List<DoneAchieveBean> getDone_achieve() {
            return done_achieve;
        }

        public void setDone_achieve(List<DoneAchieveBean> done_achieve) {
            this.done_achieve = done_achieve;
        }

        public List<NotAchieveBean> getNot_achieve() {
            return not_achieve;
        }

        public void setNot_achieve(List<NotAchieveBean> not_achieve) {
            this.not_achieve = not_achieve;
        }

        public static class XunzhangBean {
            /**
             * medal : 20180712/5b470f0f65591.jpg
             */

            private String medal;

            public String getMedal() {
                return medal;
            }

            public void setMedal(String medal) {
                this.medal = medal;
            }
        }

        public static class DoneAchieveBean {
            /**
             * name : 重操旧业
             * complete : 时隔一年再次完成任务
             * medal : 20180712/5b470f0f65591.jpg
             * get_time : 1532929911
             */

            private String name;
            private String complete;
            private String medal;
            private String get_time;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getComplete() {
                return complete;
            }

            public void setComplete(String complete) {
                this.complete = complete;
            }

            public String getMedal() {
                return medal;
            }

            public void setMedal(String medal) {
                this.medal = medal;
            }

            public String getGet_time() {
                return get_time;
            }

            public void setGet_time(String get_time) {
                this.get_time = get_time;
            }
        }

        public static class NotAchieveBean {
            /**
             * name : 一血成就
             * complete : 发布一个任务、并有人完成
             * medal : 20180712/5b470f0f65591.jpg
             * get_times : 0
             * need_times : 1
             */

            private String name;
            private String complete;
            private String medal;
            private String get_times;
            private String need_times;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getComplete() {
                return complete;
            }

            public void setComplete(String complete) {
                this.complete = complete;
            }

            public String getMedal() {
                return medal;
            }

            public void setMedal(String medal) {
                this.medal = medal;
            }

            public String getGet_times() {
                return get_times;
            }

            public void setGet_times(String get_times) {
                this.get_times = get_times;
            }

            public String getNeed_times() {
                return need_times;
            }

            public void setNeed_times(String need_times) {
                this.need_times = need_times;
            }
    }
}
