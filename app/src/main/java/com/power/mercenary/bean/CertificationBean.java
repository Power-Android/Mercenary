package com.power.mercenary.bean;

public class CertificationBean {


    /**
     * code : 3
     * msg : null
     * message : {"state":"FAILURE","result":null,"ts":1540784856078,"sign":null,"error":"\"code\" : \"99001007\",\"message\" : \"应用(BM12345678903542)参数无效，格式不对、非法值、越界等\",\"solution\" : \"3818a2d7-70c6-05eb-3b27-4c3969912cc6\",\"subErrors\" : \"[{\"code\":\"99100004\",\"message\":\"参数idCard(20)超过最大长度18\"}]\"","stringResult":null,"format":"json","validSign":false,"verSign":null}
     */

    private int code;
    private Object msg;
    private MessageBean message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
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
         * state : FAILURE
         * result : null
         * ts : 1540784856078
         * sign : null
         * error : "code" : "99001007","message" : "应用(BM12345678903542)参数无效，格式不对、非法值、越界等","solution" : "3818a2d7-70c6-05eb-3b27-4c3969912cc6","subErrors" : "[{"code":"99100004","message":"参数idCard(20)超过最大长度18"}]"
         * stringResult : null
         * format : json
         * validSign : false
         * verSign : null
         */

        private String state;
        private Object result;
        private long ts;
        private Object sign;
        private String error;
        private Object stringResult;
        private String format;
        private boolean validSign;
        private Object verSign;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public Object getSign() {
            return sign;
        }

        public void setSign(Object sign) {
            this.sign = sign;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Object getStringResult() {
            return stringResult;
        }

        public void setStringResult(Object stringResult) {
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
    }
}
