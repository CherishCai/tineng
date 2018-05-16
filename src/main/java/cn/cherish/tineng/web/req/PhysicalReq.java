
package cn.cherish.tineng.web.req;

import lombok.Data;

@Data
public class PhysicalReq implements java.io.Serializable {

    private static final long serialVersionUID = 6265204827137964278L;

    private Long id;

    private String username;

    private String nickname;

    private Integer gender;

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
     * 肺活量
     */
    private Float vitalCapacity;
    /**
     * 体前屈
     */
    private Float goodMonring;
    /**
     * 立定跳远
     */
    private Float jump;
    /**
     * 50米跑
     */
    private Float meter50;
    /**
     * 800米跑
     */
    private Float meter800;
    /**
     * 1000米跑
     */
    private Float meter1000;

    /**
     * 引体向上
     */
    private Integer pullUp;
    /**
     * 1分钟仰卧起坐
     */
    private Integer sitUp;

    /**
     * 分数
     */
    private Float score;
    /**
     * 是否通过测试
     */
    private Boolean pass;

}
