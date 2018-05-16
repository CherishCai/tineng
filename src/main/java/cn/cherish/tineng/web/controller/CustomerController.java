package cn.cherish.tineng.web.controller;

import cn.cherish.tineng.common.shiro.CryptographyUtil;
import cn.cherish.tineng.dal.entity.Customer;
import cn.cherish.tineng.dal.entity.Physical;
import cn.cherish.tineng.service.CustomerService;
import cn.cherish.tineng.service.PhysicalService;
import cn.cherish.tineng.util.MStringUtils;
import cn.cherish.tineng.util.RequestHolder;
import cn.cherish.tineng.util.SessionUtil;
import cn.cherish.tineng.web.MResponse;
import cn.cherish.tineng.web.dto.CustomerDTO;
import cn.cherish.tineng.web.dto.PhysicalDTO;
import cn.cherish.tineng.web.dto.PhysicalScore;
import cn.cherish.tineng.web.req.*;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends ABaseController {

    private final CustomerService customerService;
    private final PhysicalService physicalService;

    @Autowired
    public CustomerController(CustomerService customerService, PhysicalService physicalService) {
        this.customerService = customerService;
        this.physicalService = physicalService;
    }

    /**
     * 会员中心页面
     * @return ModelAndView
     */
    @GetMapping({"","/","/index"})
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("customer/index");
        // 查询个人信息
        Customer customer = SessionUtil.getCustomer();
        if (customer == null) {
            mv.setViewName("redirect:/customer/login");
            SessionUtil.add("url", MStringUtils.getRequestURLWithQueryString(RequestHolder.getRequest()));
        }else {
            Physical physical = physicalService.findByUsername(customer.getUsername());
            PhysicalDTO physicalDTO = physicalService.toDTO(physical);
            if (physicalDTO != null) {
                PhysicalScore physicalScore = physicalDTO.getPhysicalScore();
                mv.addObject("physicalScore", physicalScore);
                }
            mv.addObject("customer", customer);
            mv.addObject("physical", physicalDTO);
        }
        return mv;
    }

    /**
     * 会员注册页面
     * @return ModelAndView
     */
    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView("customer/register");
        return mv;
    }
    /**
     * 管理员所看的 会员列表页面
     * @return ModelAndView
     */
    @RequiresRoles("admin")
    @GetMapping("/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView("admin/customer/list");
        return mv;
    }

    /**
     * 返回新增页面
     */
    @RequiresRoles("admin")
    @GetMapping("/add")
    public ModelAndView addForm(){
        ModelAndView mv = new ModelAndView("admin/customer/add");
        return mv;
    }

    /**
     * 返回修改信息页面
     */
    @RequiresRoles("admin")
    @GetMapping("/{customerId}/update")
    public ModelAndView updateForm(@PathVariable("customerId") Long customerId){
        ModelAndView mv = new ModelAndView("admin/customer/edit");
        Customer customer = customerService.findById(customerId);
        mv.addObject(customer);
        return mv;
    }

    /**
     * 分页查询
     * @param basicSearchReq 基本搜索条件
     * @return JSON
     * @date 2016年8月30日 下午5:30:18
     */
    @RequiresRoles("admin")
    @GetMapping("/page")
    @ResponseBody
    public MResponse toPage(BasicSearchReq basicSearchReq, CustomerSearchReq customerSearchReq){
        Page<CustomerDTO> page = customerService.findAll(basicSearchReq, customerSearchReq);
        return buildResponse(Boolean.TRUE, basicSearchReq.getDraw(), page);
    }

    /**
     * 删除
     * @param customerId ID
     * @return JSON
     */
    @RequiresRoles("admin")
    @DeleteMapping("/{customerId}/delete")
    @ResponseBody
    public MResponse delete(@PathVariable("customerId") Long customerId){
        customerService.delete(customerId);
        return buildResponse(Boolean.TRUE, "删除成功", null);
    }

    /**
     * 冻结或激活
     * @param customerId ID
     * @return JSON
     */
    @RequiresRoles("admin")
    @GetMapping("/{customerId}/freezeOrActive")
    @ResponseBody
    public MResponse freezeOrActive(@PathVariable("customerId") Long customerId){
//        customerService.freezeOrActive(customerId);
        return buildResponse(Boolean.TRUE, "操作成功", null);
    }

    /**
     * 新增会员
     * @param customerSaveReq 参数
     * @param bindingResult 验证
     * @return ModelAndView
     */
    @RequiresRoles("admin")
    @PostMapping("/save")
    public ModelAndView save(@Validated CustomerReq customerSaveReq, BindingResult bindingResult) {
        log.info("【新增会员】 {}", customerSaveReq);
        ModelAndView mv = new ModelAndView("admin/customer/add");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            mv.addObject("customer", customerSaveReq);
            return mv;
        }
        try {
            boolean existUsername = customerService.exist(customerSaveReq.getUsername());
            if (existUsername){
                errorMap.put("msg", "该登录账号已被注册，请更换");
                mv.addObject("customer", customerSaveReq);
                return mv;
            }
            // 设置密码
            customerSaveReq.setPassword(CryptographyUtil.cherishSha1(customerSaveReq.getPassword()));
            customerService.save(customerSaveReq);

            errorMap.put("msg", "添加成功");
        } catch (Exception e) {
            errorMap.put("msg", "系统繁忙");
            log.error("【添加失败】 {}", Throwables.getStackTraceAsString(e));
        }
        return mv;
    }

    /**
     * 更改信息
     * @param updateReq 更新信息
     * @return ModelAndView
     */
    @RequiresRoles("admin")
    @PostMapping("/update")
    public ModelAndView update(@Validated CustomerReq updateReq, BindingResult bindingResult){
        log.info("【更改信息】 {}", updateReq);
        ModelAndView mv = new ModelAndView("admin/customer/edit");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if(updateReq == null || updateReq.getId() == null){
            errorMap.put("msg", "数据错误");
            return mv;
        }
        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            mv.addObject("customer", updateReq);
            return mv;
        }
        try {
            customerService.update(updateReq);
            mv.addObject("customer", customerService.findById(updateReq.getId()));
            errorMap.put("msg", "修改成功");
        } catch (Exception e) {
            errorMap.put("msg", "系统繁忙");
            log.error("修改错误:{}", Throwables.getStackTraceAsString(e));
        }
        return mv;
    }

    /**
     * 注册会员
     * @param customerRegisterReq 参数
     * @param bindingResult 验证
     * @return ModelAndView
     */
    @PostMapping("/register")
    public ModelAndView register(@Validated CustomerRegisterReq customerRegisterReq, BindingResult bindingResult) {
        log.info("【注册会员】 {}",customerRegisterReq);
        ModelAndView mv = new ModelAndView("customer/register");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            mv.addObject("customer", customerRegisterReq);
            return mv;
        }
        if (StringUtils.isBlank(customerRegisterReq.getPassword())
                ||!StringUtils.equals(customerRegisterReq.getPassword(), customerRegisterReq.getRepeatPwd())) {
            errorMap.put("msg", "两处密码不一致");
            mv.addObject("customer", customerRegisterReq);
            return mv;
        }
        try {
            boolean existUsername = customerService.exist(customerRegisterReq.getUsername());
            if (existUsername){
                errorMap.put("msg", "该登录账号已被注册，请更换");
                mv.addObject("customer", customerRegisterReq);
                return mv;
            }

            customerService.register(customerRegisterReq);
            errorMap.put("msg", "信息提交成功，请登录");
            SessionUtil.add("msg", "信息提交成功，请登录");
            mv.setViewName("redirect:/customer/login");
        } catch (Exception e) {
            errorMap.put("msg", "系统繁忙");
            log.error("添加失败:{}", Throwables.getStackTraceAsString(e));
        }
        return mv;
    }

    /**
     * 会员信息修改请求
     * @return ResponseBody
     */
    @PostMapping("/updateMyself")
    @ResponseBody
    public MResponse updateMyself(PhysicalReq physicalReq) {
        log.info("【信息修改】 {}", physicalReq);
        Customer customer = SessionUtil.getCustomer();
        if (customer == null) {
            return buildResponse(Boolean.FALSE, "未有记录，请去登陆", Boolean.FALSE);
        }

        physicalReq.setUsername(customer.getUsername());
        physicalReq.setNickname(customer.getNickname());
        physicalReq.setGender(customer.getGender());
        Physical update = physicalService.updateByReq(physicalReq);
        if (update != null) {
            return buildResponse(Boolean.TRUE, "更改成功", null);
        } else {
            return buildResponse(Boolean.FALSE, "更改失败", null);
        }
    }

    /**
     * 会员登陆
     * @return ResponseBody
     */
    @PostMapping("/login")
    public ModelAndView login(@Validated LoginReq loginReq, BindingResult bindingResult, HttpServletRequest request) {
        log.info("【会员登陆】 {}", loginReq);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/login");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        String code = (String) request.getSession().getAttribute("validateCode");
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
        log.debug("code: {}  submitCode: {}", code, submitCode);

        //判断验证码
        if (StringUtils.isBlank(submitCode) || !StringUtils.equalsIgnoreCase(code, submitCode.toLowerCase())) {
            log.debug("验证码不正确");
            errorMap.put("validateCodeError", "验证码不正确");
            return mv;
        }
        //表单验证是否通过
        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            return mv;
        }

        Customer customer = customerService.findByUsername(loginReq.getUsername());
        if (customer == null) {
            errorMap.put("username", "账户或密码错误，请重新输入");
            return mv;
        }

        String password = CryptographyUtil.cherishSha1(loginReq.getPassword());
        if (!StringUtils.equals(password, customer.getPassword())) {
            log.debug("密码不正确");
            errorMap.put("username", "账户或密码错误，请重新输入");
            return mv;
        }

        // 被禁用
       /* if (!Objects.equals(ActiveEnum.ACTIVE.getNum(), customer.getActive())) {
            log.debug("账号被禁用");
            errorMap.put("username", "账号被禁用");
            return mv;
        }*/

        // 登陆成功
        SessionUtil.addCustomer(customer);
        SessionUtil.add("isLogin", true);
        SessionUtil.add("customerName", customer.getNickname());

        String url = (String)SessionUtil.get("url");
        if (url != null) {
            mv.setViewName("redirect:" + url);
            SessionUtil.del("url");
        }else {
            mv.setViewName("redirect:/customer");
        }
        return mv;
    }

    /**
     * 是否已登陆
     * @return ResponseBody
     */
    @PostMapping("/isLogin")
    @ResponseBody
    public MResponse isLogin() {
        Customer customer = SessionUtil.getCustomer();
        if (customer == null) {
            return buildResponse(Boolean.FALSE, "未登陆", Boolean.FALSE);
        }
        return buildResponse(Boolean.TRUE, "已登陆", customer.getNickname());
    }

    /**
     * 登录页面
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("customer/login");
        String msg = (String) SessionUtil.get("msg");
        mv.addObject("msg", msg);
        SessionUtil.del("msg");
        return mv;
    }
    /**
     * 登出
     * @return ModelAndView
     */
    @GetMapping("/logout")
    public ModelAndView logout() {
        SessionUtil.delCustomer();
        SessionUtil.add("isLogin", false);
        return new ModelAndView("redirect:/");
    }


}