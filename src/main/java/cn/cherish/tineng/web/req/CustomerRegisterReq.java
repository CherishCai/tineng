package cn.cherish.tineng.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterReq implements java.io.Serializable {

    private static final long serialVersionUID = -4066144981192985904L;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    private String repeatPwd;
    /**
     * 性别
     */
    private Integer gender;

}
