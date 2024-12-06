package org.diguapao.cloud.framework.spring.aop.exception;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分布式锁异常
 *
 * @author DiGuaPao
 * @version 2024.10.22
 * @since 2024-10-22 08:39:35
 */
@Slf4j
public class DistributedLockException extends RuntimeException {
    public DistributedLockException(String message, Object... params) {
        super(buildMsg(message, params));
    }

    private static String buildMsg(String message, Object[] params) {
        List<String> paramList;
        String formatMsg = message.replaceAll("\\{}", "%s");
        if (ArrayUtil.isNotEmpty(params)) {
            paramList = Arrays.stream(params).map(param -> {
                if (param instanceof Throwable) {
                    Throwable throwable = (Throwable) param;
                    log.error(throwable.getMessage(), throwable);
                    return null;
                } else if (Objects.nonNull(param) && param instanceof Collection && CollectionUtils.isNotEmpty((Collection<?>) param)) {
                    String paramStr = param.toString();
                    if (paramStr.startsWith(StrUtil.BRACKET_START)) {
                        paramStr = paramStr.replace(StrUtil.BRACKET_START, StrUtil.EMPTY);
                    }
                    if (paramStr.endsWith(StrUtil.BRACKET_END)) {
                        paramStr = paramStr.replace(StrUtil.BRACKET_END, StrUtil.EMPTY);
                    }
                    return paramStr;
                } else if (Objects.nonNull(param) && param instanceof Map && MapUtils.isNotEmpty((Map<?, ?>) param)) {
                    String paramStr = param.toString();
                    if (paramStr.startsWith(StrUtil.DELIM_START)) {
                        paramStr = paramStr.replace(StrUtil.DELIM_START, StrUtil.EMPTY);
                    }
                    if (paramStr.endsWith(StrUtil.DELIM_END)) {
                        paramStr = paramStr.replace(StrUtil.DELIM_END, StrUtil.EMPTY);
                    }
                    return paramStr;
                }
                return param instanceof String ? (String) param : param.toString();
            }).filter(Objects::nonNull).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(paramList)) {
                params = paramList.toArray();
            }
        }
        Object[] o = ArrayUtil.isNotEmpty(params) ? params : null;
        String msg = String.format(formatMsg, o);
        return msg;
    }

}
