
package cn.cherish.tineng.common.exception;

import cn.cherish.tineng.common.enums.ErrorCode;
import lombok.Getter;

public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -7243774691044598098L;

    @Getter
    private String code;

    public ServiceException(String code) {
        this.code = code;
    }

    public ServiceException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
    }

    public ServiceException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }
}
