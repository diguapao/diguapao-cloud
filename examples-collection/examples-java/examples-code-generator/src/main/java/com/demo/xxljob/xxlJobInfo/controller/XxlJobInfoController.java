package com.demo.xxljob.xxlJobInfo.controller;

import cn.hutool.core.collection.CollUtil;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoDTO;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoPageDTO;
import com.demo.xxljob.xxlJobInfo.service.XxlJobInfoService;
import com.demo.xxljob.xxlJobInfo.vo.XxlJobInfoVO;
import com.demo.common.core.util.R;
import com.demo.common.core.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 调度任务信息 控制层
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(description = "调度任务信息 ", name = "调度任务信息 管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class XxlJobInfoController {

    @Resource
    private XxlJobInfoService xxljobinfoService;

    /**
     * 获调度任务信息 分页列表
     *
     * @return {@link com.demo.xxljob.xxlJobInfo.vo.XxlJobInfoVO }
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @GetMapping("/xxljobinfo/{id}")
    public XxlJobInfoVO getXxlJobInfo(@PathVariable Integer id) {
        return xxljobinfoService.getXxlJobInfoById(id);
    }

    /**
     * 获调度任务信息 分页列表
     *
     * @return {@link com.demo.common.core.util.R<com.demo.common.core.vo.PageResult> }
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @GetMapping("/page")
    @Operation(summary = "获取园区区域分页列表")
    public R<PageResult<XxlJobInfoVO>> getXxlJobInfoPage(XxlJobInfoPageDTO dto) {
        PageResult<XxlJobInfoVO> pageResult = xxljobinfoService.getXxlJobInfoPage(dto);
        if (Objects.isNull(pageResult) || CollUtil.isEmpty(pageResult.getList())) {
            return R.ok(PageResult.empty());
        }
        return R.ok(pageResult);
    }

    /**
     * 获取调度任务信息 列表方法
     *
     * @return {@link com.demo.common.core.util.R<List>}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @Operation(summary = "获取调度任务信息 列表")
    @GetMapping(value = "/xxljobinfo/list")
    public R<List<XxlJobInfoVO>> list() {
        return R.ok(xxljobinfoService.getList());
    }

    /**
     * 新增调度任务信息 
     *
     * @return {@link com.demo.common.core.util.R<String>}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @Operation(summary = "新增调度任务信息 ")
    @GetMapping(value = "/xxljobinfo/add")
    public R<String> add(XxlJobInfoDTO dto) {
        return R.ok(String.valueOf(xxljobinfoService.save(dto)));
    }

    /**
     * 修改调度任务信息 
     *
     * @return {@link R<String>}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @Operation(summary = "修改调度任务信息 ")
    @GetMapping(value = "/xxljobinfo/edit")
    public R<String> edit(XxlJobInfoDTO dto) {
        return R.ok(String.valueOf(xxljobinfoService.update(dto)));
    }

    /**
     * 删除调度任务信息 
     *
     * @return {@link R<Boolean>}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    @Operation(summary = "删除调度任务信息 ")
    @DeleteMapping(value = "/xxljobinfo/(id)")
    public R<Boolean> delete(@PathVariable Integer id) {
        return R.ok(xxljobinfoService.delete(id));
    }
}