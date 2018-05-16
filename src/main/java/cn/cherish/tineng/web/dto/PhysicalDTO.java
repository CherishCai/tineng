
package cn.cherish.tineng.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PhysicalDTO implements java.io.Serializable {

    private static final long serialVersionUID = 6265204827137964278L;

    private Long id;

    private String username;

    private String nickname;

    private Integer gender;
    private String genderStr;

    /*
    男生：身高、体重、肺活量、体前屈、立定跳远、50米跑、1000米跑、引体向上
    女生：身高、体重、肺活量、体前屈、立定跳远、50米跑、800米跑、1分钟仰卧起坐
     */
    /**
     * 身高
     */
    private Float height;
    /**
     * 体重
     */
    private Float weight;

    /**
     * 体重指数BMI
     */
    private Float bmi;
    /**
     * 体重指数BMI 得分
     */
    private Float bmiScore;

    /**
     * 肺活量
     */
    private Float vitalCapacity;
    private Float vitalCapacityScore;
    /**
     * 体前屈
     */
    private Float goodMonring;
    private Float goodMonringScore;
    /**
     * 立定跳远
     */
    private Float jump;
    private Float jumpScore;
    /**
     * 50米跑
     */
    private Float meter50;
    private Float meter50Score;
    /**
     * 800米跑
     */
    private Float meter800;
    private Float meter800Score;
    /**
     * 1000米跑
     */
    private Float meter1000;
    private Float meter1000Score;

    /**
     * 引体向上
     */
    private Integer pullUp;
    private Float pullUpScore;
    /**
     * 1分钟仰卧起坐
     */
    private Integer sitUp;
    private Float sitUpScore;

    private PhysicalScore physicalScore;

    /**
     * 分数
     */
    private Float score;
    /**
     * 是否通过测试
     */
    private Boolean pass;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

}
