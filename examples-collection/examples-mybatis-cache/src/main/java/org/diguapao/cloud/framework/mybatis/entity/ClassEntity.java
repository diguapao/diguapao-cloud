package org.diguapao.cloud.framework.mybatis.entity;

import java.io.Serializable;

/**
 * 班级
 *
 * @author DiGuaPao
 * @version 2024.10.21
 * @since 2024-10-21 15:29:16
 */
public class ClassEntity implements Serializable {

    private int classId;

    private String className;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Class{");
        sb.append("classId=").append(classId);
        sb.append(", className='").append(className).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
