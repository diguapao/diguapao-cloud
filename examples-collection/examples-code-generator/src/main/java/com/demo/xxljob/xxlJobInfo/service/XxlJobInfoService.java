package com.demo.xxljob.xxlJobInfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoPageDTO;
import com.demo.xxljob.xxlJobInfo.api.dto.XxlJobInfoDTO;
import com.demo.xxljob.xxlJobInfo.entity.XxlJobInfo;
import com.demo.xxljob.xxlJobInfo.vo.XxlJobInfoVO;
import com.demo.common.core.vo.PageResult;

import java.util.List;

/**
 * 调度任务信息 Service 接口类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
 */
public interface XxlJobInfoService extends IService<XxlJobInfo> {

    /**
     * 获调度任务信息 分页列表
     *
     * @return {@link com.demo.xxljob.xxlJobInfo.vo.XxlJobInfoVO }
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    XxlJobInfoVO getXxlJobInfoById(Integer id);

    /**
     * 获调度任务信息 分页列表
     *
     * @return {@link com.demo.common.core.vo.PageResult<com.demo.xxljob.xxlJobInfo.XxlJobInfoVO> }
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    PageResult<XxlJobInfoVO> getXxlJobInfoPage(XxlJobInfoPageDTO dto);

    /**
     * 获取列表
     *
     * @return {@link List<XxlJobInfoVO>}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    List<XxlJobInfoVO> getList();

    /**
     * 保存
     *
     * @param XxlJobInfoDTO 调度任务信息 传送对象
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    Integer save(XxlJobInfoDTO XxlJobInfoDTO);

    /**
     * 更新
     *
     * @param XxlJobInfoDTO 调度任务信息 传送对象
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    Integer update(XxlJobInfoDTO XxlJobInfoDTO);

    /**
     * 删除
     *
     * @param id 调度任务信息 ID
     * @return {@link Long}
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    Boolean delete(Integer id);

}