package com.csair.sc.device.carVehicleAccessRecord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordQueryDTO;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordDTO;
import com.csair.sc.device.carVehicleAccessRecord.entity.CarVehicleAccessRecord;
import com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO;
import com.csair.sc.common.core.vo.PageResult;

import java.util.List;
import java.util.Set;

/**
 * 车辆出入记录 Service 接口类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
public interface CarVehicleAccessRecordService extends IService<CarVehicleAccessRecord> {

    /**
     * 根据ID获取实体信息
     *
     * @param id 主键
     * @return {@link com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    CarVehicleAccessRecordVO getCarVehicleAccessRecordById(Long id);

    /**
     * 根据ID集获取实体信息
     *
     * @param ids 实体主键集
     * @return {@link com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    List<CarVehicleAccessRecordVO> getByIds(Set<Long> ids);

    /**
     * 获取实体信息分页列表
     *
     * @param dto 车辆出入记录查询传输对象
     * @return {@link com.csair.sc.common.core.vo.PageResult<com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    PageResult<CarVehicleAccessRecordVO> getCarVehicleAccessRecordPage(CarVehicleAccessRecordQueryDTO dto);

    /**
     * 获取列表
     *
     * @param dto 车辆出入记录查询传输对象
     * @return {@link List<CarVehicleAccessRecordVO>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    List<CarVehicleAccessRecordVO> getList(CarVehicleAccessRecordQueryDTO dto);

    /**
     * 保存
     *
     * @param dto 车辆出入记录传输对象
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    Long save(CarVehicleAccessRecordDTO dto);

    /**
     * 编辑
     *
     * @param dto 车辆出入记录传输对象
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    Long update(CarVehicleAccessRecordDTO dto);

    /**
     * 删除
     *
     * @param id 主键
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    Boolean delete(Long id);

}