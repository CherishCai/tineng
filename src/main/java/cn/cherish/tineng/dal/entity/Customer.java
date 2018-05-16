
package cn.cherish.tineng.dal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_customer")
@Data
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 2285174464789310329L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 16)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 40)
    private String password;

    @Column(name = "nickname", nullable = false, length = 16)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false, length = 19)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", nullable = false, length = 19)
    private Date modifiedTime;

}
