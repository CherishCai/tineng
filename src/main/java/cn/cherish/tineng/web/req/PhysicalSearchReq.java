
package cn.cherish.tineng.web.req;

import lombok.Data;

@Data
public class PhysicalSearchReq implements java.io.Serializable {

    private static final long serialVersionUID = -6252802038931388533L;

    private String nickname;

    private String username;

    private Integer gender;

    /**
     * 是否通过测试
     */
    private Boolean pass;

}
