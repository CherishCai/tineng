
package cn.cherish.tineng.web.controller;

import cn.cherish.tineng.dal.entity.Physical;
import cn.cherish.tineng.service.PhysicalService;
import cn.cherish.tineng.web.MResponse;
import cn.cherish.tineng.web.dto.PhysicalDTO;
import cn.cherish.tineng.web.req.BasicSearchReq;
import cn.cherish.tineng.web.req.PhysicalReq;
import cn.cherish.tineng.web.req.PhysicalSearchReq;
import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("physical")
public class PhysicalController extends ABaseController {

    private final PhysicalService physicalService;

    @Autowired
    public PhysicalController(PhysicalService physicalService) {
        this.physicalService = physicalService;
    }

    @GetMapping("/list")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/physical/list");
        return mv;
    }

    /**
     * 返回新增页面
     */
    @GetMapping("/add")
    public ModelAndView addForm(){
        ModelAndView mv = new ModelAndView("admin/physical/add");
        return mv;
    }

    /**
     * 返回修改页面
     */
    @GetMapping("/{physicalId}/update")
    public ModelAndView updateForm(@PathVariable("physicalId") Long physicalId){
        ModelAndView mv = new ModelAndView("admin/physical/edit");
        Physical physical = physicalService.findById(physicalId);
        mv.addObject("physical", physical);
        return mv;
    }

    /**
     * 分页查询
     * @param basicSearchReq 基本搜索条件
     */
    @GetMapping("/page")
    @ResponseBody
    public MResponse toPage(BasicSearchReq basicSearchReq, PhysicalSearchReq physicalSearchReq){
        Page<PhysicalDTO> page = physicalService.findAll(physicalSearchReq, basicSearchReq);
        return buildResponse(Boolean.TRUE, basicSearchReq.getDraw(), page);
    }

    /**
     * 删除
     * @param physicalId ID
     * @return JSON
     */
    @DeleteMapping("/{physicalId}/delete")
    @ResponseBody
    public MResponse delete(@PathVariable("physicalId") Long physicalId){
        physicalService.delete(physicalId);
        return buildResponse(Boolean.TRUE, "删除成功", null);
    }

    /**
     * 更改信息
     * @param physicalReq 更新信息
     * @return ModelAndView
     */
    @PostMapping("/update")
    public ModelAndView update(PhysicalReq physicalReq){
        log.info("【更改信息】 {}", physicalReq);
        ModelAndView mv = new ModelAndView("admin/physical/edit");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if(physicalReq == null || physicalReq.getId() == null){
            errorMap.put("msg", "数据错误");
            return mv;
        }
        try {
            physicalService.updateByReq(physicalReq);
            mv.addObject("physical", physicalService.findById(physicalReq.getId()));
            errorMap.put("msg", "修改成功");
        } catch (Exception e) {
            errorMap.put("msg", "系统繁忙");
            log.error("更改信息】 {}", Throwables.getStackTraceAsString(e));
        }
        return mv;
    }

    /**
     * 保存新
     * @param physicalReq 保存的信息
     * @return ModelAndView
     */
    @PostMapping("/save")
    public ModelAndView save(PhysicalReq physicalReq){
        log.info("【保存新数据】 {}", physicalReq);
        ModelAndView mv = new ModelAndView("admin/physical/add");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);
        try {
            if (physicalService.exist(physicalReq.getUsername())){
                errorMap.put("msg", "该会员体侧数据已存在!");
                mv.addObject("physical", physicalReq);
            }else {
                physicalService.saveByReq(physicalReq);
                errorMap.put("msg", "添加成功");
            }
        } catch (Exception e) {
            errorMap.put("msg", "系统繁忙");
            log.error("【保存新数据】 {}", Throwables.getStackTraceAsString(e));
        }
        return mv;
    }

}
