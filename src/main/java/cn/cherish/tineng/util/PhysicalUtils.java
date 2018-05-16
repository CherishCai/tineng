package cn.cherish.tineng.util;

import cn.cherish.tineng.common.enums.Gender;
import cn.cherish.tineng.dal.entity.Physical;
import cn.cherish.tineng.web.dto.PhysicalScore;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PhysicalUtils {
    private PhysicalUtils(){}

    public static PhysicalScore getScore(Physical physical) {
        PhysicalScore physicalScore = new PhysicalScore();
        if (physical == null) {
            return physicalScore;
        }
        Integer gender = physical.getGender();

        boolean pass = true;
        // 男性
        boolean man = false;
        if (gender == null || Gender.M.getCode() == gender) {
            man = true;
        }

        Float height = physical.getHeight();
        Float weight = physical.getWeight();
        float bmi = 0.0f;
        float bmiScore = 0.0f;
        if (height != null && weight != null) {
            // 计算bmi  体质指数（BMI）=体重（kg）÷身高^2（m）
            bmi = (float) (weight / Math.pow(height / 100, 2));
            if (bmi > 28.0) {
                bmiScore = 60f;
            } else if (bmi > 24.0) {
                bmiScore = 80f;
            } else if (bmi > 17.9) {
                 bmiScore = 100f;
            } else {
                bmiScore = 80f;
            }
        }
        physicalScore.setBmi(bmi);
        physicalScore.setBmiScore(bmiScore);
        if (bmiScore < 60) {
            pass = false;
        }

        Float vitalCapacity = physical.getVitalCapacity();
        float vitalCapacityScore = 0.0f;
        if (vitalCapacity != null) {
            if (vitalCapacity > 5200) {
                vitalCapacityScore = 100;
            } else if (vitalCapacity < 2500) {
                vitalCapacityScore = 0;
            } else {
                vitalCapacityScore = (vitalCapacity - 0) / (5200.0f - 0) * 100;
            }
        }
        physicalScore.setVitalCapacityScore(vitalCapacityScore);
        if (vitalCapacityScore < 60) {
            pass = false;
        }

        Float meter50 = physical.getMeter50();
        float meter50Score = 0.0f;
        if (meter50 != null) {
            if (man) {
                if (meter50 > 11) {
                    meter50Score = 0;
                } else if (meter50 < 6.7) {
                    meter50Score = 100;
                } else {
                    meter50Score = 100 - (meter50 - 6.7f) / (11 - 6.7f) * 100;
                }
            } else {
                if (meter50 > 12.8) {
                    meter50Score = 0;
                } else if (meter50 < 7.4) {
                    meter50Score = 100;
                } else {
                    meter50Score = 100 - (meter50 - 7.4f) / (12.8f - 7.4f) * 100;
                }
            }
        }
        physicalScore.setMeter50Score(meter50Score);
        if (meter50Score < 60) {
            pass = false;
        }

        Float goodMonring = physical.getGoodMonring();
        float goodMonringScore = 0.0f;
        if (goodMonring != null) {
            if (man) {
                if (goodMonring > 25.1) {
                    goodMonringScore = 100;
                } else if (goodMonring < -1.4) {
                    goodMonringScore = 0;
                } else {
                    goodMonringScore = (goodMonring + 1.4f) / (25.1f + 1.4f) * 100;
                }
            } else {
                if (goodMonring > 26.3) {
                    goodMonringScore = 100;
                } else if (goodMonring < 1.5) {
                    goodMonringScore = 0;
                } else {
                    goodMonringScore = (goodMonring - 1.5f) / (26.3f - 1.5f) * 100;
                }
            }
        }
        physicalScore.setGoodMonringScore(goodMonringScore);
        if (goodMonringScore < 60) {
            pass = false;
        }

        Float jump = physical.getJump();
        float jumpScore = 0.0f;
        if (jump != null) {
            if (man) {
                if (jump > 268) {
                    jumpScore = 100;
                } else if (jump < 144) {
                    jumpScore = 0;
                } else {
                    jumpScore = (jump - 144) / (268.0f - 144) * 100;
                }
            } else {
                if (jump > 222) {
                    jumpScore = 100;
                } else if (jump < 125) {
                    jumpScore = 0;
                } else {
                    jumpScore = (jump - 125) / (222.0f - 125) * 100;
                }
            }
        }
        physicalScore.setJumpScore(jumpScore);
        if (jumpScore < 60) {
            pass = false;
        }

        if (man) {
            // 引体向上
            Integer pullUp = physical.getPullUp();
            float pullUpScore = 0.0f;
            if (pullUp != null) {
                if (pullUp > 20) {
                    pullUpScore = 100;
                } else if (pullUp < 4) {
                    pullUpScore = 0;
                } else {
                    pullUpScore = (pullUp - 4) / (20.0f - 4) * 100;
                }
            }
            physicalScore.setPullUpScore(pullUpScore);
            if (pullUpScore < 60) {
                pass = false;
            }
            // 1000米
            Float meter1000 = physical.getMeter1000();
            float meter1000Score = 0.0f;
            if (meter1000 != null) {
                if (meter1000 > 350) {
                    meter1000Score = 0;
                } else if (meter1000 < 195) {
                    meter1000Score = 100;
                } else {
                    meter1000Score = 100 - (meter1000 - 195) / (350.0f - 195) * 100;
                }
            }
            physicalScore.setMeter1000Score(meter1000Score);
            if (meter1000Score < 60) {
                pass = false;
            }

        } else {
            // 仰卧起坐
            Integer sitUp = physical.getSitUp();
            float sitUpScore = 0.0f;
            if (sitUp != null) {
                if (sitUp > 57) {
                    sitUpScore = 100;
                } else if (sitUp < 15) {
                    sitUpScore = 0;
                } else {
                    sitUpScore = (sitUp - 15) / (57.0f - 15) * 100;
                }
            }
            physicalScore.setSitUpScore(sitUpScore);
            if (sitUpScore < 60) {
                pass = false;
            }
            // 800米
            Float meter800 = physical.getMeter800();
            float meter800Score = 0.0f;
            if (meter800 != null) {
                if (meter800 > 350) {
                    meter800Score = 0;
                } else if (meter800 < 195) {
                    meter800Score = 100;
                } else {
                    meter800Score = 100 - (meter800 - 195) / (350.f - 195) * 100;
                }
            }
            physicalScore.setMeter800Score(meter800Score);
            if (meter800Score < 60) {
                pass = false;
            }
        }

        physicalScore.setScore(proportionScore(physicalScore));
        physicalScore.setPass(pass);

        return physicalScore;
    }

    private static float proportionScore(PhysicalScore physicalScore) {
        float score = 0.0f;

        Float bmiScore = physicalScore.getBmiScore();
        if (bmiScore != null) {
            score += bmiScore * ProportionEnums.BMI.val;
        }

        Float vitalCapacityScore = physicalScore.getVitalCapacityScore();
        if (vitalCapacityScore != null) {
            score += vitalCapacityScore * ProportionEnums.vitalCapacity.val;
        }

        Float jumpScore = physicalScore.getJumpScore();
        if (jumpScore != null) {
            score += jumpScore * ProportionEnums.jump.val;
        }

        Float goodMonringScore = physicalScore.getGoodMonringScore();
        if (goodMonringScore != null) {
            score += goodMonringScore * ProportionEnums.goodMonring.val;
        }

        Float meter50Score = physicalScore.getMeter50Score();
        if (meter50Score != null) {
            score += meter50Score * ProportionEnums.meter50.val;
        }


        Float meter1000Score = physicalScore.getMeter1000Score();
        if (meter1000Score != null) {
            score += meter1000Score * ProportionEnums.meter1000.val;
        }

        Float pullUpScore = physicalScore.getPullUpScore();
        if (pullUpScore != null) {
            score += pullUpScore * ProportionEnums.pullUp.val;
        }

        Float meter800Score = physicalScore.getMeter800Score();
        if (meter800Score != null) {
            score += meter800Score * ProportionEnums.meter800.val;
        }

        Float sitUpScore = physicalScore.getSitUpScore();
        if (sitUpScore != null) {
            score += sitUpScore * ProportionEnums.sitUp.val;
        }

        physicalScore.setScore(score);
        return score;
    }


    @Getter
    @AllArgsConstructor
    public enum ProportionEnums {
        BMI(0.15f, "体重指数比重"),

        vitalCapacity(0.15f, "肺活量比重"),

        /**
         * 体前屈
         */
        goodMonring(0.10f,""),
        /**
         * 立定跳远
         */
        jump(0.10f,""),
        /**
         * 50米跑
         */
        meter50(0.20f, ""),
        /**
         * 800米跑
         */
        meter800(0.2f, ""),
        /**
         * 1000米跑
         */
        meter1000(0.20f, ""),

        /**
         * 引体向上
         */
        pullUp(0.10f, ""),
        /**
         * 1分钟仰卧起坐
         */
        sitUp(0.10f, ""),

        ;

        private float val;

        private String desc;


    }


}
