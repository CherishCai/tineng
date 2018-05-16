
package cn.cherish.tineng.service;

import cn.cherish.tineng.common.enums.Gender;
import cn.cherish.tineng.dal.entity.Physical;
import cn.cherish.tineng.dal.repository.IBaseDAO;
import cn.cherish.tineng.dal.repository.PhysicalDAO;
import cn.cherish.tineng.util.ObjectConvertUtil;
import cn.cherish.tineng.util.PhysicalUtils;
import cn.cherish.tineng.web.dto.PhysicalDTO;
import cn.cherish.tineng.web.dto.PhysicalScore;
import cn.cherish.tineng.web.req.BasicSearchReq;
import cn.cherish.tineng.web.req.PhysicalReq;
import cn.cherish.tineng.web.req.PhysicalSearchReq;
import org.springframework.beans.BeanUtils;
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
public class PhysicalService extends ABaseService<Physical, Long> {

    private final PhysicalDAO physicalDAO;

    @Autowired
    public PhysicalService(PhysicalDAO physicalDAO) {
        this.physicalDAO = physicalDAO;
    }

    @Override
    protected IBaseDAO<Physical, Long> getEntityDAO() {
        return physicalDAO;
    }

    public Physical findByNickname(String nickname) {
        return physicalDAO.findByNickname(nickname);
    }

    public Physical findByUsername(String username) {
        return physicalDAO.findByUsername(username);
    }

    public boolean exist(String username) {
        return physicalDAO.findByUsername(username) != null;
    }

    public Long getCount() {
        return physicalDAO.count();
    }

    @Transactional
    public Physical updateByReq(PhysicalReq physicalReq) {
        Physical physical = new Physical();

        Long id = physicalReq.getId();
        Physical byId = null;
        if (id != null) {
            byId = findById(id);
        }
        if (byId == null) {
            physical.setCreatedTime(new Date());
        }else {
            BeanUtils.copyProperties(byId, physical);
        }
        BeanUtils.copyProperties(physicalReq, physical);

        physical.setModifiedTime(new Date());


        PhysicalScore physicalScore = PhysicalUtils.getScore(physical);
        physical.setScore(physicalScore.getScore());
        physical.setPass(physicalScore.getPass());

        log.info("physical updateData, ", physical);
        return update(physical);
    }

    @Transactional
    public Physical saveByReq(PhysicalReq physicalReq) {
        if (exist(physicalReq.getUsername())) {
            return null;
        }
        return updateByReq(physicalReq);
    }

    public Page<PhysicalDTO> findAll(PhysicalSearchReq physicalSearchReq, BasicSearchReq basicSearchReq) {

        int pageNumber = basicSearchReq.getStartIndex() / basicSearchReq.getPageSize() + 1;
        PageRequest pageRequest = super.buildPageRequest(pageNumber, basicSearchReq.getPageSize());

        //除了分页条件没有特定搜索条件的，为了缓存count
        if (ObjectConvertUtil.objectFieldIsBlank(physicalSearchReq)){
            log.debug("没有特定搜索条件的");
            List<Physical> physicalList = physicalDAO.listAllPaged(pageRequest);
            List<PhysicalDTO> physicalDTOList = physicalList.stream().map(this::toDTO).collect(Collectors.toList());

            //为了计算总数使用缓存，加快搜索速度
            Long count = getCount();
            return new PageImpl<>(physicalDTOList, pageRequest, count);
        }

        //有了其它搜索条件
        Page<Physical> physicalPage = super.findAllBySearchParams(
                buildSearchParams(physicalSearchReq), pageNumber, basicSearchReq.getPageSize());

        return physicalPage.map(this::toDTO);
    }

    public PhysicalDTO toDTO(Physical source) {
        if (source == null) {
            return null;
        }
        PhysicalDTO physicalDTO = new PhysicalDTO();
        ObjectConvertUtil.objectCopy(physicalDTO, source);
        physicalDTO.setGenderStr(Gender.desc(physicalDTO.getGender()));

        // 分数
        PhysicalScore physicalScore = PhysicalUtils.getScore(source);
        physicalDTO.setPhysicalScore(physicalScore);

        physicalDTO.setScore(physicalScore.getScore());
        physicalDTO.setPass(physicalScore.getPass());

        return physicalDTO;
    }

}
