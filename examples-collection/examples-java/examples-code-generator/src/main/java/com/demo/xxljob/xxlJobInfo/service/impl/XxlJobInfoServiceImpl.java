package com.demo.xxljob.xxlJobInfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoPageDTO;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoDTO;
import com.demo.xxljob.xxlJobInfo.entity.XxlJobInfo;
import com.demo.xxljob.xxlJobInfo.vo.XxlJobInfoVO;
import com.demo.common.core.vo.PageResult;
import com.demo.xxljob.xxlJobInfo.mapper.XxlJobInfoMapper;
import com.demo.xxljob.xxlJobInfo.service.XxlJobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 调度任务信息 Service 接口实现类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
 */
@Slf4j
@Service
public class  XxlJobInfoServiceImpl extends ServiceImpl<XxlJobInfoMapper, XxlJobInfo> implements XxlJobInfoService {

    @Override
    public XxlJobInfoVO getXxlJobInfoById(Integer id) {
        return BeanUtil.copyProperties(getById(id),XxlJobInfoVO.class);
    }

    @Override
    public PageResult<XxlJobInfoVO> getXxlJobInfoPage(XxlJobInfoPageDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        // 页码 + 数量
        Page<XxlJobInfo> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        baseMapper.selectPage(page, new LambdaQueryWrapper<XxlJobInfo>(BeanUtil.copyProperties(dto, XxlJobInfo.class)));
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return null;
        }
        // 转换返回
        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), XxlJobInfoVO.class), page.getTotal());
    }

    @Override
    public List<XxlJobInfoVO> getList() {
        List<XxlJobInfo> el = list();
        if (CollectionUtil.isEmpty(el)) {
            return null;
        }
        return BeanUtil.copyToList(el, XxlJobInfoVO.class);
    }

    @Override
    public Integer save(XxlJobInfoDTO xxljobinfoDTO) {
        XxlJobInfo xxljobinfo = new XxlJobInfo();
        BeanUtils.copyProperties(xxljobinfoDTO, xxljobinfo);
        baseMapper.insert(xxljobinfo);
        return xxljobinfo.getId();
    }

    @Override
    public Integer update(XxlJobInfoDTO xxljobinfoDTO) {
        XxlJobInfo xxljobinfo = new XxlJobInfo();
        BeanUtils.copyProperties(xxljobinfoDTO, xxljobinfo);
        this.updateById(xxljobinfo);
        return xxljobinfo.getId();
    }

    @Override
    public Boolean delete(Integer id) {
        return removeById(id);
    }

}