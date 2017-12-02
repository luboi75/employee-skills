package com.lubo.learning.heroku;

public class GlobalConstants {
    public enum ErrorCode {
        UnknownPSQLException(100),
        UnknownSql2oException(101);

        private int code;
        ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
