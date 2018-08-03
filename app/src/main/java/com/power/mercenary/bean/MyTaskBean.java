package com.power.mercenary.bean;

/**
 * admin  2018/8/2 wan
 */
public class MyTaskBean {

    /**
     * publish : {"one":1,"tow":0,"three":5,"fore":0,"five":0,"total":6}
     * receive : {"one":1,"tow":0,"three":5,"fore":0,"five":0,"total":6}
     */

    private PublishBean publish;
    private ReceiveBean receive;

    public PublishBean getPublish() {
        return publish;
    }

    public void setPublish(PublishBean publish) {
        this.publish = publish;
    }

    public ReceiveBean getReceive() {
        return receive;
    }

    public void setReceive(ReceiveBean receive) {
        this.receive = receive;
    }

    public static class PublishBean {
        /**
         * one : 1
         * tow : 0
         * three : 5
         * fore : 0
         * five : 0
         * total : 6
         */

        private int one;
        private int tow;
        private int three;
        private int fore;
        private int five;
        private int total;

        public int getOne() {
            return one;
        }

        public void setOne(int one) {
            this.one = one;
        }

        public int getTow() {
            return tow;
        }

        public void setTow(int tow) {
            this.tow = tow;
        }

        public int getThree() {
            return three;
        }

        public void setThree(int three) {
            this.three = three;
        }

        public int getFore() {
            return fore;
        }

        public void setFore(int fore) {
            this.fore = fore;
        }

        public int getFive() {
            return five;
        }

        public void setFive(int five) {
            this.five = five;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class ReceiveBean {
        /**
         * one : 1
         * tow : 0
         * three : 5
         * fore : 0
         * five : 0
         * total : 6
         */

        private int one;
        private int tow;
        private int three;
        private int fore;
        private int five;
        private int total;

        public int getOne() {
            return one;
        }

        public void setOne(int one) {
            this.one = one;
        }

        public int getTow() {
            return tow;
        }

        public void setTow(int tow) {
            this.tow = tow;
        }

        public int getThree() {
            return three;
        }

        public void setThree(int three) {
            this.three = three;
        }

        public int getFore() {
            return fore;
        }

        public void setFore(int fore) {
            this.fore = fore;
        }

        public int getFive() {
            return five;
        }

        public void setFive(int five) {
            this.five = five;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
