package cn.cherish.tineng.web.dto;

import lombok.Data;

@Data
public class PhysicalScore {

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
    private Float vitalCapacityScore;
    /**
     * 体前屈
     */
    private Float goodMonringScore;
    /**
     * 立定跳远
     */
    private Float jumpScore;
    /**
     * 50米跑
     */
    private Float meter50Score;
    /**
     * 800米跑
     */
    private Float meter800Score;
    /**
     * 1000米跑
     */
    private Float meter1000Score;

    /**
     * 引体向上
     */
    private Float pullUpScore;
    /**
     * 1分钟仰卧起坐
     */
    private Float sitUpScore;

    /**
     * 加权后得分
     */
    private Float score;

    /**
     * 是否通过
     */
    private Boolean pass;

}
