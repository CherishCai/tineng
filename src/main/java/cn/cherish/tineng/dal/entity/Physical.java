package cn.cherish.tineng.dal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "t_physical")
@Data
public class Physical implements java.io.Serializable {

    private static final long serialVersionUID = -1416296024592858829L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "nickname", nullable = false, length = 16)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    /*
    男生：身高、体重、肺活量、体前屈、立定跳远、50米跑、1000米跑、引体向上
    女生：身高、体重、肺活量、体前屈、立定跳远、50米跑、800米跑、1分钟仰卧起坐
     */
    /**
     * 身高
     */
    @Column(name = "height")
    private Float height;
    /**
     * 体重
     */
    @Column(name = "weight")
    private Float weight;
    /**
     * 肺活量
     */
    @Column(name = "vital_capacity")
    private Float vitalCapacity;
    /**
     * 体前屈
     */
    @Column(name = "good_monring")
    private Float goodMonring;
    /**
     * 立定跳远
     */
    @Column(name = "jump")
    private Float jump;
    /**
     * 50米跑
     */
    @Column(name = "meter50")
    private Float meter50;
    /**
     * 800米跑
     */
    @Column(name = "meter800")
    private Float meter800;
    /**
     * 1000米跑
     */
    @Column(name = "meter1000")
    private Float meter1000;

    /**
     * 引体向上
     */
    @Column(name = "pull_up")
    private Integer pullUp;
    /**
     * 1分钟仰卧起坐
     */
    @Column(name = "sit_ups")
    private Integer sitUp;

    /**
     * 分数
     */
    @Column(name = "score")
    private Float score;
    /**
     * 是否通过测试
     */
    @Column(name = "pass")
    private Boolean pass;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false, length = 19)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_time", nullable = false, length = 19)
    private Date modifiedTime;


}
