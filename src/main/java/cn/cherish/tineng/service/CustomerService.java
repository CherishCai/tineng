
package cn.cherish.tineng.service;

import cn.cherish.tineng.common.enums.ErrorCode;
import cn.cherish.tineng.common.enums.Gender;
import cn.cherish.tineng.common.exception.ServiceException;
import cn.cherish.tineng.common.shiro.CryptographyUtil;
import cn.cherish.tineng.dal.entity.Customer;
import cn.cherish.tineng.dal.repository.CustomerDAO;
import cn.cherish.tineng.dal.repository.IBaseDAO;
import cn.cherish.tineng.util.ObjectConvertUtil;
import cn.cherish.tineng.web.dto.CustomerDTO;
import cn.cherish.tineng.web.req.BasicSearchReq;
import cn.cherish.tineng.web.req.CustomerRegisterReq;
import cn.cherish.tineng.web.req.CustomerReq;
import cn.cherish.tineng.web.req.CustomerSearchReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CustomerService extends ABaseService<Customer, Long> {

    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    protected IBaseDAO<Customer, Long> getEntityDAO() {
        return customerDAO;
    }

    public CustomerDTO findOne(Long CustomerId) {
        Customer Customer = customerDAO.findOne(CustomerId);
        return Customer == null ? null : ObjectConvertUtil.objectCopy(new CustomerDTO(), Customer);
    }

    public Customer findByUsername(String username){
        return customerDAO.findByUsername(username);
    }

    public boolean exist(String username) {
        return findByUsername(username) != null;
    }

    @Transactional
    public void delete(Long CustomerId) {
        Customer Customer = customerDAO.findOne(CustomerId);
        if (Customer == null) return;
        super.delete(CustomerId);
    }

    public Page<CustomerDTO> findAll(BasicSearchReq basicSearchReq, CustomerSearchReq CustomerSearchReq) {

        int pageNumber = basicSearchReq.getStartIndex() / basicSearchReq.getPageSize() + 1;
        PageRequest pageRequest = super.buildPageRequest(pageNumber, basicSearchReq.getPageSize());

        //除了分页条件没有特定搜索条件的，为了缓存count
        if (ObjectConvertUtil.objectFieldIsBlank(CustomerSearchReq)){
            log.debug("没有特定搜索条件的");
            List<Customer> customerList = customerDAO.listAllPaged(pageRequest);
            List<CustomerDTO> customerDTOList = customerList.stream().map(this::toDTO).collect(Collectors.toList());

            //为了计算总数使用缓存，加快搜索速度
            Long count = getCount();
            return new PageImpl<>(customerDTOList, pageRequest, count);
        }

        //有了其它搜索条件
        Page<Customer> customerPage = super.findAllBySearchParams(
                buildSearchParams(CustomerSearchReq), pageNumber, basicSearchReq.getPageSize());

        return customerPage.map(this::toDTO);
    }

    private CustomerDTO toDTO(Customer source) {
        CustomerDTO customerDTO = new CustomerDTO();
        ObjectConvertUtil.objectCopy(customerDTO, source);
        customerDTO.setGenderStr(Gender.desc(customerDTO.getGender()));
        return customerDTO;
    }

    @Transactional
    public Customer update(CustomerReq customerReq) {
        Customer customer = this.findById(customerReq.getId());
        if (customer == null) return null;

        ObjectConvertUtil.objectCopy(customer, customerReq);
        customer.setModifiedTime(new Date());
        return this.update(customer);
    }

    /**
     * 管理员手动添加会员
     * @param customerReq 会员信息
     */
    @Transactional
    public Customer save(CustomerReq customerReq) {
        if (exist(customerReq.getUsername())) {
            throw new ServiceException(ErrorCode.ERROR_CODE_400, "账号已存在");
        }

        Customer customer = new Customer();
        ObjectConvertUtil.objectCopy(customer, customerReq);
        customer.setCreatedTime(new Date());
        customer.setModifiedTime(new Date());
        // 设置密码
        customer.setPassword(CryptographyUtil.cherishSha1(customer.getPassword()));
        return save(customer);
    }

    @Transactional
    public Customer register(CustomerRegisterReq customerRegisterReq) {
        log.info("【注册会员】 {}", customerRegisterReq);
        if (exist(customerRegisterReq.getUsername())) {
            throw new ServiceException(ErrorCode.ERROR_CODE_400, "账号已存在");
        }
        Customer customer = new Customer();
        ObjectConvertUtil.objectCopy(customer, customerRegisterReq);
        customer.setCreatedTime(new Date());
        customer.setModifiedTime(new Date());
        // 设置密码
        customer.setPassword(CryptographyUtil.cherishSha1(customer.getPassword()));
        return this.save(customer);
    }

}
