package cn.cherish.tineng.web.req;

import lombok.Data;

@Data
public class CustomerReq implements java.io.Serializable {

    private static final long serialVersionUID = 5487881625239168954L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Integer gender;



}
