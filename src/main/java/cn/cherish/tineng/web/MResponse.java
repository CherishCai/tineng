
package cn.cherish.tineng.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * JSON数据返回模板
 */
@Data
@Builder
@AllArgsConstructor
public class MResponse<T> implements java.io.Serializable {
    private static final long serialVersionUID = -222983483999088181L;
    private String code;
    private Boolean success;
    private String message;
    private T data;
}
