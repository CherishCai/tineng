
package cn.cherish.tineng.web.req;

import lombok.Data;

@Data
public class UserReq implements java.io.Serializable {

    private static final long serialVersionUID = -1645633795923583607L;
    private Long id;

    private String username;

    private String password;

    private String nickname;


}
