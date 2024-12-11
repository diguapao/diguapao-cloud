package com.csair.sc.device.carVehicleAccessRecord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordQueryDTO;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordDTO;
import com.csair.sc.device.carVehicleAccessRecord.entity.CarVehicleAccessRecord;
import com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO;
import com.csair.sc.common.core.vo.PageResult;
import com.csair.sc.device.carVehicleAccessRecord.mapper.CarVehicleAccessRecordMapper;
import com.csair.sc.device.carVehicleAccessRecord.service.CarVehicleAccessRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.Objects;
import com.google.common.collect.Lists;

/**
 * 车辆出入记录 Service 接口实现类
 *
 * @author LiPiao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Slf4j
@Service
public class CarVehicleAccessRecordServiceImpl extends ServiceImpl<CarVehicleAccessRecordMapper, CarVehicleAccessRecord> implements CarVehicleAccessRecordService {

    @Override
    public CarVehicleAccessRecordVO getCarVehicleAccessRecordById(Long id) {
        CarVehicleAccessRecord carVehicleAccessRecord = getById(id);
        if(Objects.isNull(carVehicleAccessRecord )){
            return  null;
        }
        return BeanUtil.copyProperties(carVehicleAccessRecord , CarVehicleAccessRecordVO.class);
    }

    @Override
    public List<CarVehicleAccessRecordVO> getByIds(Set<Long> ids) {
        List<CarVehicleAccessRecord> carVehicleAccessRecords = list(new LambdaQueryWrapper<CarVehicleAccessRecord>().in(CarVehicleAccessRecord::getId, ids));
        if (CollectionUtil.isEmpty(carVehicleAccessRecords)) {
            return Lists.newArrayList();
        }
        return BeanUtil.copyToList(carVehicleAccessRecords, CarVehicleAccessRecordVO.class);
    }

    @Override
    public PageResult<CarVehicleAccessRecordVO> getCarVehicleAccessRecordPage(CarVehicleAccessRecordQueryDTO dto) {
        if (Objects.isNull(dto)) {
            dto = new CarVehicleAccessRecordQueryDTO();
        }
        // 页码 + 数量
        Page<CarVehicleAccessRecord> page = new Page<>(dto.getPageNo(), dto.getPageSize());
        baseMapper.selectPage(page, new LambdaQueryWrapper<CarVehicleAccessRecord>(BeanUtil.copyProperties(dto, CarVehicleAccessRecord.class)));
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResult<>(Lists.newArrayList(), 0L);
        }
        // 转换返回
        return new PageResult<>(BeanUtil.copyToList(page.getRecords(), CarVehicleAccessRecordVO.class), page.getTotal());
    }

    @Override
    public List<CarVehicleAccessRecordVO> getList(CarVehicleAccessRecordQueryDTO dto) {
        List<CarVehicleAccessRecord> el = null;
        if (Objects.nonNull(dto)) {
            el = list(new LambdaQueryWrapper<CarVehicleAccessRecord>(BeanUtil.copyProperties(dto, CarVehicleAccessRecord.class)).last("LIMIT " + IService.DEFAULT_BATCH_SIZE));
        } else {
            el = list();
        }
        if (CollectionUtil.isEmpty(el)) {
            return Lists.newArrayList();
        }
        return BeanUtil.copyToList(el, CarVehicleAccessRecordVO.class);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long save(CarVehicleAccessRecordDTO dto) {
        if (Objects.isNull(dto)) {
            throw new RuntimeException("保存数据不能为空");
        }
        CarVehicleAccessRecord carVehicleAccessRecord = new CarVehicleAccessRecord();
        BeanUtils.copyProperties(dto, carVehicleAccessRecord);
        baseMapper.insert(carVehicleAccessRecord);
        return carVehicleAccessRecord.getId();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long update(CarVehicleAccessRecordDTO dto) {
        if (Objects.isNull(dto)) {
            throw new RuntimeException("修改数据不能为空");
        }
        if (Objects.isNull(dto.getId())) {
            throw new RuntimeException("修改数据主键不能为空");
        }
        CarVehicleAccessRecord carVehicleAccessRecord = new CarVehicleAccessRecord();
        BeanUtils.copyProperties(dto, carVehicleAccessRecord);
        this.updateById(carVehicleAccessRecord);
        return carVehicleAccessRecord.getId();
    }

    @Override
    public Boolean delete(Long id) {
        return removeById(id);
    }

}