package com.power.mercenary.bean;

public class CertificationBean {


    /**
     * code : 4
     * msg : 系统异常!
     * message : {"state":"SUCCESS","result":{"code":"MP100001","message":"系统异常!"},"ts":1540892803037,"sign":"bFDXcsebBFjuLLZIrCujY28F1IstJTIfxfo2y9jIZSFKV2PZzgLNOaZ_TLGFjUjdy-0-lF-iwqnR7JqvLox5h49HXWZ7qVaK1OpDhKfdFEzVy-ktEv05bnf7lADUqJLGyNnyPMN_1V3ko9IMwX7ZzFGUwd4X1VvNvO2NDA8t-jkqkORDosjSEZDw86G_OsvQyeZTarcP5cRTc2udpwpiQEIQZSQKBKSZw7Z_-ch48c_AfZr1aGH4ftyTjuDn3ufnYhKayyOsSotP77ZVrPcAo-aM8IEE785iqqglghWxgdF5ZFbAKCUzoCb8RHWRrroa1goKKuBKoijebON0iL3LtA$SHA256","error":null,"stringResult":"{\n    \"code\" : \"MP100001\",\n    \"message\" : \"系统异常!\"\n  }","format":"json","validSign":true,"verSign":null}
     */

    private int code;
    private String msg;
    private MessageBean message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * state : SUCCESS
         * result : {"code":"MP100001","message":"系统异常!"}
         * ts : 1540892803037
         * sign : bFDXcsebBFjuLLZIrCujY28F1IstJTIfxfo2y9jIZSFKV2PZzgLNOaZ_TLGFjUjdy-0-lF-iwqnR7JqvLox5h49HXWZ7qVaK1OpDhKfdFEzVy-ktEv05bnf7lADUqJLGyNnyPMN_1V3ko9IMwX7ZzFGUwd4X1VvNvO2NDA8t-jkqkORDosjSEZDw86G_OsvQyeZTarcP5cRTc2udpwpiQEIQZSQKBKSZw7Z_-ch48c_AfZr1aGH4ftyTjuDn3ufnYhKayyOsSotP77ZVrPcAo-aM8IEE785iqqglghWxgdF5ZFbAKCUzoCb8RHWRrroa1goKKuBKoijebON0iL3LtA$SHA256
         * error : null
         * stringResult : {
         "code" : "MP100001",
         "message" : "系统异常!"
         }
         * format : json
         * validSign : true
         * verSign : null
         */

        private String state;
        private ResultBean result;
        private long ts;
        private String sign;
        private Object error;
        private String stringResult;
        private String format;
        private boolean validSign;
        private Object verSign;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public Object getError() {
            return error;
        }

        public void setError(Object error) {
            this.error = error;
        }

        public String getStringResult() {
            return stringResult;
        }

        public void setStringResult(String stringResult) {
            this.stringResult = stringResult;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public boolean isValidSign() {
            return validSign;
        }

        public void setValidSign(boolean validSign) {
            this.validSign = validSign;
        }

        public Object getVerSign() {
            return verSign;
        }

        public void setVerSign(Object verSign) {
            this.verSign = verSign;
        }

        public static class ResultBean {
            /**
             * code : MP100001
             * message : 系统异常!
             */

            private String code;
            private String message;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
