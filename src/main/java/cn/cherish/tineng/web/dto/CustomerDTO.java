
package cn.cherish.tineng.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by Cherish on 2017/2/17.
 */
@Data
public class CustomerDTO implements java.io.Serializable {

    private static final long serialVersionUID = -3859558785433931029L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Integer gender;

    private String genderStr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

}
