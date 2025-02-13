package com.csair.sc.device.carVehicleAccessRecord.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordDTO;
import com.csair.sc.device.carVehicleAccessRecord.api.dto.CarVehicleAccessRecordQueryDTO;
import com.csair.sc.device.carVehicleAccessRecord.service.CarVehicleAccessRecordService;
import com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO;
import com.csair.sc.common.core.util.R;
import com.csair.sc.common.core.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

/**
 * 车辆出入记录控制层
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Slf4j
@Validated
@RestController
// @RequiredArgsConstructor
// @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@Tag(description = "车辆出入记录", name = "车辆出入记录管理")
public class CarVehicleAccessRecordController {

    @Resource
    private CarVehicleAccessRecordService carVehicleAccessRecordService;

    /**
     * 获取车辆出入记录
     *
     * @param id 主键
     * @return {@link com.csair.sc.common.core.util.R<com.csair.sc.device.carVehicleAccessRecord.vo.CarVehicleAccessRecordVO>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @Operation(summary = "获取车辆出入记录")
    @Parameter(name = "id", description = "主键")
    @GetMapping("/carVehicleAccessRecord/{id}")
    public R<CarVehicleAccessRecordVO> getCarVehicleAccessRecord(@PathVariable("id") Long id) {
        return R.ok(carVehicleAccessRecordService.getCarVehicleAccessRecordById(id));
    }

    /**
     * 获取车辆出入记录分页列表
     *
     * @param dto 车辆出入记录查询传输对象
     * @return {@link com.csair.sc.common.core.util.R<com.csair.sc.common.core.vo.PageResult>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @PostMapping("/carVehicleAccessRecord/page")
    @Operation(summary = "获取车辆出入记录分页列表")
    public R<PageResult<CarVehicleAccessRecordVO>> getCarVehicleAccessRecordPage(@Valid @RequestBody(required = false) CarVehicleAccessRecordQueryDTO dto) {
        PageResult<CarVehicleAccessRecordVO> pageResult = carVehicleAccessRecordService.getCarVehicleAccessRecordPage(dto);
        if (Objects.isNull(pageResult) || CollUtil.isEmpty(pageResult.getList())) {
            return R.ok(PageResult.empty());
        }
        return R.ok(pageResult);
    }

    /**
     * 获取车辆出入记录列表方法
     *
     * @param dto 车辆出入记录查询传输对象
     * @return {@link com.csair.sc.common.core.util.R<List>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @Operation(summary = "获取车辆出入记录列表")
    @PostMapping(value = "/carVehicleAccessRecord/list")
    public R<List<CarVehicleAccessRecordVO>> list(@Valid @RequestBody(required = false) CarVehicleAccessRecordQueryDTO dto) {
        return R.ok(carVehicleAccessRecordService.getList(dto));
    }

    /**
     * 新增车辆出入记录
     *
     * @param dto 车辆出入记录传输对象
     * @return {@link com.csair.sc.common.core.util.R<String>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @Operation(summary = "新增车辆出入记录")
    @PostMapping(value = "/carVehicleAccessRecord")
    public R<String> add(@Valid @RequestBody CarVehicleAccessRecordDTO dto) {
        return R.ok(String.valueOf(carVehicleAccessRecordService.save(dto)));
    }

    /**
     * 编辑车辆出入记录
     *
     * @param dto 车辆出入记录传输对象
     * @return {@link com.csair.sc.common.core.util.R<String>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @Operation(summary = "编辑车辆出入记录")
    @PutMapping(value = "/carVehicleAccessRecord")
    public R<String> edit(@Valid @RequestBody CarVehicleAccessRecordDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new RuntimeException("编辑数据主键不能为空");
        }
        Assert.notNull(carVehicleAccessRecordService.getById(dto.getId()), () -> new RuntimeException("被编辑的数据不存在，主键ID：" + dto.getId()));
        return R.ok(String.valueOf(carVehicleAccessRecordService.update(dto)));
    }

    /**
     * 删除车辆出入记录
     *
     * @param id 主键
     * @return {@link com.csair.sc.common.core.util.R<Boolean>}
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    @Operation(summary = "删除车辆出入记录")
    @Parameter(name = "id", description = "主键")
    @DeleteMapping(value = "/carVehicleAccessRecord/{id}")
    public R<Boolean> delete(@PathVariable("id") Long id) {
        return R.ok(carVehicleAccessRecordService.delete(id));
    }
}